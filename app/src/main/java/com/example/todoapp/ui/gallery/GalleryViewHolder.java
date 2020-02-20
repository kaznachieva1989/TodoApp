package com.example.todoapp.ui.gallery;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoapp.R;

import static android.widget.Toast.LENGTH_SHORT;

public class GalleryViewHolder extends RecyclerView.ViewHolder {
    TextView textViewGallery;

    public GalleryViewHolder(@NonNull final View itemView) {
        super(itemView);
        textViewGallery = itemView.findViewById(R.id.vhg_text);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (textViewGallery.getText().toString().trim().equals(textViewGallery.getText().toString())) {
                    Toast.makeText(itemView.getContext(), textViewGallery.getText().toString(), LENGTH_SHORT).show();
                }
            }
        });
    }

    public void setTextViewGallery(String s) {
        textViewGallery.setText(s);
    }
}
