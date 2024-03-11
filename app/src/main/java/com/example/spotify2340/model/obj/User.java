package com.example.spotify2340.model.obj;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.UUID;

@Entity
public class User {

    @PrimaryKey
    public UUID id;

    @ColumnInfo(name = "username")
    public String username;

    @ColumnInfo(name = "password")
    private String password;

}
