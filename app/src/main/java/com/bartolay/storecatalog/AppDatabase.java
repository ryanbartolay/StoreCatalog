package com.bartolay.storecatalog;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.bartolay.storecatalog.dao.UserDao;
import com.bartolay.storecatalog.entity.User;

@Database(entities = {User.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
}