package com.kurashiru.kurashirutrial.model;

import com.google.gson.annotations.SerializedName;

public class Attributes {
    private String title;

    @SerializedName("thumbnail-square-url")
    private String thumbnailSquareUrl;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumbnailSquareUrl() {
        return thumbnailSquareUrl;
    }

    public void setThumbnailSquareUrl(String thumbnailSquareUrl) {
        this.thumbnailSquareUrl = thumbnailSquareUrl;
    }
}
