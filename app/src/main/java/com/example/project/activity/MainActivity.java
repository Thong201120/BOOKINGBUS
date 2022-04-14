package com.example.project.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.project.R;

public class MainActivity extends AppCompatActivity {
    String strUsername = "";
    String password = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DBHelper DB = new DBHelper(this);
        Button admin = findViewById(R.id.admin);
        Button customer = findViewById(R.id.customer);

        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkLoginRemember()>0)
                {
                    SharedPreferences sharedPreferences = getSharedPreferences("USER_FILE.txt", MODE_PRIVATE);
                    strUsername = sharedPreferences.getString("USERNAME", "");
                    password = sharedPreferences.getString("PASSWORD", "");
                    Cursor cursor = DB.getadmin(strUsername);
                    String fullname = "";
                    if (cursor.moveToFirst()) {
                        fullname = cursor.getString(cursor.getColumnIndex("fullname"));
                    }
                    Boolean checkadminuserpass = DB.checkadminusernamepassword(strUsername, password);
                    if (checkadminuserpass==true){
                        Toast.makeText(MainActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                        adminlogin.ADMINUSERNAME = strUsername;
                        adminlogin.ADMINFULLNAME = fullname;
                        Intent intent= new Intent(getApplicationContext(), AdminMainActivity.class);
                        startActivity(intent);
                    }
                    else {
                        Intent intent = new Intent(getApplicationContext(), adminlogin.class);
                        startActivity(intent);
                    }

                }
                else{
                    Intent intent = new Intent(getApplicationContext(), adminlogin.class);
                    startActivity(intent);
                }

            }
        });

        customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkLoginRemember()>0)
                {
                    SharedPreferences sharedPreferences = getSharedPreferences("USER_FILE.txt", MODE_PRIVATE);
                    strUsername = sharedPreferences.getString("USERNAME", "");
                    password = sharedPreferences.getString("PASSWORD", "");
                    Cursor cursor = DB.getuser(strUsername);
                    String fullname = "";
                    if (cursor.moveToFirst()) {
                        fullname = cursor.getString(cursor.getColumnIndex("fullname"));
                    }
                    Boolean checkuserpass = DB.checkusernamepassword(strUsername, password);
                    if (checkuserpass==true){
                        Toast.makeText(MainActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                        Login.CUSTOMUSERNAME = strUsername;
                        Login.CUSTOMFULLNAME = fullname;
                        Intent intent= new Intent(getApplicationContext(), UserMainActivity.class);
                        startActivity(intent);
                    }
                    else {
                        Intent intent = new Intent(getApplicationContext(), Login.class);
                        startActivity(intent);
                    }

                }
                else {
                    Intent intent = new Intent(getApplicationContext(), Login.class);
                    startActivity(intent);
                }

            }
        });
    }
    public int checkLoginRemember() {
        SharedPreferences sharedPreferences = getSharedPreferences("USER_FILE.txt", MODE_PRIVATE);
        boolean chk = sharedPreferences.getBoolean("REMEMBER", false);
        if (chk) {
            strUsername = sharedPreferences.getString("USERNAME", "");
            return 1;
        }
        return -1;
    }

}