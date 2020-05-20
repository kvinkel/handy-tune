package com.example.handytune;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PlaylistAdapter extends RecyclerView.Adapter<PlaylistAdapter.PlaylistViewHolder> {

    ArrayList<String> numbersOfPlaylists;

    public PlaylistAdapter(ArrayList<String> numbersOfPlaylists) {
        this.numbersOfPlaylists = numbersOfPlaylists;
    }

    @NonNull
    @Override
    public PlaylistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_playlist, parent, false);
        TextView textView = view.findViewById(R.id.rowItemPlaylist);
        final PlaylistViewHolder viewHolder = new PlaylistViewHolder(view, textView);
        return viewHolder;

    }



    @Override
    public void onBindViewHolder(@NonNull PlaylistViewHolder holder, int position) {
        holder.getTextView().setText(numbersOfPlaylists.get(position));

        String pink="#F06292";
        String lightblue ="#4FC3F7";
        String lighgreen ="#4DB6AC";
        String lightpurple ="#9575CD";

//        String[] mColors = {pink,lightblue,lighgreen,lightpurple};
//        holder.getTextView().setBackgroundColor(Color.parseColor(mColors[position % 4]));
//



    }



    @Override
    public int getItemCount() {
        return numbersOfPlaylists.size();
    }

    public static class PlaylistViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        private TextView textView;
        private View frameLayout;
        private Context context;

        public PlaylistViewHolder(View frameLayout, TextView v) {
            super(frameLayout);
            textView = v;
        }

        public TextView getTextView() {
            return textView;
        }
    }


}




