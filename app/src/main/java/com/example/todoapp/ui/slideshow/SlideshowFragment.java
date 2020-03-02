package com.example.todoapp.ui.slideshow;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.todoapp.Prefs;
import com.example.todoapp.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class SlideshowFragment extends Fragment implements OnBackPressedListener {

    public static final String EXTRA_KEY_TEST = "stih";
    public static final int REQUEST_CODE_11 = 11;
    EditText inputET;
    File file;
    int size;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_slideshow, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        inputET = view.findViewById(R.id.inputET);
        readTxtFile();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_11 && resultCode == Activity.RESULT_OK) {
            size = data.getExtras().getInt(EXTRA_KEY_TEST);
            inputET.setTextSize(size);
        }
    }

    private void saveToTxtFile() {
        Prefs.getInstance(getContext()).saveShown();
        File folder = new File(Environment.getExternalStorageDirectory(), "TodoApp");
        folder.mkdirs();
        file = new File(folder, "note.txt");
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(inputET.getText().toString().getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readTxtFile() {
        StringBuilder sb = new StringBuilder();
        File folder = new File(Environment.getExternalStorageDirectory(), "TodoApp");
        folder.mkdirs();
        file = new File(folder, "note.txt");

        try {
            //   File textFile = new File(Environment.getExternalStorageDirectory(), "note.txt");
            FileInputStream fis = new FileInputStream(file);

            if (fis != null) {
                InputStreamReader isr = new InputStreamReader(fis);
                BufferedReader buf = new BufferedReader(isr);

                String line = null;
                while ((line = buf.readLine()) != null) {
                    sb.append(line + "\n");
                }
                fis.close();
            }
            inputET.setText(sb);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        saveToTxtFile();
        readTxtFile();
    }

    @Override
    public boolean onBackPressed() {
        saveToTxtFile();
        readTxtFile();
        return false;
    }

}
