package com.example.praktikum_4;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private BookAdapter bookAdapter;
    private SearchView searchView;
    private ProgressBar progressBar;
    private TextView noResultsTextView;
    private BooksDataSource dataSource;
    private String currentSearchQuery = "";
    private String currentGenre = "All";

    private TextView genreAll;
    private TextView genreFantasy;
    private TextView genreSciFi;
    private TextView genreRomance;
    private TextView genreHorror;
    private TextView genreThriller;

    private List<TextView> genreViews = new ArrayList<>();

    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final Handler handler = new Handler(Looper.getMainLooper());

    private final Handler searchHandler = new Handler(Looper.getMainLooper());
    private Runnable searchRunnable;

    private static final long SEARCH_DELAY_MS = 600;
    private static final long PROCESSING_DELAY_MS = 400;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        dataSource = BooksDataSource.getInstance(requireContext());

        initializeViews(view);
        setupSearchView();
        setupGenreSelectionViews();
        setupRecyclerView();

        return view;
    }

    private void initializeViews(View view) {
        searchView = view.findViewById(R.id.search_view);
        progressBar = view.findViewById(R.id.progress_bar);
        noResultsTextView = view.findViewById(R.id.text_no_results);

        genreAll = view.findViewById(R.id.genre_all);
        genreFantasy = view.findViewById(R.id.genre_fantasy);
        genreSciFi = view.findViewById(R.id.genre_sci_fi);
        genreRomance = view.findViewById(R.id.genre_romance);
        genreHorror = view.findViewById(R.id.genre_horror);
        genreThriller = view.findViewById(R.id.genre_thriller);

        genreViews.add(genreAll);
        genreViews.add(genreFantasy);
        genreViews.add(genreSciFi);
        genreViews.add(genreRomance);
        genreViews.add(genreHorror);
        genreViews.add(genreThriller);

        recyclerView = view.findViewById(R.id.recycler_view);
    }

    private void setupSearchView() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filterBooks(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (searchRunnable != null) {
                    searchHandler.removeCallbacks(searchRunnable);
                }

                searchRunnable = () -> filterBooks(newText);
                searchHandler.postDelayed(searchRunnable, SEARCH_DELAY_MS);

                return true;
            }
        });
    }
    private void setupGenreSelectionViews() {
        updateGenreSelectionUI(genreAll);

        genreAll.setOnClickListener(v -> {
            currentGenre = "All";
            updateGenreSelectionUI(genreAll);
            applyFilters();
        });

        genreFantasy.setOnClickListener(v -> {
            currentGenre = "Fantasy";
            updateGenreSelectionUI(genreFantasy);
            applyFilters();
        });

        genreSciFi.setOnClickListener(v -> {
            currentGenre = "Science Fiction";
            updateGenreSelectionUI(genreSciFi);
            applyFilters();
        });

        genreRomance.setOnClickListener(v -> {
            currentGenre = "Romance";
            updateGenreSelectionUI(genreRomance);
            applyFilters();
        });

        genreHorror.setOnClickListener(v -> {
            currentGenre = "Horror";
            updateGenreSelectionUI(genreHorror);
            applyFilters();
        });

        genreThriller.setOnClickListener(v -> {
            currentGenre = "Thriller";
            updateGenreSelectionUI(genreThriller);
            applyFilters();
        });
    }

    private void updateGenreSelectionUI(TextView selectedGenre) {
        Context context = requireContext();
        for (TextView view : genreViews) {
            view.setBackgroundResource(R.drawable.pill_background_unselected);
            view.setTextColor(context.getResources().getColor(android.R.color.black));
        }

        selectedGenre.setBackgroundResource(R.drawable.pill_background_selected);
        selectedGenre.setTextColor(context.getResources().getColor(android.R.color.white));
    }

    private void setupRecyclerView() {
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(layoutManager);

        bookAdapter = new BookAdapter(new ArrayList<>(), getContext());
        recyclerView.setAdapter(bookAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        applyFilters();
    }

    private void filterBooks(String query) {
        currentSearchQuery = query;
        applyFilters();
    }

    private void applyFilters() {
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        noResultsTextView.setVisibility(View.GONE);

        executor.execute(() -> {
            List<Book> filteredBooks = dataSource.getBooksByGenre(currentGenre);

            if (!currentSearchQuery.isEmpty()) {
                filteredBooks = filteredBooks.stream()
                        .filter(book -> book.getTitle().toLowerCase().contains(currentSearchQuery.toLowerCase()))
                        .collect(Collectors.toList());
            }

            if (filteredBooks.size() < 20) {
                try {
                    Thread.sleep(PROCESSING_DELAY_MS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            List<Book> finalFilteredBooks = filteredBooks;
            handler.post(() -> {
                bookAdapter.updateData(finalFilteredBooks);

                if (finalFilteredBooks.isEmpty()) {
                    noResultsTextView.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                } else {
                    noResultsTextView.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                }

                progressBar.setVisibility(View.GONE);
            });
        });
    }
}