package com.example.netdie4u.data;

public class Ghost_List {
    private Item_ item;
    private String title_show;

    public Ghost_List(Item_ item, String title_show) {
        this.item = item;
        this.title_show = title_show;
    }

    @Override
    public String toString() {
        return "Ghost_List{" +
                "item=" + item +
                ", title_show='" + title_show + '\'' +
                '}';
    }

    public String getTitle_show() {
        return title_show;
    }

    public Item_ getFirstItem() {
        return item;
    }
}
