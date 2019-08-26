package com.inc.niccher.task1;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

/**
 * Created by niccher on 11/06/19.
 */

public class Task1 extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        Picasso.Builder bul=new Picasso.Builder(this);
        bul.downloader(new OkHttp3Downloader(this,Integer.MAX_VALUE));
        Picasso buil=bul.build();
        buil.setIndicatorsEnabled(true);
        buil.setLoggingEnabled(true);
        Picasso.setSingletonInstance(buil);
    }
}
