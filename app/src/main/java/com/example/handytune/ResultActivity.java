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
        String imageUrl = getIntent().getExtras().getString(SearchActivity.ResultTypes.IMAGE_URL);
        String externalUrl = getIntent().getExtras().getString(SearchActivity.ResultTypes.EXTERNAL_TRACK_URL);
        String openInAppUrl = getIntent().getExtras().getString(SearchActivity.ResultTypes.OPEN_IN_SPOTIFY_URL);


        switch (resultType) {
            case SearchActivity.ResultTypes.ARTIST:
                ArtistFragment artistFragment = ArtistFragment.newInstance(itemId, name, imageUrl);
                transaction.add(R.id.frame, artistFragment);
                transaction.commit();
                break;
            case SearchActivity.ResultTypes.ALBUM:
                TrackFragment trackFragment = TrackFragment.newInstance(itemId, imageUrl);
                transaction.add(R.id.frame, trackFragment);
                transaction.commit();
                break;
            case SearchActivity.ResultTypes.TRACK:
                openAddtoPlaylistFragment(itemId, imageUrl,name,externalUrl,openInAppUrl);
                break;
        }
    }

    public void updateFragment(){

        addToPlaylistFragment.updateAdapter();

    }

    public void openAddtoPlaylistFragment(String id,String name , String imageUrl, String externalUrl, String openInApp){
                            FragmentManager manager = getSupportFragmentManager();
                    FragmentTransaction transaction = manager.beginTransaction();
                    addToPlaylistFragment= AddToPlaylistFragment.newInstance(id,name,imageUrl,externalUrl,openInApp);
                    transaction.replace(R.id.frame, addToPlaylistFragment);
                    transaction.commit();
    }

}
