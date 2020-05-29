package com.example.handytune;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.handytune.fragments.AddToPlaylistFragment;
import com.example.handytune.fragments.CreatePlaylistFragment;
import com.example.handytune.spotify.model.Image;
import com.example.handytune.spotify.model.artist.Track;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import java.util.List;

public class TrackAdapter extends  RecyclerView.Adapter<TrackAdapter.TrackViewHolder>   {

    private List<Track> results;
    private Context context;

    public TrackAdapter(Context context, List<Track> results) {
        this.context = context;
        this.results = results;
    }

    @NonNull
    @Override
    public TrackViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.track_item, parent, false);
        TextView trackName = view.findViewById(R.id.track);
        ImageView imageView = view.findViewById(R.id.trackImage);
        Button addButton = view.findViewById(R.id.addButton);
        final TrackViewHolder viewHolder = new TrackViewHolder(view, trackName, imageView, addButton, context);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TrackViewHolder holder, int position) {
        holder.getTrack().setText(results.get(position).getName());
        holder.setTrackUrl(results.get(position).getExternalUrls().getSpotify());
        List<Image> images = results.get(position).getAlbum().getImages();

        if (images != null && images.size() > 0) {
            String imageUrl = images.get(0).getUrl();
            Glide.with(context).clear(holder.getImageView());
            Glide.with(context)
                    .load(imageUrl)
                    .placeholder(R.drawable.music_note)
                    .into(holder.getImageView());
        } else {
            holder.getImageView().setImageResource(R.drawable.music_note);
        }
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public static class TrackViewHolder extends RecyclerView.ViewHolder {
        private TextView track;
        private ImageView imageView;
        private String trackUrl;

        public TrackViewHolder(@NonNull final View itemView, TextView track, ImageView imageView, Button addButton, Context context) {
            super(itemView);
            this.track = track;
            this.imageView = imageView;
            String trackName ="Test trackname from track adapter";
            String albumURL= "Test albumURL from track adapter";
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent browse = new Intent(Intent.ACTION_VIEW , Uri.parse(trackUrl));
                    context.startActivity(browse);
                }
            });

            addButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO redirect to playlist fragment
                    FragmentManager manager = ((ResultActivity) context).getSupportFragmentManager();
                    FragmentTransaction transaction = manager.beginTransaction();
                    AddToPlaylistFragment createPlaylistFragment= AddToPlaylistFragment.newInstance(trackName,trackUrl,albumURL);
                    transaction.add(R.id.frame, createPlaylistFragment).addToBackStack(null);
                    transaction.commit();
                }
            });
        }

        public TextView getTrack() {
            return this.track;
        }

        public ImageView getImageView() {
            return imageView;
        }

        public String getTrackUrl() {
            return trackUrl;
        }

        public void setTrackUrl(String trackUrl) {
            this.trackUrl = trackUrl;
        }

    }
}
