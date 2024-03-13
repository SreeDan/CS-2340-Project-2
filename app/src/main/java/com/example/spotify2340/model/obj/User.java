package com.example.spotify2340.model.obj;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.UUID;

@Entity
public class User {

    @PrimaryKey
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

    public User(long id, String username, String password, boolean loggedIn) {
        //not sure what to do about id
        this.username = username;
        this.password = password;
        this.loggedIn = false;

    }
    @Ignore
    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }
    @Ignore
    public boolean getLoggedIn(User user) {
        return user.loggedIn;
    }


}
