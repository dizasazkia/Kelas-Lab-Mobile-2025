package com.example.praktikum_4;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FavoritesFragment extends Fragment implements FavoriteBookAdapter.FavoritesUpdateListener {
    private RecyclerView recyclerView;
    private FavoriteBookAdapter favoriteBookAdapter;
    private TextView noFavoritesText;
    private ProgressBar progressBar;
    private BooksDataSource dataSource;

    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final Handler handler = new Handler(Looper.getMainLooper());

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);

        dataSource = BooksDataSource.getInstance(requireContext());

        recyclerView = view.findViewById(R.id.recycler_view_favorites);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        noFavoritesText = view.findViewById(R.id.text_no_favorites);
        progressBar = view.findViewById(R.id.progress_bar_favorites);

        loadFavoriteBooks();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadFavoriteBooks();
    }

    private void loadFavoriteBooks() {
        showLoading(true);

        executor.execute(() -> {
            List<Book> favoriteBooks = dataSource.getLikedBooks();

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            handler.post(() -> {
                updateUI(favoriteBooks);
                showLoading(false);
            });
        });
    }

    private void showLoading(boolean isLoading) {
        if (isLoading) {
            progressBar.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            noFavoritesText.setVisibility(View.GONE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    private void updateUI(List<Book> favoriteBooks) {
        if (favoriteBooks.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            noFavoritesText.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            noFavoritesText.setVisibility(View.GONE);

            if (favoriteBookAdapter == null) {
                favoriteBookAdapter = new FavoriteBookAdapter(favoriteBooks, getContext(), this);
                recyclerView.setAdapter(favoriteBookAdapter);
            } else {
                favoriteBookAdapter.updateData(favoriteBooks);
            }
        }
    }

    @Override
    public void onFavoriteRemoved() {
        loadFavoriteBooks();
    }
}