package com.example.project.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.project.R;
import com.example.project.adapters.ListAdminAdapter;
import com.example.project.models.ListAdminModel;

import java.util.ArrayList;
import java.util.List;

public class AdminProfileActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<ListAdminModel> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_profile_edit);
        Toolbar toolbar = findViewById(R.id.admin_profile_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        recyclerView = findViewById(R.id.ListAdminProfileView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Cursor data = new DBHelper(this).admin();
//        if(data.getCount() == 0){
//            Toast.makeText(this, "There are no contents in this list!",Toast.LENGTH_LONG).show();
//        }else{
        while (data.moveToNext()) {
            ListAdminModel obj = new ListAdminModel(data.getString(0), data.getString(1), data.getString(2), data.getString(3), data.getString(4));
            list.add(obj);
        }
        ListAdminAdapter listAdminAdapter = new ListAdminAdapter(list, this);
        recyclerView.setAdapter(listAdminAdapter);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}