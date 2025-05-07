package com.example.praktikum_3;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.google.android.material.imageview.ShapeableImageView;

public class BottomNavigationView extends LinearLayout {

    private ImageView homeIcon, searchIcon, addFeedIcon, addReelsIcon;
    private ShapeableImageView profileIcon;
    private String profileImageUri = "";
    public static final int SCREEN_HOME = 1;
    public static final int SCREEN_UPLOAD = 2;
    public static final int SCREEN_PROFILE = 3;

    private int currentScreen = 0;
    private Context context;

    public BottomNavigationView(Context context) {
        super(context);
        init(context);
    }

    public BottomNavigationView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }
    private void init(Context context) {
        this.context = context;
        LayoutInflater.from(context).inflate(R.layout.bottom_nav_layout, this, true);

        homeIcon = findViewById(R.id.homeIcon);
        searchIcon = findViewById(R.id.searchIcon);
        addFeedIcon = findViewById(R.id.addFeedIcon);
        addReelsIcon = findViewById(R.id.addReelsIcon);
        profileIcon = findViewById(R.id.profileIcon);
        setupClickListeners();
    }

    private void setupClickListeners() {
        homeIcon.setOnClickListener(v -> {
            if (!(context instanceof HomeActivity)) {
                Intent intent = new Intent(context, HomeActivity.class);
                context.startActivity(intent);
            }
        });

        addFeedIcon.setOnClickListener(v -> {
            if (!(context instanceof UploadActivity)) {
                Intent intent = new Intent(context, UploadActivity.class);
                if (context instanceof android.app.Activity) {
                    ((android.app.Activity) context).startActivityForResult(intent, 1001);
                }
            }
        });

        profileIcon.setOnClickListener(v -> {
            if (!(context instanceof MainActivity)) {
                Intent intent = new Intent(context, MainActivity.class);
                context.startActivity(intent);
            }
        });
    }

    public void setActiveScreen(int screenType) {
        homeIcon.setSelected(false);
        addFeedIcon.setSelected(false);
        profileIcon.setSelected(false);

        currentScreen = screenType;

        switch (screenType) {
            case SCREEN_HOME:
                homeIcon.setSelected(true);
                break;
            case SCREEN_UPLOAD:
                addFeedIcon.setSelected(true);
                break;
            case SCREEN_PROFILE:
                profileIcon.setSelected(true);
                break;
        }
    }

    public void updateProfileImage(String imageUri) {
        this.profileImageUri = imageUri;
        if (profileIcon != null && imageUri != null && !imageUri.isEmpty()) {
            try {
                profileIcon.setImageURI(Uri.parse(imageUri));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}