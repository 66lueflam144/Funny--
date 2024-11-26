package com.example.netdie4u.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.netdie4u.basic.XFragment;
import com.example.netdie4u.dao.FeedDao;
import com.example.netdie4u.data.Feed;
import com.example.netdie4u.data.Ghost_List;
import com.example.netdie4u.data.database.AppDatabase;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.List;

public class SnakeRepository {
    private static SnakeRepository instance;
    private final FeedDao feedDao;
    private final LiveData<List<Feed>> allFeeds;

    private SnakeRepository(Context context){
        AppDatabase database = AppDatabase.getInstance(context);
        feedDao = database.feedDao();
        allFeeds = feedDao.getAllFeeds();

    }


    public static synchronized SnakeRepository getInstance(Context context) {
        if (instance == null) {
            instance = new SnakeRepository(context);
        }
        return instance;
    }

    public LiveData<List<Feed>> getAllFeeds() {
        return allFeeds;
    }

    public void insert(Feed feed) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            feedDao.insert(feed);
        });
    }
    public void delete(Feed feed) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            feedDao.delete(feed);
        });
    }

}
