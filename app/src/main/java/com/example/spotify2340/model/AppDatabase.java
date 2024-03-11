package com.example.spotify2340.model;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.spotify2340.model.dao.UserDao;
import com.example.spotify2340.model.obj.User;

@Database(entities = {User.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
}
