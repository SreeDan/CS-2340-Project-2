package com.example.spotify2340.ui.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.spotify2340.R;
import com.example.spotify2340.ui.spotifyauth.ConnectToSpotifyActivity;

public class LoginPageActivity extends AppCompatActivity {
    Button createNewAccountButton;
    Button connectToSpotifyButton;
    Button logInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        //Initializing buttons
        createNewAccountButton = findViewById(R.id.signup_button_login);
        connectToSpotifyButton = findViewById(R.id.spotify_button_login);
        logInButton = findViewById(R.id.login_button_login);

        createNewAccountButton.setOnClickListener((v) -> {
            // Redirect user to CreateNewAccountActivity
            Intent intent = new Intent(LoginPageActivity.this, CreateNewAccountActivity.class);
            startActivity(intent);
        });

        connectToSpotifyButton.setOnClickListener((v) -> {
            // Redirect user to ConnectToSpotifyActivity
            Intent intent = new Intent(LoginPageActivity.this, ConnectToSpotifyActivity.class);
            startActivity(intent);
        });

        logInButton.setOnClickListener((v) -> {
            // Redirect user to LoginActivity
            Intent intent = new Intent(LoginPageActivity.this, LoginActivity.class);
            startActivity(intent);
        });
    }

    //Create method for CreateNewAccountActivity

    // Code for starting AccountFragment (later replace) instead of login
//    @Override
//    protected void onStart() {
//        if (email != null && password != null) {
//            Intent i = new Intent(LoginActivity.this, AccountFragment.class);
//            startActivity(i);
//        }
//    }
}