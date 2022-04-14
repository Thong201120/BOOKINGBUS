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
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.project.R;
import com.example.project.models.ListBusModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AdminEditActivity extends AppCompatActivity {
    final Calendar myCalendar = Calendar.getInstance();
    int hourtime, minutetime;
    TextView id, from, to, dt, tm, seats, price, seated;
    Button endbus, deletebus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_edit);

        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            return;
        }
        String adminname = adminlogin.ADMINUSERNAME;
        String adminfullname = adminlogin.ADMINFULLNAME;
        ListBusModel item = (ListBusModel) bundle.get("list");
        Toolbar toolbar = findViewById(R.id.editbus_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        id = findViewById(R.id.edit_id);
        from = findViewById(R.id.edit_from);
        to = findViewById(R.id.edit_to);
        dt = findViewById(R.id.edit_date);
        tm = findViewById(R.id.edit_time);
        seats = findViewById(R.id.edit_seats);
        price = findViewById(R.id.edit_price);
        seated = findViewById(R.id.edit_seated);
        deletebus = findViewById(R.id.deletebus_admin);
        endbus = findViewById(R.id.endbus_admin);
        id.setText(item.getId());
        from.setText(item.getDeparture());
        to.setText(item.getArrival());
        dt.setText(item.getDate());
        tm.setText(item.getTime());
        seats.setText(String.valueOf(item.getTotal_seats()));
        price.setText(String.valueOf(item.getPrice()));
        seated.setText(String.valueOf(item.getSeated()));

        Button updatebus = findViewById(R.id.editbus_admin);
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
                new DatePickerDialog(AdminEditActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        tm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        AdminEditActivity.this,
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
        deletebus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String busid = id.getText().toString();

                if (id.equals(""))
                {
                    Toast.makeText(AdminEditActivity.this, "Trường ID không được bỏ trống", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    int intbusid = Integer.parseInt(busid);
                    Boolean checkid= DB.checkidbusstatus(busid, adminname);
                    if (checkid==true){
                        Cursor cursor = DB.getsinglebus(busid);
                        String total_seats = "";
                        String seated = "";
                        if (cursor.moveToFirst()) {
                            total_seats = cursor.getString(cursor.getColumnIndex("total_seats"));
                            seated = cursor.getString(cursor.getColumnIndex("seated"));
                        }
                        if (!seated.equals(total_seats))
                        {
                            Toast.makeText(AdminEditActivity.this, "Tuyến xe đã được đặt vé, không thể xóa", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Boolean delete = DB.updateBusStatus(intbusid, 0);
                            if (delete == true) {
                                Toast.makeText(AdminEditActivity.this, "Xóa tuyến thành công", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), AdminMainActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(AdminEditActivity.this, "Tuyến chưa được xóa", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                    else {
                        Toast.makeText(AdminEditActivity.this, "ID không tồn tại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


        updatebus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String busid = id.getText().toString();
                String departure = from.getText().toString();
                String arrival = to.getText().toString();
                String date = dt.getText().toString();
                String time = tm.getText().toString();
                String total_seats = seats.getText().toString();
                int inttotal_seats = Integer.parseInt(total_seats);
                String prices = price.getText().toString();
                int intprices = Integer.parseInt(prices);
                String adminname = adminlogin.ADMINUSERNAME;
                Cursor cursor = DB.getnumberseat(adminname, busid);
                Cursor cursor1 = DB.getsinglebus(busid);
                String finaltotal_seats = "";
                String finalseated = "";
                if (cursor1.moveToFirst()) {
                    finaltotal_seats = cursor1.getString(cursor1.getColumnIndex("total_seats"));
                    finalseated = cursor1.getString(cursor1.getColumnIndex("seated"));
                }

                if (busid.equals("") || departure.equals("") || arrival.equals("") || date.equals("") || time.equals("") || total_seats.equals("") || prices.equals(""))
                {
                    Toast.makeText(AdminEditActivity.this, "Vui lòng không bỏ trống trường", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Boolean checkid= DB.checkid(busid);
                    if (!finalseated.equals(finaltotal_seats))
                    {
                        System.out.println(Integer.parseInt(finalseated));
                        Toast.makeText(AdminEditActivity.this, "Tuyến xe đã được đặt vé, không thể cập nhật", Toast.LENGTH_SHORT).show();
                    }
                    else if (checkid==true){
                        Boolean update = DB.updateBus(busid,departure,arrival,date, time, inttotal_seats, inttotal_seats, intprices);
                        if (update==true){
                            Toast.makeText(AdminEditActivity.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), AdminMainActivity.class);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(AdminEditActivity.this, "Mục không được cập nhật", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(AdminEditActivity.this, "ID Không tồn tại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        endbus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int countid = DB.countorder(adminname);
                int setid = countid+1;
                String busid = id.getText().toString();
                int intbusid = Integer.parseInt(busid);
                String departure = from.getText().toString();
                String arrival = to.getText().toString();
                String date = dt.getText().toString();
                String time = tm.getText().toString();
                String total_seats = seats.getText().toString();
                int inttotal_seats = Integer.parseInt(total_seats);
                String prices = price.getText().toString();
                int intprices = Integer.parseInt(prices);
                String seateds = seated.getText().toString();
                int intseated = Integer.parseInt(seateds);
                int revenue = (inttotal_seats - intseated) * intprices;
                int status = 0;
                if (busid.equals("") || departure.equals("") || arrival.equals("") || date.equals("") || time.equals("") || total_seats.equals("") || prices.equals(""))
                {
                    Toast.makeText(AdminEditActivity.this, "Vui lòng không bỏ trống trường", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Boolean checkid= DB.checkid(busid);

                    if (checkid==true){
                        //id TEXT primary key, departure TEXT, arrival TEXT, date TEXT, time TEXT, total_seats INT, seated INT, price INT, revenue INT
                        Boolean insert = DB.insertOrder(intbusid, adminname, departure,arrival,date, time, inttotal_seats, intseated, intprices, revenue);
                        Boolean updatestatus = DB.updateBusStatus(intbusid, status);
                        if (insert==true && updatestatus==true){
                            Toast.makeText(AdminEditActivity.this, "Đã kết thúc tuyến", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), AdminMainActivity.class);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(AdminEditActivity.this, "Thao tác không được cập nhật", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(AdminEditActivity.this, "ID Không tồn tại", Toast.LENGTH_SHORT).show();
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
            finish();
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