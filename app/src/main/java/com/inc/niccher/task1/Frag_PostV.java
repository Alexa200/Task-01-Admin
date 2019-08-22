package com.inc.niccher.task1;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class Frag_PostV extends Fragment {

    RecyclerView recyl;
    ActionBar acb;

    public Frag_PostV() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View solv= inflater.inflate(R.layout.frag_postv, container, false);
        //acb.setTitle("All Users");

        /*recyl=solv.findViewById(R.id.user_list);
        recyl.setHasFixedSize(true);
        recyl.setLayoutManager(new LinearLayoutManager(getActivity()));*/

        return solv;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int cas=item.getItemId();
        if (cas== R.id.act_logout){
        }

        return super.onOptionsItemSelected(item);
    }
}

