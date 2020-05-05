package com.example.handytune.database;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

    @Entity(tableName = "track")
    public class Track {
        @PrimaryKey
        public int uid;
        public int trackId;
        public String name;
        public String url;

    }
