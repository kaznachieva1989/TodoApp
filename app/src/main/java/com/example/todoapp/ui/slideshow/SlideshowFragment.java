package com.example.todoapp.ui.slideshow;

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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.todoapp.Prefs;
import com.example.todoapp.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static androidx.core.provider.FontsContractCompat.FontRequestCallback.RESULT_OK;

public class SlideshowFragment extends Fragment implements OnBackPressedListener {

    public static final String EXTRA_KEY_TEST = "stih";
    public static final int REQUEST_CODE_11 = 11;
    EditText inputET;
    File file;
    Long size;

    private SlideshowViewModel slideshowViewModel;
    private Intent intent;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                ViewModelProviders.of(this).get(SlideshowViewModel.class);
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);
        final TextView textView = root.findViewById(R.id.text_slideshow);
        slideshowViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        inputET = view.findViewById(R.id.inputET);


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_11 && resultCode == RESULT_OK) {
            size = data.getExtras().getLong(EXTRA_KEY_TEST);
            inputET.setTextSize(size);
            inputET.notify();
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

    @Override
    public void onPause() {
        super.onPause();
        saveToTxtFile();
    }

    @Override
    public boolean onBackPressed() {
        saveToTxtFile();
        return false;
    }

}
