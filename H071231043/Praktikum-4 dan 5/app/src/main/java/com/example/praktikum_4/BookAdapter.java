package com.example.praktikum_4;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.praktikum_4.DetailActivity;
import com.example.praktikum_4.R;
import com.example.praktikum_4.Book;

import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {

    private List<Book> books;
    private Context context;

    public BookAdapter(List<Book> books, Context context) {
        this.books = books;
        this.context = context;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book book = books.get(position);

        holder.titleTextView.setText(book.getTitle());
        holder.authorTextView.setText(book.getAuthor());
        holder.yearTextView.setText(String.valueOf(book.getPublishYear()));
        holder.genreTextView.setText(book.getGenre());

        // Set rating
        holder.ratingTextView.setText(String.format("%.1f", book.getRating()));

        // Set image
        if (book.getCoverImage() != null) {
            holder.coverImageView.setImageURI(book.getCoverImage());
        } else {
            holder.coverImageView.setImageResource(R.drawable.default_book_cover);
        }

        // Set like indicator
        if (book.isLiked()) {
            holder.likeIndicator.setVisibility(View.VISIBLE);
        } else {
            holder.likeIndicator.setVisibility(View.GONE);
        }

        // Set click listener
        holder.cardView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("BOOK_ID", book.getId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public void updateData(List<Book> newBooks) {
        this.books = newBooks;
        notifyDataSetChanged();
    }

    static class BookViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView coverImageView;
        TextView titleTextView;
        TextView authorTextView;
        TextView yearTextView;
        TextView genreTextView;
        TextView ratingTextView;
        ImageView likeIndicator;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card_view);
            coverImageView = itemView.findViewById(R.id.image_book_cover);
            titleTextView = itemView.findViewById(R.id.text_book_title);
            authorTextView = itemView.findViewById(R.id.text_book_author);
            yearTextView = itemView.findViewById(R.id.text_book_year);
            genreTextView = itemView.findViewById(R.id.text_book_genre);
            ratingTextView = itemView.findViewById(R.id.text_book_rating);
            likeIndicator = itemView.findViewById(R.id.image_like_indicator);
        }
    }
}