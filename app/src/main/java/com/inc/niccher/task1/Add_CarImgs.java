package com.inc.niccher.task1;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;


public class Add_CarImgs extends AppCompatActivity {

    private Button btnSubmit;

    ImageView imgsel,imgshow;
    ProgressBar prg;
    ProgressDialog pds;

    Uri uri_image;
    Uri uri_thums;
    Bitmap bit_thum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_carimgs);

        getSupportActionBar().setTitle("Upload Images");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        imgsel = findViewById(R.id.com_imagesel);

        pds=new ProgressDialog(this);

        imgsel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setCropShape(CropImageView.CropShape.RECTANGLE)
                        .setBorderLineColor(Color.RED)
                        .setBorderCornerColor(Color.BLUE)
                        .setGuidelinesColor(Color.GREEN)
                        .setBorderLineThickness(2)
                        .start(Add_CarImgs.this);
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
                Picasso.get().load(uri_image).into(imgsel);//imgshow
                //Verrif();
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int idd=item.getItemId();
        if (idd==android.R.id.home){
            finish();
            startActivity(new Intent(Add_CarImgs.this,Add_Car.class));
        }
        return super.onOptionsItemSelected(item);
    }


}
