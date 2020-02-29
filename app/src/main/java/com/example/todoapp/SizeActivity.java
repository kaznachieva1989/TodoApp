package com.example.todoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.todoapp.ui.slideshow.SlideshowFragment;

public class SizeActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {
    SlideshowFragment slideshowFragment;
    RadioGroup radioGroup;
    RadioButton size_14, size_22, size_28;
    EditText ed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_size);

        slideshowFragment = new SlideshowFragment();
        radioGroup = findViewById(R.id.radioGroup1);
        size_14 = findViewById(R.id.size_14);
        size_22 = findViewById(R.id.size_22);
        size_28 = findViewById(R.id.size_28);
        radioGroup.setOnCheckedChangeListener(this);
        ed = findViewById(R.id.ed);
    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        Intent intent = new Intent();
        if (size_14.isChecked()) {
            ed.setTextSize(14);
            intent.putExtra("stih", ed.getText().toString());
        }
        if (size_22.isChecked()) {
            ed.setTextSize(22);
            intent.putExtra("stih", ed.getText().toString());
        }
        if (size_28.isChecked()) {
            ed.setTextSize(28);
            intent.putExtra("stih", ed.getText().toString());
        }
        setResult(RESULT_OK, intent);
        finish();
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
//            fragment.onActivityResult(requestCode, resultCode, data);
//        }
//    }
}

