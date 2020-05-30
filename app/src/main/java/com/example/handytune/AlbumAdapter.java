package com.example.handytune;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.AlbumViewHolder> {

    @NonNull
    @Override
    public AlbumViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class AlbumViewHolder extends RecyclerView.ViewHolder {
        private Context context;
        private TextView textView;

        public AlbumViewHolder(@NonNull Context context, TextView textView,  View itemView) {
            super(itemView);
            this.textView = textView;
        }
    }
}
