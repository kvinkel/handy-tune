package com.example.handytune;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.handytune.fragments.TrackFragment;
import com.example.handytune.spotify.model.Item;

import java.util.List;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.AlbumViewHolder> {
    private List<Item> result;
    private Context context;

    public AlbumAdapter(List<Item> result, Context context) {
        this.context = context;
        this.result = result;
    }

    @NonNull
    @Override
    public AlbumViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.album_item, parent, false);
        TextView textView = view.findViewById(R.id.albumTitle);
        ImageView imageView = view.findViewById(R.id.albumImage);
        final AlbumViewHolder viewHolder = new AlbumViewHolder(context, view, textView, imageView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumViewHolder holder, int position) {
        holder.getAlbumListView().setText(result.get(position).getName());
        Glide.with(context).clear(holder.getAlbumImage());
        Glide.with(context).load(result.get(position).getImages().get(0).getUrl()).placeholder(R.drawable.music_note).into(holder.albumImage);
        holder.setAlbumId(result.get(position).getId());
        holder.setImageUrl(result.get(position).getImages().get(0).getUrl());
    }

    @Override
    public int getItemCount() {
        return result.size();
    }

    public static class AlbumViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        private ImageView albumImage;
        private String albumId;
        private String imageUrl;

        public AlbumViewHolder(@NonNull Context context, View itemView, TextView textView, ImageView albumImage) {
            super(itemView);
            this.textView = textView;
            this.albumImage = albumImage;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentManager manager = ((ResultActivity)context).getSupportFragmentManager();
                    FragmentTransaction transaction = manager.beginTransaction();
                    TrackFragment fragment = TrackFragment.newInstance(getAlbumId(), getImageUrl());
                    transaction.replace(R.id.frame, fragment).addToBackStack(null);
                    transaction.commit();
                }
            });
        }
        public String getImageUrl() { return imageUrl; }

        public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

        public String getAlbumId() { return albumId; }

        public void setAlbumId(String albumId) { this.albumId = albumId; }
        public TextView getAlbumListView() {return textView;}
        public ImageView getAlbumImage() {return albumImage;}
    }


}
