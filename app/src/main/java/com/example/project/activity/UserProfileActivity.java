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
import com.example.project.adapters.ListUserAdapter;
import com.example.project.models.ListAdminModel;
import com.example.project.models.ListUserModel;

import java.util.ArrayList;
import java.util.List;

public class UserProfileActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<ListUserModel> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile_edit);

        Toolbar toolbar = findViewById(R.id.user_profile_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        recyclerView = findViewById(R.id.ListUserProfileView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Cursor data = new DBHelper(this).users();
//        if(data.getCount() == 0){
//            Toast.makeText(this, "There are no contents in this list!",Toast.LENGTH_LONG).show();
//        }else{
        while (data.moveToNext()) {
            ListUserModel obj = new ListUserModel(data.getString(0), data.getString(1), data.getString(2), data.getString(3), data.getString(4));
            list.add(obj);
        }
        ListUserAdapter listUserAdapter = new ListUserAdapter(list, this);
        recyclerView.setAdapter(listUserAdapter);
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