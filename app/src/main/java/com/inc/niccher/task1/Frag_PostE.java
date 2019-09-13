package com.inc.niccher.task1;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Frag_PostE extends Fragment {

    ProgressDialog pd2;

    FirebaseAuth mAuth;
    FirebaseUser userf;

    RecyclerView postlist;
    List<Mod_E> modpost;
    Adpter_Estate adpost;
    Context relcon;

    public Frag_PostE() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View pose= inflater.inflate(R.layout.frag_poste, container, false);

        mAuth= FirebaseAuth.getInstance();
        userf=mAuth.getCurrentUser();

        pd2=new ProgressDialog(getActivity());

        postlist=pose.findViewById(R.id.postrecycla);
        postlist.setHasFixedSize(true);

        LinearLayoutManager lima=new LinearLayoutManager(getActivity());
        lima.setReverseLayout(true);
        lima.setStackFromEnd(true);
        postlist.setLayoutManager(lima);

        modpost=new ArrayList<>();

        FetcpostE();

        return pose;
    }

    private void FetcpostE() {
        //"+userf.getUid()+"
        DatabaseReference dref3 = FirebaseDatabase.getInstance().getReference("Posteds/Estates");
        dref3.keepSynced(true);

        dref3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot3) {
                modpost.clear();
                for (DataSnapshot dataSnapshot2:dataSnapshot3.getChildren()){
                    Mod_E poss=dataSnapshot2.getValue(Mod_E.class);

                    modpost.add(poss);

                    adpost=new Adpter_Estate(getContext(),modpost);
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

