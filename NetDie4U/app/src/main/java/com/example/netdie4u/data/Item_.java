package com.example.netdie4u.data;

public class Item_ {
    private String title;
    private String link;
    private String author;
    private String pubDate;

    private String description;

    private boolean isChannelItem;

    public boolean isChannelItem() {
        return isChannelItem;
    }

    public void setChannelItem(boolean channelItem) {
        isChannelItem = channelItem;
    }

    public Item_(String title, String link, String author, String pubDate, String description) {
        this.title = title;
        this.link = link;
        this.author = author;
        this.pubDate = pubDate;
        this.description = description;
    }
    public Item_(){}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public String toString() {
        return "Item_{" +
                "title='" + title + '\'' +
                ", link='" + link + '\'' +
                ", author='" + author + '\'' +
                ", pubDate='" + pubDate + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
