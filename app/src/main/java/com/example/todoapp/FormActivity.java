package com.example.todoapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.todoapp.com.example.todoapp.models.Work;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public class FormActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_IMAGE = 555;
    Work work = new Work();
    private EditText editTitle;
    private EditText editDesc;
    private ImageView imageViewAdd;
    Uri imageUri;
    StorageReference storageReference;

    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        editTitle = findViewById(R.id.editTitle);
        editDesc = findViewById(R.id.editDesc);
        imageViewAdd = findViewById(R.id.imageViewAdd);
        String uniqueId = UUID.randomUUID().toString();
        storageReference = FirebaseStorage.getInstance().getReference().child("ImageFolder" + uniqueId);
        db = FirebaseFirestore.getInstance();

        getIncomingIntent();

        imageViewAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, REQUEST_CODE_IMAGE);
            }
        });
    }

    public void onClick(View view) {
        String title = editTitle.getText().toString().trim();
        String desc = editDesc.getText().toString().trim();
        Work work = new Work();
        work.setTitle(title);
        work.setDesc(desc);
        work.setImageUrl(String.valueOf(imageUri));
        App.getDatabase().workDao().insert(work);
        saveToFirestore(work);

        Intent intent = new Intent();
        intent.putExtra("title", title);
        intent.putExtra("work", work);
        setResult(RESULT_OK, intent);
        finish();
    }

    private void saveToFirestore(Work work) {
//        FirebaseFirestore.getInstance().collection("works").add(work).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentReference> task) {
//                if (task.isSuccessful()) {
//                    Toast.makeText(FormActivity.this, "Great", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
        storageReference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(final UploadTask.TaskSnapshot taskSnapshot) {
                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Map<String, Object> file = new HashMap();
                        file.put("imageUrl", uri.toString());
                        file.put("title", editTitle.getText().toString());
                        file.put("desc", editDesc.getText().toString());
                        db.collection("works").document().set(file).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
            }
        });
    }

    public void getIncomingIntent() {
        Intent intent = getIntent();
        work = (Work) intent.getSerializableExtra("work");
        if (work != null) {
            editTitle.setText(work.getTitle());
            editDesc.setText(work.getDesc());
            Picasso.get().load(work.getImageUrl()).into(imageViewAdd);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_IMAGE && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
            Picasso.get().load(data.getData()).into(imageViewAdd);
        }
    }
}
