package com.example.spotify2340;

import android.os.Bundle;
import android.view.View;
import android.view.Menu;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.spotify2340.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_account, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}




//package com.example.spotify2340;
//
//import androidx.appcompat.app.AppCompatActivity;
//import android.os.Bundle;
//import android.content.Intent;
//import android.net.Uri;
//import android.util.Log;
//import android.widget.Button;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.spotify.sdk.android.auth.AuthorizationClient;
//import com.spotify.sdk.android.auth.AuthorizationRequest;
//import com.spotify.sdk.android.auth.AuthorizationResponse;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.io.IOException;
//
//import okhttp3.Call;
//import okhttp3.Callback;
//import okhttp3.OkHttpClient;
//import okhttp3.Request;
//import okhttp3.Response;
//
//
//public class MainActivity extends AppCompatActivity {
//
//    public static final String CLIENT_ID = "ffb6d0b9973d4ca9bb946f66370fe166";
//    public static final String REDIRECT_URI = "com.example.spotify2340://auth";
//
//    public static final int AUTH_TOKEN_REQUEST_CODE = 0;
//    public static final int AUTH_CODE_REQUEST_CODE = 1;
//
//    private final OkHttpClient mOkHttpClient = new OkHttpClient();
//    private String mAccessToken, mAccessCode;
//    private Call mCall;
//
//    private TextView tokenTextView, codeTextView, profileTextView, playlistTextView;
//    private String userId;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        // Initialize the views
//        tokenTextView = (TextView) findViewById(R.id.token_text_view);
//        codeTextView = (TextView) findViewById(R.id.code_text_view);
//        profileTextView = (TextView) findViewById(R.id.profile_text_view);
//        playlistTextView = findViewById(R.id.playlist_text_view);
//
//        // Initialize the buttons
//        Button tokenBtn = (Button) findViewById(R.id.token_btn);
//        Button codeBtn = (Button) findViewById(R.id.code_btn);
//        Button profileBtn = (Button) findViewById(R.id.profile_btn);
//        Button playlistBtn = findViewById(R.id.playlist_btn);
//
//        // Set the click listeners for the buttons
//
//        tokenBtn.setOnClickListener((v) -> {
//            getToken();
//        });
//
//        codeBtn.setOnClickListener((v) -> {
//            getCode();
//        });
//
//        profileBtn.setOnClickListener((v) -> {
//            onGetUserProfileClicked();
//        });
//
//        playlistBtn.setOnClickListener((v) -> {
//            onGetUserPlaylistClicked();
//        });
//
//    }
//
//    /**
//     * Get token from Spotify
//     * This method will open the Spotify login activity and get the token
//     * What is token?
//     * https://developer.spotify.com/documentation/general/guides/authorization-guide/
//     */
//    public void getToken() {
//        final AuthorizationRequest request = getAuthenticationRequest(AuthorizationResponse.Type.TOKEN);
//        Log.i("getToken()", request.toString());
//        AuthorizationClient.openLoginActivity(MainActivity.this, AUTH_TOKEN_REQUEST_CODE, request);
//        Log.i("getToken()", "openLoginActivity done");
//        Log.i("getToken()", MainActivity.this.toString());
//    }
//
//    /**
//     * Get code from Spotify
//     * This method will open the Spotify login activity and get the code
//     * What is code?
//     * https://developer.spotify.com/documentation/general/guides/authorization-guide/
//     */
//    public void getCode() {
//        final AuthorizationRequest request = getAuthenticationRequest(AuthorizationResponse.Type.CODE);
//        AuthorizationClient.openLoginActivity(MainActivity.this, AUTH_CODE_REQUEST_CODE, request);
//    }
//
//
//    /**
//     * When the app leaves this activity to momentarily get a token/code, this function
//     * fetches the result of that external activity to get the response from Spotify
//     */
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        Log.i("onActivityResult", "Received information");
//        super.onActivityResult(requestCode, resultCode, data);
//        final AuthorizationResponse response = AuthorizationClient.getResponse(resultCode, data);
//
//        // Check which request code is present (if any)
//        if (AUTH_TOKEN_REQUEST_CODE == requestCode) {
//            mAccessToken = response.getAccessToken();
//            setTextAsync(mAccessToken, tokenTextView);
//
//        } else if (AUTH_CODE_REQUEST_CODE == requestCode) {
//            mAccessCode = response.getCode();
//            setTextAsync(mAccessCode, codeTextView);
//        }
//    }
//
//    /**
//     * Get user profile
//     * This method will get the user profile using the token
//     */
//    public void onGetUserProfileClicked() {
//        if (mAccessToken == null) {
//            Toast.makeText(this, "You need to get an access token first!", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        // Create a request to get the user profile
//        final Request request = new Request.Builder()
//                .url("https://api.spotify.com/v1/me")
//                .addHeader("Authorization", "Bearer " + mAccessToken)
//                .build();
//
//        cancelCall();
//        mCall = mOkHttpClient.newCall(request);
//
//        mCall.enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                Log.d("HTTP", "Failed to fetch data: " + e);
//                runOnUiThread(() -> {
//                    Toast.makeText(MainActivity.this, "Failed to fetch data, watch Logcat for more details", Toast.LENGTH_SHORT).show();
//                });
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                try {
//                    final JSONObject jsonObject = new JSONObject(response.body().string());
//                    setTextAsync(jsonObject.toString(3), profileTextView);
//                    userId = jsonObject.getString("id");
//                } catch (JSONException e) {
//                    Log.d("JSON", "Failed to parse data: " + e);
//                    runOnUiThread(() -> {
//                        Toast.makeText(MainActivity.this, "Failed to parse data, watch Logcat for more details", Toast.LENGTH_SHORT).show();
//                    });
//                }
//            }
//        });
//    }
//
//    public void onGetUserPlaylistClicked() {
//        if (mAccessToken == null) {
//            Toast.makeText(this, "You need to get an access token first!", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        if (userId == null) {
//            Toast.makeText(this, "You need to collect the userID first!", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        // Create a request to get the user profile
//        final Request request = new Request.Builder()
//                .url("https://api.spotify.com/v1/users/" + userId + "/playlists")
//                .addHeader("Authorization", "Bearer " + mAccessToken)
//                .build();
//
//        cancelCall();
//        mCall = mOkHttpClient.newCall(request);
//
//        mCall.enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                Log.d("HTTP", "Failed to fetch data: " + e);
//                Toast.makeText(MainActivity.this, "Failed to fetch data, watch Logcat for more details",
//                        Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                try {
//                    final JSONObject jsonObject = new JSONObject(response.body().string());
//                    setTextAsync(jsonObject.toString(3), playlistTextView);
//                } catch (JSONException e) {
//                    Log.d("JSON", "Failed to parse data: " + e);
//                    Toast.makeText(MainActivity.this, "Failed to parse data, watch Logcat for more details",
//                            Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//    }
//
//    /**
//     * Creates a UI thread to update a TextView in the background
//     * Reduces UI latency and makes the system perform more consistently
//     *
//     * @param text the text to set
//     * @param textView TextView object to update
//     */
//    private void setTextAsync(final String text, TextView textView) {
//        runOnUiThread(() -> textView.setText(text));
//    }
//
//    /**
//     * Get authentication request
//     *
//     * @param type the type of the request
//     * @return the authentication request
//     */
//    private AuthorizationRequest getAuthenticationRequest(AuthorizationResponse.Type type) {
//        return new AuthorizationRequest.Builder(CLIENT_ID, type, getRedirectUri().toString())
//                .setShowDialog(false)
//                .setScopes(new String[] { "user-read-email", "user-read-private", "user-follow-read", "playlist-read-private"}) // <--- Change the scope of your requested token here
//                .setCampaign("your-campaign-token")
//                .build();
//    }
//
//    /**
//     * Gets the redirect Uri for Spotify
//     *
//     * @return redirect Uri object
//     */
//    private Uri getRedirectUri() {
//        return Uri.parse(REDIRECT_URI);
//    }
//
//    private void cancelCall() {
//        if (mCall != null) {
//            mCall.cancel();
//        }
//    }
//
//    @Override
//    protected void onDestroy() {
//        cancelCall();
//        AuthorizationClient.stopLoginActivity(this, AUTH_TOKEN_REQUEST_CODE); // Stop the login activity
//        super.onDestroy();
//    }
//}