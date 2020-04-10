package com.example.handytune;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class SpotifyUserActivity extends AppCompatActivity {

    Intent userSearchIntent = new Intent(this, SpotifyUserActivity.class);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_user);



        findViewById(R.id.userSearchField).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchUser();
            }
        });
    }

    public void searchUser () {

    }
}
