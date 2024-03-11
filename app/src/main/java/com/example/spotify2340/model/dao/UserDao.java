package com.example.spotify2340.model.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.spotify2340.model.obj.User;

import java.util.List;

@Dao
public interface UserDao {

    @Query("SELECT * FROM user")
    List<User> getAll();

    @Query("SELECT * FROM user WHERE username like :username")
    List<User> findByUsername(String username);

    @Insert
    void insert(User... users);

    @Delete
    void Delete(User user);

}
