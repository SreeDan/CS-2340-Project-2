package com.example.spotify2340.model.obj;

import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;

public class Comment {

    @PrimaryKey
    public long id;

    @ColumnInfo(name = "user_id")
    public String userId;



}
