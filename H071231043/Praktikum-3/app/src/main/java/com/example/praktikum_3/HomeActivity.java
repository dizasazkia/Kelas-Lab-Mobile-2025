package com.example.praktikum_3;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private FeedAdapter feedAdapter;
    private List<FeedPost> postList;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        bottomNavigationView = findViewById(R.id.bottomNavView);

        if (bottomNavigationView != null) {
            bottomNavigationView.setActiveScreen(BottomNavigationView.SCREEN_HOME);
        }

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        postList = DataSource.getDummyFeedPosts(getPackageName());
        feedAdapter = new FeedAdapter(this, postList);
        recyclerView.setAdapter(feedAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (bottomNavigationView != null) {
            bottomNavigationView.setActiveScreen(BottomNavigationView.SCREEN_HOME);
        }
    }
}