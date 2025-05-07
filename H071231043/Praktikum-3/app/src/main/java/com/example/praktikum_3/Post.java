package com.example.praktikum_3;

public class Post {
    private final String imageUri;
    private final String caption;

    public Post(String imageUri, String caption) {
        this.imageUri = imageUri;
        this.caption = caption;
    }

    public String getImageUri() {
        return imageUri;
    }

    public String getCaption() {
        return caption;
    }
}
