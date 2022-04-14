package com.example.project.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.project.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class AdminNewPasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_new_password);
        Toolbar toolbar = findViewById(R.id.admin_add_new_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        ImageView home = findViewById(R.id.returnhome);
        EditText enterpass = findViewById(R.id.adminnewpass);
        EditText reenter = findViewById(R.id.adminconfirmnewpass);
        Button submit = findViewById(R.id.admin_reset_change);
        Intent intent = getIntent();
        String email = intent.getStringExtra("EMAIL");
        DBHelper DB = new DBHelper(this);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strPass = enterpass.getText().toString();
                String strReenter = reenter.getText().toString();
                if (strPass.equals("") || strReenter.equals(""))
                {
                    Toast.makeText(getApplicationContext(), "Vui lòng nhập đầy dủ các trường", Toast.LENGTH_SHORT).show();
                }
                else if(strPass.length() < 8) {
                    Toast.makeText(getApplicationContext(), "Mật khẩu mới phải dài tối thiểu 8 ký tự", Toast.LENGTH_SHORT).show();
                }
                else if(!strPass.equals(strReenter)) {
                    Toast.makeText(getApplicationContext(), "Mật khẩu nhập lại không khớp với mật khẩu mới", Toast.LENGTH_SHORT).show();
                }
                else {
                    Boolean updatepass = DB.updateAdminPassword(strPass, email);
                    Toast.makeText(getApplicationContext(), "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), adminlogin.class);
                    startActivity(intent);
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