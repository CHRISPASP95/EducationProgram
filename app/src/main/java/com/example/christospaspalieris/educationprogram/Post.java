package com.example.christospaspalieris.educationprogram;

/**
 * Created by peira on 09-Jul-17.
 */

public class Post {

    private String title,desc,poster_image,uid;

    public Post()
    {}

    public Post(String title, String desc, String poster_image, String uid) {
        this.title = title;
        this.desc = desc;
        this.poster_image = poster_image;
        this.uid = uid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPoster_image() {
        return poster_image;
    }

    public void setPoster_image(String poster_image) {
        this.poster_image = poster_image;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
