package com.example.netdie4u.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "feeds")
public class Feed {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String titleShow;
    public String titleOfArticle;
    public String author;
    public String link;
    public String pubDate;
    public String description;
}
