package com.example.handytune;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.handytune.spotify.RetrofitClient;

import java.util.Random;

public class SearchActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        recyclerView = (RecyclerView) findViewById(R.id.searchRecyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        SearchItem[] results = generatePlaceholderResults(50);
        adapter = new SearchAdapter(results);
        recyclerView.setAdapter(adapter);

        SearchView searchView = (SearchView) findViewById(R.id.searchMusicView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                RetrofitClient.getInstance();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter = new SearchAdapter(new SearchItem[0]);
                recyclerView.setAdapter(adapter);
                return false;
            }
        });
    }

    public SearchItem[] generatePlaceholderResults(int amount) {
        SearchItem[] items = new SearchItem[amount];
        Random random = new Random();
        for (int i = 0; i < amount; i++) {
            items[i] = new SearchItem("Handy Tune", "https://i.picsum.photos/id/" + random.nextInt(1000) + "/200/200.jpg");
        }
        return items;
    }

    public class SearchItem {
        private String result;
        private ResultType type;
        private String imageUrl;
        private String href;
        private String externalUrl;

        public SearchItem(String result, String type, String imageUrl, String href, String externalUrl) {
            this.result = result;
            this.type = ResultType.valueOf(type.toUpperCase());
            this.imageUrl = imageUrl;
            this.href = href;
            this.externalUrl = externalUrl;
        }

        public SearchItem(String result, String imageUrl) {
            this.result = result;
            this.imageUrl = imageUrl;
        }

        public String getResult() {
            return result;
        }

        public ResultType getType() {
            return type;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public String getHref() {
            return href;
        }

        public String getExternalUrl() {
            return externalUrl;
        }
    }

    public enum ResultType {
        ARTIST,
        ALBUM,
        TRACK
    }
}
