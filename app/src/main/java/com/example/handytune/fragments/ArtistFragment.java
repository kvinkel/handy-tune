package com.example.handytune.fragments;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.handytune.R;
import com.example.handytune.TrackAdapter;
import com.example.handytune.spotify.RetrofitClient;
import com.example.handytune.spotify.SpotifyService;
import com.example.handytune.spotify.model.Albums;
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
    // the fragment initialization parameters, e.g. ITEM_ID
    private static final String ITEM_ID = "itemId";
    private static final String NAME = "name";
    private static final String ARTIST_IMAGE_URL = "artistImgUrl";

    private String itemId;
    private String name;
    private String artistImageUrl;
    private TextView artistView;
    private ImageView artistImg;
    private CardView albumRow1;
    private CardView albumRow2;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private Albums albums;


    public ArtistFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param itemId       Item id for the artist search result.
     * @param name         name for the artist search result.
     * @param artistImgUrl image url for the artist search result.
     * @return A new instance of fragment ArtistFragment.
     */
    public static ArtistFragment newInstance(String itemId, String name, String artistImgUrl) {
        ArtistFragment fragment = new ArtistFragment();
        Bundle args = new Bundle();
        args.putString(ITEM_ID, itemId);
        args.putString(NAME, name);
        args.putString(ARTIST_IMAGE_URL, artistImgUrl);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            itemId = getArguments().getString(ITEM_ID);
            name = getArguments().getString(NAME);
            artistImageUrl = getArguments().getString(ARTIST_IMAGE_URL);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ScrollView scroll = new ScrollView(getActivity());
        scroll.setBackgroundColor(ContextCompat.getColor(getActivity(), android.R.color.transparent));
        scroll.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        ConstraintLayout layout = (ConstraintLayout) inflater.inflate(R.layout.fragment_artist, container, false);
        // Artist
        artistView = (TextView) layout.getViewById(R.id.artistTitle);
        artistView.setText(name);
        artistImg = (ImageView) layout.getViewById(R.id.artistImage);
        Glide.with(getActivity()).clear(artistImg);
        Glide.with(getActivity())
                .load(artistImageUrl)
                .placeholder(R.drawable.music_note)
                .into(artistImg);
        // Albums
        albumRow1 = (CardView) layout.getViewById(R.id.albumView1);
        albumRow2 = (CardView) layout.getViewById(R.id.albumView2);
        albumRetroCall(itemId);

        // TODO add OnClickListener to moreAlbumsBtn from layout and redirect to album fragment
        layout.getViewById(R.id.moreAlbumsBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.frame, AlbumFragment.newInstance(albums, artistImageUrl))
                        .addToBackStack(null)
                        .commit();
            }
        });

        // Tracks
        recyclerView = (RecyclerView) layout.getViewById(R.id.artistTrackRecycler);
        topTracksRetroCall(itemId);
        scroll.addView(layout);
        return scroll;
    }

    private void topTracksRetroCall(String itemId) {
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

    private void albumRetroCall(String itemId) {
        String albumLimit = "50";
        SpotifyService service = RetrofitClient.getInstance().create(SpotifyService.class);
        Call<Albums> call = service.getAlbums(itemId, "album", albumLimit, RetrofitClient.getAuthToken());
        // Using enqueue() to make the request asynchronous and make Retrofit handle it in a background thread
        call.enqueue(new Callback<Albums>() {
            @Override
            public void onResponse(Call<Albums> call, Response<Albums> response) {
                System.out.println(response.raw().request().url());
                if (response.body() != null) {
                    albums = response.body();
                    setUpAlbums(albums);
                } else {
                    Toast.makeText(getActivity(), response.headers().toString(), Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<Albums> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setUpAlbums(Albums albums) {
        ImageView albumImg1 = albumRow1.findViewById(R.id.artistImage2);
        ImageView albumImg2 = albumRow2.findViewById(R.id.artistImage2);
        TextView albumName1 = albumRow1.findViewById(R.id.albumTitle);
        TextView albumName2 = albumRow2.findViewById(R.id.albumTitle);

        if (albums.getItems().size() > 0) {
            // Set up album 1
            albumName1.setText(albums.getItems().get(0).getName());
            String albumImageUrl1 = albums.getItems().get(0).getImages().get(0).getUrl();
            Glide.with(getActivity())
                    .load(albumImageUrl1)
                    .placeholder(R.drawable.music_note)
                    .into(albumImg1);
            albumRow1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getFragmentManager()
                            .beginTransaction()
                            .replace(R.id.frame, TrackFragment.newInstance(albums.getItems().get(0).getId(), albumImageUrl1))
                            .addToBackStack(null)
                            .commit();
                }
            });

            // Set up album 2
            albumName2.setText(albums.getItems().get(albums.getItems().size() - 1).getName());
            String albumImageUrl2 = albums.getItems().get(albums.getItems().size() - 1).getImages().get(0).getUrl();
            Glide.with(getActivity())
                    .load(albumImageUrl2)
                    .placeholder(R.drawable.music_note)
                    .into(albumImg2);
            albumRow2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getFragmentManager()
                            .beginTransaction()
                            .replace(R.id.frame, TrackFragment.newInstance(albums.getItems().get(0).getId(), albumImageUrl2))
                            .addToBackStack(null)
                            .commit();
                }
            });
        }

    }
}
