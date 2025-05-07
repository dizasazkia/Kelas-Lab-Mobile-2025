package com.example.praktikum_3;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {

    private TextView usernameText, fullNameText, bioText, postsCountText, followersCountText, followingCountText;
    private ImageView profileImageView;
    private Button actionButton;
    private RecyclerView postsRecyclerView, highlightsRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        usernameText = findViewById(R.id.profileUsername);
        fullNameText = findViewById(R.id.profileFullName);
        bioText = findViewById(R.id.profileBio);
        profileImageView = findViewById(R.id.profileImage);
        postsCountText = findViewById(R.id.postsCount);
        followersCountText = findViewById(R.id.followersCount);
        followingCountText = findViewById(R.id.followingCount);
        actionButton = findViewById(R.id.profileActionButton);

        postsRecyclerView = findViewById(R.id.recyclerView);
        highlightsRecyclerView = findViewById(R.id.highlightsRecyclerView);

        String username = getIntent().getStringExtra("username");
        if (username == null) {
            username = "toystory";
        }

        Map<String, UserProfile> profiles = DataSource.getUserProfiles(getPackageName());
        UserProfile profile = profiles.get(username);

        if (profile != null) {
            // Set profile info
            usernameText.setText(profile.getUsername());
            fullNameText.setText(profile.getFullName());
            bioText.setText(profile.getBio());

            // Set profile image
            String profileImageUrl = profile.getProfileImageUrl();
            if (profileImageUrl != null) {
                Uri uri = Uri.parse(profileImageUrl);
                profileImageView.setImageURI(uri);
            }

            postsCountText.setText(String.valueOf(profile.getPostCount()));
            followersCountText.setText(String.valueOf(profile.getFollowersCount()));
            followingCountText.setText(String.valueOf(profile.getFollowingCount()));

            actionButton.setText("Follow");
        }

        // Set up the highlights RecyclerView
        LinearLayoutManager highlightsLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        highlightsRecyclerView.setLayoutManager(highlightsLayoutManager);
        ArrayList<Highlight> highlights = DataSource.getUserProfileHighlights(username);

        HighlightAdapter highlightAdapter = new HighlightAdapter(
                this,
                highlights,
                username,
                profile != null ? profile.getProfileImageUrl() : ""
        );

        highlightsRecyclerView.setAdapter(highlightAdapter);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        postsRecyclerView.setLayoutManager(gridLayoutManager);

        List<FeedPost> userPosts = new ArrayList<>();
        for (FeedPost post : DataSource.getDummyFeedPosts(getPackageName())) {
            if (post.getUsername().equals(username)) {
                userPosts.add(post);
            }
        }

        ProfileGridAdapter gridAdapter = new ProfileGridAdapter(this, userPosts);
        postsRecyclerView.setAdapter(gridAdapter);
    }
}