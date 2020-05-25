package com.example.handytune.fragments;

import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.handytune.AddToPlaylistAdapter;
import com.example.handytune.DbRepository;
import com.example.handytune.PlaylistAdapter;
import com.example.handytune.R;
import com.example.handytune.database.PlaylistWithTracks;
import com.example.handytune.database.Track;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddToPlaylistFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddToPlaylistFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ITEM_ID = "itemId";
    private static final String ARTIST_IMAGE_URL = "artistImgUrl";
    private static final String TRACK = "trackName";

    private String itemId;
    private String trackName;
    private String artistImageUrl;

    private TextView playlistView;
    private RecyclerView recyclerView;

    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    Button createPlaylistBtn;
    Thread readThread;
    DbRepository dbRepository;
    List<PlaylistWithTracks> listOfPlaylistAndTracks;
    Track track;

    public AddToPlaylistFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param itemId       Item id for the artist search result.
     * @param artistImgUrl image url for the artist search result.
     * @param trackName    name for the track search result.
     * @return A new instance of fragment ArtistFragment.
     */
    public static AddToPlaylistFragment newInstance(String itemId, String artistImgUrl, String trackName) {
        AddToPlaylistFragment fragment = new AddToPlaylistFragment();
        Bundle args = new Bundle();
        args.putString(ITEM_ID, itemId);
        args.putString(ARTIST_IMAGE_URL, artistImgUrl);
        args.putString(TRACK, trackName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            itemId = getArguments().getString(ITEM_ID);
            artistImageUrl = getArguments().getString(ARTIST_IMAGE_URL);
            trackName = getArguments().getString(TRACK);
        }
        dbRepository = new DbRepository(getContext());
        listOfPlaylistAndTracks = new ArrayList<>();

        System.out.println("trackName (in fragment) " + trackName);
        System.out.println("artistImageUrl (in fragment) " + artistImageUrl);
        track = new Track();
        track.setTrackName(trackName);
        track.setAlbumImageUrl(artistImageUrl);


        startThreadForReadDataInDatabase();
        //Wait for thread to finish
        try {
            readThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        ConstraintLayout layout = (ConstraintLayout) inflater.inflate(R.layout.fragment_add_to_playlist, container, false);

        playlistView = (TextView) layout.getViewById(R.id.addToPlaylistTitle);

        recyclerView = (RecyclerView) layout.getViewById(R.id.playlistRecyclerViewInFragment);

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(layoutManager);
        adapter = new AddToPlaylistAdapter((ArrayList<PlaylistWithTracks>) listOfPlaylistAndTracks, track, getActivity());
        recyclerView.setAdapter(adapter);

        createPlaylistBtn = (Button) layout.findViewById(R.id.createPlaylistBtn);
        createPlaylistBtn.setOnClickListener(this::onClick);


        return layout;
    }


    public void startThreadForReadDataInDatabase() {
        //Create a thread
        readThread = new Thread(new Runnable() {
            @Override
            public void run() {
                listOfPlaylistAndTracks = dbRepository.getTrackWithPlaylists();
            }
        });
        readThread.start();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.createPlaylistBtn:
                System.out.println("%¤&¤&/¤%&/¤%/¤&¤%&#¤%&#¤&%************************************");
                break;
        }
    }
}
