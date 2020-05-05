package com.example.handytune.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "playlist")
    public class Playlist implements Serializable {
        @PrimaryKey
        public int userId;


}
