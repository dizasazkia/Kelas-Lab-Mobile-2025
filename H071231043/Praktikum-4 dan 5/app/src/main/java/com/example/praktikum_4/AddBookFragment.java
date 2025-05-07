package com.example.praktikum_4;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.praktikum_4.R;
import com.example.praktikum_4.BooksDataSource;
import com.example.praktikum_4.Book;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class AddBookFragment extends Fragment {

    private static final int PICK_IMAGE_REQUEST = 1;

    private EditText titleInput;
    private EditText authorInput;
    private EditText yearInput;
    private EditText blurbInput;
    private EditText reviewInput;
    private Spinner genreSpinner;
    private RatingBar ratingBar;
    private ImageView coverImageView;
    private Button selectImageButton;
    private Button addBookButton;

    private Uri coverImageUri;
    private BooksDataSource dataSource;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_book, container, false);

        dataSource = BooksDataSource.getInstance(requireContext());

        titleInput = view.findViewById(R.id.edit_text_title);
        authorInput = view.findViewById(R.id.edit_text_author);
        yearInput = view.findViewById(R.id.edit_text_year);
        blurbInput = view.findViewById(R.id.edit_text_blurb);
        reviewInput = view.findViewById(R.id.edit_text_review);
        genreSpinner = view.findViewById(R.id.spinner_genre);
        ratingBar = view.findViewById(R.id.rating_bar);
        coverImageView = view.findViewById(R.id.image_view_cover);
        selectImageButton = view.findViewById(R.id.button_select_image);
        addBookButton = view.findViewById(R.id.button_add_book);

        List<String> genres = Arrays.asList(
                "Fantasy", "Science Fiction",
                "Horror", "Romance", "thriller"
        );

        ArrayAdapter<String> genreAdapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_spinner_item,
                genres
        );
        genreAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genreSpinner.setAdapter(genreAdapter);

        selectImageButton.setOnClickListener(v -> openGallery());

        addBookButton.setOnClickListener(v -> addNewBook());

        return view;
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            coverImageUri = data.getData();
            coverImageView.setImageURI(coverImageUri);
            coverImageView.setVisibility(View.VISIBLE);
        }
    }

    private void addNewBook() {
        String title = titleInput.getText().toString().trim();
        String author = authorInput.getText().toString().trim();
        String yearStr = yearInput.getText().toString().trim();
        String blurb = blurbInput.getText().toString().trim();
        String genre = genreSpinner.getSelectedItem().toString();
        float rating = ratingBar.getRating();
        String review = reviewInput.getText().toString().trim();

        if (title.isEmpty() || author.isEmpty() || yearStr.isEmpty() || blurb.isEmpty()) {
            Toast.makeText(getContext(), "Please fill all required fields", Toast.LENGTH_SHORT).show();
            return;
        }

        int publishYear;
        try {
            publishYear = Integer.parseInt(yearStr);
            if (publishYear < 1000 || publishYear > 2025) {
                Toast.makeText(getContext(), "Please enter a valid year", Toast.LENGTH_SHORT).show();
                return;
            }
        } catch (NumberFormatException e) {
            Toast.makeText(getContext(), "Please enter a valid year", Toast.LENGTH_SHORT).show();
            return;
        }

        if (coverImageUri == null) {
            coverImageUri = Uri.parse("android.resource://" + requireContext().getPackageName() + "/" + R.drawable.default_book_cover);
        }

        Book newBook = new Book(
                UUID.randomUUID().toString(),
                title,
                author,
                publishYear,
                blurb,
                coverImageUri,
                genre,
                rating,
                review
        );

        dataSource.addBook(newBook);

        // Reset form
        titleInput.setText("");
        authorInput.setText("");
        yearInput.setText("");
        blurbInput.setText("");
        reviewInput.setText("");
        genreSpinner.setSelection(0);
        ratingBar.setRating(0);
        coverImageView.setImageURI(null);
        coverImageView.setVisibility(View.GONE);
        coverImageUri = null;

        Toast.makeText(getContext(), "Book added successfully", Toast.LENGTH_SHORT).show();
    }
}