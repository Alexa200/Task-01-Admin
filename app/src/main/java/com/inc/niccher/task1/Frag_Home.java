package com.inc.niccher.task1;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.cardview.widget.CardView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;


/**
 * A simple {@link Fragment} subclass.
 */
public class Frag_Home extends Fragment {

    CardView cvcars,cvestate,cvadcar,cvadest,cvprof;
    FirebaseAuth mAuth;
    FirebaseUser userf;
    DatabaseReference dref,drefup;
    FirebaseDatabase fdbas;

    String hexid;

    private StorageReference mStorageRef;
    private StorageTask mUploadTask;


    public Frag_Home() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fraghome= inflater.inflate(R.layout.frag_home, container, false);

        mAuth = FirebaseAuth.getInstance();
        userf=mAuth.getCurrentUser();
        fdbas= FirebaseDatabase.getInstance();

        mStorageRef = FirebaseStorage.getInstance().getReference("VehicleImgs");

        cvcars=fraghome.findViewById(R.id.cardcars);
        cvestate=fraghome.findViewById(R.id.cardestate);
        cvadcar=fraghome.findViewById(R.id.cardaddcar);
        cvadest=fraghome.findViewById(R.id.cardaddestate);
        cvprof=fraghome.findViewById(R.id.cardprof);

        cvcars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Frag_PostV fracar=new Frag_PostV();

                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.maincontaina, fracar);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        cvestate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Frag_PostE fraest=new Frag_PostE();

                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.maincontaina, fraest);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        cvadcar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Setta();
                Intent newcar=new Intent(getActivity(), Add_Car.class);
                newcar.putExtra("Kiy",hexid);
                hexid=null;
                startActivity(newcar);
            }
        });

        cvadest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Setta();
                Intent newest=new Intent(getActivity(), Add_Estate.class);
                newest.putExtra("Kiy",hexid);
                hexid=null;
                startActivity(newest);
            }
        });

        cvprof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Add_Car fracar=new Add_Car();
                startActivity(new Intent(getActivity(), Act_myProfile.class));
            }
        });


        return fraghome;
    }

    private void Setta(){
        dref = FirebaseDatabase.getInstance().getReference("Posteds");
        try {
            if (hexid.length() < 0){
                hexid= dref.push().getKey();
                //Log.e("Ids ", "dref.push().getKey() new ID Generated"+hexid );
                //Toast.makeText(getContext(), "New Key", Toast.LENGTH_SHORT).show();
            }else {
                //Log.e("Ids ", "dref.push().getKey() Old upload =new ID "+hexid );
                //Toast.makeText(getContext(), "Old Key=New Key", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception ex){
            hexid= dref.push().getKey();
            //Log.e("Ids ", "Setta error Caught "+hexid );
            //Toast.makeText(getContext(), "Setta Caught"+hexid, Toast.LENGTH_SHORT).show();
            //Toast.makeText(getContext(), "Setta Caught"+ex, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onCreate( Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

}
