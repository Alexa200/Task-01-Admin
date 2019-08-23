package com.inc.niccher.task1;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

    //FirebaseAuth mAuth;
    //FirebaseUser userf;

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

        //mAuth= FirebaseAuth.getInstance();

        //userf=mAuth.getCurrentUser();

        GetState();

        usapic=findViewById(R.id.com_usaprof);

        usaname=findViewById(R.id.com_name);
        usaphone=findViewById(R.id.com_phone);
        usaemail=findViewById(R.id.com_email);


        pds=new ProgressDialog(this);
        myDialog=new Dialog(this);
        //Findame();

        usapic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //PopingProf();
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
        /*FirebaseUser fuse=mAuth.getCurrentUser();
        if (fuse!=null){}
        else {
            startActivity(new Intent(this,UserLogin.class));
            finish();
        }*/
    }

    /*private void Findame(){
        pds.setMessage("Fetching User Data, Please Wait");
        pds.show();
        DatabaseReference dref4=FirebaseDatabase.getInstance().getReference("GretsaUsers").child(targetuid);
        dref4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                usaname.setText((String) dataSnapshot.child("gUsername").getValue());
                usaphone.setText((String) dataSnapshot.child("gPhone").getValue());
                usaemail.setText((String) dataSnapshot.child("gEmail").getValue());
                usaprof= (String) dataSnapshot.child("gProfile").getValue();
                usacova= (String) dataSnapshot.child("gCover").getValue();

                try {
                    Picasso.get().load((String) dataSnapshot.child("gProfile").getValue()).into(usapic);
                    Picasso.get().load((String) dataSnapshot.child("gCover").getValue()).into(usacover);
                }catch (Exception ex){
                    Toast.makeText(Act_Usas.this, "Error \n"+ex.getMessage(), Toast.LENGTH_SHORT).show();
                }

                pds.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                pds.dismiss();
                Toast.makeText(Act_Usas.this, "Error \n"+databaseError.getMessage(), Toast.LENGTH_SHORT).show();
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

    private void PopingCova() {
        myDialog.setContentView(R.layout.part_poppa);
        popclos=(TextView) myDialog.findViewById(R.id.popclose);
        popimg= (ImageView) myDialog.findViewById(R.id.popimg);
        popclos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        Picasso.get().load(usacova).networkPolicy(NetworkPolicy.OFFLINE).into(popimg, new Callback() {
            @Override
            public void onSuccess() {}

            @Override
            public void onError(Exception e) {
                Picasso.get().load(usacova).into(popimg);
            }
        });
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
    }*/
}
