package com.example.yosemite.data;

public class Schedule {
    private String title;
    private String content;
    private String date;
    private String time;

    public Schedule(String title, String time, String date, String content) {
        this.title = title;
        this.time = time;
        this.date = date;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
