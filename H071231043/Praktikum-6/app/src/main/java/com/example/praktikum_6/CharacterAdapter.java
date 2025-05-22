package com.example.praktikum_6;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class CharacterAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private enum ViewType { ITEM, LOADING }
    private final List<Character> characterList;
    private OnLoadMoreListener onLoadMoreListener;
    private boolean isLoadingAdded = false;
    private boolean hasMoreData = true;

    public interface OnLoadMoreListener {
        void onLoadMore();
    }

    public CharacterAdapter(List<Character> characterList) {
        this.characterList = characterList;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == ViewType.ITEM.ordinal()) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.character_item, parent, false);
            return new CharacterViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_load_more, parent, false);
            return new LoadMoreViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof CharacterViewHolder) {
            Character character = characterList.get(position);
            ((CharacterViewHolder) holder).bind(character);
        } else if (holder instanceof LoadMoreViewHolder) {
            LoadMoreViewHolder loadMoreViewHolder = (LoadMoreViewHolder) holder;

            if (hasMoreData) {
                loadMoreViewHolder.btnLoadMore.setVisibility(View.VISIBLE);
                loadMoreViewHolder.progressBar.setVisibility(View.GONE);

                loadMoreViewHolder.btnLoadMore.setOnClickListener(v -> {
                    if (onLoadMoreListener != null) {
                        loadMoreViewHolder.btnLoadMore.setVisibility(View.GONE);
                        loadMoreViewHolder.progressBar.setVisibility(View.VISIBLE);
                        onLoadMoreListener.onLoadMore();
                    }
                });
            } else {
                loadMoreViewHolder.btnLoadMore.setVisibility(View.GONE);
                loadMoreViewHolder.progressBar.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return characterList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position == characterList.size() - 1 && isLoadingAdded)
                ? ViewType.LOADING.ordinal() : ViewType.ITEM.ordinal();
    }

    public void addLoadingFooter() {
        isLoadingAdded = true;
        notifyItemInserted(characterList.size() - 1);
    }

    public void removeLoadingFooter() {
        if (isLoadingAdded) {
            isLoadingAdded = false;
            int position = characterList.size() - 1;
            characterList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        characterList.clear();
        notifyDataSetChanged();
    }

    public void setHasMoreData(boolean hasMoreData) {
        this.hasMoreData = hasMoreData;
        if (isLoadingAdded) {
            notifyItemChanged(characterList.size() - 1);
        }
    }

    public static class CharacterViewHolder extends RecyclerView.ViewHolder {
        private final ImageView avatarImageView;
        private final TextView nameTextView;
        private final TextView speciesTextView;
        private final TextView statusTextView;
        private final CardView cardView;

        public CharacterViewHolder(@NonNull View itemView) {
            super(itemView);
            avatarImageView = itemView.findViewById(R.id.iv_avatar);
            nameTextView = itemView.findViewById(R.id.tv_name);
            speciesTextView = itemView.findViewById(R.id.tv_species);
            statusTextView = itemView.findViewById(R.id.tv_status);
            cardView = itemView.findViewById(R.id.card_view);
        }

        public void bind(final Character character) {
            Picasso.get().load(character.getImage()).into(avatarImageView);
            nameTextView.setText(character.getName());
            speciesTextView.setText(character.getSpecies());
            statusTextView.setText(character.getStatus());

            cardView.setOnClickListener(v -> {
                Intent intent = new Intent(itemView.getContext(), DetailActivity.class);
                intent.putExtra("CHARACTER_ID", character.getId());
                itemView.getContext().startActivity(intent);
            });
        }
    }
    public static class LoadMoreViewHolder extends RecyclerView.ViewHolder {
        private final Button btnLoadMore;
        private final ProgressBar progressBar;
        public LoadMoreViewHolder(@NonNull View itemView) {
            super(itemView);
            btnLoadMore = itemView.findViewById(R.id.btnLoadMore);
            progressBar = itemView.findViewById(R.id.progressBar);
        }
    }
}