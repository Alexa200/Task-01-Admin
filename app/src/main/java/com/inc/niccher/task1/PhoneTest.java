package com.inc.niccher.task1;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

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

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class PhoneTest extends AppCompatActivity {

    Button sel,sendvid;
    TextView lin,metas;
    VideoView mplay;

    private String uploadId ;

    int reqcod=4,coun=0;

    Uri uri_vid;

    private ProgressDialog pds2;

    FirebaseAuth mAuth;
    FirebaseUser userf;
    DatabaseReference dref,drefup;
    FirebaseDatabase fdbas;

    private StorageReference mStorageRef;
    private StorageTask mUploadTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activ_phonetest);

        sel=findViewById(R.id.buttonSEL);
        sendvid=findViewById(R.id.buttonUo);

        lin=findViewById(R.id.links);
        metas=findViewById(R.id.metas);
        mplay=findViewById(R.id.viuvid);

        mAuth = FirebaseAuth.getInstance();
        userf=mAuth.getCurrentUser();
        fdbas= FirebaseDatabase.getInstance();

        mStorageRef = FirebaseStorage.getInstance().getReference("VehicleVideos");
        dref = FirebaseDatabase.getInstance().getReference("Posteds");

        uploadId= dref.push().getKey();
        Log.e("Ids ", "dref.push().getKey()  "+uploadId );

        sel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Picka();
            }
        });

        sendvid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pds2=new ProgressDialog(PhoneTest.this);

                uploadFile();
            }
        });
    }

    private void Picka(){
        Intent intent = new Intent();
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Video"),reqcod);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == reqcod) {
                lin.setText( (String)getPath(data.getData()));
                mplay.setVideoPath((String)getPath(data.getData()));
                mplay.start();

                MediaController controller = new MediaController(this);
                controller.setMediaPlayer(mplay);
                controller.setAnchorView(mplay);
                mplay.setMediaController(controller);

                MediaMetadataRetriever metaRetriver;
                metaRetriver = new MediaMetadataRetriever();
                metaRetriver.setDataSource((String)getPath(data.getData()));

                Long secon=TimeUnit.MILLISECONDS.toSeconds(Long.parseLong(metaRetriver.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)));

                String met="Bitrate -> "+metaRetriver.extractMetadata(MediaMetadataRetriever.METADATA_KEY_BITRATE)+"\n"
                            +"Duration -> "+metaRetriver.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)+"\n"
                            +"Has Video -> "+metaRetriver.extractMetadata(MediaMetadataRetriever.METADATA_KEY_HAS_VIDEO)+"\n"
                            +"Has Audio -> "+metaRetriver.extractMetadata(MediaMetadataRetriever.METADATA_KEY_HAS_AUDIO)+"\n"
                        +"Video Height -> "+metaRetriver.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT)+"\n"
                        +"Video Width -> "+metaRetriver.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH)+"\n"
                        +"Capture Frame Rate -> "+metaRetriver.extractMetadata(MediaMetadataRetriever.METADATA_KEY_CAPTURE_FRAMERATE)+"\n";

                metas.setText(met+"\n Duration -> "+secon);

                uri_vid=data.getData();

            }
        }
    }

    public String getPath(Uri uri) {
        String[] projection = { MediaStore.Video.Media.DATA };
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } else
            return null;
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void uploadFile() {
        pds2.setTitle("Uploading Video Resource");
        pds2.setMessage("Please Wait");
        pds2.show();

        if (uri_vid != null) {
            final StorageReference fileReference = mStorageRef.child("CVideo-"+System.currentTimeMillis()+"."+getFileExtension(uri_vid));

            mUploadTask = fileReference.putFile(uri_vid)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            Task<Uri> result = taskSnapshot.getStorage().getDownloadUrl();
                            result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {

                                    drefup= FirebaseDatabase.getInstance().getReference("Posteds/Videos/");

                                    HashMap<String , Object> hasm3=new HashMap<String, Object>();

                                    hasm3.put("cVideo"+coun,uri.toString());
                                    coun=coun+1;

                                    uri_vid=null;

                                    drefup.updateChildren(hasm3).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            pds2.dismiss();
                                            Toast.makeText(PhoneTest.this, "Add Image URL addOnFailureListener\n"+e.getMessage(), Toast.LENGTH_LONG).show();
                                        }
                                    });

                                    pds2.dismiss();

                                    Toast.makeText(PhoneTest.this, "Upload Succesfull", Toast.LENGTH_SHORT).show();

                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            pds2.dismiss();
                            Toast.makeText(PhoneTest.this, "Upload task addOnFailureListener\n"+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            //pds.setProgress((int) progress);
                        }
                    });
        } else {
            pds2.dismiss();
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
        }
    }
}
