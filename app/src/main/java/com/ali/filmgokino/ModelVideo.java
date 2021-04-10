package com.ali.filmgokino;

import java.io.Serializable;



public class ModelVideo implements Serializable {
    String description;
    String duration;
    String id;
    String image;
    String likeCount;
    String time;
    String title;
    String viewCount;

    public ModelVideo() {

    }

    public ModelVideo(String description, String duration, String id, String image, String likeCount, String time, String title, String viewCount) {
        this.description = description;
        this.duration = duration;
        this.id = id;
        this.image = image;
        this.likeCount = likeCount;
        this.time = time;
        this.title = title;
        this.viewCount = viewCount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getViewCount() {
        return viewCount;
    }

    public void setViewCount(String viewCount) {
        this.viewCount = viewCount;
    }

    public String getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(String likeCount) {
        this.likeCount = likeCount;
    }
}
