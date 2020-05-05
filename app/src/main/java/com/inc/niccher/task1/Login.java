package com.inc.niccher.task1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    Button sig,log;
    ProgressDialog pds;
    EditText eml,pwd;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activ_login);

        sig=findViewById(R.id.user_newaccnt);
        log=findViewById(R.id.user_login);

        eml= (EditText) findViewById(R.id.user_logusr);
        pwd= (EditText) findViewById(R.id.user_logpwd);
        pds=new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();

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
                //startActivity(it);
                //finish();
                LogmeIN();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() != null) {
            finish();
            startActivity(new Intent(this, Casa.class));
        }else if (mAuth.getCurrentUser() == null) {
            //Toast.makeText(this, "Toasted", Toast.LENGTH_SHORT).show();;
        }
    }

    private void LogmeIN() {
        String email = eml.getText().toString().trim();
        String password = pwd.getText().toString().trim();

        if (email.isEmpty()) {
            eml.setError("Email is required");
            eml.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            eml.setError("Please enter a valid email");
            eml.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            pwd.setError("Password is required");
            pwd.requestFocus();
            return;
        }

        if (password.length() < 6) {
            pwd.setError("Minimum length of password should be 6 Characters");
            pwd.requestFocus();
            return;
        }

        pds.setMessage("Please wait");
        pds.show();

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                pds.dismiss();
                if (task.isSuccessful()) {
                    finish();
                    Intent intent = new Intent(Login.this, Casa.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
