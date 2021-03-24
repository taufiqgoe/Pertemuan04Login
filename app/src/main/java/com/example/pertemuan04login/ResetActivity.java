package com.example.pertemuan04login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.annotations.NotNull;

import java.util.regex.Pattern;

public class ResetActivity extends AppCompatActivity {
    Button reset;
    EditText email;
    ProgressBar progressBar;
    FirebaseAuth mFIrebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset);

        reset = findViewById(R.id.reset);
        email = findViewById(R.id.resetEmail);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);
        mFIrebaseAuth = FirebaseAuth.getInstance();

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();
            }


        });
    }
    private void resetPassword() {
        String getEmail = email.getText().toString().trim();

        if (getEmail.isEmpty()) {
            email.setError("Masukkan Email!");
            email.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(getEmail).matches()) {
            email.setError("Masukkan Email yang valid!");
            email.requestFocus();
            return;
        }

        mFIrebaseAuth.sendPasswordResetEmail(getEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    progressBar.setVisibility(View.VISIBLE);
                    Toast.makeText(ResetActivity.this, "Periksa Email kamu!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(ResetActivity.this, "Ada yang salah, Coba kembali!", Toast.LENGTH_LONG).show();
                }
                progressBar.setVisibility(View.INVISIBLE);
            }
        });

    }
}