package com.example.handytune;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PlayListAdapter extends RecyclerView.Adapter<PlayListAdapter.PlaylistViewHolder> {

    ArrayList<String> numbersOfPlaylists;

    public PlayListAdapter(ArrayList<String> numbersOfPlaylists) {
        this.numbersOfPlaylists = numbersOfPlaylists;
    }

    @NonNull
    @Override
    public PlaylistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_playlist, parent, false);
        TextView textView = view.findViewById(R.id.rowItemPlaylist);
        final PlaylistViewHolder viewHolder = new PlaylistViewHolder(view, textView);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull PlaylistViewHolder holder, int position) {


    }

    @Override
    public int getItemCount() {
        return numbersOfPlaylists.size();
    }

    public static class PlaylistViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView textView;
        public View frameLayout;
        private Context context;

        public PlaylistViewHolder(View frameLayout, TextView v) {
            super(frameLayout);
            textView = v;
        }
    }
}




