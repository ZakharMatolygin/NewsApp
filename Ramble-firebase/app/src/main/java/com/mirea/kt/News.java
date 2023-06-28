package com.mirea.kt;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class News implements Parcelable {
    public String newsPubDate;
    public String newsTitle;
    public String newsDescription;
    public String newsAuthor;
    public String newsLink;

    public News(String newsPubDate, String newsTitle, String newsDescription, String newsAuthor, String newsLink) {
        this.newsPubDate = newsPubDate;
        this.newsTitle = newsTitle;
        this.newsDescription = newsDescription;
        this.newsAuthor = newsAuthor;
        this.newsLink = newsLink;
    }

    protected News(Parcel in) {
        newsPubDate = in.readString();
        newsTitle = in.readString();
        newsDescription = in.readString();
        newsAuthor = in.readString();
        newsLink = in.readString();
    }

    public static final Creator<News> CREATOR = new Creator<News>() {
        @Override
        public News createFromParcel(Parcel in) {
            return new News(in);
        }

        @Override
        public News[] newArray(int size) {return new News[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(newsPubDate);
        dest.writeString(newsTitle);
        dest.writeString(newsDescription);
        dest.writeString(newsAuthor);
        dest.writeString(newsLink);
    }
}