package com.example.spotify2340.ui.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.content.SharedPreferences;
import android.widget.Toast;


import com.example.spotify2340.MainActivity;
import com.example.spotify2340.R;

public class LoginActivity extends AppCompatActivity {
    Button login_button;
    Button backButton;
    EditText usernameEditText;
    EditText passwordEditText;
    public static final String SHARED_PREFS = "shared_prefs";
    public static final String USERNAME_KEY = "username_key";
    public static final String PASSWORD_KEY = "password_key";
    SharedPreferences sharedpreferences;
    String username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Instantiate buttons and fields
        login_button = findViewById(R.id.button_login);
        backButton = findViewById(R.id.back_button_login);
        usernameEditText = findViewById(R.id.username_login);
        passwordEditText = findViewById(R.id.password_login);

        backButton.setOnClickListener((v) -> {
            // Redirect user to CreateNewAccountActivity
            startActivity(new Intent(LoginActivity.this, LoginPageActivity.class));
        });

        // getting the data which is stored in shared preferences.
        sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);

        // in shared prefs inside get string method
        // we are passing key value as USERNAME_KEY and
        // default value is
        // set to null if not present.
        username = sharedpreferences.getString("USERNAME_KEY", null);
        password = sharedpreferences.getString("PASSWORD_KEY", null);

        login_button.setOnClickListener((v) -> {
            if (TextUtils.isEmpty(usernameEditText.getText().toString()) && TextUtils.isEmpty(passwordEditText.getText().toString())) {
                // this method will call when email and password fields are empty.
                Toast.makeText(LoginActivity.this, "Please enter a Username and Password", Toast.LENGTH_SHORT).show();
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
    }
}