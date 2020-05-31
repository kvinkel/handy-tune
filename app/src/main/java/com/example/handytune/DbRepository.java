package com.example.handytune;

import android.content.Context;
import android.os.AsyncTask;

import androidx.room.Room;

import com.example.handytune.database.AppDatabase;
import com.example.handytune.database.Playlist;
import com.example.handytune.database.PlaylistWithTracks;
import com.example.handytune.database.Track;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class DbRepository {

    private String DB_NAME = "db_spotify";

    private AppDatabase database;

    public DbRepository(Context context) {
        database = Room.databaseBuilder(context, AppDatabase.class, DB_NAME).build();
    }


    public void insertPlaylist(
                               String playlistName
    ) {
        Playlist playlist = new Playlist();

        playlist.setPlaylistName(playlistName);
        insertPlaylist(playlist);
    }

    public void insertPlaylist(final Playlist playlist) {
        try {
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
                    database.playlistDAO().insertPlaylist(playlist);
                    return null;
                }
            }.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }




    public void insertTrack(String trackId,
                            String trackName, String externalTrackUrl, String openInAppTrackUrl, String albumImageUrl
    ,String belongToPlaylistName) {
        Track track = new Track();

        track.setTrackId(trackId);
        track.setTrackName(trackName);
        track.setExternalTrackUrl(externalTrackUrl);
        track.setOpenInAppTrackUrl(openInAppTrackUrl);
        track.setAlbumImageUrl(albumImageUrl);
        track.setBelongToPlaylistName(belongToPlaylistName);

        insertTrack(track);
    }

    public void insertTrack(final Track track) {
        try {
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
                    database.trackDAO().insertTrack(track);
                    return null;
                }
            }.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public List<Playlist> getAllPLaylists() {
        return database.playlistDAO().getAllPLaylists();
    }

    public void nukePlaylistInDatabase() {
        database.playlistDAO().nukePlaylist();
    }

    public void nukeTracksInDatabase() {
        database.trackDAO().nukeTracks();
    }

    public List<PlaylistWithTracks> getTrackWithPlaylists() {
       return database.playlistDAO().getTrackWithPlaylists();
    }

}
