package com.example.pertemuan04login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    EditText emailid, pass;
    Button daftar;
    TextView masuk;
    FirebaseAuth mFIrebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    CheckBox rememberMe;

    int isChecked = 1;

    protected void onStart() {
        super.onStart();
        mFIrebaseAuth.addAuthStateListener(mAuthStateListener);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFIrebaseAuth = FirebaseAuth.getInstance();
        emailid = findViewById(R.id.emailInput);
        pass = findViewById(R.id.passInput);
        daftar = findViewById(R.id.button);
        masuk = findViewById(R.id.textView);
        rememberMe = findViewById(R.id.rememberMe);

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser = mFIrebaseAuth.getCurrentUser();
                if (mFirebaseUser != null){
                    Toast.makeText(MainActivity.this, "Anda telah masuk", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(MainActivity.this, HomeActivity.class);
                    i.putExtra("isChecked", 1);
                    startActivity(i);
                }
            }
        };

        daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailid.getText().toString();
                String pwd = pass.getText().toString();

                if (email.isEmpty()) {
                    emailid.setText("Tolong Masukkan Email");
                    emailid.requestFocus();
                } else if (pwd.isEmpty()) {
                    pass.setText("Tolong Masukkan Password");
                    pass.requestFocus();
                } else if (!(email.isEmpty() && pwd.isEmpty())) {
                    mFIrebaseAuth.createUserWithEmailAndPassword(email,pwd).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(MainActivity.this, "Pendaftaran Gagal, coba lagi", Toast.LENGTH_SHORT).show();
                            } else {
                                Intent a = new Intent(MainActivity.this, HomeActivity.class);
                                if (rememberMe.isChecked()) {
                                    a.putExtra("isChecked", 1);
                                } else {
                                    a.putExtra("isChecked", 0);
                                }
                                startActivity(a);
                            }
                        }
                    });
                }
                else {
                    Toast.makeText(MainActivity.this, "Terjadi kesalahan", Toast.LENGTH_SHORT).show();
                }
            }
        });

        masuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(a);
            }
        });


    }
}