package com.example.praktikum_4;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FavoriteBookAdapter extends RecyclerView.Adapter<FavoriteBookAdapter.FavoriteViewHolder> {

    private List<Book> favoriteBooks;
    private Context context;
    private BooksDataSource dataSource;
    private FavoritesUpdateListener listener;

    public interface FavoritesUpdateListener {
        void onFavoriteRemoved();
    }

    public FavoriteBookAdapter(List<Book> favoriteBooks, Context context, FavoritesUpdateListener listener) {
        this.favoriteBooks = favoriteBooks;
        this.context = context;
        this.dataSource = BooksDataSource.getInstance(context);
        this.listener = listener;
    }

    @NonNull
    @Override
    public FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favorite_book, parent, false);
        return new FavoriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteViewHolder holder, int position) {
        Book book = favoriteBooks.get(position);

        holder.titleTextView.setText(book.getTitle());
        holder.authorTextView.setText(book.getAuthor());
        holder.yearTextView.setText(String.valueOf(book.getPublishYear()));
        holder.genreTextView.setText(book.getGenre());
        holder.ratingTextView.setText(String.format("%.1f", book.getRating()));

        if (book.getCoverImage() != null) {
            holder.coverImageView.setImageURI(book.getCoverImage());
        } else {
            holder.coverImageView.setImageResource(R.drawable.default_book_cover);
        }

        // Set click listener for the card
        holder.cardView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("BOOK_ID", book.getId());
            context.startActivity(intent);
        });

        // Set click listener for the remove button
        holder.removeButton.setOnClickListener(v -> {
            dataSource.toggleLikeStatus(book.getId());
            Toast.makeText(context, "Removed from favorites", Toast.LENGTH_SHORT).show();

            favoriteBooks.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, favoriteBooks.size());

            if (listener != null) {
                listener.onFavoriteRemoved();
            }
        });
    }

    @Override
    public int getItemCount() {
        return favoriteBooks.size();
    }

    public void updateData(List<Book> newFavorites) {
        this.favoriteBooks = newFavorites;
        notifyDataSetChanged();
    }

    static class FavoriteViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView coverImageView;
        TextView titleTextView;
        TextView authorTextView;
        TextView yearTextView;
        TextView genreTextView;
        TextView ratingTextView;
        Button removeButton;

        public FavoriteViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card_view_favorite);
            coverImageView = itemView.findViewById(R.id.image_favorite_cover);
            titleTextView = itemView.findViewById(R.id.text_favorite_title);
            authorTextView = itemView.findViewById(R.id.text_favorite_author);
            yearTextView = itemView.findViewById(R.id.text_favorite_year);
            genreTextView = itemView.findViewById(R.id.text_favorite_genre);
            ratingTextView = itemView.findViewById(R.id.text_favorite_rating);
            removeButton = itemView.findViewById(R.id.button_remove_favorite);
        }
    }
}