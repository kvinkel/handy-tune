package com.example.handytune;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.handytune.database.PlaylistWithTracks;
import com.example.handytune.database.Track;


import java.util.ArrayList;

public class AddToPlaylistAdapter extends RecyclerView.Adapter<AddToPlaylistAdapter.PlaylistViewHolder> {

    private ArrayList<PlaylistWithTracks> playlistWithTracks;
    private DbRepository dbRepository;
    private Track track;

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
        Button createPlaylistBtn = (Button) view.findViewById(R.id.createPlaylistBtn_in_activity);
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
        private ArrayList<PlaylistWithTracks> playlistWithTracks;
        private DbRepository dbRepository;
        private Track track;
        private Thread readThread;
        private String rowName;
        private ArrayList<Track> listOfTracks = new ArrayList<>();


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
            rowName = playlistWithTracks.get(position).playlist.getPlaylistName();
            track.setBelongToPlaylistName(rowName);
            dbRepository.insertTrack(track);

            listOfTracks.clear();

            startThreadForReadDataInDatabase();

            for (int i = 0; i < playlistWithTracks.get(position).tracks.size(); i++) {
                listOfTracks.add(playlistWithTracks.get(position).tracks.get(i));
            }
            checkIfTrackExistsInDb(track);
        }

        private void checkIfTrackExistsInDb(Track track)
        {
            for (int i = 0; i < listOfTracks.size(); i++) {

                if(listOfTracks.get(i).getTrackName().equals(track.getTrackName())){
                    Toast.makeText(context.getApplicationContext(), track.getTrackName()+ " to " + rowName+" playlist", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(context.getApplicationContext(), "Could not add track to playlist", Toast.LENGTH_LONG).show();
                }
            }
        }

        private void startThreadForReadDataInDatabase() {
            //Create a thread
            readThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    playlistWithTracks = (ArrayList<PlaylistWithTracks>) dbRepository.getTrackWithPlaylists();
                }
            });
            readThread.start();

            try {
                readThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void setPlaylistWithTracks(ArrayList<PlaylistWithTracks> playlistWithTracks) {
        this.playlistWithTracks = playlistWithTracks;
    }


}



