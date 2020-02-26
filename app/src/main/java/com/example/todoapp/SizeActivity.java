package com.example.todoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SizeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_size);
    }

    public void onRadioClick(View view) {
            switch (view.getId()) {
                case R.id.size_14:
                    break;
                case R.id.size_22:
                    break;
                case R.id.size_28:
                    break;
                default:
                    break;
            }
        }
    }

