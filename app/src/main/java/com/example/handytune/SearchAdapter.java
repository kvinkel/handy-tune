package com.example.handytune;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {
    private String[] results;

    public SearchAdapter(String[] results) {
        this.results = results;
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_row_item, parent, false);
        TextView textView = view.findViewById(R.id.findMusicResult);
        final SearchViewHolder viewHolder = new SearchViewHolder(view, textView);
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        holder.textView.setText(results[position]);
    }

    @Override
    public int getItemCount() {
        return results.length;
    }

    public static class SearchViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;

        public SearchViewHolder(@NonNull View itemView, TextView textView) {
            super(itemView);
            this.textView = textView;
        }

        public TextView getTextView() {
            return textView;
        }
    }
}
