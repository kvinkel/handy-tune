package com.example.handytune.fragments;

import android.content.Context;
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
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ITEM_ID = "itemId";
    private static final String IMAGE_URL = "artistImgUrl";
    private static final String TRACK = "trackName";
    private static final String EXTERNAL_TRACK_URL = "externalTrackUrl";
    private static final String OPEN_IN_APP_URL = "openInAppUrl";


    private String itemId;
    private String trackName;
    private String artistImageUrl;
    private String externalTrackUrl;
    private String openInAppUrl;

    private TextView playlistView;
    private RecyclerView recyclerView;
    private AddToPlaylistAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private Button createPlaylistBtn;
    private Thread readThread;
    private DbRepository dbRepository;
    private List<PlaylistWithTracks> listOfPlaylistAndTracks;
    private Track track;

    private Context context;

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
    public static AddToPlaylistFragment newInstance(String itemId, String artistImgUrl, String trackName, String externalTrackUrl ,String openInAppUrl) {
        AddToPlaylistFragment fragment = new AddToPlaylistFragment();
        Bundle args = new Bundle();
        args.putString(ITEM_ID, itemId);
        args.putString(IMAGE_URL, artistImgUrl);
        args.putString(TRACK, trackName);
        args.putString(EXTERNAL_TRACK_URL, externalTrackUrl);
        args.putString(OPEN_IN_APP_URL, openInAppUrl);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            itemId = getArguments().getString(ITEM_ID);
            artistImageUrl = getArguments().getString(IMAGE_URL);
            trackName = getArguments().getString(TRACK);
            externalTrackUrl=getArguments().getString(EXTERNAL_TRACK_URL);
            openInAppUrl=getArguments().getString(OPEN_IN_APP_URL);
        }

        dbRepository = new DbRepository(getContext());
        listOfPlaylistAndTracks = new ArrayList<>();
        track = new Track();
        track.setTrackName(trackName);
        track.setAlbumImageUrl(artistImageUrl);
        track.setExternalTrackUrl(externalTrackUrl);
        track.setOpenInAppTrackUrl(openInAppUrl);
        track.setTrackId(itemId);

        System.out.println("From AddToPlaylistFragment trackName " +trackName);
        System.out.println("From AddToPlaylistFragment artistImageUrl " +artistImageUrl);

        readFromDB();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        getActivity().setTitle("Add to playlist");
        ConstraintLayout layout = (ConstraintLayout) inflater.inflate(R.layout.activity_playlist, container, false);
        recyclerView = (RecyclerView) layout.getViewById(R.id.playlistRecyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new AddToPlaylistAdapter((ArrayList<PlaylistWithTracks>) listOfPlaylistAndTracks, track, getActivity());
        recyclerView.setAdapter(adapter);

        createPlaylistBtn = (Button) layout.findViewById(R.id.createPlaylistBtn_in_activity);
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
        int frameId = R.id.frame;
        if (getActivity().findViewById(R.id.frame2) != null && getFragmentManager().findFragmentByTag("ARTIST") != null) {
            frameId = R.id.frame2;
        }
        switch (v.getId()) {
            case R.id.createPlaylistBtn_in_activity:
                String calledFrom = "AddToPlaylistFragment";
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                CreatePlaylistFragment createPlaylistFragment= CreatePlaylistFragment.newInstance(calledFrom);
                transaction.add(frameId, createPlaylistFragment).addToBackStack(null);
                transaction.commit();
                break;
        }
    }

    public void readFromDB(){
        startThreadForReadDataInDatabase();
        //Wait for thread to finish
        try {
            readThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void updateAdapter()
    {
        readFromDB();
        adapter.setPlaylistWithTracks((ArrayList<PlaylistWithTracks>) listOfPlaylistAndTracks);
        adapter.notifyDataSetChanged();
    }

}
