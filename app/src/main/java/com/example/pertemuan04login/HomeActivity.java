package com.example.pertemuan04login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {

    Button btnlogout;
    FirebaseAuth mFirebasAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btnlogout = findViewById(R.id.btnLogout);

        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent b = new Intent(HomeActivity.this,MainActivity.class);
                startActivity(b);
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        Intent mIntent = getIntent();
        int intValue = mIntent.getIntExtra("isChecked", 0);

        if (intValue == 0) {
            FirebaseAuth.getInstance().signOut();
        }
    }

    //    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//
//    }
}