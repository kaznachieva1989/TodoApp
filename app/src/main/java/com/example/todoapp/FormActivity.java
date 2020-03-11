package com.example.todoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.todoapp.com.example.todoapp.models.Work;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class FormActivity extends AppCompatActivity {

    Work work = new Work();
    private EditText editTitle;
    private EditText editDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        editTitle = findViewById(R.id.editTitle);
        editDesc = findViewById(R.id.editDesc);
        getIncomingIntent();
    }

    public void onClick(View view) {
        String title = editTitle.getText().toString().trim();
        String desc = editDesc.getText().toString().trim();
        Work work = new Work(title, desc);
        App.getDatabase().workDao().insert(work);

        saveToFirestore(work);

        Intent intent = new Intent();
        intent.putExtra("title", title);
        intent.putExtra("work", work);
        setResult(RESULT_OK, intent);
        finish();

    }

    private void saveToFirestore(Work work) {
        FirebaseFirestore.getInstance().collection("works").add(work).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(FormActivity.this, "Great", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void getIncomingIntent() {
        Intent intent = getIntent();
        work = (Work) intent.getSerializableExtra("work");
        if (work != null) {
            editTitle.setText(work.getTitle());
            editDesc.setText(work.getDesc());

        }
    }
}
