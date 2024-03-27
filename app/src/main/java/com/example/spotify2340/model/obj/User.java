package com.example.spotify2340.model.obj;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.UUID;

@Entity
public class User {

    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo(name = "username")
    public String username;

    @ColumnInfo(name = "password")
    public String password;

    @ColumnInfo(name = "logged_in")
    public boolean loggedIn;

    @Ignore
    public User() {

    }

    @Ignore
    public User(String username, String password, boolean loggedIn) {
        this.username = username;
        this.password = password;
        this.loggedIn = loggedIn;
    }

    public User(long id, String username, String password, boolean loggedIn) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.loggedIn = false;

    }

}
