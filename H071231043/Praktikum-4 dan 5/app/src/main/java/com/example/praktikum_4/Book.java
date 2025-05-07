package com.example.praktikum_4;

import android.net.Uri;
import java.io.Serializable;

public class Book implements Serializable {
    private String id;
    private String title;
    private String author;
    private int publishYear;
    private String blurb;
    private Uri coverImage;
    private boolean isLiked;
    private String genre;
    private float rating;
    private String review;

    public Book(String id, String title, String author, int publishYear, String blurb, Uri coverImage, String genre, float rating, String review) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.publishYear = publishYear;
        this.blurb = blurb;
        this.coverImage = coverImage;
        this.isLiked = false;
        this.genre = genre;
        this.rating = rating;
        this.review = review;
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getPublishYear() {
        return publishYear;
    }

    public void setPublishYear(int publishYear) {
        this.publishYear = publishYear;
    }

    public String getBlurb() {
        return blurb;
    }

    public void setBlurb(String blurb) {
        this.blurb = blurb;
    }

    public Uri getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(Uri coverImage) {
        this.coverImage = coverImage;
    }

    public boolean isLiked() {
        return isLiked;
    }

    public void setLiked(boolean liked) {
        isLiked = liked;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }
}