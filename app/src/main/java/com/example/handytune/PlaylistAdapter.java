package com.example.handytune;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PlaylistAdapter extends RecyclerView.Adapter<PlaylistAdapter.PlaylistViewHolder> {

    ArrayList<String> numbersOfPlaylists;



    private Context context;

    public PlaylistAdapter(ArrayList<String> numbersOfPlaylists, Context context) {

        this.context = context;
        this.numbersOfPlaylists = numbersOfPlaylists;

    }

    @NonNull
    @Override
    public PlaylistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_playlist, parent, false);
        TextView textView = view.findViewById(R.id.rowItemPlaylist);
        final PlaylistViewHolder viewHolder = new PlaylistViewHolder(numbersOfPlaylists, view, textView);
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


        public PlaylistViewHolder(ArrayList<String> numbersOfPlaylists, View frameLayout, TextView v) {
            super(frameLayout);
            textView = v;
            textView.setOnClickListener(this);
            this.numbersOfPlaylists = numbersOfPlaylists;
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
    }



