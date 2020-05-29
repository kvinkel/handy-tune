package com.example.handytune;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.handytune.fragments.AddToPlaylistFragment;
import com.example.handytune.fragments.ArtistFragment;
import com.example.handytune.fragments.TrackFragment;

public class ResultActivity extends AppCompatActivity {

    AddToPlaylistFragment addToPlaylistFragment;
    FragmentManager manager;
    FragmentTransaction transaction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        String resultType = getIntent().getExtras().getString(SearchActivity.ResultTypes.RESULT_TYPE);
         manager = getSupportFragmentManager();
         transaction = manager.beginTransaction();

        String itemId = getIntent().getExtras().getString(SearchActivity.ResultTypes.ITEM_ID);
        String name = getIntent().getExtras().getString(SearchActivity.ResultTypes.NAME);
        String artistImageUrl = getIntent().getExtras().getString(SearchActivity.ResultTypes.IMAGE_URL);
        String trackName = getIntent().getExtras().getString(SearchActivity.ResultTypes.TRACK);


        switch (resultType) {
            case SearchActivity.ResultTypes.ARTIST:
                ArtistFragment artistFragment = ArtistFragment.newInstance(itemId, name, artistImageUrl);
                transaction.add(R.id.frame, artistFragment);
                transaction.commit();
                break;
            case SearchActivity.ResultTypes.ALBUM:
                String albumId = getIntent().getExtras().getString(SearchActivity.ResultTypes.ITEM_ID);
                String albumImageUrl = getIntent().getExtras().getString(SearchActivity.ResultTypes.IMAGE_URL);
                TrackFragment trackFragment = TrackFragment.newInstance(albumId, albumImageUrl);
                transaction.add(R.id.frame, trackFragment);
                transaction.commit();
                break;
            case SearchActivity.ResultTypes.TRACK:
                addToPlaylistFragment= AddToPlaylistFragment.newInstance(itemId, artistImageUrl,trackName);
                transaction.add(R.id.frame, addToPlaylistFragment);
                transaction.commit();
                break;
        }
    }

    public void updateFragment(){

        addToPlaylistFragment.updateAdapter();

    }

}
