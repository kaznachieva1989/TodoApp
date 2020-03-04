package com.example.todoapp.ui.home;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoapp.AnimationActivity;
import com.example.todoapp.App;
import com.example.todoapp.FormActivity;
import com.example.todoapp.MainActivity;
import com.example.todoapp.R;
import com.example.todoapp.com.example.todoapp.models.Work;
import com.example.todoapp.ui.gallery.GalleryFragment;
import com.example.todoapp.ui.onboard.BoardFragment;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements WorkAdapter.OnNoteListener {
    private WorkAdapter adapter;
    private List<Work> list;
    int position;
    Button button;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        list = new ArrayList<>();
        adapter = new WorkAdapter(list, this);
        recyclerView.setAdapter(adapter);
        App.getDatabase().workDao().getAll().observe(getViewLifecycleOwner(), new Observer<List<Work>>() {
            @Override
            public void onChanged(List<Work> works) {
                list.clear();
                list.addAll(works);
                adapter.notifyDataSetChanged();
            }
        });

        button = view.findViewById(R.id.btn_animation);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AnimationActivity.class);
                String transitionName = getString(R.string.transition_string);

                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) getContext(),
                        button, transitionName);
                ActivityCompat.startActivity(getContext(),intent, options.toBundle());
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == 42) {
            Work work = (Work) data.getSerializableExtra("work");
            App.getDatabase().workDao().delete(list.get(position));
            App.getDatabase().workDao().update(list.get(position));
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onNoteClick(int position) {

        Log.d("ololo", "OnNoteClick : clicked");
        this.position = position;
        Intent intent = new Intent(getActivity(), FormActivity.class);
        intent.putExtra("work", list.get(position));
        getActivity().startActivityForResult(intent, 42);
    }

    @Override
    public void onItemLongClick(final int position) {
        list.get(position);
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setTitle("Android");
        alert.setMessage("Вы действительно хотите удалить?");
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                App.getDatabase().workDao().delete(list.get(position));
                adapter.notifyDataSetChanged();
                Toast.makeText(getContext(), "Good", Toast.LENGTH_SHORT).show();
            }
        });
        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getContext(), "Bad", Toast.LENGTH_SHORT).show();
            }
        });
        alert.show();
    }
}