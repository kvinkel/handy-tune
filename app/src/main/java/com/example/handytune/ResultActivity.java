package com.example.handytune;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.handytune.fragments.ArtistFragment;
import com.example.handytune.fragments.TrackFragment;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        String resultType = getIntent().getExtras().getString(SearchActivity.ResultTypes.RESULT_TYPE);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();


        switch (resultType) {
            case SearchActivity.ResultTypes.ARTIST:
                String artistId = getIntent().getExtras().getString(SearchActivity.ResultTypes.ITEM_ID);
                String artistName = getIntent().getExtras().getString(SearchActivity.ResultTypes.NAME);
                String artistImageUrl = getIntent().getExtras().getString(SearchActivity.ResultTypes.IMAGE_URL);
                if (savedInstanceState == null) {
                    ArtistFragment artistFragment = ArtistFragment.newInstance(artistId, artistName, artistImageUrl);
                    if (findViewById(R.id.frame1) != null) { // check for tablet layout
                        transaction.add(R.id.frame1, artistFragment);
                    } else {
                        transaction.add(R.id.frame, artistFragment);
                    }
                    transaction.commit();
                }
                break;
            case SearchActivity.ResultTypes.ALBUM:
                String albumId = getIntent().getExtras().getString(SearchActivity.ResultTypes.ITEM_ID);
                String albumImageUrl = getIntent().getExtras().getString(SearchActivity.ResultTypes.IMAGE_URL);
                if (savedInstanceState == null) {
                    TrackFragment trackFragment = TrackFragment.newInstance(albumId, albumImageUrl);
                    transaction.add(R.id.frame, trackFragment);
                    transaction.commit();
                }
                break;
            case SearchActivity.ResultTypes.TRACK:
                // TODO Go to playlist fragment
                break;
        }
    }
}
