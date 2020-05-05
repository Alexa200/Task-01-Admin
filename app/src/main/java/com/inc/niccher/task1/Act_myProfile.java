package com.inc.niccher.task1;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

public class Act_myProfile extends AppCompatActivity {

    ImageView usapic,usacover;
    TextView usaname,usaphone,usaemail,popclos;
    Button usachat;
    Intent getdef;

    ImageView popimg;
    Dialog myDialog;

    FirebaseAuth mAuth;
    FirebaseUser userf;
    DatabaseReference dref1,mDatabaseRef;
    StorageTask mUploadTask;
    StorageReference mStorageRef;

    ProgressDialog pds;
    String targeteml,targetuid,usaprof,usacova;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_myprofile);

        getSupportActionBar().setTitle("My Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getdef=getIntent();

        mAuth= FirebaseAuth.getInstance();

        userf=mAuth.getCurrentUser();

        dref1= FirebaseDatabase.getInstance().getReference("Task1Admin").child(userf.getUid());
        dref1.keepSynced(true);

        GetState();

        usapic=findViewById(R.id.com_usaprof);

        usaname=findViewById(R.id.com_name);
        usaphone=findViewById(R.id.com_phone);
        usaemail=findViewById(R.id.com_email);


        pds=new ProgressDialog(this);
        myDialog=new Dialog(this);
        Findame();

        usapic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopingProf();
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int idd=item.getItemId();
        if (idd==android.R.id.home){
            finish();
            startActivity(new Intent(Act_myProfile.this,Casa.class));
        }
        return super.onOptionsItemSelected(item);
    }

    private void GetState(){
        FirebaseUser fuse=mAuth.getCurrentUser();
        if (fuse!=null){}
        else {
            startActivity(new Intent(this,Login.class));
            finish();
        }
    }

    private void Findame(){
        pds.setMessage("Fetching User Data, Please Wait");
        pds.show();
        DatabaseReference dref4=FirebaseDatabase.getInstance().getReference("Task1Admin").child(userf.getUid());
        dref4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                usaname.setText((String) dataSnapshot.child("aUsername").getValue());
                usaphone.setText((String) dataSnapshot.child("aPhone").getValue());
                usaemail.setText((String) dataSnapshot.child("aEmail").getValue());
                usaprof= (String) dataSnapshot.child("aProfile").getValue();

                try {
                    Picasso.get().load((String) dataSnapshot.child("aProfile").getValue()).into(usapic);
                }catch (Exception ex){
                    Toast.makeText(Act_myProfile.this, "Error \n"+ex.getMessage(), Toast.LENGTH_SHORT).show();
                }

                pds.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                pds.dismiss();
                Toast.makeText(Act_myProfile.this, "Error \n"+databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void PopingProf() {
        myDialog.setContentView(R.layout.part_poppa);
        popclos=(TextView) myDialog.findViewById(R.id.popclose);
        popimg= (ImageView) myDialog.findViewById(R.id.popimg);
        popclos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        Picasso.get().load(usaprof).networkPolicy(NetworkPolicy.OFFLINE).into(popimg, new Callback() {
            @Override
            public void onSuccess() {}

            @Override
            public void onError(Exception e) {
                Picasso.get().load(usaprof).into(popimg);
            }
        });
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
    }

}
