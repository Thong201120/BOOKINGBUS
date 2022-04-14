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

import com.example.project.activity.AdminChangePasswordActivity;
import com.example.project.activity.AdminEditProfileActivity;
import com.example.project.activity.AdminMainActivity;
import com.example.project.activity.AdminProfileActivity;
import com.example.project.activity.MainActivity;
import com.example.project.activity.adminlogin;
import com.example.project.activity.deletebus;

public class AdminProfileFragment extends Fragment {
    Button viewInfo, changePass, loGout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root =  inflater.inflate(R.layout.fragment_admin_profile, container, false);
        viewInfo = root.findViewById(R.id.btn_admin_profile_viewinfo);
        changePass = root.findViewById(R.id.btn_admin_profile_changepass);
        loGout = root.findViewById(R.id.admin_logout);

        viewInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AdminEditProfileActivity.class);
                startActivity(intent);
            }
        });

        changePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AdminChangePasswordActivity.class);
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