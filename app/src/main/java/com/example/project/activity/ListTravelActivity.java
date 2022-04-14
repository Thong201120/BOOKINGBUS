package com.example.project.activity;

import android.app.SearchManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.R;
import com.example.project.adapters.ListOrderAdapter;
import com.example.project.adapters.ListTravelAdapter;
import com.example.project.models.ListOrderModel;
import com.example.project.models.ListTravelModel;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ListTravelActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    SearchView searchView, barsearchView;
    ListTravelAdapter listTravelAdapter;
    List<ListTravelModel> list = new ArrayList<>();
//ArrayList<Creator> list = new ArrayList<Creator>();
    public static final int REQUEST_CODE_SPEECH_INPUT_TRAVEL = 1000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_travel);
        Toolbar toolbar = findViewById(R.id.viewtravel_toolbar);
        setSupportActionBar(toolbar);
        TextView sumRevenue = findViewById(R.id.sumUserrevenue);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        recyclerView = findViewById(R.id.ListTravelView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

//        myDB = new DBHelper(this);
        String username = Login.CUSTOMUSERNAME;
        int getsum = new DBHelper(this).sumUserRevenue(username);
        sumRevenue.setText("TỔNG CHI: " + String.valueOf(getsum) + "vnđ");
        Cursor data = new DBHelper(this).viewtravel(username);
//        if(data.getCount() == 0){
//            Toast.makeText(this, "There are no contents in this list!",Toast.LENGTH_LONG).show();
//        }else{
        while(data.moveToNext()) {
//          id INTEGER primary key,username TEXT, departure TEXT, arrival TEXT, date TEXT, time TEXT, price INT, number_seat INT, revenue INT)"
            ListTravelModel obj = new ListTravelModel(data.getInt(0), data.getInt(1), data.getString(2),data.getString(3), data.getString(4), data.getString(5), data.getString(6), data.getInt(7), data.getInt(8), data.getInt(9));
            list.add(obj);
            }
        listTravelAdapter = new ListTravelAdapter(list, this);
        recyclerView.setAdapter(listTravelAdapter);
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
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);
        barsearchView = (SearchView) menu.findItem(R.id.action_voice).getActionView();
        barsearchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Bạn muốn tìm gì?");
                try {
                    startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT_TRAVEL);
                }
                catch (ActivityNotFoundException e)
                {
                    Toast.makeText(ListTravelActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                listTravelAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                listTravelAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_SPEECH_INPUT_TRAVEL: {
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