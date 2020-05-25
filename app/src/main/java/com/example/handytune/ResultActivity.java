package com.example.handytune;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.handytune.fragments.AddToPlaylistFragment;
import com.example.handytune.fragments.ArtistFragment;
import com.example.handytune.fragments.CreatePlaylistFragment;

public class ResultActivity extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        String resultType = getIntent().getExtras().getString(SearchActivity.ResultTypes.RESULT_TYPE);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        String itemId = getIntent().getExtras().getString(SearchActivity.ResultTypes.ITEM_ID);
        String artistName = getIntent().getExtras().getString(SearchActivity.ResultTypes.NAME);
        String artistImageUrl = getIntent().getExtras().getString(SearchActivity.ResultTypes.ARTIST_IMAGE_URL);
        String trackName = getIntent().getExtras().getString(SearchActivity.ResultTypes.TRACK);


        switch (resultType) {
            case SearchActivity.ResultTypes.ARTIST:
                ArtistFragment artistFragment = ArtistFragment.newInstance(itemId, artistName, artistImageUrl);
                transaction.add(R.id.artistFrame, artistFragment);
                transaction.commit();
                break;
            case SearchActivity.ResultTypes.ALBUM:
                // TODO Go to track fragment
                break;
            case SearchActivity.ResultTypes.TRACK:
                System.out.println("trackname in result activity " + trackName+" ¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤");
                System.out.println("artistImageUrl in result activity " + artistImageUrl+" ¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤");
                AddToPlaylistFragment addToPlaylistFragment= AddToPlaylistFragment.newInstance(itemId, artistImageUrl,trackName);
                transaction.add(R.id.artistFrame, addToPlaylistFragment);
                transaction.commit();
                break;
        }
    }

}
