package com.example.praktikum_6;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements CharacterAdapter.OnLoadMoreListener {
    private ApiService apiService;
    private RecyclerView recyclerView;
    private CharacterAdapter characterAdapter;
    private List<Character> characterList = new ArrayList<>();
    private View errorView;
    private ImageView retryImageView;
    private ShapeableImageView headerImage;
    private int currentPage = 1;
    private int totalPages = 1;
    private boolean isLoading = false;
    private ExecutorService executorService;
    private Handler mainHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        executorService = Executors.newSingleThreadExecutor();
        mainHandler = new Handler(Looper.getMainLooper());
        apiService = RetrofitClient.getClient().create(ApiService.class);

        recyclerView = findViewById(R.id.recyclerView);
        errorView = findViewById(R.id.error_view);
        retryImageView = findViewById(R.id.iv_retry);
        headerImage = findViewById(R.id.headerImage);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        characterAdapter = new CharacterAdapter(characterList);
        characterAdapter.setOnLoadMoreListener(this);
        recyclerView.setAdapter(characterAdapter);

        loadCharacters(currentPage, true);

        retryImageView.setOnClickListener(v -> {
            if (isNetworkAvailable()) {
                showErrorState(false);
                currentPage = 1;
                loadCharacters(currentPage, true);
            } else {
                Toast.makeText(MainActivity.this, "No internet connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadCharacters(int page, boolean isFirstPage) {
        if (isLoading) return;
        isLoading = true;

        executorService.execute(() -> {
            Call<CharacterResponse> call = apiService.getCharacters(page);
            try {
                Response<CharacterResponse> response = call.execute();
                mainHandler.post(() -> handleResponse(response, isFirstPage));
            } catch (Exception e) {
                mainHandler.post(() -> handleError(isFirstPage, "Error: " + e.getMessage()));
            }
        });
    }

    private void handleResponse(Response<CharacterResponse> response, boolean isFirstPage) {
        if (!isFirstPage && !characterList.isEmpty() && characterList.get(characterList.size() - 1) == null) {
            characterList.remove(characterList.size() - 1);
            characterAdapter.notifyItemRemoved(characterList.size());
        }

        if (response.isSuccessful() && response.body() != null) {
            CharacterResponse characterResponse = response.body();
            totalPages = characterResponse.getInfo() != null ? characterResponse.getInfo().getPages() : 1;
            List<Character> result = characterResponse.getResults() != null ? characterResponse.getResults() : new ArrayList<>();

            if (isFirstPage) {
                characterList.clear();
                characterAdapter.clear();
                showErrorState(false);
            }

            int startPosition = characterList.size();
            characterList.addAll(result);
            characterAdapter.notifyItemRangeInserted(startPosition, result.size());

            if (currentPage < totalPages) {
                characterList.add(null);
                characterAdapter.addLoadingFooter();
                characterAdapter.setHasMoreData(true);
            } else {
                characterAdapter.setHasMoreData(false);
            }
        } else {
            if (isFirstPage) {
                showErrorState(true);
            }
            Toast.makeText(MainActivity.this, "Gagal mengambil data", Toast.LENGTH_SHORT).show();
        }
        isLoading = false;
    }

    private void handleError(boolean isFirstPage, String errorMessage) {
        if (!isFirstPage && !characterList.isEmpty() && characterList.get(characterList.size() - 1) == null) {
            characterList.remove(characterList.size() - 1);
            characterAdapter.notifyItemRemoved(characterList.size());
            characterAdapter.removeLoadingFooter();
        }
        if (isFirstPage) {
            showErrorState(true);
        }
        Toast.makeText(MainActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
        isLoading = false;
    }

    @Override
    public void onLoadMore() {
        if (!isLoading && currentPage < totalPages) {
            if (isNetworkAvailable()) {
                currentPage++;
                loadCharacters(currentPage, false);
            } else {
                mainHandler.post(() -> {
                    characterAdapter.setHasMoreData(false);
                    Toast.makeText(MainActivity.this, "No internet connection", Toast.LENGTH_SHORT).show();
                });
            }
        }
    }
    private void showErrorState(boolean show) {
        recyclerView.setVisibility(show ? View.GONE : View.VISIBLE);
        errorView.setVisibility(show ? View.VISIBLE : View.GONE);
        headerImage.setVisibility(show ? View.GONE : View.VISIBLE);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        return ni != null && ni.isConnected();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (executorService != null) {
            executorService.shutdown();
        }
    }
}
