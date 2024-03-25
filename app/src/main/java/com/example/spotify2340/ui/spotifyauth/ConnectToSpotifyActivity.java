package com.example.spotify2340.ui.spotifyauth;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.spotify2340.MainActivity;
import com.example.spotify2340.R;
import com.example.spotify2340.ui.home.AccountFragment;
import com.spotify.sdk.android.auth.AuthorizationClient;
import com.spotify.sdk.android.auth.AuthorizationRequest;
import com.spotify.sdk.android.auth.AuthorizationResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class ConnectToSpotifyActivity extends AppCompatActivity {

    public static final String CLIENT_ID = "ffb6d0b9973d4ca9bb946f66370fe166";
    public static final String REDIRECT_URI = "com.example.spotify2340://auth";

    public static final int AUTH_TOKEN_REQUEST_CODE = 0;

//    public static final int AUTH_CODE_REQUEST_CODE = 1;

    private final OkHttpClient mOkHttpClient = new OkHttpClient();
    private String mAccessToken;
    private Call mCall;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Retrieve Token
        getToken();
    }

    /**
     * Get token from Spotify
     * This method will open the Spotify login activity and get the token
     * What is token?
     * https://developer.spotify.com/documentation/general/guides/authorization-guide/
     */
    public void getToken() {
        final AuthorizationRequest request = getAuthenticationRequest(AuthorizationResponse.Type.TOKEN);
        Log.i("getToken()", request.toString());
        AuthorizationClient.openLoginActivity(ConnectToSpotifyActivity.this, AUTH_TOKEN_REQUEST_CODE, request);
        Log.i("getToken()", "openLoginActivity done");
        Log.i("getToken()", ConnectToSpotifyActivity.this.toString());
    }

//    /**
//     * Get code from Spotify
//     * This method will open the Spotify login activity and get the code
//     * What is code?
//     * https://developer.spotify.com/documentation/general/guides/authorization-guide/
//     */
//    public void getCode() {
//        final AuthorizationRequest request = getAuthenticationRequest(AuthorizationResponse.Type.CODE);
//        AuthorizationClient.openLoginActivity(ConnectToSpotifyActivity.this, AUTH_CODE_REQUEST_CODE, request);
//    }


    /**
     * When the app leaves this activity to momentarily get a token/code, this function
     * fetches the result of that external activity to get the response from Spotify
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i("onActivityResult", "Received information");
        super.onActivityResult(requestCode, resultCode, data);
        final AuthorizationResponse response = AuthorizationClient.getResponse(resultCode, data);

        // Check which request code is present (if any)
        if (AUTH_TOKEN_REQUEST_CODE == requestCode) {
            if (response.getAccessToken() == null) {
                Log.i("Access Token: ", "Access Token is null.");
            }
            mAccessToken = response.getAccessToken();
            Log.i("Access Token:", "Access Token: " + mAccessToken.toString());
            onGetUserProfileClicked();
        }

        //        else if (AUTH_CODE_REQUEST_CODE == requestCode) {
//            mAccessCode = response.getCode();
//        }
    }

    /**
     * Get user profile
     * This method will get the user profile using the token
     */
    public void onGetUserProfileClicked() {
        if (mAccessToken == null) {
            Toast.makeText(this, "You need to get an access token first!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create a request to get the user profile
        final Request request = new Request.Builder()
                .url("https://api.spotify.com/v1/me")
                .addHeader("Authorization", "Bearer " + mAccessToken)
                .build();

        cancelCall();
        mCall = mOkHttpClient.newCall(request);

        mCall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("HTTP", "Failed to fetch data: " + e);
                runOnUiThread(() -> {
                    Toast.makeText(ConnectToSpotifyActivity.this, "Failed to fetch data, watch Logcat for more details", Toast.LENGTH_SHORT).show();
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    /**
                     You can only read the response once before it disappears
                     If you try to read it again -> OkHttp Dipatcher IllegalStateException:closed occurs
                     Thus, I save it in a variable to prevent issues.
                     **/

                    String responseBody = response.body().string();
                    Log.i("User Profile: ", "User Profile: " + responseBody);
                    final JSONObject jsonObject = new JSONObject(responseBody);
                    userId = jsonObject.getString("id");
                    onGetUserPlaylistClicked();
                } catch (JSONException e) {
                    Log.d("JSON", "Failed to parse data: " + e);
                    runOnUiThread(() -> {
                        Toast.makeText(ConnectToSpotifyActivity.this, "Failed to parse data, watch Logcat for more details", Toast.LENGTH_SHORT).show();
                    });
                }
            }
        });
    }

    public void onGetUserPlaylistClicked() {
        if (mAccessToken == null) {
            Toast.makeText(this, "You need to get an access token first!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (userId == null) {
            Toast.makeText(this, "You need to collect the userID first!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create a request to get the user profile
        final Request request = new Request.Builder()
                .url("https://api.spotify.com/v1/users/" + userId + "/playlists")
                .addHeader("Authorization", "Bearer " + mAccessToken)
                .build();

        cancelCall();
        mCall = mOkHttpClient.newCall(request);

        mCall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("HTTP", "Failed to fetch data: " + e);
                Toast.makeText(ConnectToSpotifyActivity.this, "Failed to fetch data, watch Logcat for more details",
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String responseBody = response.body().string();
                    final JSONObject jsonObject = new JSONObject(responseBody);
                    Log.i("ConnectToSpotifyActivity - User Playlists: ", "User Playlists: " + responseBody);

                    //Redirecting to MainActivity which redirects to AccountFragment
                    Intent intent = new Intent(ConnectToSpotifyActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish(); // Finish the current activity

                } catch (JSONException e) {
                    Log.d("JSON", "Failed to parse data: " + e);
                    Toast.makeText(ConnectToSpotifyActivity.this, "Failed to parse data, watch Logcat for more details",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * Get authentication request
     *
     * @param type the type of the request
     * @return the authentication request
     */
    private AuthorizationRequest getAuthenticationRequest(AuthorizationResponse.Type type) {
        return new AuthorizationRequest.Builder(CLIENT_ID, type, getRedirectUri().toString())
                .setShowDialog(false)
                .setScopes(new String[] { "user-read-email", "user-read-private", "user-follow-read", "playlist-read-private"}) // <--- Change the scope of your requested token here
                .setCampaign("your-campaign-token")
                .build();
    }

    /**
     * Gets the redirect Uri for Spotify
     *
     * @return redirect Uri object
     */
    private Uri getRedirectUri() {
        return Uri.parse(REDIRECT_URI);
    }

    private void cancelCall() {
        if (mCall != null) {
            mCall.cancel();
        }
    }

    @Override
    protected void onDestroy() {
        cancelCall();
        AuthorizationClient.stopLoginActivity(this, AUTH_TOKEN_REQUEST_CODE); // Stop the login activity
        super.onDestroy();
    }
}