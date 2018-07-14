package com.kurashiru.kurashirutrial.model;

public class Recipe {
    private String id;
    private String type;
    private Attributes attributes;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Attributes getAttributes() {
        return attributes;
    }

    public void setAttributes(Attributes attributes) {
        this.attributes = attributes;
    }

    public class Attributes {
        private String title;
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


}
