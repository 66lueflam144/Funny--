package com.example.netdie4u.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.netdie4u.data.Feed;

import java.util.List;

@Dao
public interface FeedDao {
    @Query("SELECT * FROM feeds")
    LiveData<List<Feed>> getAllFeeds();

    @Insert
    void insert(Feed feed);

    @Delete
    void delete(Feed feed);
}
