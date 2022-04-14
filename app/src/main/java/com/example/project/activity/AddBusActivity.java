package com.example.project.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.project.AdminMainFragment;
import com.example.project.R;
import com.google.android.material.navigation.NavigationView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddBusActivity extends AppCompatActivity {
    final Calendar myCalendar = Calendar.getInstance();
    EditText busid, from, to, dt, tm, seats, price;
    int hourtime, minutetime;
    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addbus);
        DBHelper DB = new DBHelper(this);
        int countid = DB.countid();
        String setid = String.valueOf(countid+1);
        busid = findViewById(R.id.id);
        from = findViewById(R.id.from);
        to = findViewById(R.id.to);
        dt = findViewById(R.id.date);
        tm = findViewById(R.id.time);
        seats = findViewById(R.id.seats);
        price = findViewById(R.id.price);
        Button addbus = findViewById(R.id.addbus);
        Toolbar toolbar = findViewById(R.id.addbus_toolbar);
        busid.setText(setid);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

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
                new DatePickerDialog(AddBusActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        tm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        AddBusActivity.this,
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


        addbus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = busid.getText().toString();
                int intbusid = Integer.parseInt(id);
                String departure = from.getText().toString();
                String arrival = to.getText().toString();
                String date = dt.getText().toString();
                String time = tm.getText().toString();
                String total_seats = seats.getText().toString();
                Intent intent0 = getIntent();
                int status = 1;
                String adminname = adminlogin.ADMINUSERNAME;

                String prices= price.getText().toString();

                System.out.println("Đây nè!!!!");
                System.out.println(adminname);
                if (id.equals("") || departure.equals("") || arrival.equals("") || date.equals("") || time.equals("") || total_seats.equals("") || prices.equals("")) {
                    Toast.makeText(AddBusActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }
                else if (!isNumeric(total_seats))
                {
                    Toast.makeText(AddBusActivity.this, "Số ghế phải là chữ số", Toast.LENGTH_SHORT).show();
                }
                else if (!isNumeric(prices))
                {
                    Toast.makeText(AddBusActivity.this, "Giá vé phải là chữ số", Toast.LENGTH_SHORT).show();
                }
                else {
                    int int_totalseats = Integer.parseInt(total_seats);
                    int finalValue=Integer.parseInt(prices);
                    Boolean checkid = DB.checkid(id);
                    if (checkid == false) {
                        Boolean insert = DB.insertBus(intbusid, adminname, departure, arrival, date, time, int_totalseats, int_totalseats, finalValue, status);
                        if (insert == true) {
                            Toast.makeText(AddBusActivity.this, "Tuyến xe vừa thêm thành công", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), AdminMainActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(AddBusActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(AddBusActivity.this, "ID đã tồn tại", Toast.LENGTH_SHORT).show();
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