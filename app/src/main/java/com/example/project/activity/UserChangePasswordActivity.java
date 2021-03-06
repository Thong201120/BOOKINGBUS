package com.example.project.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.project.R;

public class UserChangePasswordActivity extends AppCompatActivity {

    TextView currentpassword, newpassword, reenterpassword;
    Button update, cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_change_password);

        Toolbar toolbar = findViewById(R.id.edituserpassword_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        currentpassword = findViewById(R.id.edituser_currpass);
        newpassword = findViewById(R.id.edituser_newpass);
        reenterpassword = findViewById(R.id.edituser_reenterpass);
        String Strusername = Login.CUSTOMUSERNAME;
        update = findViewById(R.id.btn_updateuser_changepass);
        cancel = findViewById(R.id.btn_cancel_user_changepass);

        DBHelper DB = new DBHelper(this);

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
                String ecurrpass = currentpassword.getText().toString();
                String enewpass = newpassword.getText().toString();
                String ereenter = reenterpassword.getText().toString();

                if (ecurrpass.equals("") || enewpass.equals("") || ereenter.equals(""))
                {
                    Toast.makeText(UserChangePasswordActivity.this, "Vui l??ng kh??ng b??? tr???ng tr?????ng", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (!enewpass.equals(ereenter))
                    {
                        Toast.makeText(UserChangePasswordActivity.this, "M???t kh???u nh???p l???i kh??ng kh???p", Toast.LENGTH_SHORT).show();
                    }
                    else if(enewpass.length() < 8) {
                        Toast.makeText(getApplicationContext(), "M???t kh???u ph???i d??i t???i thi???u 8 k?? t???", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Boolean checkpass = DB.checkuserpass(Strusername, ecurrpass);
                        if (checkpass == true){
                            Boolean update = DB.updateUserPass(Strusername, enewpass);
                            if (update == true) {
                                System.out.println("Sai ??? ????y");
                                Toast.makeText(UserChangePasswordActivity.this, "C???p nh???t th??nh c??ng", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), UserMainActivity.class);
                                startActivity(intent);
                            }
                            else {
                                Toast.makeText(UserChangePasswordActivity.this, "M???c kh??ng ???????c c???p nh???t", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else {
                            Toast.makeText(UserChangePasswordActivity.this, "M???t kh???u hi???n t???i kh??ng ch??nh x??c", Toast.LENGTH_SHORT).show();
                        }
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
