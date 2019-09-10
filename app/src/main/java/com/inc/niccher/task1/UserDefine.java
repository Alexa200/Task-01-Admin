package com.inc.niccher.task1;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import id.zelory.compressor.Compressor;

public class UserDefine extends AppCompatActivity {

    EditText emlR,pwdR;
    TextView sback;
    ImageView imgsel,imgshow;
    ProgressDialog pds2;

    Uri uri_image;
    Uri uri_thums;

    FirebaseAuth mAuth;
    FirebaseUser userf;
    DatabaseReference dref;
    FirebaseDatabase fdbas;
    StorageReference stoRef,stoThumb;

    StorageTask mUploadTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userdefine);

        mAuth = FirebaseAuth.getInstance();
        userf=mAuth.getCurrentUser();
        fdbas= FirebaseDatabase.getInstance();
        stoRef = FirebaseStorage.getInstance().getReference("Task1AdminImgs");
        stoThumb = FirebaseStorage.getInstance().getReference("Task1AdminImgsThumbs");

        dref = FirebaseDatabase.getInstance().getReference("Task1Admin");

        sback = (TextView) findViewById(R.id.user_new_procid);
        imgsel = findViewById(R.id.com_imgsel);
        imgshow = findViewById(R.id.com_image);
        //prg=findViewById(R.id.progressBarR);

        emlR= (EditText) findViewById(R.id.user_set_usr);
        pwdR= (EditText) findViewById(R.id.user_set_phone);

        pds2=new ProgressDialog(this);

        imgsel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setCropShape(CropImageView.CropShape.OVAL)
                        .setBorderLineColor(Color.RED)
                        .setBorderCornerColor(Color.BLUE)
                        .setGuidelinesColor(Color.GREEN)
                        .setBorderLineThickness(2)
                        .start(UserDefine.this);
            }
        });

        sback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mUploadTask != null && mUploadTask.isInProgress()) {
                    Toast.makeText(UserDefine.this, "Upload in progress", Toast.LENGTH_SHORT).show();
                } else {
                    Verrif();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                uri_image = result.getUri();
                uri_thums = result.getUri();
                Picasso.get().load(uri_image).into(imgshow);
                Verrif();
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                String er=error.getMessage().toString();
                Toast.makeText(this, ""+er, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void Verrif() {
        final String usern = emlR.getText().toString().trim();
        final String phone = pwdR.getText().toString().trim();

        if (usern.isEmpty()) {
            emlR.setError("Username is required");
            emlR.requestFocus();
            return;
        }

        if (phone.isEmpty()) {
            pwdR.setError("Phone is required");
            pwdR.requestFocus();
            return;
        }

        if (!Patterns.PHONE.matcher(phone).matches()) {
            emlR.setError("Please enter a valid Phone Number");
            emlR.requestFocus();
            return;
        }

        if (phone.length() < 10) {
            pwdR.setError("Minimum length of a Phone should be 10");
            pwdR.requestFocus();
            return;
        }

        if (uri_image != null) {

            pds2.setMessage("Please wait");
            pds2.show();

            StorageReference fileReference = stoRef.child(userf.getUid()+"." + "jpeg");

            mUploadTask = fileReference.putFile(uri_image).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    Task<Uri> result = taskSnapshot.getStorage().getDownloadUrl();
                    result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            UserConfig upload = new UserConfig(userf.getUid(),userf.getEmail(),usern,phone,uri.toString(),uri.toString());
                            dref.child(userf.getUid()).setValue(upload);

                            startActivity(new Intent(UserDefine.this, Casa.class));
                            finish();
                        }
                    });
                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    pds2.dismiss();
                    //prg.setVisibility(View.GONE);
                    Toast.makeText(UserDefine.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            })
            .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    pds2.dismiss();
                    //prg.setVisibility(View.VISIBLE);
                    double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                    //mProgressBar.setProgress((int) progress);
                }
            });

        } else {
            Toast.makeText(this, "No associative Profile image is selected", Toast.LENGTH_SHORT).show();
        }
    }

}
