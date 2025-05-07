package com.example.praktikum_3;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class UploadActivity extends AppCompatActivity {
    private ImageView uploadImage;
    private EditText captionEditText;
    private Button uploadButton;
    private Button cancelButton;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_activity);

        bottomNavigationView = findViewById(R.id.bottomNavView);

        if (bottomNavigationView != null) {
            bottomNavigationView.setActiveScreen(BottomNavigationView.SCREEN_UPLOAD);
        }

        uploadImage = findViewById(R.id.uploadImage);
        captionEditText = findViewById(R.id.captionEditText);
        uploadButton = findViewById(R.id.uploadButton);

        uploadImage.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.setType("image/*");
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            startActivityForResult(intent, 100);
        });

        uploadButton.setOnClickListener(v -> {
            String caption = captionEditText.getText().toString();
            Uri imageUri = (Uri) uploadImage.getTag();

            if (imageUri != null && !caption.isEmpty()) {
                Intent intent = new Intent();
                intent.putExtra("imageUri", imageUri.toString());
                intent.putExtra("caption", caption);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (bottomNavigationView != null) {
            bottomNavigationView.setActiveScreen(BottomNavigationView.SCREEN_UPLOAD);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            if (imageUri != null) {
                getContentResolver().takePersistableUriPermission(
                        imageUri, Intent.FLAG_GRANT_READ_URI_PERMISSION
                );
                uploadImage.setImageURI(imageUri);
                uploadImage.setTag(imageUri);
            }
        }
    }
}