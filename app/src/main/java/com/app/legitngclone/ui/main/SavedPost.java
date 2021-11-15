package com.app.legitngclone.ui.main;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "bookmarked_posts")
public class SavedPost {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String title;

    private String urlToImage;

    private String description;

    public SavedPost(String title, String urlToImage, String description, String content, String publishedAt, String author, String url) {
        this.title = title;
        this.urlToImage = urlToImage;
        this.description = description;
        this.content = content;
        this.publishedAt = publishedAt;
        this.author = author;
        this.url = url;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public String getDescription() {
        return description;
    }

    public String getContent() {
        return content;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public String getAuthor() {
        return author;
    }

    public String getUrl() {
        return url;
    }

    private String content;

    private String publishedAt;

    private String author;

    private String url;

}
