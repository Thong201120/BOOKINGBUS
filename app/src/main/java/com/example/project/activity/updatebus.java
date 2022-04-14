package com.example.project.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.project.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class updatebus extends AppCompatActivity {
    final Calendar myCalendar = Calendar.getInstance();
    private EditText busid, from, to, dt, tm, seats, price;
    int hourtime, minutetime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updatebus);
        Toolbar toolbar = findViewById(R.id.updatebus_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
         busid = findViewById(R.id.id);
         from = findViewById(R.id.from);
         to = findViewById(R.id.to);
         dt = findViewById(R.id.date);
         tm = findViewById(R.id.time);
         seats = findViewById(R.id.seats);
        price = findViewById(R.id.price);
        Button updatebus = findViewById(R.id.updatebus);
        DBHelper DB = new DBHelper(this);

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDate();
            }

        };


        dt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(updatebus.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        tm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        updatebus.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                hourtime = hourOfDay;
                                minutetime = minute;

                                Calendar calendar = Calendar.getInstance();
                                calendar.set(0, 0, 0, hourtime, minutetime);
                                tm.setText(DateFormat.format("hh:mm aa", calendar));
                            }
                        }, 12, 0, false
                );
                timePickerDialog.updateTime(hourtime, minutetime);
                timePickerDialog.show();
            }
        });


        updatebus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = busid.getText().toString();
                String departure = from.getText().toString();
                String arrival = to.getText().toString();
                String date = dt.getText().toString();
                String time = tm.getText().toString();
                String total_seats = seats.getText().toString();
                int int_total_seats = Integer.parseInt(total_seats);
                String prices = price.getText().toString();
                int intprice = Integer.parseInt(prices);
                String adminname = adminlogin.ADMINUSERNAME;
                Cursor cursor = DB.getnumberseat(adminname, id);
                String finalseat = "";
                String finalseated = "";
                if (cursor.moveToFirst()) {
                    finalseat = cursor.getString(cursor.getColumnIndex("total_seats"));
                    finalseated = cursor.getString(cursor.getColumnIndex("seated"));
                }
                if (id.equals("") || departure.equals("") || arrival.equals("") || date.equals("") || time.equals("") || total_seats.equals("") || prices.equals(""))
                {
                    Toast.makeText(updatebus.this, "Vui lòng không bỏ trống các trường", Toast.LENGTH_SHORT).show();
                }
                else
                {   Boolean checkid= DB.checkid(id);
                    if (!checkid)
                    {
                        Toast.makeText(updatebus.this, "Tuyến xe không tồn tại", Toast.LENGTH_SHORT).show();
                    }
                    else if (!finalseated.equals(finalseat))
                    {
                        Toast.makeText(updatebus.this, "Tuyến xe đã được đặt vé, không thể cập nhật", Toast.LENGTH_SHORT).show();
                    }

                    else if (checkid==true){
                        Boolean update = DB.updateBus(id,departure,arrival,date, time, int_total_seats, int_total_seats, intprice);
                        if (update==true){
                            Toast.makeText(updatebus.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), AdminMainActivity.class);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(updatebus.this, "Tuyến không được cập nhật", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(updatebus.this, "ID không tồn tại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


    }

    private void updateDate() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);


        dt.setText(sdf.format(myCalendar.getTime()));
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