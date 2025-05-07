package com.example.praktikum_3;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.praktikum_3.Highlight;
import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TextView textName, textUsername, textBio;
    private ImageView profileImage;
    private ActivityResultLauncher<Intent> editProfileLauncher;
    private ActivityResultLauncher<Intent> uploadLauncher;

    private String currentImageUriString = null;

    private RecyclerView recyclerView;
    private List<Post> postList = new ArrayList<>();
    private PostAdapter postAdapter;
    private RecyclerView highlightRecyclerView;
    private HighlightAdapter highlightAdapter;

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Initialize UI elements
        textName = findViewById(R.id.textView);
        textUsername = findViewById(R.id.textView5);
        textBio = findViewById(R.id.textView3);
        profileImage = findViewById(R.id.imageView);
        recyclerView = findViewById(R.id.recyclerView);
        highlightRecyclerView = findViewById(R.id.highlightRecyclerView);
        bottomNavigationView = findViewById(R.id.bottomNavView);

        // Setup RecyclerView for Posts
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        postAdapter = new PostAdapter(this, postList);
        postAdapter.setUserProfile(
                textUsername.getText().toString(),
                currentImageUriString != null ? currentImageUriString : ""
        );
        recyclerView.setAdapter(postAdapter);

        // Setup RecyclerView for Highlights
        highlightRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        highlightAdapter = new HighlightAdapter(
                this,
                DataSource.getDummyHighlights(),
                textUsername.getText().toString(),
                currentImageUriString != null ? currentImageUriString : ""
        );
        highlightRecyclerView.setAdapter(highlightAdapter);

        postList.addAll(DataSource.getDummyPosts(getPackageName()));
        postAdapter.notifyDataSetChanged();

        editProfileLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        updateProfileWithNewData(result.getData());
                    }
                }
        );

        uploadLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        String imageUri = result.getData().getStringExtra("imageUri");
                        String caption = result.getData().getStringExtra("caption");
                        if (imageUri != null && caption != null) {
                            postList.add(new Post(imageUri, caption));
                            postAdapter.notifyItemInserted(postList.size() - 1);
                        }
                    }
                }
        );

        // Event for edit profile
        profileImage.setOnClickListener(this::goToEditProfile);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (bottomNavigationView != null) {
            bottomNavigationView.setActiveScreen(BottomNavigationView.SCREEN_PROFILE);
        }
    }

    public void goToEditProfile(View view) {
        Intent intent = new Intent(this, EditProfileActivity.class);
        intent.putExtra("current_name", textName.getText().toString());
        intent.putExtra("current_username", textUsername.getText().toString());
        intent.putExtra("current_bio", textBio.getText().toString());
        intent.putExtra("current_image_uri", currentImageUriString != null ? currentImageUriString : "");
        editProfileLauncher.launch(intent);
    }

    private void updateProfileWithNewData(Intent data) {
        if (data == null) return;

        String name = data.getStringExtra("name");
        String username = data.getStringExtra("username");
        String bio = data.getStringExtra("bio");
        String imageUri = data.getStringExtra("imageUri");

        if (name != null && !name.isEmpty()) textName.setText(name);
        if (username != null && !username.isEmpty()) textUsername.setText(username);
        if (bio != null) textBio.setText(bio);

        if (imageUri != null && !imageUri.isEmpty()) {
            try {
                Uri uri = Uri.parse(imageUri);
                profileImage.setImageURI(uri);

                if (bottomNavigationView != null) {
                    bottomNavigationView.updateProfileImage(imageUri);
                }

                makeImageCircular(profileImage);

                currentImageUriString = imageUri;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        postAdapter.setUserProfile(
                textUsername.getText().toString(),
                currentImageUriString != null ? currentImageUriString : ""
        );
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1001 && resultCode == RESULT_OK && data != null) {
            String imageUri = data.getStringExtra("imageUri");
            String caption = data.getStringExtra("caption");
            if (imageUri != null && caption != null) {
                postList.add(0, new Post(imageUri, caption));
                postAdapter.notifyItemInserted(0);
                recyclerView.smoothScrollToPosition(0);
            }
        }
    }
}