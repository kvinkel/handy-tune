package com.example.handytune;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.handytune.database.PlaylistWithTracks;
import com.example.handytune.database.Track;


import java.util.ArrayList;

public class AddToPlaylistAdapter extends RecyclerView.Adapter<AddToPlaylistAdapter.PlaylistViewHolder> {


    private ArrayList<PlaylistWithTracks> playlistWithTracks;

    DbRepository dbRepository;
    Track track;


    private Context context;

    public AddToPlaylistAdapter(ArrayList<PlaylistWithTracks> playlistWithTracks, Track track, Context context) {
        this.context = context;
        this.playlistWithTracks = playlistWithTracks;
        this.track = track;
        dbRepository = new DbRepository(context);
    }

    @NonNull
    @Override
    public PlaylistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_playlist, parent, false);
        TextView textView = view.findViewById(R.id.rowItemPlaylist);
        Button createPlaylistBtn = (Button) view.findViewById(R.id.createPlaylistBtn);
        final PlaylistViewHolder viewHolder = new PlaylistViewHolder(playlistWithTracks, createPlaylistBtn, dbRepository, track, view, textView, context);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final PlaylistViewHolder holder, final int position) {
        holder.getTextView().setText(playlistWithTracks.get(position).playlist.getPlaylistName());
    }

    @Override
    public int getItemCount() {
        return playlistWithTracks.size();
    }

    public static class PlaylistViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        private TextView textView;
        private Context context;
        ArrayList<PlaylistWithTracks> playlistWithTracks;
        DbRepository dbRepository;
        Track track;


        public PlaylistViewHolder(ArrayList<PlaylistWithTracks> playlistWithTracks, Button createPlaylistBtn, DbRepository dbRepository, Track track, View frameLayout, TextView v, Context context) {
            super(frameLayout);
            textView = v;
            textView.setOnClickListener(this);
            this.playlistWithTracks = playlistWithTracks;
            this.dbRepository = dbRepository;
            this.track = track;
            this.context = context;
        }

        public TextView getTextView() {
            return textView;
        }

        @Override
        public void onClick(View v) {

            int position = getAdapterPosition();
            String rowName = playlistWithTracks.get(position).playlist.getPlaylistName();
            track.setBelongToPlaylistName(rowName);
            dbRepository.insertTrack(track);
        }
    }

    public void setPlaylistWithTracks(ArrayList<PlaylistWithTracks> playlistWithTracks) {
        this.playlistWithTracks = playlistWithTracks;
    }
}



