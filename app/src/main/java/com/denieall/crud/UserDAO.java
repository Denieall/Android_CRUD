package com.denieall.crud;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.denieall.crud.Model.User;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface UserDAO {

    @Query("SELECT * FROM User")
    List<User> getAllUsers();

    @Query("SELECT * FROM User WHERE id = :id")
    User getSingleUser(int id);

    // Insert one user or an array of multiple user or nothing at all --- varargs
    @Insert
    void insertAll(User... users);

    @Delete
    void delete(User user);

    @Query("DELETE FROM User")
    void deleteAll();

    @Update
    void update(User user);


}
