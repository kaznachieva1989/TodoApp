package com.example.todoapp.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoapp.R;
import com.example.todoapp.com.example.todoapp.models.Work;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import java.util.ArrayList;
import java.util.List;

public class WorkAdapter extends RecyclerView.Adapter<WorkAdapter.ViewHolder> {
    private OnNoteListener listenerClick;
    List<Work> list = new ArrayList<>();

    public WorkAdapter(List<Work> list, OnNoteListener onNoteListener) {
        this.list = list;
        this.listenerClick = onNoteListener;
    }

    public WorkAdapter(FirestoreRecyclerOptions<Work> options) {

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_work, parent, false);
        ViewHolder viewHolder = new ViewHolder(view, listenerClick);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        OnNoteListener onNoteListener;
        private TextView textTitle;
        private TextView textDesc;


        public ViewHolder(@NonNull View itemView, OnNoteListener onNoteListener) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.textTitle);
            textDesc = itemView.findViewById(R.id.textDesc);
            this.onNoteListener = onNoteListener;
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);

        }

        public void bind(Work work) {
            textTitle.setText(work.getTitle());
            textDesc.setText(work.getDesc());
        }

        @Override
        public void onClick(View v) {
            onNoteListener.onNoteClick(getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View v) {
            onNoteListener.onItemLongClick(getAdapterPosition());
            return true;
        }
    }

    public interface OnNoteListener {
        void onNoteClick(int position);

        void onItemLongClick(int position);
    }
}
