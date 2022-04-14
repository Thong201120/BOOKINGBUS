package com.example.project.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project.AdminMainFragment;
import com.example.project.R;
import com.example.project.adapters.ListBusAdapter;
import com.example.project.models.ListBusModel;
import com.google.android.material.navigation.NavigationView;
import com.google.common.base.MoreObjects;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ListBusActivity extends AppCompatActivity {
    private AppBarConfiguration mAppBarConfiguration;
    ListBusAdapter listBusAdapter;
    RecyclerView recyclerView;
    List<ListBusModel> list = new ArrayList<>();
    SearchView searchView, barsearchView;
    public static final int REQUEST_CODE_SPEECH_INPUT = 1000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_bus);
        Toolbar toolbar = findViewById(R.id.viewbus_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        recyclerView = findViewById(R.id.ListBusView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

//        myDB = new DBHelper(this);
//        Intent intent0 = getIntent();
//        String admin = "";
//        String adminname = intent0.getStringExtra(AdminMainFragment.USERNAME);
        ;
        String adminname = adminlogin.ADMINUSERNAME;
        System.out.println("Đây nè");
        System.out.println(adminname);
        Cursor data = new DBHelper(this).viewactivebuses(adminname);
//        if(data.getCount() == 0){
//            Toast.makeText(this, "There are no contents in this list!",Toast.LENGTH_LONG).show();
//        }else{
        while(data.moveToNext()) {
            ListBusModel obj = new ListBusModel(data.getString(0), data.getString(1), data.getString(2),data.getString(3), data.getString(4), data.getString(5), data.getInt(6), data.getInt(7), data.getInt(8), data.getInt(9));
            System.out.println(data.getString(1));
            list.add(obj);
            }
        listBusAdapter = new ListBusAdapter(list, this);
        recyclerView.setAdapter(listBusAdapter);
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
        getMenuInflater().inflate(R.menu.menu_main, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        MenuItem sItem = menu.findItem(R.id.action_voice);
        barsearchView = (SearchView) menu.findItem(R.id.action_voice).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);
        barsearchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Bạn muốn tìm gì?");
                try {
                    startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT);
                }
                catch (ActivityNotFoundException e)
                {
                    Toast.makeText(ListBusActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                listBusAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                listBusAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

    @Override
    public void onBackPressed() {
        if (!searchView.isIconified() || !barsearchView.isIconified()) {
            searchView.setIconified(true);
            return;
        }
        super.onBackPressed();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    searchView.setQuery(result.get(0), true);
                    searchView.clearFocus();
                }
                break;
            }
        }
    }
}