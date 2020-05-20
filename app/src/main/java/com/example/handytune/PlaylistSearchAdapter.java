package com.example.handytune;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.handytune.spotify.model.Item;
import java.util.List;

public class PlaylistSearchAdapter extends RecyclerView.Adapter<PlaylistSearchAdapter.PlaylistViewHolder> {
    private List<Item> result;
    private Context context;

    public PlaylistSearchAdapter(List<Item> result, Context context) {
        this.context = context;
        this.result = result;
    }

    @NonNull
    @Override
    public PlaylistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_playlist, parent, false);
        TextView playlistView = view.findViewById(R.id.userPlaylistView);
        final PlaylistViewHolder viewHolder = new PlaylistViewHolder(context, playlistView, view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PlaylistViewHolder holder, int position) {
        holder.getPlaylistView().setText(result.get(position).getName());
    }

    @Override
    public int getItemCount() { return result.size(); }


    public static class PlaylistViewHolder extends RecyclerView.ViewHolder {
        private Context context;
        private TextView playlistView;

        public PlaylistViewHolder(@NonNull Context context, TextView playlistView, View view) {
            super(view);
            this.playlistView = playlistView;

        }

        public TextView getPlaylistView() {return playlistView;}
    }


}
