package com.example.todoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.todoapp.com.example.todoapp.models.Work;

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
        Intent intent = new Intent();
        intent.putExtra("title", title);
        intent.putExtra("work", work);
        setResult(RESULT_OK, intent);
        finish();

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
