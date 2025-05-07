package com.example.praktikum_3;

public class FeedPost {
    private final String imageUri;
    private final String caption;
    private final String username;
    private final int likesCount;
    private final String timePosted;
    private final String profileImageUri;

    public FeedPost(String imageUri, String caption, String username,
                    String profileImageUri,int likesCount, String timePosted) {
        this.imageUri = imageUri;
        this.caption = caption;
        this.username = username;
        this.profileImageUri = profileImageUri;
        this.likesCount = likesCount;
        this.timePosted = timePosted;
    }

    public String getImageUri() {
        return imageUri;
    }

    public String getCaption() {
        return caption;
    }

    public String getUsername() {
        return username;
    }
    public String getProfileImageUri() {
        return profileImageUri;
    }
    public int getLikesCount() {
        return likesCount;
    }
    public String getTimePosted() {
        return timePosted;
    }
}