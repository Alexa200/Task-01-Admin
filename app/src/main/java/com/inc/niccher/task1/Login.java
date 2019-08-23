package com.inc.niccher.task1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class Login extends AppCompatActivity {

    Button sig,log;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activ_login);

        sig=findViewById(R.id.user_newaccnt);
        log=findViewById(R.id.user_login);

        sig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(Login.this,NewDetails.class);
                startActivity(it);
            }
        });

        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(Login.this,Casa.class);
                startActivity(it);
                finish();
            }
        });
    }
}
