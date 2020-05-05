package com.example.handytune.database;

import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

public interface TrackDAO {
    @Query("SELECT * FROM track where userId =1")
    Album getTrack();

    @Update
    int update(Track track);

    @Insert
    void insert(Track track);

    @Query("SELECT COUNT(*) from track")
    int countTracks();
}
