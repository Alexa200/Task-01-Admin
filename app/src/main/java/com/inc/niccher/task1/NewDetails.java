package com.inc.niccher.task1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class NewDetails extends AppCompatActivity {

    Button cret;
    ImageView bak;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activ_userdetail);

        cret=findViewById(R.id.user_cret);
        bak=findViewById(R.id.sback);

        cret.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(NewDetails.this,NewDetails.class);
                //startActivity(it);
            }
        });

        bak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(NewDetails.this,Login.class);
                startActivity(it);
                finish();
            }
        });
    }
}
