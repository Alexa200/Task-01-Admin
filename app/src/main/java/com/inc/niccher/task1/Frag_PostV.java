package com.inc.niccher.task1;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Frag_PostV extends Fragment {

    ProgressDialog pd2;

    FirebaseAuth mAuth;
    FirebaseUser userf;

    RecyclerView postlist;
    List<Mod_V> modpost;
    Adpter_Car adpost;
    Context relcon;

    public Frag_PostV() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View posv= inflater.inflate(R.layout.frag_postv, container, false);
        //acb.setTitle("All Users");

        mAuth= FirebaseAuth.getInstance();
        userf=mAuth.getCurrentUser();

        pd2=new ProgressDialog(getActivity());

        postlist=posv.findViewById(R.id.postrecycla);
        postlist.setHasFixedSize(true);

        LinearLayoutManager lima=new LinearLayoutManager(getActivity());
        lima.setReverseLayout(true);
        lima.setStackFromEnd(true);
        postlist.setLayoutManager(lima);

        modpost=new ArrayList<>();

        FetcpostV();

        return posv;
    }

    private void FetcpostV() {
        //"+userf.getUid()+"
        DatabaseReference dref3 = FirebaseDatabase.getInstance().getReference("Posteds/Vehicles");
        dref3.keepSynced(true);

        dref3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot3) {
                modpost.clear();
                for (DataSnapshot dataSnapshot2:dataSnapshot3.getChildren()){
                    Mod_V poss=dataSnapshot2.getValue(Mod_V.class);

                    modpost.add(poss);

                    adpost=new Adpter_Car(getContext(),modpost);
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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    /*@Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.menu_more, menu);
        super.onCreateOptionsMenu(menu, inflater);
        //super.onCreateOptionsMenu(menu, inflater);
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.act_settings) {
            Toast.makeText(getContext(), "Activity Under Active Development", Toast.LENGTH_SHORT).show();
            return true;
        }

        if (id == R.id.act_search) {
            //startActivity(new Intent(getActivity(), PostCarSearch.class));
            Toast.makeText(getContext(), "Settings Pressed", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

