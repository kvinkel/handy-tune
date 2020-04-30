package com.example.handytune;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.Arrays;
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

    }

    public SearchItem[] generatePlaceholderResults(int amount) {
        SearchItem[] items = new SearchItem[amount];
        Random random = new Random();
        for (int i = 0; i < amount; i++) {
            items[i] = new SearchItem("Handy Tune", "https://i.picsum.photos/id/"+ random.nextInt(1000) +"/200/200.jpg");
        }
        return items;
    }

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
