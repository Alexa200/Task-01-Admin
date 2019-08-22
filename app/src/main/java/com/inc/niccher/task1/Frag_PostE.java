package com.inc.niccher.task1;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Frag_PostE extends Fragment {

    RecyclerView recyl;

    List <String> chatedlist;

    public Frag_PostE() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View solv= inflater.inflate(R.layout.frag_poste, container, false);

        /*recyl=solv.findViewById(R.id.chat_list);
        recyl.setHasFixedSize(true);
        recyl.setLayoutManager(new LinearLayoutManager(getActivity()));
        chatedlist=new ArrayList<>();*/

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

