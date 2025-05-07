package com.example.praktikum_3;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.FeedViewHolder> {

    private Context context;
    private List<FeedPost> postList;

    public FeedAdapter(Context context, List<FeedPost> postList) {
        this.context = context;
        this.postList = postList;
    }

    @NonNull
    @Override
    public FeedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_feed, parent, false);
        return new FeedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedViewHolder holder, int position) {
        FeedPost post = postList.get(position);

        holder.username.setText(post.getUsername());
        holder.usernameCaption.setText(post.getUsername());

        holder.postImage.setImageURI(Uri.parse(post.getImageUri()));

        holder.caption.setText(post.getCaption());
        holder.likes.setText(post.getLikesCount() + " suka");
        holder.timePosted.setText(post.getTimePosted());

        if (post.getProfileImageUri() != null) {
            holder.profileImage.setImageURI(Uri.parse(post.getProfileImageUri()));
        }

        View.OnClickListener goToProfile = v -> {
            Intent intent = new Intent(context, ProfileActivity.class);
            intent.putExtra("username", post.getUsername());
            context.startActivity(intent);
        };

        holder.username.setOnClickListener(goToProfile);
        holder.profileImage.setOnClickListener(goToProfile);
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public static class FeedViewHolder extends RecyclerView.ViewHolder {
        ImageView postImage, profileImage;
        ImageView btnLike, btnComment, btnShare, btnSave, btnOptions;
        TextView username, usernameCaption, caption, likes, timePosted;

        public FeedViewHolder(@NonNull View itemView) {
            super(itemView);
            // View yang sudah ada
            postImage = itemView.findViewById(R.id.image_post);
            profileImage = itemView.findViewById(R.id.image_profile);
            username = itemView.findViewById(R.id.text_username);
            caption = itemView.findViewById(R.id.text_caption);

            // View baru
            usernameCaption = itemView.findViewById(R.id.text_username_caption);
            likes = itemView.findViewById(R.id.text_likes);
            timePosted = itemView.findViewById(R.id.text_time);

            btnLike = itemView.findViewById(R.id.btn_like);
            btnComment = itemView.findViewById(R.id.btn_comment);
            btnShare = itemView.findViewById(R.id.btn_share);
            btnSave = itemView.findViewById(R.id.btn_save);
            btnOptions = itemView.findViewById(R.id.btn_options);
        }
    }
}