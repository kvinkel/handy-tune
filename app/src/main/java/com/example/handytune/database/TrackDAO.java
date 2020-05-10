package com.example.handytune.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface TrackDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTrack(Track track);

    @Update
    int update(Track track);

    @Query("DELETE FROM track")
    public void nukeTracks();
}
