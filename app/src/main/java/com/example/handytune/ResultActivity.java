package com.example.handytune;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.handytune.fragments.ArtistFragment;

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
                String itemId = getIntent().getExtras().getString(SearchActivity.ResultTypes.ITEM_ID);
                String artistName = getIntent().getExtras().getString(SearchActivity.ResultTypes.NAME);
                String artistImageUrl = getIntent().getExtras().getString(SearchActivity.ResultTypes.ARTIST_IMAGE_URL);
                ArtistFragment fragment = ArtistFragment.newInstance(itemId, artistName, artistImageUrl);
                transaction.add(R.id.artistFrame, fragment);
                transaction.commit();
                break;
            case SearchActivity.ResultTypes.ALBUM:
                // TODO Go to album fragment
                break;
            case SearchActivity.ResultTypes.TRACK:
                // TODO Go to playlist fragment
                break;
        }
    }
}
