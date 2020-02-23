package com.example.todoapp.ui.onboard;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.todoapp.MainActivity;
import com.example.todoapp.Prefs;
import com.example.todoapp.R;
import com.google.android.material.tabs.TabLayout;

/**
 * A simple {@link Fragment} subclass.
 */
public class BoardFragment extends Fragment {
    TextView skip;
    Button btnGetStarted;
    public ViewPager viewPager;

    public BoardFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_board, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView textTitle = view.findViewById(R.id.textTitle);
        ImageView imageView = view.findViewById(R.id.imageView);


        viewPager = view.findViewById(R.id.viewPager);

        final int pos = getArguments().getInt("pos");
        switch (pos) {
            case 0:
                textTitle.setText("Привет");
                imageView.setImageResource(R.drawable.image1);
                break;
            case 1:
                textTitle.setText("Как дела");
                imageView.setImageResource(R.drawable.image2);
                break;
            case 2:
                textTitle.setText("Отлично");
                imageView.setImageResource(R.drawable.image3);
                btnGetStarted = view.findViewById(R.id.btn_get_started);
                btnGetStarted.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Prefs.getInstance(getContext()).saveShown();
                        startActivity(new Intent(getContext(), MainActivity.class));
                        getActivity().finish();
                    }
                });
                btnGetStarted.setVisibility(View.VISIBLE);
                break;
        }
        skip = view.findViewById(R.id.skip);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Prefs.getInstance(getContext()).saveShown();
                startActivity(new Intent(getContext(), MainActivity.class));
                getActivity().finish();
            }
        });
    }
}
