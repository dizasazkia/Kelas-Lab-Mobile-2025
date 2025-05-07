package com.example.praktikum_3;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.praktikum_3.Highlight;

import java.util.List;

public class HighlightAdapter extends RecyclerView.Adapter<HighlightAdapter.HighlightViewHolder> {
    private final List<Highlight> highlightList;
    private final Context context;
    private final String currentUsername;
    private final String currentProfileUri;

    public HighlightAdapter(Context context, List<Highlight> highlightList, String username, String profileUri) {
        this.context = context;
        this.highlightList = highlightList;
        this.currentUsername = username;
        this.currentProfileUri = profileUri;
    }

    @NonNull
    @Override
    public HighlightViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_highlight, parent, false);
        return new HighlightViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HighlightViewHolder holder, int position) {
        Highlight highlight = highlightList.get(position);
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), highlight.getImageResId());
        RoundedBitmapDrawable roundedDrawable = RoundedBitmapDrawableFactory.create(context.getResources(), bitmap);
        roundedDrawable.setCircular(true);
        holder.imageView.setImageDrawable(roundedDrawable);

        holder.titleView.setText(highlight.getTitle());

        holder.imageView.setOnClickListener(v -> {
            Intent intent = new Intent(context, HighlightDetailActivity.class);
            intent.putExtra("imageResId", highlight.getImageResId());
            intent.putExtra("title", highlight.getTitle());
            intent.putExtra("username", currentUsername);
            intent.putExtra("profileUri", currentProfileUri);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return highlightList.size();
    }

    static class HighlightViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView titleView;

        public HighlightViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.highlightImage);
            titleView = itemView.findViewById(R.id.highlightTitle);
        }
    }
}
