package com.example.project.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;

import com.example.project.R;

import java.util.Properties;
import java.util.Random;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class AdminEnterEmailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_enter_email);
        EditText textmail = findViewById(R.id.admin_mail);
        Button add = findViewById(R.id.admin_reset_button);
        Toolbar toolbar = findViewById(R.id.admin_phone_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        String email = textmail.getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z.]+";
        DBHelper DB = new DBHelper(this);

        System.out.println(email);
        ImageView home = findViewById(R.id.returnhome);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = textmail.getText().toString();
                Boolean checkadminemail = DB.checkadminemail(email);
                if (email.equals("")){
                    Toast.makeText(AdminEnterEmailActivity.this, "Email kh??ng ???????c b??? tr???ng", Toast.LENGTH_SHORT).show();
                }
                else if (!(email.matches(emailPattern)))
                {
                    Toast.makeText(AdminEnterEmailActivity.this, "Email kh??ng h???p l???", Toast.LENGTH_SHORT).show();
                }
                else if (checkadminemail == false)
                {

                    Toast.makeText(AdminEnterEmailActivity.this, "Email ch??a ???????c ????ng k??", Toast.LENGTH_SHORT).show();
                }
                else {
                    Random rnd = new Random();
                    int number = rnd.nextInt(999999);
                    final String sendingusername = "doanchuyennganh02@gmail.com";
                    final String sendingpassword = "1234@doan";
                    Properties  props = new Properties();
                    props.put("mail.smtp.auth", "true");
                    props.put("mail.smtp.starttls.enable", "true");
                    props.put("mail.smtp.host", "smtp.gmail.com");
                    props.put("mail.smtp.port", "587");
                    Session session = Session.getInstance(props, new javax.mail.Authenticator() {
                        @Override
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(sendingusername, sendingpassword);
                        }
                    });
                    try{
                        Message message = new MimeMessage(session);
                        message.setFrom(new InternetAddress(sendingusername));
                        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
                        message.setSubject("M?? x??c minh t??i kho???n");
                        message.setText(String.valueOf(number) + " l?? m?? x??c minh ????? kh??i ph???c m???t kh???u c???a b???n, vui l??ng kh??ng cung c???p m?? n??y cho ng?????i kh??c ????? b???o m???t cho t??i kho???n c???a b???n");
                        Transport.send(message);
                        Toast.makeText(getApplicationContext(), "???? g???i email ?????n b???n", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), AdminResetPasswordActivity.class);
                        intent.putExtra("EMAIL", email);
                        intent.putExtra("OTPCODE", String.valueOf(number));
                        startActivity(intent);
                    }
                    catch (MessagingException ex)
                    {
                        throw  new RuntimeException(ex);
                    }
                }
            }
        });
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
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