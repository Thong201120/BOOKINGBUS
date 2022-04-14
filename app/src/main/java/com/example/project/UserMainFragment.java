package com.example.project;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.project.activity.ListBusUserActivity;
import com.example.project.activity.ListTravelActivity;
import com.example.project.activity.MainActivity;

public class UserMainFragment extends Fragment {

    Button ViewList, ViewTravel, loGout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_user_main, container, false);
        ViewList = root.findViewById(R.id.btnviewuser);
        ViewTravel = root.findViewById(R.id.btnviewtravel);
        loGout = root.findViewById(R.id.logout);
        ViewList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ListBusUserActivity.class);
                startActivity(intent);
            }
        });

        ViewTravel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ListTravelActivity.class);
                startActivity(intent);
            }
        });

        loGout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getContext().getSharedPreferences("USER_FILE.txt", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.commit();
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });
    return root;
    }
}