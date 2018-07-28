package com.denieall.crud;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.denieall.crud.Model.User;

@Database(entities = {User.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDAO userDAO();
}
