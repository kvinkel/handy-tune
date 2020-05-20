package com.example.handytune.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = { Track.class, Playlist.class }, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract TrackDAO trackDAO();
    public abstract PlaylistDAO playlistDAO();

}
