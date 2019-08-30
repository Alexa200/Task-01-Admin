package com.inc.niccher.task1;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class PostCarSearch extends AppCompatActivity {

    ProgressDialog pd2;

    FirebaseAuth mAuth;
    FirebaseUser userf;

    RecyclerView postlist;
    List<Mod_V> modpost;
    Adpter_Car adpost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.postcarsearch);

        getSupportActionBar().setTitle("Search");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mAuth= FirebaseAuth.getInstance();
        userf=mAuth.getCurrentUser();

        pd2=new ProgressDialog(this);

        postlist=findViewById(R.id.postrecycla);
        postlist.setHasFixedSize(true);

        LinearLayoutManager lima=new LinearLayoutManager(this);
        lima.setReverseLayout(true);
        lima.setStackFromEnd(true);
        postlist.setLayoutManager(lima);

        modpost=new ArrayList<>();

        FetcpostV();
    }

    private void FetcpostV() {
        DatabaseReference dref3 = FirebaseDatabase.getInstance().getReference("Posteds/Vehicles");
        dref3.keepSynced(true);

        dref3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot3) {
                modpost.clear();
                for (DataSnapshot dataSnapshot2:dataSnapshot3.getChildren()){
                    Mod_V poss=dataSnapshot2.getValue(Mod_V.class);

                    modpost.add(poss);

                    adpost=new Adpter_Car(PostCarSearch.this,modpost);
                    postlist.setAdapter(adpost);
                }

                adpost.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int idd=item.getItemId();
        if (idd==android.R.id.home){
            finish();
            Intent pos=new Intent(PostCarSearch.this,Casa.class);
            pos.putExtra("PostUUIDCode","Posts");
            startActivity(pos);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_more, menu);

        MenuItem searchItem = menu.findItem(R.id.act_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //adapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
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
