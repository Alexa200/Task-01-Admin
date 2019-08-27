package com.inc.niccher.task1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;


public class PostCarD extends AppCompatActivity {

    ImageView vImg0;

    TextView vMaker,vBody,vModel,vYear,vMileage,vvondition,vEngine,vvolor,vTransmision,vInterior,vFuel,vDesv,vKey,vTime;

    ProgressDialog pds2;


    private String pat;

    Uri uri_image;

    Intent getta;

    FirebaseAuth mAuth;
    FirebaseUser userf;
    DatabaseReference dref;
    FirebaseDatabase fdbas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.postcard);

        getSupportActionBar().setTitle("Post Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        pds2=new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();
        userf=mAuth.getCurrentUser();
        fdbas= FirebaseDatabase.getInstance();

        getta=getIntent();

        if (getta.getStringExtra("PostUUIDCode")==null){
            Toast.makeText(this, "No Cross Data", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(PostCarD.this, Add_Car.class));
        }else {
            pat=getta.getStringExtra("PostUUIDCode");
        }

        vMaker=findViewById(R.id.disp_maker);
        vBody=findViewById(R.id.disp_body);
        vYear=findViewById(R.id.disp_year);
        vMileage=findViewById(R.id.disp_mileage);
        vvondition=findViewById(R.id.disp_condit);
        vEngine=findViewById(R.id.disp_engine);
        vvolor=findViewById(R.id.disp_color);
        vTransmision=findViewById(R.id.disp_trans);
        vInterior=findViewById(R.id.disp_inter);
        vFuel=findViewById(R.id.disp_fuel);
        vDesv=findViewById(R.id.disp_desc);
        vTime=findViewById(R.id.disp_time);

        vImg0=findViewById(R.id.disp_imgs);

        LoadPost();
    }

    private void LoadPost(){
        DatabaseReference dref3 = FirebaseDatabase.getInstance().getReference("Posteds/"+userf.getUid()+"/Vehicles/"+pat);
        dref3.keepSynced(true);

        try {
            dref3.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    vMaker.setText((String) dataSnapshot.child("cMaker").getValue());
                    vBody.setText((String) dataSnapshot.child("cBody").getValue());
                    vYear.setText((String) dataSnapshot.child("cYear").getValue());
                    vMileage.setText((String) dataSnapshot.child("cMileage").getValue());
                    vvondition.setText((String) dataSnapshot.child("cCondition").getValue());
                    vEngine.setText((String) dataSnapshot.child("cEngine").getValue());
                    vvolor.setText((String) dataSnapshot.child("cColor").getValue());
                    vTransmision.setText((String) dataSnapshot.child("cTransmision").getValue());
                    vInterior.setText((String) dataSnapshot.child("cInterior").getValue());
                    vFuel.setText((String) dataSnapshot.child("cFuel").getValue());
                    vDesv.setText((String) dataSnapshot.child("cDesc").getValue());
                    vTime.setText((String) dataSnapshot.child("cTime").getValue());

                    try {
                        Picasso.get().load((String) dataSnapshot.child("cImg0").getValue()).into(vImg0);

                    }catch (Exception ex){
                        Picasso.get().load(R.drawable.ic_defuser).into(vImg0);
                        Toast.makeText(PostCarD.this, "Picasso.get() Error"+ex, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }catch (Exception ex){
            Log.e("Casa ", "LoadUsa: \n" +ex.getMessage());
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int idd=item.getItemId();
        if (idd==android.R.id.home){
            finish();
            startActivity(new Intent(PostCarD.this,Add_Car.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (mAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, Login.class));
        }
    }
}
