package com.example.praktikum_3;

public class UserProfile {
    private String username;
    private String fullName;
    private String bio;
    private String profileImageUrl;
    private int postCount;
    private int followersCount;
    private int followingCount;

    public UserProfile(String username, String fullName, String bio, String profileImageUrl,
                       int postCount, int followersCount, int followingCount) {
        this.username = username;
        this.fullName = fullName;
        this.bio = bio;
        this.profileImageUrl = profileImageUrl;
        this.postCount = postCount;
        this.followersCount = followersCount;
        this.followingCount = followingCount;
    }

    // Getter methods
    public String getUsername() {
        return username;
    }

    public String getFullName() {
        return fullName;
    }

    public String getBio() {
        return bio;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public int getPostCount() {
        return postCount;
    }

    public int getFollowersCount() {
        return followersCount;
    }

    public int getFollowingCount() {
        return followingCount;
    }
}