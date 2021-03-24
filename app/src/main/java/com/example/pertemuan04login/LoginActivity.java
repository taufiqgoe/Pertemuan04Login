package com.example.pertemuan04login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    EditText emailid,pass;
    Button signin, reset;
    TextView tvdaftar;
    FirebaseAuth mFirebaseAuth;
    CheckBox rememberMe;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    int isChecked = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mFirebaseAuth = FirebaseAuth.getInstance();
        emailid = findViewById(R.id.emailInput);
        pass = findViewById(R.id.passInput);
        signin = findViewById(R.id.button);
        tvdaftar = findViewById(R.id.daftar);
        reset = findViewById(R.id.resetPass);
        rememberMe = findViewById(R.id.rememberMe);

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
                if (mFirebaseUser != null){
                    Toast.makeText(LoginActivity.this, "Anda telah masuk", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(i);
                }
            }
        };

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailid.getText().toString();
                String password = pass.getText().toString();
                if(email.isEmpty()){
                    emailid.setText("Tolong masukkan email");
                    emailid.requestFocus();
                }
                else if(password.isEmpty()){
                    pass.setText("Tolong masukkan password");
                    pass.requestFocus();
                }
                else if(email.isEmpty() && password.isEmpty()){
                    Toast.makeText(LoginActivity.this, "Isi yang kosong", Toast.LENGTH_SHORT).show();
                }
                else if(!(email.isEmpty() && password.isEmpty())){
                    mFirebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(LoginActivity.this,
                            new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(!task.isSuccessful()){
                                        Toast.makeText(LoginActivity.this, "Login error", Toast.LENGTH_SHORT).show();
                                    }
                                    else{
                                        Intent i = new Intent(LoginActivity.this,HomeActivity.class);
                                        if (rememberMe.isChecked()) {
                                            i.putExtra("isChecked", 1);
                                        } else {
                                            i.putExtra("isChecked", 0);
                                        }
                                        startActivity(i);
                                    }
                                }
                            });
                }
                else {
                    Toast.makeText(LoginActivity.this, "Terjadi kesalahan", Toast.LENGTH_SHORT).show();
                }
            }
        });

        tvdaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent dftr = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(dftr);
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(LoginActivity.this, ResetActivity.class);
                startActivity(a);
            }
        });

    }


    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }
}