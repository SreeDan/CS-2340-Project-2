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
import android.content.SharedPreferences;
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


public class ConnectToSpotifyActivity extends AppCompatActivity implements SpotifyActionListener {

    public static final String CLIENT_ID = "ffb6d0b9973d4ca9bb946f66370fe166";
    public static final String REDIRECT_URI = "com.example.spotify2340://auth";

    public static final int AUTH_TOKEN_REQUEST_CODE = 0;

//    public static final int AUTH_CODE_REQUEST_CODE = 1;

    private final OkHttpClient mOkHttpClient = new OkHttpClient();
    private String mAccessToken;
    private Call mCall;
    private String userId;
    String userProfileResponse, userPlaylistResponse;
    String InvalidAccessToken = "User Profile: {\n" +
            "\"error\": {\n" +
            "\"status\": 401,\n" +
            "\"message\": \"Invalid access token\"\n" +
            "}\n" +
            "}";

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
        getUserProfile();
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
        }

        //        else if (AUTH_CODE_REQUEST_CODE == requestCode) {
//            mAccessCode = response.getCode();
//        }
    }

    /**
     * Get user profile
     * This method will get the user profile using the token
     */
    public void getUserProfile() {
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

                    userProfileResponse = response.body().string();
                    Log.i("User Profile: ", "User Profile: " + userProfileResponse);
                    final JSONObject jsonObject = new JSONObject(userProfileResponse);
                    userId = jsonObject.getString("id");
                    getUserPlaylists();
                } catch (JSONException e) {
                    Log.d("JSON", "Failed to parse data: " + e);
                    runOnUiThread(() -> {
                        Toast.makeText(ConnectToSpotifyActivity.this, "Failed to parse data, watch Logcat for more details", Toast.LENGTH_SHORT).show();
                    });
                }
            }
        });
    }

    public void getUserPlaylists() {
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
                    userPlaylistResponse = response.body().string();
                    final JSONObject jsonObject = new JSONObject(userPlaylistResponse);
                    Log.i("ConnectToSpotifyActivity - User Playlists: ", "User Playlists: " + userPlaylistResponse);
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
                .setScopes(new String[] { "user-read-email", "user-read-private", "user-follow-read",
                        "playlist-read-private"}) // <--- Change the scope of your requested token here
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

    @Override
    public String fetchAccessToken() {
        getToken();
        return mAccessToken;
    }

    @Override
    public void checkAndExecuteWithToken() {
        if (isTokenValid()) {
                // Token is valid, execute the desired action
                executeAction();
        } else {
            // Token is expired or invalid, fetch a new token
            fetchAccessToken();
        }
    }

    private boolean isTokenValid() {
        getUserProfile();
        boolean invalidAccessTokenResponse = userProfileResponse.equals(InvalidAccessToken);
        return mAccessToken != null && !mAccessToken.isEmpty() && !invalidAccessTokenResponse;
    }

    @Override
    public void fetchUserData() {

    }

    @Override
    public void fetchUserPlaylists() {

    }

    private void executeAction() {
        // Implement the desired action based on the context
        // For example:
        // getUserData();
        // Or
        // fetchTopItems();
    }
}