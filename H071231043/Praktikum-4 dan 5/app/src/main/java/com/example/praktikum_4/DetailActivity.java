package com.example.praktikum_4;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

public class DetailActivity extends AppCompatActivity {
    private ImageView coverImageView;
    private TextView titleTextView;
    private TextView authorTextView;
    private TextView yearTextView;
    private TextView genreTextView;
    private TextView blurbTextView;
    private TextView reviewTextView;
    private RatingBar ratingBar;
    private Button likeButton;

    private BooksDataSource dataSource;
    private String bookId;
    private Book currentBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail);

        try {
            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.detail_main), (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });

            dataSource = BooksDataSource.getInstance(this);

            initializeViews();
            bookId = getIntent().getStringExtra("BOOK_ID");

            if (bookId == null || bookId.isEmpty()) {
                Toast.makeText(this, "Error: Book ID not found", Toast.LENGTH_SHORT).show();
                finish();
                return;
            }
            loadBookDetails();

            likeButton.setOnClickListener(v -> toggleLikeStatus());

        } catch (Exception e) {
            Toast.makeText(this, "Error loading book details", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void initializeViews() {
        coverImageView = findViewById(R.id.image_detail_cover);
        titleTextView = findViewById(R.id.text_detail_title);
        authorTextView = findViewById(R.id.text_detail_author);
        yearTextView = findViewById(R.id.text_detail_year);
        genreTextView = findViewById(R.id.text_detail_genre);
        blurbTextView = findViewById(R.id.text_detail_blurb);
        reviewTextView = findViewById(R.id.text_detail_review);
        ratingBar = findViewById(R.id.rating_bar_detail);
        likeButton = findViewById(R.id.button_like);
    }

    private void loadBookDetails() {
        currentBook = getBookById(bookId);
        if (currentBook == null) {
            Toast.makeText(this, "Book not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        populateBookDetails();
    }

    private void populateBookDetails() {
        titleTextView.setText(currentBook.getTitle());
        authorTextView.setText(currentBook.getAuthor());
        yearTextView.setText(String.valueOf(currentBook.getPublishYear()));
        genreTextView.setText(currentBook.getGenre());
        blurbTextView.setText(currentBook.getBlurb());

        String review = currentBook.getReview();
        reviewTextView.setText(review != null ? review : "No review available");

        ratingBar.setRating(currentBook.getRating());

        if (currentBook.getCoverImage() != null) {
            coverImageView.setImageURI(currentBook.getCoverImage());
        } else {
            coverImageView.setImageResource(R.drawable.default_book_cover);
        }

        updateLikeButtonText();
    }

    private void toggleLikeStatus() {
        dataSource.toggleLikeStatus(bookId);
        reloadCurrentBook();
        updateLikeButtonText();

        String message = currentBook.isLiked() ? "Added to favorites" : "Removed from favorites";
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void reloadCurrentBook() {
        currentBook = getBookById(bookId);
    }

    private void updateLikeButtonText() {
        if (currentBook.isLiked()) {
            likeButton.setText("Unlike");
        } else {
            likeButton.setText("Like");
        }
    }

    private Book getBookById(String id) {
        for (Book book : dataSource.getAllBooks()) {
            if (book.getId().equals(id)) {
                return book;
            }
        }
        return null;
    }
}
