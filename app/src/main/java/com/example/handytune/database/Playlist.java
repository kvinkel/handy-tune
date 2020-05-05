package com.example.handytune.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "playlist")
    public class Playlist {
        @PrimaryKey
        public int uid;


}
