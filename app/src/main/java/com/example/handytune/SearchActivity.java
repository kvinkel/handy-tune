package com.example.handytune;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.handytune.spotify.RetrofitClient;
import com.example.handytune.spotify.SpotifyService;
import com.example.handytune.spotify.model.Item;
import com.example.handytune.spotify.model.MusicSearchResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        if(RetrofitClient.getAuthToken().isEmpty()) {
            RetrofitClient.noLoginAlert(SearchActivity.this);
        }

        SearchView searchView = (SearchView) findViewById(R.id.searchMusicView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                retroSearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void retroSearch(String query) {
        SpotifyService service = RetrofitClient.getInstance().create(SpotifyService.class);
        String encodedQuery = query.replace(' ', '+');
        Call<MusicSearchResult> call = service.searchMusic(encodedQuery, "artist,album,track", RetrofitClient.getAuthToken());

        // Using enqueue() to make the request asynchronous and make Retrofit handle it in a background thread
        call.enqueue(new Callback<MusicSearchResult>() {

            @Override
            public void onResponse(Call<MusicSearchResult> call, Response<MusicSearchResult> response) {
                System.out.println(response.raw().request().url());
                if (response.body() != null) {
                    generateResultList(response.body());
                } else {
                    Toast.makeText(SearchActivity.this, response.headers().toString(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<MusicSearchResult> call, Throwable t) {
                Toast.makeText(SearchActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void generateResultList(MusicSearchResult result) {
        recyclerView = (RecyclerView) findViewById(R.id.searchRecyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        List<Item> itemResults = new ArrayList<>();
        itemResults.addAll(result.getArtists().getItems());
        itemResults.addAll(result.getAlbums().getItems());
        itemResults.addAll(result.getTracks().getItems());
        adapter = new SearchAdapter(SearchActivity.this, itemResults);
        recyclerView.setAdapter(adapter);
    }

    public static class ResultTypes {
        public static final String RESULT_TYPE = "RESULT_TYPE";
        public static final String ITEM_ID = "ITEM_ID";
        public static final String ARTIST = "ARTIST";
        public static final String ALBUM = "ALBUM";
        public static final String TRACK = "TRACK";
        public static final String NAME = "NAME";
        public static final String IMAGE_URL = "IMAGE_URL";
        public static final String EXTERNAL_TRACK_URL = "EXTERNAL_TRACK_URL";
        public static final String OPEN_IN_SPOTIFY_URL = "OPEN_IN_SPOTIFY_URL";
    }

    public SearchItem[] generatePlaceholderResults(int amount) {
        SearchItem[] items = new SearchItem[amount];
        Random random = new Random();
        for (int i = 0; i < amount; i++) {
            items[i] = new SearchItem("Handy Tune", "https://i.picsum.photos/id/" + random.nextInt(1000) + "/200/200.jpg");
        }
        return items;
    }

    // Placeholder SearchItem for dummy data
    public class SearchItem {
        private String result;
        private String imageUrl;

        public SearchItem(String result, String imageUrl) {
            this.result = result;
            this.imageUrl = imageUrl;
        }

        public String getResult() {
            return result;
        }


        public String getImageUrl() {
            return imageUrl;
        }

    }

}
