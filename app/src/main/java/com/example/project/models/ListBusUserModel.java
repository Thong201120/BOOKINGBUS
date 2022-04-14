package com.example.project.models;

import java.io.Serializable;

//id TEXT primary key, departure TEXT, arrival TEXT, date TEXT, time TEXT, price INT, number_seat INT, revenue INT
public class ListBusUserModel implements Serializable {
    String id;
    String adminname;
    String departure;
    String arrival;
    String date;
    String time;
    int total_seats;
    int seated;
    int price;

    public ListBusUserModel() {
    }

    public ListBusUserModel(String id, String adminname, String departure, String arrival, String date, String time, int total_seats, int seated, int price) {
        this.id = id;
        this.adminname = adminname;
        this.departure = departure;
        this.arrival = arrival;
        this.date = date;
        this.time = time;
        this.total_seats = total_seats;
        this.seated = seated;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAdminname() {
        return adminname;
    }

    public void setAdminname(String adminname) {
        this.adminname = adminname;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getArrival() {
        return arrival;
    }

    public void setArrival(String arrival) {
        this.arrival = arrival;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getTotal_seats() {
        return total_seats;
    }

    public void setTotal_seats(int total_seats) {
        this.total_seats = total_seats;
    }

    public int getSeated() {
        return seated;
    }

    public void setSeated(int seated) {
        this.seated = seated;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
