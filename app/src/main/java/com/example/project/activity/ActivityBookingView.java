package com.example.project.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project.R;
import com.example.project.models.ListBusUserModel;

import java.util.regex.Pattern;

public class ActivityBookingView extends AppCompatActivity {
    TextView id, from, to, dt, tm, seats, price, seated, number_seat;
    Button bookingview;
    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }
    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_booking_view);

        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            return;
        }

        ListBusUserModel item = (ListBusUserModel) bundle.get("list");
        Toolbar toolbar = findViewById(R.id.bookingview_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

//        id, from, to, dt, tm, seats, price, seated, number_seat;

        id = findViewById(R.id.bookingview_id);
        from = findViewById(R.id.bookingview_from);
        to = findViewById(R.id.bookingview_to);
        dt = findViewById(R.id.bookingview_date);
        tm = findViewById(R.id.bookingview_time);
        seats = findViewById(R.id.bookingview_seats);
        price = findViewById(R.id.bookingview_price);
        seated = findViewById(R.id.bookingview_seated);
        number_seat = findViewById(R.id.bookingview_numberseat);
        bookingview = findViewById(R.id.bookingview_admin);
        DBHelper DB = new DBHelper(this);
        int countid = DB.counttravel(Login.CUSTOMUSERNAME);
        int setid = countid+1;
        id.setText(item.getId());
        from.setText(item.getDeparture());
        to.setText(item.getArrival());
        dt.setText(item.getDate());
        tm.setText(item.getTime());
        seats.setText(String.valueOf(item.getTotal_seats()));
        seated.setText(String.valueOf(item.getSeated()));
        price.setText(String.valueOf(item.getPrice()));




        bookingview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                String seatedd = seated.getText().toString();
                int intseated = Integer.parseInt(seatedd);
                String numberseat = number_seat.getText().toString();
                String Strusername = Login.CUSTOMUSERNAME;

                if (numberseat.equals("") || seatedd.equals("") || busid.equals("") || departure.equals("") || arrival.equals("") || date.equals("") || time.equals("") || total_seats.equals("") || prices.equals(""))
                {
                    Toast.makeText(ActivityBookingView.this, "Vui lòng không bỏ trống trường", Toast.LENGTH_SHORT).show();

                }
                else if (!isNumeric(numberseat))
                {
                    Toast.makeText(ActivityBookingView.this, "Số lượng vé phải là chữ số", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    int intnumberseat = Integer.parseInt(numberseat);
                    if (intnumberseat <= 0 || intnumberseat > intseated)
                    {
                        Toast.makeText(ActivityBookingView.this, "Số vé không hợp lệ", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        int revenue = intprices * intnumberseat;
//                        Boolean checkid = DB.checkidbustravel(busid, Login.CUSTOMUSERNAME);
//                        if (checkid == true)
//                        {
//
//                            Cursor cursor = DB.getseattravel(busid);
//                            String oldseat = "";
//                            String oldrevenue = "";
//                            if (cursor.moveToFirst()) {
//                                oldseat = cursor.getString(cursor.getColumnIndex("number_seat"));
//                                oldrevenue = cursor.getString(cursor.getColumnIndex("revenue"));
//                            }
//                            int controng = intseated - intnumberseat;
//                            Boolean insertTravel = DB.updateTravel(String.valueOf(intbusid), Strusername, departure, arrival, date, time, intprices, intnumberseat + Integer.parseInt(oldseat), revenue + Integer.parseInt(oldrevenue));
//                            Boolean updatebusseated = DB.updateBusSeated(busid, controng);
//                            if (insertTravel == true && updatebusseated == true)
//
//                            {
//                                System.out.println(intseated);
//                                System.out.println(intnumberseat);
//                                System.out.println(controng);
//                                Toast.makeText(ActivityBookingView.this, "Đặt vé thành công", Toast.LENGTH_SHORT).show();
//                                AlertDialog.Builder builder = new AlertDialog.Builder(ActivityBookingView.this);
//                                //thiết lập tiêu đề cho Dialog
//                                builder.setTitle("Đặt vé thành công");
//                                //Thiết lập nội dung cho Dialog
//                                builder.setMessage("Vui lòng chuẩn bị đủ số tiền và đến đúng lúc để có một chuyến đi thật suôn sẻ nhé");
////                            để thiết lập Icon
////                            builder.setIcon(R.mipmap.ic_launcher);
//
//                                builder.setPositiveButton("Okie", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        Intent intent = new Intent(getApplicationContext(), UserMainActivity.class);
//                                        startActivity(intent);
//                                    }
//                                });
//                                builder.create().show();
//                            }
//                            else
//                            {
//                                Toast.makeText(ActivityBookingView.this, "Mục không được cập nhật", Toast.LENGTH_SHORT).show();
//                            }
//
//                        }
//                        else {
                            System.out.println("ở đây nè");
                            int controng = intseated - intnumberseat;
                            int count = DB.countidbus();
                            Boolean insertTravel = DB.insertTravel(count + 1, intbusid, Login.CUSTOMUSERNAME,  departure, arrival, date, time, intprices, intnumberseat, revenue);
                            Boolean updatebusseated = DB.updateBusSeated(busid, controng);
                            System.out.println(insertTravel);
                            System.out.println(updatebusseated);
                            if (insertTravel == true && updatebusseated == true)
                            {

                                System.out.println(intnumberseat);
                                System.out.println(controng);
                                Toast.makeText(ActivityBookingView.this, "Đặt vé thành công", Toast.LENGTH_SHORT).show();
                                AlertDialog.Builder builder = new AlertDialog.Builder(ActivityBookingView.this);
                                //thiết lập tiêu đề cho Dialog
                                builder.setTitle("Đặt vé thành công");
                                //Thiết lập nội dung cho Dialog
                                builder.setMessage("Vui lòng chuẩn bị đủ số tiền và đến đúng lúc để có một chuyến đi thật suôn sẻ nhé");
//                            để thiết lập Icon
//                            builder.setIcon(R.mipmap.ic_launcher);
                                builder.setPositiveButton("Okie", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent(getApplicationContext(), UserMainActivity.class);
                                        startActivity(intent);
                                    }
                                });
                                builder.create().show();
                            }
                            else
                            {
                                Toast.makeText(ActivityBookingView.this, "Mục không được cập nhật", Toast.LENGTH_SHORT).show();
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
