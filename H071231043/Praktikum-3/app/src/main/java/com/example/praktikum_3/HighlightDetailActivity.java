package com.example.praktikum_3;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;

public class HighlightDetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highlight_detail);

        ImageView detailImage = findViewById(R.id.detailImage);
        TextView title = findViewById(R.id.storyTitle);
        TextView date = findViewById(R.id.storyDate);
        ImageView profileImage = findViewById(R.id.profileImage);
        ImageView btnClose = findViewById(R.id.btnClose);

        int imgRes = getIntent().getIntExtra("imageResId", -1);
        String titleText = getIntent().getStringExtra("title");
        String username = getIntent().getStringExtra("username");
        String profileUriStr = getIntent().getStringExtra("profileUri");

        if (imgRes != -1) {
            detailImage.setImageResource(imgRes);
        }
        title.setText(username != null ? username : titleText);
        date.setText("April 24, 2025");

        if (profileUriStr != null && !profileUriStr.isEmpty()) {
            Uri uri = Uri.parse(profileUriStr);
            profileImage.setImageURI(uri);
            profileImage.post(() -> {
                if (profileImage.getDrawable() instanceof BitmapDrawable) {
                    BitmapDrawable drawable = (BitmapDrawable) profileImage.getDrawable();
                    Bitmap bitmap = drawable.getBitmap();
                    if (bitmap != null && !bitmap.isRecycled()) {
                        RoundedBitmapDrawable rounded = RoundedBitmapDrawableFactory.create(getResources(), bitmap);
                        rounded.setCircular(true);
                        profileImage.setImageDrawable(rounded);
                    }
                }
            });
        }

        btnClose.setOnClickListener(v -> finish());
    }
}
