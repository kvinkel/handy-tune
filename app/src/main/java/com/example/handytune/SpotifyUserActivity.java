package com.example.handytune;

import android.os.Bundle;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.handytune.spotify.RetrofitClient;
import com.example.handytune.spotify.SpotifyService;
import com.example.handytune.spotify.model.UserSearchResult;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SpotifyUserActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PlaylistAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private TextView username;
    private TextView userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_user);
        recyclerView = findViewById(R.id.userPlaylistView);
        recyclerView.setHasFixedSize(true);

        adapter = new PlaylistAdapter(generatePlaylistForTesting(11));

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        username = findViewById(R.id.usernameView);
        userId = findViewById(R.id.userId);
        SearchView searchView = (SearchView) findViewById(R.id.userSearchField);
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

        Call<UserSearchResult> call = service.searchUser(query);

        call.enqueue(new Callback<UserSearchResult>() {
            @Override
            public void onResponse(Call<UserSearchResult> call, Response<UserSearchResult> response) {
                System.out.println(response.raw().request().url());
                if (response.body() != null) {
                    UserSearchResult userResult = response.body();
                    userId.setText(userResult.getId());
                    username.setText(userResult.getDisplayName());
                } else {
                    Toast.makeText(SpotifyUserActivity.this, response.headers().toString(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<UserSearchResult> call, Throwable t) {
                Toast.makeText(SpotifyUserActivity.this, "Search failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void generateResultList(UserSearchResult result) {

    }

    private ArrayList<String> generatePlaylistForTesting ( int amount){
        ArrayList<String> arrayList = new ArrayList<>();

        String s = "My playlist ";
        for (int i = 1; i < amount; i++) {
            arrayList.add(s + i);
        }
        return arrayList;
    }




}
