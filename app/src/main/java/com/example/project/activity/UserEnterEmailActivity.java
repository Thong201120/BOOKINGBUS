package com.example.project.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.project.R;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Properties;
import java.util.Random;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class UserEnterEmailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_enter_email);
        Toolbar toolbar = findViewById(R.id.user_email_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        EditText textmail = findViewById(R.id.user_email);
        ImageView home = findViewById(R.id.returnhome);
        Button add = findViewById(R.id.user_reset_button);
        String email = textmail.getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z.]+";
        DBHelper DB = new DBHelper(this);
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
                Boolean checkuseremail = DB.checkuseremail(email);
                if (email.equals("")){
                    Toast.makeText(UserEnterEmailActivity.this, "Email không được bỏ trống", Toast.LENGTH_SHORT).show();
                }
                else if (!(email.matches(emailPattern)))
                {
                    Toast.makeText(UserEnterEmailActivity.this, "Email không hợp lệ", Toast.LENGTH_SHORT).show();
                }
                else if (checkuseremail == false)
                {

                    Toast.makeText(UserEnterEmailActivity.this, "Email chưa được đăng ký", Toast.LENGTH_SHORT).show();
                }
                else {
                    Random rnd = new Random();
                    int number = rnd.nextInt(999999);
                    final String sendingusername = "doanchuyennganh02@gmail.com";
                    final String sendingpassword = "1234@doan";
                    Properties props = new Properties();
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
                        message.setSubject("Mã xác minh tài khoản");
                        message.setText(String.valueOf(number) + " là mã xác minh để khôi phục mật khẩu của bạn, vui lòng không cung cấp mã này cho người khác để bảo mật cho tài khoản của bạn");
                        Transport.send(message);
                        Toast.makeText(getApplicationContext(), "Đã gửi email đến bạn", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), UserResetPasswordActivity.class);
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