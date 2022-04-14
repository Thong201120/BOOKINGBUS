package com.example.project;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.project.activity.AddBusActivity;
import com.example.project.activity.ListBusActivity;
import com.example.project.activity.ListOrderActivity;
import com.example.project.activity.deletebus;
import com.example.project.activity.updatebus;
import com.google.android.material.navigation.NavigationView;

public class AdminMainFragment extends Fragment {
    public static final String USERNAME = "USERNAME";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_admin_main, container, false);
        NavigationView navigationView = (NavigationView) getActivity().findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        TextView headerUserName = headerView.findViewById(R.id.nav_header_username);
        Button add = root.findViewById(R.id.add);
        Button update= root.findViewById(R.id.update);
        Button delete= root.findViewById(R.id.delete);
        Button view = root.findViewById(R.id.view);
        Button orderview = root.findViewById(R.id.vieworder);
        Button end = root.findViewById(R.id.logout);
        String username = headerUserName.getText().toString();
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddBusActivity.class);
                intent.putExtra(USERNAME, username);
                startActivity(intent);
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), updatebus.class);
                intent.putExtra(USERNAME, username);
                startActivity(intent);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), deletebus.class);
                intent.putExtra(USERNAME, username);
                startActivity(intent);
            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ListBusActivity.class);
                intent.putExtra(USERNAME, username);
                startActivity(intent);
            }
        });

        orderview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ListOrderActivity.class);
                intent.putExtra(USERNAME, username);
                startActivity(intent);
            }
        });


        return root;
    }
}