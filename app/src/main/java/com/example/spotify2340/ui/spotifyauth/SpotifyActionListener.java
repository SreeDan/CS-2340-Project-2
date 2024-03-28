package com.example.spotify2340.ui.spotifyauth;

public interface SpotifyActionListener {
    void checkAndExecuteWithToken();
    String fetchAccessToken();
    void fetchUserData();
    void fetchUserPlaylists();
}
