package com.inc.niccher.task1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class NewDetails extends AppCompatActivity {

    private Button cret;
    private ImageView bak;
    private EditText realeml,realpwd;
    private ProgressDialog pds;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activ_userdetail);

        cret=findViewById(R.id.user_cret);
        bak=findViewById(R.id.sback);

        realeml=findViewById(R.id.user_setusr);
        realpwd=findViewById(R.id.user_setpwd);

        pds=new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();

        cret.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent it = new Intent(NewDetails.this,UserDefine.class);
                //startActivity(it);
                RegisterUser();
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

    private void RegisterUser() {
        String email = realeml.getText().toString().trim();
        String password = realpwd.getText().toString().trim();

        if (email.isEmpty()) {
            realeml.setError("Email is required");
            realeml.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            realeml.setError("Please enter a valid email");
            realeml.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            realpwd.setError("Password is required");
            realpwd.requestFocus();
            return;
        }

        if (password.length() < 6) {
            realpwd.setError("Minimum length of password should be 6");
            realpwd.requestFocus();
            return;
        }

        pds.setTitle("Creating Account");
        pds.setMessage("Please wait");
        pds.show();

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                pds.dismiss();
                if (task.isSuccessful()) {
                    finish();
                    startActivity(new Intent(NewDetails.this, UserDefine.class));
                } else {

                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                        Toast.makeText(getApplicationContext(), "Email has been registered", Toast.LENGTH_LONG).show();

                    } else {
                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }

                }
            }
        });

    }
}
