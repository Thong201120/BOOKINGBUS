package com.example.project.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.project.R;

public class Login extends AppCompatActivity {
    public static String CUSTOMUSERNAME = "CUSTOMUSERNAME";
    public static String CUSTOMFULLNAME = "CUSTOMFULLNAME";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText username = findViewById(R.id.user);
        EditText password = findViewById(R.id.pass);
        Button signin = findViewById(R.id.login);
        Button signup = findViewById(R.id.signup);
        DBHelper DB = new DBHelper(this);
        ImageView home = findViewById(R.id.returnhome);
        Button forgot = findViewById(R.id.user_forgot);
        Toolbar toolbar = findViewById(R.id.user_login_toolbar);
        setSupportActionBar(toolbar);
        CheckBox checkBox = findViewById(R.id.checkBoxuser);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = username.getText().toString();
                String pass = password.getText().toString();

                if (user.equals("") || pass.equals(""))
                    Toast.makeText(Login.this, "Không bỏ trống các trường", Toast.LENGTH_SHORT).show();
                else{
                    Boolean checkuserpass = DB.checkusernamepassword(user, pass);
                    Cursor cursor = DB.getuser(user);
                    String fullname = "";
                    if (cursor.moveToFirst()) {
                        fullname = cursor.getString(cursor.getColumnIndex("fullname"));
                    }
                    if (checkuserpass==true){
                        Toast.makeText(Login.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                        rememberUser(user, pass, checkBox.isChecked());
                        Intent intent= new Intent(getApplicationContext(), UserMainActivity.class);
                        CUSTOMUSERNAME = user;
                        CUSTOMFULLNAME = fullname;
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(Login.this, "Đăng nhập thất bại. Vui lòng kiểm tra lại thông tin!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });




        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Signup.class);
                startActivity(intent);
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), UserEnterEmailActivity.class);
                startActivity(intent);
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

    public void rememberUser(String username, String password, boolean status) {
        SharedPreferences sharedPreferences = getSharedPreferences("USER_FILE.txt", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (!status) {
            editor.clear();
        }
        else {
            //Thêm data vào file
            editor.putString("USERNAME", username);
            editor.putString("PASSWORD", password);
            editor.putBoolean("REMEMBER", status);
        }

        //Lưu lại
        editor.commit();
    }

}