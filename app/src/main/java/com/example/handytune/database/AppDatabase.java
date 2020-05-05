package com.example.handytune.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Album.class, Track.class, Playlist.class }, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract AlbumDAO albumDAO();
    public abstract TrackDAO trackDAO();
    public abstract PlaylistDAO playlistDAO();

}
