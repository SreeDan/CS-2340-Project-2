package com.example.spotify2340;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class LoginPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    // Code for starting AccountFragment (later replace) instead of login
//    @Override
//    protected void onStart() {
//        if (email != null && password != null) {
//            Intent i = new Intent(LoginActivity.this, AccountFragment.class);
//            startActivity(i);
//        }
//    }
}