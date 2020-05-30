package com.example.handytune;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.handytune.database.PlaylistWithTracks;
import com.example.handytune.database.Track;


import java.util.ArrayList;

public class PlaylistAdapter extends RecyclerView.Adapter<PlaylistAdapter.PlaylistViewHolder> {

    private ArrayList<PlaylistWithTracks> playlistWithTracks;

    private Context context;

    public PlaylistAdapter(ArrayList<PlaylistWithTracks> playlistWithTracks, Context context) {
        this.context = context;
        this.playlistWithTracks = playlistWithTracks;
    }

    @NonNull
    @Override
    public PlaylistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_playlist, parent, false);
        TextView textView = view.findViewById(R.id.rowItemPlaylist);
        final PlaylistViewHolder viewHolder = new PlaylistViewHolder(playlistWithTracks, view, textView, context);
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
        private View frameLayout;
        private Context context;
        ArrayList<PlaylistWithTracks> playlistWithTracks;
        private ArrayList<Track> listOfTracks = new ArrayList<>();

        public PlaylistViewHolder(ArrayList<PlaylistWithTracks> playlistWithTracks, View frameLayout, TextView v, Context context) {
            super(frameLayout);
            textView = v;
            textView.setOnClickListener(this);
            this.playlistWithTracks = playlistWithTracks;
            this.context = context;
        }

        public TextView getTextView() {
            return textView;
        }

        @Override
        public void onClick(View v) {

            int position = getAdapterPosition();
            String rowName = playlistWithTracks.get(position).playlist.getPlaylistName();
            System.out.println("Clicked on position : " + position + " ******************");
            System.out.println("Clicked on name : " + rowName + " ******************");

            listOfTracks.clear();

            System.out.println("This playlist has these tracks:  ");
            for (int i = 0; i < playlistWithTracks.get(position).tracks.size(); i++) {
                System.out.println(playlistWithTracks.get(position).tracks.get(i).getTrackName());
                listOfTracks.add(playlistWithTracks.get(position).tracks.get(i));
            }

            Intent intent = new Intent(context, ShowTrackFromPlaylistActivity.class);
            intent.putExtra("ListOfTracks", listOfTracks);
            v.getContext().startActivity(intent);
        }
    }
    public void setPlaylistWithTracks(ArrayList<PlaylistWithTracks> playlistWithTracks) {
        this.playlistWithTracks = playlistWithTracks;
    }
}



