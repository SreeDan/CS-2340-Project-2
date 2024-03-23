package com.example.spotify2340.ui.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

import com.example.spotify2340.R;

public class CreateNewAccountActivity extends AppCompatActivity {
    EditText usernameEditText;
    EditText passwordEditText;
    Button connect_to_spotify;
    Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_new_account);

        //Instantiate buttons and fields
        connect_to_spotify = findViewById(R.id.connect_spotify_create_new_account);
        backButton = findViewById(R.id.back_button_create_new_account);
        usernameEditText = findViewById(R.id.username_create_new_account);
        passwordEditText = findViewById(R.id.password_create_new_account);

        backButton.setOnClickListener((v) -> {
            // Redirect user to CreateNewAccountActivity
            startActivity(new Intent(CreateNewAccountActivity.this, LoginPageActivity.class));
        });
    }

}