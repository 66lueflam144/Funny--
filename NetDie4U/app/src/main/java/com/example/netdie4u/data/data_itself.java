package com.example.netdie4u.data;

import android.os.Parcel;
import android.os.Parcelable;

public class data_itself implements Parcelable{

    private String Title_show;
    private String title;
    private String author;
    private String pubDate;
    private String link;
    private String description;

    public data_itself() {}


    public data_itself(String title_show, String title, String author, String pubDate, String link, String description) {
        Title_show = title_show;
        this.title = title;
        this.author = author;
        this.pubDate = pubDate;
        this.link = link;
        this.description = description;
    }


    protected data_itself(Parcel in) {
        Title_show = in.readString();
        title = in.readString();
        author = in.readString();
        pubDate = in.readString();
        link = in.readString();
        description = in.readString();
    }


    public static final Creator<data_itself> CREATOR = new Creator<data_itself>() {
        @Override
        public data_itself createFromParcel(Parcel in) {
            return new data_itself(in);
        }

        @Override
        public data_itself[] newArray(int i) {
            return new data_itself[0];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Title_show);
        dest.writeString(title);
        dest.writeString(author);
        dest.writeString(pubDate);
        dest.writeString(link);
        dest.writeString(description);

    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getTitle_show() {
        return Title_show;
    }

    public void setTitle_show(String title_show) {
        Title_show = title_show;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "data_itself{" +
                "Title_show='" + Title_show + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", pubDate='" + pubDate + '\'' +
                ", link='" + link + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
