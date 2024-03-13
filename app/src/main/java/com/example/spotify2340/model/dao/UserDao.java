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

    @Query("SELECT EXISTS (SELECT * FROM user WHERE username like :username and password like :password)")
    boolean login(String username, String password);

    @Query("SELECT EXISTS (SELECT * FROM user WHERE username like :username)")
    boolean usernameTaken(String username);

    @Query("SELECT * FROM user WHERE logged_in = 1")
    User getCurrUser();

    @Insert
    void insert(User... users);


    @Delete
    void Delete(User user);

    /*

    //Code to get a user logged in.

    String username; //from user input
    String password; //from user input
    if (UserDao.login(username, password)) {
        UserDao.getCurrUser().setLoggedIn(false);
        UserDao.findByUsername(username).get(0).setLoggedIn(true);
    } else {
        Toast.makeText(MainActivity.this, "This combination does not exist. Check your credentials or register instead.", Toast.LENGTH_SHORT);
    }


   //Code to register a user.
   String username;
   String password;
   if (!UserDao.usernameTaken(username)) {

        User added_user = new User(username, password);
        UserDao.getCurrUser().setLoggedIn(false);
        added_user.setLoggedIn(true);
        UserDao.insert(new User(username, password));

   } else {

        Toast.makeText(MainActivity.this, "This username has been taken. Please find another username.", Toast.LENGTH_LONG);
   }


     */


}
