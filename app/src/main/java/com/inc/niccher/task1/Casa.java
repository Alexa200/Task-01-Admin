package com.inc.niccher.task1;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

public class Casa extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private NavigationView naviw;
    private DrawerLayout drwlay;
    RecyclerView recyvw;
    private Toolbar mToobar;
    ActionBarDrawerToggle togo;
    TextView usr_name,usr_email,popclos;
    ImageView usr_img,popimg;
    Dialog myDialog;

    String gEmail,gUsername,gProfile,had;

    Intent targ=null;

    FirebaseAuth mAuth;
    FirebaseUser userf;
    DatabaseReference dref1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_casa);

        mToobar= (Toolbar) findViewById(R.id.mainpgbar);
        setSupportActionBar(mToobar);
        //getSupportActionBar().setTitle("Posts");

        myDialog = new Dialog(this);

        drwlay= (DrawerLayout) findViewById(R.id.drawa1);
        togo=new ActionBarDrawerToggle(Casa.this,drwlay, R.string.dopen , R.string.dopen );
        drwlay.addDrawerListener(togo);
        togo.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        naviw= (NavigationView) findViewById(R.id.nav_viu);

        mAuth= FirebaseAuth.getInstance();
        userf=mAuth.getCurrentUser();

        View viu=naviw.inflateHeaderView(R.layout.nav_headah);

        naviw.setNavigationItemSelectedListener(Casa.this);

        usr_email= viu.findViewById(R.id.nav_usreml);
        usr_name=viu.findViewById(R.id.nav_usrname);
        usr_img=viu.findViewById(R.id.nav_usrimg);

        usr_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {}
        });

        targ=getIntent();

        GetTarget();
        LoadUsa();
    }

    private void GetTarget() {
        try {
            had=targ.getStringExtra("PostUUIDCode")+"--";
            Log.e("getStringExtra", "GetTarget: "+had );
            if (had.equals("Posts--")){
                Fragment frags=null;
                getSupportActionBar().setTitle("Posts");
                frags=new Frag_PostV();
                FragmentManager frman0=getSupportFragmentManager();
                frman0.beginTransaction().replace(R.id.maincontaina,frags).commit();

            }else {
                Fragment frags=null;
                getSupportActionBar().setTitle("Dashboard");
                frags=new Frag_Home();
                FragmentManager frman0=getSupportFragmentManager();
                frman0.beginTransaction().replace(R.id.maincontaina,frags).commit();
            }
            had=null;
        }catch (Exception es){
            Fragment frags=null;
            getSupportActionBar().setTitle("Dashboard");
            frags=new Frag_Home();
            FragmentManager frman0=getSupportFragmentManager();
            frman0.beginTransaction().replace(R.id.maincontaina,frags).commit();

            Log.e("getStringError", "GetTarget: "+es );
        }
    }

    private void LoadUsa() {
        //Toast.makeText(this, "Uuid"+userf.getUid(), Toast.LENGTH_LONG).show();
        try {
            dref1= FirebaseDatabase.getInstance().getReference("Task1Admin").child(userf.getUid());
            dref1.keepSynced(true);

            dref1.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    //aUid, aEmail, aUsername, aPhone, aProfile, aProfilethumb;
                    gUsername= (String) dataSnapshot.child("aUsername").getValue();
                    gEmail= (String) dataSnapshot.child("aEmail").getValue();
                    gProfile=dataSnapshot.child("aProfile").getValue().toString();
                    usr_name.setText(gUsername);
                    usr_email.setText(gEmail);

                    try {
                        Picasso.get().load(gProfile).into(usr_img);

                    }catch (Exception ex){
                        Picasso.get().load(R.drawable.ic_defuser).into(usr_img);
                        Toast.makeText(Casa.this, "Picasso.get() Error"+ex, Toast.LENGTH_SHORT).show();
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
        if (togo.onOptionsItemSelected(item)){
            return true;
        }

        int id = item.getItemId();
        if (id == R.id.act_settings) {
            Toast.makeText(this, "Settings Pressed", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawa1);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_more, menu);
        return true;
    }

    @Override
    public boolean onNavigationItemSelected( MenuItem item) {
        int id = item.getItemId();

        Fragment frags=null;

        switch (item.getItemId()){
            case R.id.nav_postv:{
                frags=new Frag_PostV();
                getSupportActionBar().setTitle("Posted Estates");
                FragmentManager frman2=getSupportFragmentManager();
                frman2.beginTransaction().replace(R.id.maincontaina,frags).commit();
                break;
            }case R.id.nav_postr:{
                //startActivity(new Intent(Casa.this, Add_Estate.class));
                frags=new Frag_PostE();
                getSupportActionBar().setTitle("Posted Estates");
                FragmentManager frman3=getSupportFragmentManager();
                frman3.beginTransaction().replace(R.id.maincontaina,frags).commit();
                break;
            }case R.id.nav_add:{
                frags=new Frag_Add();
                getSupportActionBar().setTitle("Add An Element");
                FragmentManager frman3=getSupportFragmentManager();
                frman3.beginTransaction().replace(R.id.maincontaina,frags).commit();
                break;
            }case R.id.nav_prof:{
                startActivity(new Intent(Casa.this, Act_myProfile.class));
                //Toast.makeText(this, "This Activity under active progress", Toast.LENGTH_SHORT).show();
                break;
            }case R.id.nav_settings:{
                startActivity(new Intent(Casa.this, PostCarSearch.class));
                //Toast.makeText(this, "This Activity under active progress", Toast.LENGTH_SHORT).show();
                break;
            }case R.id.nav_logout: {
                Intent it = new Intent(Casa.this,Login.class);
                startActivity(it);
                finish();
                break;
            }case R.id.nav_exit:{
                Intent intt=new Intent(Intent.ACTION_MAIN);
                intt.addCategory(Intent.CATEGORY_HOME);
                intt.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intt);

                finish();
                System.exit(0);
                break;
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawa1);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
