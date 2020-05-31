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

    private AddToPlaylistFragment addToPlaylistFragment;
    private FragmentManager manager;
    private FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        String resultType = getIntent().getExtras().getString(SearchActivity.ResultTypes.RESULT_TYPE);
        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();

        String itemId = getIntent().getExtras().getString(SearchActivity.ResultTypes.ITEM_ID);
        String name = getIntent().getExtras().getString(SearchActivity.ResultTypes.NAME);
        String imageUrl = getIntent().getExtras().getString(SearchActivity.ResultTypes.IMAGE_URL);
        String externalUrl = getIntent().getExtras().getString(SearchActivity.ResultTypes.EXTERNAL_TRACK_URL);
        String openInAppUrl = getIntent().getExtras().getString(SearchActivity.ResultTypes.OPEN_IN_SPOTIFY_URL);


        switch (resultType) {
            case SearchActivity.ResultTypes.ARTIST:
                if (savedInstanceState == null) {
                    ArtistFragment artistFragment = ArtistFragment.newInstance(itemId, name, imageUrl);
                    if (findViewById(R.id.frame1) != null) { // check for tablet layout
                        transaction.add(R.id.frame1, artistFragment, "ARTIST");
                    } else {
                        transaction.add(R.id.frame, artistFragment, "ARTIST");
                    }
                    transaction.commit();
                }
                break;
            case SearchActivity.ResultTypes.ALBUM:
                if (savedInstanceState == null) {
                    TrackFragment trackFragment = TrackFragment.newInstance(itemId, imageUrl);
                    transaction.add(R.id.frame, trackFragment);
                    transaction.commit();
                }
                break;
            case SearchActivity.ResultTypes.TRACK:
                openAddToPlaylistFragment(itemId, imageUrl, name, externalUrl, openInAppUrl, false);
                break;
        }
    }

    public void updateFragment() {

        addToPlaylistFragment.updateAdapter();

    }

    public void openAddToPlaylistFragment(String id, String name, String imageUrl, String externalUrl, String openInApp, Boolean addToStack) {
        int frameId = R.id.frame;
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        addToPlaylistFragment = AddToPlaylistFragment.newInstance(id, name, imageUrl, externalUrl, openInApp);
        if(findViewById(R.id.frame2) != null && manager.findFragmentByTag("ARTIST") != null) {
            frameId = R.id.frame2;
            addToStack = false;
        }
        if (addToStack) {
            transaction.replace(frameId, addToPlaylistFragment).addToBackStack(null);
        } else {
            transaction.replace(frameId, addToPlaylistFragment);
        }
        transaction.commit();
    }

}
