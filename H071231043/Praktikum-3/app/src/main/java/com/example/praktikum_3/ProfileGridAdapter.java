package com.example.praktikum_3;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ProfileGridAdapter extends RecyclerView.Adapter<ProfileGridAdapter.GridViewHolder> {

    private final Context context;
    private final List<FeedPost> posts;

    public ProfileGridAdapter(Context context, List<FeedPost> posts) {
        this.context = context;
        this.posts = posts;
    }

    @NonNull
    @Override
    public GridViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_profile_grid, parent, false);
        return new GridViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GridViewHolder holder, int position) {
        FeedPost post = posts.get(position);
        holder.gridImage.setImageURI(Uri.parse(post.getImageUri()));

        holder.gridImage.setOnClickListener(v -> {
            Intent intent = new Intent(context, PostDetailActivity.class);
            intent.putExtra("imageUri", post.getImageUri());
            intent.putExtra("caption", post.getCaption());
            intent.putExtra("username", post.getUsername());
            intent.putExtra("profileUri", post.getProfileImageUri());
            intent.putExtra("likes", post.getLikesCount());
            intent.putExtra("timePosted", post.getTimePosted());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    static class GridViewHolder extends RecyclerView.ViewHolder {
        ImageView gridImage;

        public GridViewHolder(@NonNull View itemView) {
            super(itemView);
            gridImage = itemView.findViewById(R.id.gridImage);
        }
    }
}