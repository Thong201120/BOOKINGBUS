package com.example.project.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.example.project.R;
import com.example.project.models.ListAdminModel;
import com.example.project.models.ListUserModel;

public class UserEditProfileActivity extends AppCompatActivity {
    TextView  username, fullname, phone, email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_edit_profile);


        Toolbar toolbar = findViewById(R.id.edituserprofile_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        username = findViewById(R.id.edituser_username);
        fullname = findViewById(R.id.edituser_fullname);
        phone = findViewById(R.id.edituser_phone);
        email = findViewById(R.id.edituser_email);
        String Strusername = Login.CUSTOMUSERNAME;
        DBHelper DB = new DBHelper(this);
        Cursor cursor = DB.getcontentuser(Strusername);

        String Strfullname = "";
        String Strphone = "";
        String Stremail = "";

        if (cursor.moveToFirst()) {
            Strfullname = cursor.getString(cursor.getColumnIndex("fullname"));
            Strphone = cursor.getString(cursor.getColumnIndex("phone"));
            Stremail = cursor.getString(cursor.getColumnIndex("email"));
        }
        username.setText(Strusername);
        fullname.setText(Strfullname);
        phone.setText(Strphone);
        email.setText(Stremail);

        Button update = findViewById(R.id.btn_updateuser_profile);
        Button cancel = findViewById(R.id.btn_cancel_user_profile);



        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), UserMainActivity.class);
                startActivity(intent);
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String efullname = fullname.getText().toString();
                String eusername  = username.getText().toString();
                String eemail = email.getText().toString();
                String ephone = phone.getText().toString();
                String phonePattern = "^[+]?[0-9]{10,13}$";
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z.]+";
                if (efullname.equals("") || eusername.equals("") || eemail.equals("") || ephone.equals(""))
                {
                    Toast.makeText(UserEditProfileActivity.this, "Vui l??ng kh??ng b??? tr???ng tr?????ng", Toast.LENGTH_SHORT).show();
                }
                else if(!(eemail.matches(emailPattern))) {
                    Toast.makeText(getApplicationContext(), "?????a ch??? mail kh??ng h???p l???", Toast.LENGTH_SHORT).show();
                }
                else if(!(ephone.matches(phonePattern))) {
                    Toast.makeText(getApplicationContext(), "S??? ??i???n tho???i kh??ng h???p l???", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Boolean checkid= DB.checkiduserusername(eusername);
                    if (checkid==true){
                        Boolean update = DB.updateUserProfile(eusername, ephone, eemail, efullname);
                        if (update==true){
                            Toast.makeText(UserEditProfileActivity.this, "C???p nh???t th??nh c??ng", Toast.LENGTH_SHORT).show();
                            Login.CUSTOMFULLNAME = efullname;
                            Login.CUSTOMUSERNAME = eusername;
                            Intent intent = new Intent(getApplicationContext(), UserMainActivity.class);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(UserEditProfileActivity.this, "M???c kh??ng ???????c c???p nh???t", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(UserEditProfileActivity.this, "ID Kh??ng t???n t???i", Toast.LENGTH_SHORT).show();
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

//                Intent intent = new Intent(AdminEditProfileActivity.this, AdminProfileActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent);
//                finish();
//                return true;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}
