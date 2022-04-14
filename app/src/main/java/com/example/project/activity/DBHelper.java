package com.example.project.activity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.Date;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DBNAME = "Project.db";

    public DBHelper(Context context) {
        super(context, "Project.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase MyDB) {
        MyDB.execSQL("create Table users(username TEXT primary key, password TEXT, phone TEXT, email TEXT, fullname TEXT)");
        MyDB.execSQL("create Table buses(id INTEGER primary key ,adminname TEXT, departure TEXT, arrival TEXT, date TEXT, time TEXT, total_seats INT, seated INT, price INT, status INT)");
        MyDB.execSQL("create Table admin(username TEXT primary key, password TEXT, phone TEXT, email TEXT, fullname TEXT)");
        MyDB.execSQL("create Table orders(id INTEGER primary key,adminname TEXT, departure TEXT, arrival TEXT, date TEXT, time TEXT, total_seats INT, seated INT, price INT, revenue INT)");
        MyDB.execSQL("create Table travel(id INTEGER primary key,busid INTEGER, username TEXT, departure TEXT, arrival TEXT, date TEXT, time TEXT, price INT, number_seat INT, revenue INT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int oldVersion, int newVersion) {
        MyDB.execSQL("drop Table if exists users");
        MyDB.execSQL("drop Table if exists buses");
        MyDB.execSQL("drop table if exists admin");
        MyDB.execSQL("drop table if exists orders");
        MyDB.execSQL("drop table if exists travel");
    }

    public Boolean insertData(String fullname, String email, String username, String phone, String password)
    {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("fullname", fullname);
        contentValues.put("email", email);
        contentValues.put("username", username);
        contentValues.put("phone", phone);
        contentValues.put("password", password);
        long result = MyDB.insert("users",null,contentValues);
        if(result==-1) return false;
        else
            return true;
    }

    public Boolean insertadmin(String fullname, String email, String username, String phone, String password)
    {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("fullname", fullname);
        contentValues.put("email", email);
        contentValues.put("username", username);
        contentValues.put("phone", phone);
        contentValues.put("password", password);
        long result = MyDB.insert("admin",null,contentValues);
        if(result==-1) return false;
        else
            return true;
    }
//id TEXT primary key, departure TEXT, arrival TEXT, date TEXT, time TEXT, total_seats INT, seated INT, price INT
    public Boolean insertBus(int id, String adminname, String departure, String arrival, String date, String time, int total_seats, int seated, int price, int status)
    {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", id);
        contentValues.put("adminname", adminname);
        contentValues.put("departure", departure);
        contentValues.put("arrival", arrival);
        contentValues.put("date", date);
        contentValues.put("time", time);
        contentValues.put("total_seats", total_seats);
        contentValues.put("seated", seated);
        contentValues.put("price", price);
        contentValues.put("status", status);
        long result = MyDB.insert("buses",null,contentValues);
        if(result==-1) return false;
        else
            return true;
    }
//id INTEGER primary key,username TEXT, departure TEXT, arrival TEXT, date TEXT, time TEXT, price INT, number_seat INT, revenue INT
    public Boolean insertOrder(int id, String adminname, String departure, String arrival, String date, String time, int total_seats, int seated, int price, int revenue)
    {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", id);
        contentValues.put("adminname", adminname);
        contentValues.put("departure", departure);
        contentValues.put("arrival", arrival);
        contentValues.put("date", date);
        contentValues.put("time", time);
        contentValues.put("total_seats", total_seats);
        contentValues.put("seated", seated);
        contentValues.put("price", price);
        contentValues.put("revenue", revenue);
        long result = MyDB.insert("orders",null,contentValues);
        if(result==-1) return false;
        else
            return true;
    }

    public int sumAdminRevenue(String adminname) {
        int result = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select sum(revenue) from orders where adminname = ?", new String[] {adminname});
        if (cursor.moveToFirst()) result = cursor.getInt(0);
        cursor.close();
        db.close();
        return result;
    }

    public int sumUserRevenue(String username) {
        int result = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select sum(revenue) from travel where username = ?", new String[] {username});
        if (cursor.moveToFirst()) result = cursor.getInt(0);
        cursor.close();
        db.close();
        return result;
    }


//id INTEGER primary key,username TEXT, departure TEXT, arrival TEXT, date TEXT, time TEXT, price INT, number_seat INT, revenue INT
    public Boolean insertTravel(int id, int busid, String username, String departure, String arrival, String date, String time, int price, int number_seat, int revenue)
    {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", id);
        contentValues.put("busid", busid);
        contentValues.put("username", username);
        contentValues.put("departure", departure);
        contentValues.put("arrival", arrival);
        contentValues.put("date", date);
        contentValues.put("time", time);
        contentValues.put("price", price);
        contentValues.put("number_seat", number_seat);
        contentValues.put("revenue", revenue);
        long result = MyDB.insert("travel",null,contentValues);
        if(result==-1) return false;
        else
            return true;
    }

    public Boolean updateTravel(String id, String username, String departure, String arrival, String date, String time, int price, int number_seat, int revenue)
    {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("departure", departure);
        contentValues.put("arrival", arrival);
        contentValues.put("date", date);
        contentValues.put("time", time);
        contentValues.put("price", price);
        contentValues.put("number_seat", number_seat);
        contentValues.put("revenue", revenue);
        Cursor cursor = MyDB.rawQuery("Select * from travel where busid = ?", new String[] {String.valueOf(id)});
        if (cursor.getCount()>0){
            long result = MyDB.update("travel", contentValues,"busid=?", new String[] {String.valueOf(id)});
            if(result==-1) return false;
            else
                return true;
        }
        else
        {
            return false;
        }
    }
    public boolean checkusername(String username){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from users where username = ?", new String[] {username});
        if (cursor.getCount()>0)
            return true;
        else
            return false;
    }

    public boolean checkadminemail(String email){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from admin where email = ?", new String[] {email});
        if (cursor.getCount()>0)
            return true;
        else
            return false;
    }

    public boolean checkuseremail(String email){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from users where email = ?", new String[] {email});
        if (cursor.getCount()>0)
            return true;
        else
            return false;
    }
//"create Table travel(id INTEGER primary key,username TEXT, departure TEXT, arrival TEXT, date TEXT, time TEXT, price INT, number_seat INT, revenue INT)"
    public boolean checkidbustravel(String id, String username){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from travel where busid = ? and username = ?", new String[] {id, username});
        if (cursor.getCount()>0)
            return true;
        else
            return false;
    }

    public boolean checkadminusername(String username){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from admin where username = ?", new String[] {username});
        if (cursor.getCount()>0)
            return true;
        else
            return false;
    }

    public boolean checkusernamepassword(String username, String password){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from users where username = ? and password = ?", new String[] {username,password});
        if(cursor.getCount()>0)
            return true;
        else
            return false;
    }

    public boolean checkadminusernamepassword(String username, String password){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from admin where username = ? and password = ?", new String[] {username,password});
        if(cursor.getCount()>0)
            return true;
        else
            return false;
    }

//id TEXT primary key, departure TEXT, arrival TEXT, date TEXT, time TEXT, total_seats INT, seated INT, price INT
    public Boolean updateBus(String id, String departure, String arrival, String date, String time, int total_seats, int seated, int price)
    {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("departure", departure);
        contentValues.put("arrival", arrival);
        contentValues.put("date", date);
        contentValues.put("time", time);
        contentValues.put("total_seats", total_seats);
        contentValues.put("seated", seated);
        contentValues.put("price", price);
        Cursor cursor = MyDB.rawQuery("Select * from buses where id = ?", new String[] {id});
        if (cursor.getCount()>0){
        long result = MyDB.update("buses", contentValues,"id=?", new String[] {id});
        if(result==-1) return false;
        else
            return true;
        }
        else
            {
                return false;
            }
    }

    public Boolean updateAdminPassword(String password, String email)
    {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("password", password);
        Cursor cursor = MyDB.rawQuery("Select * from admin where email = ?", new String[] {email});
        if (cursor.getCount()>0){
            long result = MyDB.update("admin", contentValues,"email=?", new String[] {email});
            if(result==-1) return false;
            else
                return true;
        }
        else
        {
            return false;
        }
    }

    public Boolean updateUserPassword(String password, String email)
    {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("password", password);
        Cursor cursor = MyDB.rawQuery("Select * from users where email = ?", new String[] {email});
        if (cursor.getCount()>0){
            long result = MyDB.update("users", contentValues,"email=?", new String[] {email});
            if(result==-1) return false;
            else
                return true;
        }
        else
        {
            return false;
        }
    }

    public Boolean updateBusSeated(String id, int seated)
    {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("seated", seated);
        Cursor cursor = MyDB.rawQuery("Select * from buses where id = ?", new String[] {id});
        if (cursor.getCount()>0){
            long result = MyDB.update("buses", contentValues,"id=?", new String[] {id});
            if(result==-1) return false;
            else
                return true;
        }
        else
        {
            return false;
        }
    }

    public Boolean updateBusStatus(int id, int status)
    {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("status", status);
        Cursor cursor = MyDB.rawQuery("Select * from buses where id = ?", new String[] {String.valueOf(id)});
        if (cursor.getCount()>0){
            long result = MyDB.update("buses", contentValues,"id=?", new String[] {String.valueOf(id)});
            if(result==-1) return false;
            else
                return true;
        }
        else
        {
            return false;
        }
    }

    public Cursor viewbuses(String adminname)
    {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from buses where adminname = ?", new String[] {adminname});
        return cursor;
    }

    public Cursor viewactivebuses(String adminname)
    {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from buses where adminname = ? and status = 1", new String[] {adminname});
        return cursor;
    }

    public Cursor viewallbuses()
    {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from buses where status = 1", null);
        return cursor;
    }

    public Cursor getadmin(String username)
    {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select fullname from admin where username = ?", new String[] {username});
        return cursor;
    }

    public Cursor getseattravel(String id)
    {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select number_seat, revenue from travel where busid = ?", new String[] {id});
        return cursor;
    }

    public Cursor getnumberseat(String adminname, String id)
    {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select total_seats, seated from buses where adminname = ? and id = ?", new String[] {adminname, id});
        return cursor;
    }

    public Cursor getcontentadmin(String username)
    {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select fullname, email, phone from admin where username = ?", new String[] {username});
        return cursor;
    }
    public Cursor getuser(String username)
    {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select fullname from users where username = ?", new String[] {username});
        return cursor;
    }

    public Cursor getsinglebus(String id)
    {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select seated, total_seats from buses where id = ?", new String[] {id});
        return cursor;
    }

    public Cursor getcontentuser(String username)
    {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select fullname, email, phone from users where username = ?", new String[] {username});
        return cursor;
    }
    public Cursor viewtravel(String adminname)
    {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from travel where username = ?", new String[] {adminname});
        return cursor;
    }

    public Cursor viewbusesseated()
    {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select seated from buses", null);
        return cursor;
    }

    public Cursor vieworder(String adminname)
    {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from orders where adminname = ?", new String[] {adminname});
        return cursor;
    }

    public Cursor admin()
    {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from admin", null);
        return cursor;
    }

    public Cursor users()
    {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from users", null);
        return cursor;
    }

    //username TEXT primary key, password TEXT, phone TEXT, email TEXT, fullname TEXT)
    public Boolean updateAdminProfile(String username, String phone, String email, String fullname)
    {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("phone", phone);
        contentValues.put("email", email);
        contentValues.put("fullname", fullname);
        Cursor cursor = MyDB.rawQuery("Select * from admin where username = ?", new String[] {username});
        if (cursor.getCount()>0){
            long result = MyDB.update("admin", contentValues,"username=?", new String[] {username});
            if(result==-1) return false;
            else
                return true;
        }
        else
        {
            return false;
        }
    }

    public Boolean updateAdminPass(String username, String password)
    {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("password", password);
        Cursor cursor = MyDB.rawQuery("Select * from admin where username = ?", new String[] {username});
        if (cursor.getCount()>0){
            long result = MyDB.update("admin", contentValues,"username=?", new String[] {username});
            if(result==-1) return false;
            else
                return true;
        }
        else
        {
            return false;
        }
    }
    public Boolean updateUserPass(String username, String password)
    {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("password", password);
        Cursor cursor = MyDB.rawQuery("Select * from users where username = ?", new String[] {username});
        if (cursor.getCount()>0){
            long result = MyDB.update("users", contentValues,"username=?", new String[] {username});
            if(result==-1) return false;
            else
                return true;
        }
        else
        {
            return false;
        }
    }

    /*public Cursor getListContents(){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor data = MyDB.rawQuery("Select * from buses", null);
//        return data;*/
//    public Cursor getusers()
//    {
//        SQLiteDatabase MyDB = this.getWritableDatabase();
//        Cursor cursor = MyDB.rawQuery("Select * from admin where username = 'thu'", null);
//        return cursor;
//    }


    public boolean checkid(String id){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from buses where id = ?", new String[] {id});
        if (cursor.getCount()>0)
            return true;
        else
            return false;
    }

    public boolean checkidbusstatus(String id, String adminname){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from buses where id = ? and adminname = ? and status = 1", new String[] {id, adminname});
        if (cursor.getCount()>0)
            return true;
        else
            return false;
    }



    public int countid()
    {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from buses", null);
        return cursor.getCount();
    }

    public int countidbus()
    {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from travel", null);
        return cursor.getCount();
    }

    public int countorder(String adminname)
    {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from orders where adminname = ?", new String[] {adminname});
        return cursor.getCount();
    }

    public int counttravel(String username)
    {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from travel where username = ?", new String[] {username});
        return cursor.getCount();
    }
    public boolean checkidadminusername(String username){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from admin where username = ?", new String[] {username});
        if (cursor.getCount()>0)
            return true;
        else
            return false;
    }

    public boolean checkadminpass(String username, String password){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from admin where username = ? and password = ?", new String[] {username, password});
        if (cursor.getCount()>0)
            return true;
        else
            return false;
    }

    public boolean checkuserpass(String username, String password){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from users where username = ? and password = ?", new String[] {username, password});
        if (cursor.getCount()>0)
            return true;
        else
            return false;
    }

    public boolean checkiduserusername(String username){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from users where username = ?", new String[] {username});
        if (cursor.getCount()>0)
            return true;
        else
            return false;
    }


    public Boolean updateUserProfile(String username, String phone, String email, String fullname)
    {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("phone", phone);
        contentValues.put("email", email);
        contentValues.put("fullname", fullname);
        Cursor cursor = MyDB.rawQuery("Select * from users where username = ?", new String[] {username});
        if (cursor.getCount()>0){
            long result = MyDB.update("users", contentValues,"username=?", new String[] {username});
            if(result==-1) return false;
            else
                return true;
        }
        else
        {
            return false;
        }
    }















}
