package com.inc.niccher.task1;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import static android.app.Activity.RESULT_OK;

//import com.theartofdev.edmodo.cropper.CropImage;
//import com.theartofdev.edmodo.cropper.CropImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class Frag_Add extends Fragment {

    Button cars,rea;
    private Toolbar mToobar;

    public Frag_Add() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragadd= inflater.inflate(R.layout.frag_additem, container, false);

        cars=fragadd.findViewById(R.id.vehi);
        rea=fragadd.findViewById(R.id.real);

        cars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Frag_AddCar fracar=new Frag_AddCar();

                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.maincontaina, fracar);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        rea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Frag_AddEstate fraest=new Frag_AddEstate();

                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.maincontaina, fraest);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        return fragadd;
    }

    @Override
    public void onCreate( Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

}
