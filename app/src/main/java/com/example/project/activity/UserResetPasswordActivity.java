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

public class UserResetPasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_reset_password);
        Toolbar toolbar = findViewById(R.id.user_reset_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setSupportActionBar(toolbar);

        EditText otpCode = findViewById(R.id.user_otp_code);
        Button submit = findViewById(R.id.user_otp_button);
        ImageView home = findViewById(R.id.returnhome);
        Intent intent = getIntent();
        String email = intent.getStringExtra("EMAIL");
        String comfirmOTP = intent.getStringExtra("OTPCODE");
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
                String OTP = otpCode.getText().toString();
                if (OTP.equals(""))
                {
                    Toast.makeText(getApplicationContext(), "Vui lòng nhập mã xác minh để tiếp tục", Toast.LENGTH_SHORT).show();
                }
                else if (!OTP.equals(comfirmOTP))
                {

                    Toast.makeText(getApplicationContext(), "Mã xác minh không chính xác", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Xác minh thành công", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), UserNewPasswordActivity.class);
                    intent.putExtra("EMAIL", email);
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