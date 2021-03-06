package com.example.project.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.project.R;

import java.util.regex.Pattern;

public class Signup extends AppCompatActivity {
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[0-9])" +         //at least 1 digit
                    //"(?=.*[a-z])" +         //at least 1 lower case letter
                    //"(?=.*[A-Z])" +         //at least 1 upper case letter
                    "(?=.*[a-zA-Z])" +      //any letter
                    "(?=.*[@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces".
//                    ".{4,}" +               //at least 4 characters
                    "$");
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        Button btnsignup = findViewById(R.id.btn1);
        Button btnsignin = findViewById(R.id.user_login_below);
        EditText uFullname = findViewById(R.id.user_signup_fullname);
        EditText uUserName = findViewById(R.id.user_signup_username);
        EditText uEmail = findViewById(R.id.user_signup_email);
        EditText uPhone = findViewById(R.id.user_signup_phone);
        EditText uPassword = findViewById(R.id.user_signup_password);
        ImageView home = findViewById(R.id.returnhome);
        Toolbar toolbar = findViewById(R.id.user_signup_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        DB = new DBHelper(this);

        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullname = uFullname.getText().toString();
                String email = uEmail.getText().toString();
                String username = uUserName.getText().toString();
                String password = uPassword.getText().toString();
                String phone = uPhone.getText().toString();
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z.]+";
                String phonePattern = "^[+]?[0-9]{10,13}$";
                if(DB.checkiduserusername(username)) {
                    Toast.makeText(getApplicationContext(), "Username ???? t???n t???i", Toast.LENGTH_SHORT).show();
                }
                else if (DB.checkadminemail(email)) {
                    Toast.makeText(getApplicationContext(), "Email ???? t???n t???i", Toast.LENGTH_SHORT).show();
                }
                if(!(email.matches(emailPattern))) {
                    Toast.makeText(getApplicationContext(), "?????a ch??? mail kh??ng h???p l???", Toast.LENGTH_SHORT).show();
                }
                else if(!(phone.matches(phonePattern))) {
                    Toast.makeText(getApplicationContext(), "S??? ??i???n tho???i kh??ng h???p l???", Toast.LENGTH_SHORT).show();
                }
                else if(password.length() < 8) {
                    Toast.makeText(getApplicationContext(), "M???t kh???u ph???i d??i t???i thi???u 8 k?? t???", Toast.LENGTH_SHORT).show();
                }
//                else if(!PASSWORD_PATTERN.matcher(password).matches()) {
//                    Toast.makeText(getApplicationContext(), "M???t kh???u ph???i bao g???m ??t nh???t 1 s??? v?? 1 k?? t??? ?????c bi???t", Toast.LENGTH_SHORT).show();
//                }
                else if (fullname.equals("") ||email.equals("")||username.equals("")||password.equals("")||phone.equals(""))
                    Toast.makeText(Signup.this, "Kh??ng b??? tr???ng c??c tr?????ng", Toast.LENGTH_SHORT).show();
                else {
                    Boolean checkuser = DB.checkusername(username);
                    if (checkuser==false){
                        Boolean insert = DB.insertData(fullname,email,username, phone, password);
                        if (insert==true){
                            Toast.makeText(Signup.this, "????ng k?? th??nh c??ng", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), Login.class);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(Signup.this, "????ng k?? th???t b???i. Vui l??ng ki???m tra l???i th??ng tin!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
        btnsignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
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