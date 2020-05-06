package com.example.handytune;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.handytune.database.Album;

import java.util.ArrayList;
import java.util.List;

public class PlaylistAdapter extends RecyclerView.Adapter<PlaylistAdapter.PlaylistViewHolder> {

    ArrayList<String> numbersOfPlaylists;

    Thread insertThread;
    Thread deleteThread;

    DbRepository dbRepository;
    List<Album> listOfAlbums;

    public PlaylistAdapter(ArrayList<String> numbersOfPlaylists,Context context) {
        this.numbersOfPlaylists = numbersOfPlaylists;


        dbRepository = new DbRepository(context);
        listOfAlbums = new ArrayList<>();
    }


    @NonNull
    @Override
    public PlaylistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_playlist, parent, false);
        TextView textView = view.findViewById(R.id.rowItemPlaylist);
        final PlaylistViewHolder viewHolder = new PlaylistViewHolder(numbersOfPlaylists,view, textView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final PlaylistViewHolder holder, final int position) {
        holder.getTextView().setText(numbersOfPlaylists.get(position));
    }

    @Override
    public int getItemCount() {
        return numbersOfPlaylists.size();
    }

    public static class PlaylistViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        private TextView textView;
        private View frameLayout;
        private Context context;
        ArrayList<String> numbersOfPlaylists;


        public PlaylistViewHolder(ArrayList<String> numbersOfPlaylists,View frameLayout, TextView v) {
            super(frameLayout);
            textView = v;
            textView.setOnClickListener(this);
            this.numbersOfPlaylists= numbersOfPlaylists;
        }

        public TextView getTextView() {
            return textView;
        }

        @Override
        public void onClick(View v) {

            int position = getAdapterPosition();
            String rowName = numbersOfPlaylists.get(position);
            System.out.println("Clicked on position : " + position + " ******************");
            System.out.println("Clicked on name : " + rowName + " ******************");
        }
    }


    public void startThreadForInsertData() {

        //Create a thread
        insertThread = new Thread(new Runnable() {
            @Override
            public void run() {

                System.out.println("Thread run*******");

                int userId = 1;
                System.out.println("Before insert album(s) to database *********");
                /*For testing*/
                dbRepository.insertAlbum(userId, 100, "Barbiegirl", "testUrl 1", "imageUrl 1");
                listOfAlbums = dbRepository.getAlbumNamesFromUser(userId);
                for (int j = 0; j < listOfAlbums.size(); j++) {
                    System.out.println("Id " + listOfAlbums.get(j).getId() + " *********");
                    System.out.println("User id " + listOfAlbums.get(j).getUserId() + " *********");
                    System.out.println("Album id " + listOfAlbums.get(j).getAlbumId() + " *********");
                    System.out.println("Album name " + listOfAlbums.get(j).getAlbumName() + " *********");
                    System.out.println("Url " + listOfAlbums.get(j).getUrl() + " *********");
                    System.out.println("ImageUrl " + listOfAlbums.get(j).getImageUrl() + " *********");
                }
                System.out.println("After insert album to database *********");
            }
        });
        insertThread.start();
    }

    public void startThreadForDeleteData() {

        //Create a thread
        deleteThread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Nuke album");
                dbRepository.nukeAlbum();
            }
        });
        deleteThread.start();
    }




}



