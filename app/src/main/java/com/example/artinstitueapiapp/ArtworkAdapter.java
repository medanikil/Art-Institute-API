package com.example.artinstitueapiapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import java.util.List;

public class ArtworkAdapter extends RecyclerView.Adapter<ArtworkAdapter.ViewHolder> {

    private final List<Artwork> artworkList;
    private final OnArtworkClickListener listener;

    public ArtworkAdapter(List<Artwork> artworkList, OnArtworkClickListener listener) {
        this.artworkList = artworkList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_artwork, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Artwork artwork = artworkList.get(position);
        holder.title.setText(artwork.getTitle());
        Picasso.get().load(artwork.getImageUrl()).into(holder.thumbnail);
        holder.itemView.setOnClickListener(v -> listener.onArtworkClick(artwork));
    }

    @Override
    public int getItemCount() {
        return artworkList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView thumbnail;

        ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.artworktitledisplay);
            thumbnail = itemView.findViewById(R.id.artwork_thumbnail);
        }
    }

    public interface OnArtworkClickListener {
        void onArtworkClick(Artwork artwork);
    }
}
