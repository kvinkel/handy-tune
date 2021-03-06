package com.example.handytune.fragments;


import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.handytune.DbRepository;
import com.example.handytune.PlaylistActivity;
import com.example.handytune.R;
import com.example.handytune.ResultActivity;
import com.example.handytune.database.Playlist;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreatePlaylistFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreatePlaylistFragment extends Fragment implements View.OnClickListener {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String CALLED_FROM = "calledFrom";

    private String calledFrom;
    private TextView textView;
    private Button savePlaylistBtn;
    private ArrayList<Playlist> listOfPlaylists;
    private DbRepository dbRepository;
    private Thread readThread;

    public CreatePlaylistFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment CreatePlaylistFragment.
     */
    public static CreatePlaylistFragment newInstance(String calledFrom) {
        CreatePlaylistFragment fragment = new CreatePlaylistFragment();
        Bundle args = new Bundle();
        args.putString(CALLED_FROM,calledFrom);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
calledFrom = getArguments().getString(CALLED_FROM);
        }
        dbRepository = new DbRepository(getContext());
        listOfPlaylists = new ArrayList<>();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ConstraintLayout layout = (ConstraintLayout) inflater.inflate(R.layout.fragment_create_playlist, container, false);
        textView = (TextView) layout.getViewById(R.id.createPlaylist);
        savePlaylistBtn = (Button) layout.findViewById(R.id.savePlaylistBtn);
        savePlaylistBtn.setOnClickListener(this::onClick);

        return layout;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.savePlaylistBtn:
                String playlistName = textView.getText().toString();
                dbRepository.insertPlaylist(playlistName);

                startThreadForReadDataInDatabase();
                checkIfPlaylistExistsInDb(playlistName);

                if(calledFrom == "AddToPlaylistFragment"){
                    ResultActivity resultActivity = (ResultActivity) getActivity();
                    resultActivity.updateFragment();
                }

                if(calledFrom == "PlaylistActivity"){
                    PlaylistActivity playlistActivity = (PlaylistActivity) getActivity();
                    playlistActivity.updateAdapter();
                }
                break;
        }
    }

    private void checkIfPlaylistExistsInDb(String playlistName)
    {
        boolean exists = false;
        for (int i = 0; i < listOfPlaylists.size(); i++) {

            if(listOfPlaylists.get(i).getPlaylistName().equals(playlistName)){
                exists = true;
            }
            else{
                exists = false;
            }
        }
        if(exists){
            Toast.makeText(getActivity(), "Added playlist "+playlistName, Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(getActivity(), "Could not add playlist", Toast.LENGTH_LONG).show();
        }
    }

    private void startThreadForReadDataInDatabase() {
        //Create a thread
        readThread = new Thread(new Runnable() {
            @Override
            public void run() {
                listOfPlaylists = (ArrayList<Playlist>) dbRepository.getAllPLaylists();
            }
        });
        readThread.start();

        try {
            readThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
