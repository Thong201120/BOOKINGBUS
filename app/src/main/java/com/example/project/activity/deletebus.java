package com.example.project.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.project.R;

public class deletebus extends AppCompatActivity {
    public static String ADMINNAME = "ADMINNAME";
    public static String ADMINFULLNAME = "ADMINFULLNAME";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deletebus);

        EditText busid = findViewById(R.id.busid);
        Button deletebus = findViewById(R.id.deletebus);
        DBHelper DB = new DBHelper(this);
        Toolbar toolbar = findViewById(R.id.delete_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        String adminfullname = adminlogin.ADMINFULLNAME;
        String adminname = adminlogin.ADMINUSERNAME;
        deletebus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = busid.getText().toString();

                if (id.equals(""))
                {
                    Toast.makeText(deletebus.this, "Vui lòng nhập id tuyến xe", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    int intbusid = Integer.parseInt(id);
                    Boolean checkidbusstatus= DB.checkidbusstatus(id, adminname);
                    if (checkidbusstatus==true){
                        Cursor cursor = DB.getsinglebus(id);
                        String total_seats = "";
                        String seated = "";
                        if (cursor.moveToFirst()) {
                            total_seats = cursor.getString(cursor.getColumnIndex("total_seats"));
                            seated = cursor.getString(cursor.getColumnIndex("seated"));
                        }
                        if (!seated.equals(total_seats))
                        {
                            Toast.makeText(deletebus.this, "Tuyến xe đã được đặt vé, không thể xóa", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Boolean delete = DB.updateBusStatus(intbusid, 0);
                            if (delete == true) {
                                Toast.makeText(deletebus.this, "Xóa tuyến thành công", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), AdminMainActivity.class);
                                intent.putExtra(ADMINNAME, adminname);
                                intent.putExtra(ADMINFULLNAME, adminfullname);
                                startActivity(intent);
                            } else {
                                Toast.makeText(deletebus.this, "Tuyến chưa được xóa", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                    else {
                        Toast.makeText(deletebus.this, "Tuyến xe không tồn tại để xóa", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

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