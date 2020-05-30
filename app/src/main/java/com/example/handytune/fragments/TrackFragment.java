package com.example.handytune.fragments;

import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Toast;

import com.example.handytune.R;
import com.example.handytune.TrackAdapter;
import com.example.handytune.spotify.RetrofitClient;
import com.example.handytune.spotify.SpotifyService;
import com.example.handytune.spotify.model.Albums;
import com.example.handytune.spotify.model.Image;
import com.example.handytune.spotify.model.Item;
import com.example.handytune.spotify.model.Tracks;
import com.example.handytune.spotify.model.artist.Album;
import com.example.handytune.spotify.model.artist.Track;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TrackFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TrackFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ALBUM_ID = "albumId";
    private static final String ALBUM_IMAGE_URL = "albumImageUrl";
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private String albumId;
    private String albumImageUrl;

    public TrackFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param albumId Parameter of the album id to get the tracks from.
     * @param albumImageUrl Parameter of the album image url
     * @return A new instance of fragment TrackFragment.
     */
    public static TrackFragment newInstance(String albumId, String albumImageUrl) {
        TrackFragment fragment = new TrackFragment();
        Bundle args = new Bundle();
        args.putString(ALBUM_ID, albumId);
        args.putString(ALBUM_IMAGE_URL, albumImageUrl);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            albumId = getArguments().getString(ALBUM_ID);
            albumImageUrl = getArguments().getString(ALBUM_IMAGE_URL);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().setTitle("Tracks");
        ConstraintLayout layout = (ConstraintLayout) inflater.inflate(R.layout.fragment_track, container, false);
        recyclerView = (RecyclerView) layout.getViewById(R.id.trackFragRecyc);
        getTracksRetroCall(albumId);
        return layout;
    }

    private void getTracksRetroCall(String albumId) {
        SpotifyService service = RetrofitClient.getInstance().create(SpotifyService.class);
        Call<Tracks> call = service.getAlbumTracks(albumId, RetrofitClient.getAuthToken());
        call.enqueue(new Callback<Tracks>() {
            @Override
            public void onResponse(Call<Tracks> call, Response<Tracks> response) {
                System.out.println(response.raw().request().url());
                if (response.body() != null) {
                    Tracks tracks = response.body();
                    setUpTracks(tracks);
                } else {
                    Toast.makeText(getActivity(), response.headers().toString(), Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<Tracks> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setUpTracks(Tracks tracks) {
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        List<Item> items = tracks.getItems();
        List<Track> trackList = new ArrayList<>();

        for (Item i : items) {
            Track track = new Track();
            track.setName(i.getName());
            track.setExternalUrls(i.getExternalUrls());
            Album album = new Album();
            List<Image> images = new ArrayList<>();
            Image image = new Image();
            image.setUrl(albumImageUrl);
            images.add(image);
            album.setImages(images);
            track.setAlbum(album);
            trackList.add(track);
        }
        adapter = new TrackAdapter(getActivity(), trackList);
        recyclerView.setAdapter(adapter);
    }
}
