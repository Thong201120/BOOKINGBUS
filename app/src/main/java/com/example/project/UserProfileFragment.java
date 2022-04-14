package com.example.project;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.proto.ProtoOutputStream;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.project.activity.AdminChangePasswordActivity;
import com.example.project.activity.AdminProfileActivity;
import com.example.project.activity.MainActivity;
import com.example.project.activity.UserChangePasswordActivity;
import com.example.project.activity.UserEditProfileActivity;
import com.example.project.activity.UserProfileActivity;


public class UserProfileFragment extends Fragment {

    Button viewInfo, changePass, loGout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root =  inflater.inflate(R.layout.fragment_user_profile, container, false);

        viewInfo = root.findViewById(R.id.btn_user_profile_viewinfo);
        changePass = root.findViewById(R.id.btn_user_profile_changepass);
        loGout = root.findViewById(R.id.logout);
        viewInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), UserEditProfileActivity.class);
                startActivity(intent);
            }
        });

        changePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), UserChangePasswordActivity.class);
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