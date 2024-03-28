package com.example.spotify2340.ui.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.content.SharedPreferences;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.spotify2340.R;

public class CreateNewAccountActivity extends AppCompatActivity {
    EditText usernameEditText;
    EditText passwordEditText;
    Button connect_to_spotify;
    Button backButton;
    public static final String SHARED_PREFS = "shared_prefs";
    public static final String USERNAME_KEY = "username_key";
    public static final String PASSWORD_KEY = "password_key";
    SharedPreferences sharedpreferences;
    String username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_new_account);

        //Instantiate buttons and fields
        connect_to_spotify = findViewById(R.id.connect_spotify_create_new_account);
        backButton = findViewById(R.id.back_button_create_new_account);
        usernameEditText = findViewById(R.id.username_create_new_account);
        passwordEditText = findViewById(R.id.password_create_new_account);

        // in shared prefs inside get string method
        // we are passing key value as USERNAME_KEY and
        // default value is
        // set to null if not present.
        username = sharedpreferences.getString("USERNAME_KEY", null);
        password = sharedpreferences.getString("PASSWORD_KEY", null);

        connect_to_spotify.setOnClickListener((v) -> {
            if (TextUtils.isEmpty(usernameEditText.getText().toString()) && TextUtils.isEmpty(passwordEditText.getText().toString())) {
                // this method will call when email and password fields are empty.
                Toast.makeText(CreateNewAccountActivity.this, "Please enter a Username and Password", Toast.LENGTH_SHORT).show();
            } else {
                SharedPreferences.Editor editor = sharedpreferences.edit();

                // below two lines will put values for
                // email and password in shared preferences.
                editor.putString(USERNAME_KEY, usernameEditText.getText().toString());
                editor.putString(PASSWORD_KEY, passwordEditText.getText().toString());

                // to save our data with key and value.
                editor.apply();
            }
        });

        backButton.setOnClickListener((v) -> {
            // Redirect user to CreateNewAccountActivity
            startActivity(new Intent(CreateNewAccountActivity.this, LoginPageActivity.class));
        });
    }

}