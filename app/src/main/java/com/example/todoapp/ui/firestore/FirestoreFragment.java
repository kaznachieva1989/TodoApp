package com.example.todoapp.ui.firestore;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.todoapp.App;
import com.example.todoapp.FormActivity;
import com.example.todoapp.R;
import com.example.todoapp.com.example.todoapp.models.Work;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.firebase.ui.firestore.SnapshotParser;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * A simple {@link Fragment} subclass.
 */
public class FirestoreFragment extends Fragment {
    WorkAdapter_Firestore fireAdapter;
    FirebaseFirestore firebaseFirestore;
    EditText inputSearch;
    ArrayList<Work> list;


    public FirestoreFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_firestore, container, false);

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        inputSearch = view.findViewById(R.id.inputSearch);

        firebaseFirestore = FirebaseFirestore.getInstance();
        Query query = firebaseFirestore.collection("works");

        FirestoreRecyclerOptions<Work> options = new FirestoreRecyclerOptions.Builder<Work>()
                .setQuery(query,Work.class)
                .build();

        fireAdapter = new WorkAdapter_Firestore(options);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_firestore);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(fireAdapter);

        fireAdapter.setOnWorkListener(new WorkAdapter_Firestore.OnWorkListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                Work work = documentSnapshot.toObject(Work.class);
                Intent intent = new Intent(getActivity(), FormActivity.class);
                intent.putExtra("work", work);
                getActivity().startActivityForResult(intent, 42);
                String id = documentSnapshot.getId();
                String path = documentSnapshot.getReference().getPath();
                documentSnapshot.getReference().delete();
            }

            @Override
            public void onItemLongClick(final DocumentSnapshot documentSnapshot, int position) {
                AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                alert.setTitle("Android");
                alert.setMessage("Вы действительно хотите удалить?");
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Work work = documentSnapshot.toObject(Work.class);
                        String id = documentSnapshot.getId();
                        String path = documentSnapshot.getReference().getPath();
                        documentSnapshot.getReference().delete();
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
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        fireAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        fireAdapter.stopListening();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == 42) {
            Work work = (Work) data.getSerializableExtra("work");
        }
    }
}