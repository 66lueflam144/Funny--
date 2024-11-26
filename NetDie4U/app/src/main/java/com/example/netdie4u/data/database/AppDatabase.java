package com.example.netdie4u.data.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.netdie4u.dao.FeedDao;
import com.example.netdie4u.data.Feed;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Feed.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase instance;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(4);

    public abstract FeedDao feedDao();

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    AppDatabase.class,
                    "feed_database"
            ).build();
        }
        return instance;
    }
}
