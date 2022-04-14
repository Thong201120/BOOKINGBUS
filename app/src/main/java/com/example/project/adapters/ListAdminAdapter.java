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

import com.example.project.activity.AdminEditProfileActivity;
import com.example.project.R;
import com.example.project.models.ListAdminModel;

import java.util.List;

public class ListAdminAdapter extends RecyclerView.Adapter<ListAdminAdapter.ViewHolder>{
    public ListAdminAdapter(List<ListAdminModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    List<ListAdminModel> list;
    Context context;


    @NonNull
    @Override
    public ListAdminAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_admin, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ListAdminAdapter.ViewHolder holder, int position) {
        final ListAdminModel adminModel = list.get(position);
        if (adminModel == null)
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
                onClickUpdateProfileAdmin(adminModel);
            }
        });
    }

    private void onClickUpdateProfileAdmin(ListAdminModel adminModel)
    {
        Intent intent = new Intent(context, AdminEditProfileActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("list", adminModel);
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

            username = itemView.findViewById(R.id.admin_profile_username);
            fullname = itemView.findViewById(R.id.admin_profile_name);
            phone = itemView.findViewById(R.id.admin_profile_number);
            email = itemView.findViewById(R.id.admin_profile_email);
            layoutItem = itemView.findViewById(R.id.admin_profile_layout);
            update = itemView.findViewById(R.id.btn_admin_profile_update);
        }
    }
}
