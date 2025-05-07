package com.example.praktikum_3;

public class Highlight {
    private final int imageResId;
    private final String title;

    public Highlight(int imageResId, String title) {
        this.imageResId = imageResId;
        this.title = title;
    }

    public int getImageResId() {
        return imageResId;
    }

    public String getTitle() {
        return title;
    }
}
