package com.example.todoapp.ui.slideshow;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.todoapp.Prefs;
import com.example.todoapp.R;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

import static android.content.Context.MODE_PRIVATE;
import static androidx.core.content.PermissionChecker.checkSelfPermission;

public class SlideshowFragment extends Fragment implements OnBackPressedListener{

    EditText inputET;
    File file;

    private SlideshowViewModel slideshowViewModel;


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
    public void onStop() {
        super.onStop();
        saveToTxtFile();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        saveToTxtFile();
    }


    @Override
    public boolean onBackPressed() {
        saveToTxtFile();
        return false;
    }
}