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
import android.view.MenuItem;
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

public class Add_CarVideo extends AppCompatActivity {

    Button sel,sendvid;
    TextView lin,metas,dete;
    VideoView mplay;

    Intent ids;

    int lengt,width,heigh;
    boolean vidpres=false;

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
        setContentView(R.layout.add_carvideo);

        getSupportActionBar().setTitle("Upload a Video");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        sel=findViewById(R.id.buttonSEL);
        sendvid=findViewById(R.id.buttonUo);

        lin=findViewById(R.id.links);
        metas=findViewById(R.id.metas);
        dete=findViewById(R.id.deta);
        mplay=findViewById(R.id.viuvid);

        mAuth = FirebaseAuth.getInstance();
        userf=mAuth.getCurrentUser();
        fdbas= FirebaseDatabase.getInstance();

        mStorageRef = FirebaseStorage.getInstance().getReference("VehicleVideos");
        dref = FirebaseDatabase.getInstance().getReference("Posteds");

        ids=getIntent();

        Setta();

        sel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Picka();
            }
        });

        sendvid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pds2=new ProgressDialog(Add_CarVideo.this);

                uploadFile();
            }
        });
    }

    private void Setta(){
        uploadId=ids.getStringExtra("ChildNode");

        Log.e("Keys : ", "Id Video->"+uploadId);
        Log.e("Keys : ", "Id Video1->"+ids.getStringExtra("ChildNode"));

        try {
            if (ids.getStringExtra("ChildNode")==null){
                finish();
                startActivity(new Intent(Add_CarVideo.this, Add_Car.class));
            }else {
                uploadId=ids.getStringExtra("ChildNode");
            }
        }catch (Exception ex){
            //Toast.makeText(this, "Setta Error -> "+ex, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        uploadId=ids.getStringExtra("ChildNode");
        int idd=item.getItemId();
        if (idd==android.R.id.home){
            Intent adcar=new Intent(Add_CarVideo.this,Add_Car.class);
            adcar.putExtra("keyid",uploadId);
            startActivity(adcar);
        }
        return super.onOptionsItemSelected(item);
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

                lengt= (Integer.parseInt(metaRetriver.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)))/1000;
                width=(Integer.parseInt(metaRetriver.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH)));
                heigh=(Integer.parseInt(metaRetriver.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT)));

                if (metaRetriver.extractMetadata(MediaMetadataRetriever.METADATA_KEY_HAS_VIDEO)=="yes"){
                    vidpres=true;
                }

                metas.setText(met+"\n Duration -> "+secon);

                Allow();

                uri_vid=data.getData();

            }
        }
    }

    private void Allow(){
        vidpres=true;
        if (vidpres){
            if (lengt <30 && width > 1200 && heigh > 710){
                dete.setText("Proceeds");
            }else {
                if (lengt > 30){
                    dete.setText("Time Limit exceeded");
                }
                if (width < 1200){
                    dete.setText("Width requirement not met");
                }
                if (heigh < 700){
                    dete.setText("Height requirement not met");
                }
            }
        }else{
            dete.setText("Selected File has no video Stream");
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

                                    drefup= FirebaseDatabase.getInstance().getReference("Posteds/Vehicles/"+uploadId);

                                    HashMap<String , Object> hasm3=new HashMap<String, Object>();

                                    hasm3.put("cVid"+coun,uri.toString());
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
                                            Toast.makeText(Add_CarVideo.this, "Adding Video URL addOnFailureListener\n"+e.getMessage(), Toast.LENGTH_LONG).show();
                                        }
                                    });

                                    pds2.dismiss();

                                    Toast.makeText(Add_CarVideo.this, "Video Upload Succesfull", Toast.LENGTH_SHORT).show();

                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            pds2.dismiss();
                            Toast.makeText(Add_CarVideo.this, "Upload task addOnFailureListener\n"+e.getMessage(), Toast.LENGTH_SHORT).show();
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
