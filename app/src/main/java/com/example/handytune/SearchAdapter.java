package com.example.handytune;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.handytune.spotify.model.Image;
import com.example.handytune.spotify.model.Item;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {
    private SearchActivity.SearchItem[] dummyData;
    private List<Item> results;

    public SearchAdapter(List<Item> results) {
        this.results = results;
    }

    public SearchAdapter(SearchActivity.SearchItem[] dummyData) {
        this.dummyData = dummyData;
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_row_item, parent, false);
        TextView textView = view.findViewById(R.id.findMusicResult);
        ImageView imageView = view.findViewById(R.id.findMusicImage);
        final SearchViewHolder viewHolder = new SearchViewHolder(view, textView, imageView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        holder.getTextView().setText(results.get(position).getName());
        List<Image> images = results.get(position).getImages();
        String imageUrl = "";
        if (images.size() > 0) {
            imageUrl = images.get(0).getUrl();
        }
        Glide.with(holder.getContext()).clear(holder.getImageView());
        Glide.with(holder.getContext())
                .load(imageUrl)
                .placeholder(R.drawable.vader)
                .into(holder.getImageView());
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public static class SearchViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        private ImageView imageView;
        private Context context;

        public SearchViewHolder(@NonNull final View itemView, TextView textView, ImageView imageView) {
            super(itemView);
            this.textView = textView;
            this.imageView = imageView;
            context = itemView.getContext();
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, HomeActivity.class);
                    context.startActivity(intent);
                }
            });
        }

        public TextView getTextView() {
            return textView;
        }

        public ImageView getImageView() {
            return imageView;
        }

        public Context getContext() {
            return context;
        }
    }
}
