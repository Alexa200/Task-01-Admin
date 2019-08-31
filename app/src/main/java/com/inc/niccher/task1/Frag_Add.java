package com.inc.niccher.task1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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
                /*Add_Car fracar=new Add_Car();

                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.maincontaina, fracar);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();*/

                startActivity(new Intent(getActivity(), Add_Car.class));
            }
        });

        rea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Add_Estate fraest=new Add_Estate();

                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.maincontaina, fraest);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();*/

                startActivity(new Intent(getActivity(), Add_Estate.class));
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
