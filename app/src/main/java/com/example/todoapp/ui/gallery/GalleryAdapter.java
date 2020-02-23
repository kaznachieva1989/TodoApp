package com.example.todoapp.ui.gallery;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoapp.R;

import java.util.ArrayList;


public class GalleryAdapter extends RecyclerView.Adapter<GalleryViewHolder> {
    ArrayList<String> names;

    public GalleryAdapter() {
        names = new ArrayList<>();
        names.add("Nurzhamal");
        names.add("Aygerim");
        names.add("Kunduz");
        names.add("Ayima");
        names.add("Pavel");
        names.add("Kubat");
        names.add("Syimyk");
        names.add("Nursultan");
    }

    @NonNull
    @Override
    public GalleryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.viewholder_gallery, parent, false);
        GalleryViewHolder galleryViewHolder = new GalleryViewHolder(view);
        return galleryViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull GalleryViewHolder holder, int position) {
        holder.setTextViewGallery(names.get(position));
    }

    @Override
    public int getItemCount() {
        return names.size();
    }

}
