package com.example.praktikum_6;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.squareup.picasso.Picasso;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import retrofit2.Call;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {
    private ApiService apiService;
    private int characterId;
    private ImageView avatarImageView;
    private TextView nameTextView;
    private TextView statusTextView;
    private TextView speciesTextView;
    private TextView genderTextView;
    private TextView originTextView;
    private TextView locationTextView;
    private TextView episodeCountTextView;
    private ProgressBar progressBar;
    private ScrollView contentContainer;
    private View errorView;
    private ImageView retryImageView;
    private ExecutorService executorService;
    private Handler mainHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        executorService = Executors.newSingleThreadExecutor();
        mainHandler = new Handler(Looper.getMainLooper());

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Character Detail");

        avatarImageView = findViewById(R.id.iv_detail_avatar);
        nameTextView = findViewById(R.id.tv_detail_name);
        statusTextView = findViewById(R.id.tv_detail_status);
        speciesTextView = findViewById(R.id.tv_detail_species);
        genderTextView = findViewById(R.id.tv_detail_gender);
        originTextView = findViewById(R.id.tv_detail_origin);
        locationTextView = findViewById(R.id.tv_detail_location);
        episodeCountTextView = findViewById(R.id.tv_detail_episode_count);
        progressBar = findViewById(R.id.progress_bar_detail);
        contentContainer = findViewById(R.id.content_container);
        errorView = findViewById(R.id.error_view);
        retryImageView = findViewById(R.id.iv_retry);

        showLoading(true);

        characterId = getIntent().getIntExtra("CHARACTER_ID", -1);
        if (characterId == -1) {
            Toast.makeText(this, "Character ID not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        apiService = RetrofitClient.getClient().create(ApiService.class);
        loadCharacterDetail(characterId);

        retryImageView.setOnClickListener(v -> {
            if (isNetworkAvailable()) {
                showErrorState(false);
                showLoading(true);
                loadCharacterDetail(characterId);
            } else {
                Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showLoading(boolean isLoading) {
        progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        contentContainer.setVisibility(isLoading ? View.GONE : View.VISIBLE);
        errorView.setVisibility(View.GONE);
    }

    private void showErrorState(boolean show) {
        contentContainer.setVisibility(show ? View.GONE : View.VISIBLE);
        errorView.setVisibility(show ? View.VISIBLE : View.GONE);
        progressBar.setVisibility(View.GONE);
    }

    private void loadCharacterDetail(int id) {
        executorService.execute(() -> {
            final boolean[] isSuccess = {false};
            final Character[] character = {null};
            final String[] errorMessage = {null};

            try {
                Call<Character> call = apiService.getCharacterDetail(id);
                Response<Character> response = call.execute();
                if (response.isSuccessful() && response.body() != null) {
                    isSuccess[0] = true;
                    character[0] = response.body();
                } else {
                    errorMessage[0] = "Failed to load character details";
                }
            } catch (Exception e) {
                errorMessage[0] = "Failed to load character details";
            }

            mainHandler.post(() -> {
                showLoading(false);
                if (isSuccess[0]) {
                    displayCharacterDetails(character[0]);
                    showErrorState(false);
                } else {
                    showErrorState(true);
                    Toast.makeText(this, errorMessage[0], Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    private void displayCharacterDetails(Character character) {
        Picasso.get().load(character.getImage()).into(avatarImageView);
        nameTextView.setText(character.getName());
        statusTextView.setText("Status: " + character.getStatus());
        speciesTextView.setText("Species: " + character.getSpecies());
        genderTextView.setText("Gender: " + character.getGender());
        originTextView.setText("Origin: " + character.getOrigin().getName());
        locationTextView.setText("Location: " + character.getLocation().getName());
        int episodeCount = character.getEpisode() != null ? character.getEpisode().length : 0;
        episodeCountTextView.setText("Appears in " + episodeCount + " episodes");
        getSupportActionBar().setTitle(character.getName());
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (executorService != null) {
            executorService.shutdown();
        }
    }
}