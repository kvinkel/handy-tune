package com.example.handytune.fragments;

import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.handytune.R;
import com.example.handytune.TrackAdapter;
import com.example.handytune.spotify.RetrofitClient;
import com.example.handytune.spotify.SpotifyService;
import com.example.handytune.spotify.model.artist.TopTracks;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ArtistFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ArtistFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ITEM_ID = "itemId";
    private static final String NAME = "name";
    private String itemId;
    private String name;
    private TextView artistView;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    public ArtistFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param itemId Item id for the search result.
     * @return A new instance of fragment ArtistFragment.
     */
    public static ArtistFragment newInstance(String itemId, String name) {
        ArtistFragment fragment = new ArtistFragment();
        Bundle args = new Bundle();
        args.putString(ITEM_ID, itemId);
        args.putString(NAME, name);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            itemId = getArguments().getString(ITEM_ID);
            name = getArguments().getString(NAME);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ConstraintLayout layout = (ConstraintLayout) inflater.inflate(R.layout.fragment_artist, container, false);
        artistView = (TextView) layout.getViewById(R.id.artistTitle);
        recyclerView = (RecyclerView) layout.getViewById(R.id.artistTrackRecycler);
        artistView.setText(name);
        retroCall(itemId);
        return layout;
    }

    private void retroCall(String itemId) {
        SpotifyService service = RetrofitClient.getInstance().create(SpotifyService.class);
        Call<TopTracks> call = service.topTracks(itemId, "US", RetrofitClient.getAuthToken());

        // Using enqueue() to make the request asynchronous and make Retrofit handle it in a background thread
        call.enqueue(new Callback<TopTracks>() {

            @Override
            public void onResponse(Call<TopTracks> call, Response<TopTracks> response) {
                System.out.println(response.raw().request().url());
                if (response.body() != null) {
                    TopTracks topTracks = response.body();
                    generateTrackList(topTracks);
                } else {
                    Toast.makeText(getActivity(), response.headers().toString(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<TopTracks> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void generateTrackList(TopTracks topTracks) {
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new TrackAdapter(getActivity(), topTracks.getTracks());
        recyclerView.setAdapter(adapter);
    }
}
