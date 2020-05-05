package com.example.handytune.database;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "album")
public class Album {
    @PrimaryKey
    public int uid;
    public int albumId;
    public String albumName;
    public String url;
    public String imageUrl;

}
