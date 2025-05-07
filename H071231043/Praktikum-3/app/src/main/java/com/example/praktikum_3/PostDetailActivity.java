package com.example.praktikum_3;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;

public class PostDetailActivity extends AppCompatActivity {

    private ImageView postImage, profileImage;
    private TextView usernameText;
    private TextView usernameCaptionText, captionOnlyText, likesText, timeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);

        // Find Views
        postImage = findViewById(R.id.postImage);
        profileImage = findViewById(R.id.profileImage);
        usernameText = findViewById(R.id.usernameText);
        usernameCaptionText = findViewById(R.id.text_username_caption);
        captionOnlyText = findViewById(R.id.text_caption);
        likesText = findViewById(R.id.text_likes);
        timeText = findViewById(R.id.text_time);

        // Ambil data dari Intent
        Intent intent = getIntent();
        String imageUri = intent.getStringExtra("imageUri");
        String caption = intent.getStringExtra("caption");
        String username = intent.getStringExtra("username");
        String profileUri = intent.getStringExtra("profileUri");
        int likes = intent.getIntExtra("likes", 123);
        String timePosted = intent.getStringExtra("timePosted");

        // Set data ke view
        if (caption != null) {
            captionOnlyText.setText(caption);
        }
        if (username != null) {
            usernameText.setText(username);
            usernameCaptionText.setText(username);
        }

        // Set jumlah likes dan waktu post dari data yang dikirim
        likesText.setText(likes + " suka");
        if (timePosted != null) {
            timeText.setText(timePosted);
        } else {
            timeText.setText("5 menit yang lalu");
        }

        try {
            if (imageUri != null) {
                Uri uri = Uri.parse(imageUri);
                postImage.setImageURI(uri);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Gagal menampilkan gambar", Toast.LENGTH_SHORT).show();
        }

        try {
            if (profileUri != null) {
                Uri profileImageUri = Uri.parse(profileUri);
                profileImage.setImageURI(profileImageUri);
                profileImage.post(() -> makeImageCircular(profileImage));
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Gagal menampilkan foto profil", Toast.LENGTH_SHORT).show();
        }
    }

    private void makeImageCircular(ImageView imageView) {
        try {
            if (imageView.getDrawable() instanceof BitmapDrawable) {
                BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
                Bitmap bitmap = drawable.getBitmap();
                if (bitmap != null && !bitmap.isRecycled()) {
                    RoundedBitmapDrawable roundedDrawable =
                            RoundedBitmapDrawableFactory.create(getResources(), bitmap);
                    roundedDrawable.setCircular(true);
                    imageView.setImageDrawable(roundedDrawable);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
