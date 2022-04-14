package com.example.project.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.R;
import com.example.project.activity.AdminEditProfileActivity;
import com.example.project.activity.UserEditProfileActivity;
import com.example.project.models.ListAdminModel;
import com.example.project.models.ListUserModel;

import java.util.List;

public class ListUserAdapter extends RecyclerView.Adapter<ListUserAdapter.ViewHolder>{
    public ListUserAdapter(List<ListUserModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    List<ListUserModel> list;
    Context context;


    @NonNull
    @Override
    public ListUserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ListUserAdapter.ViewHolder holder, int position) {
        final ListUserModel userModel = list.get(position);
        if (userModel == null)
        {
            return;
        }

        holder.fullname.setText(list.get(position).getFullname());
        holder.username.setText(list.get(position).getUsername());
        holder.email.setText(list.get(position).getEmail());
        holder.phone.setText(list.get(position).getPhone());

        holder.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickUpdateProfileAdmin(userModel);
            }
        });
    }

    private void onClickUpdateProfileAdmin(ListUserModel userModel)
    {
        Intent intent = new Intent(context, UserEditProfileActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("list", userModel);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView layoutItem;
        Button update;
        TextView username, fullname, phone, email;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            username = itemView.findViewById(R.id.user_profile_username);
            fullname = itemView.findViewById(R.id.user_profile_name);
            phone = itemView.findViewById(R.id.user_profile_number);
            email = itemView.findViewById(R.id.user_profile_email);
            layoutItem = itemView.findViewById(R.id.user_profile_layout);
            update = itemView.findViewById(R.id.btn_user_profile_update);
        }
    }
}
