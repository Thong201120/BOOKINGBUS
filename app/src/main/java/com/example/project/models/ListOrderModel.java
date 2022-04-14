package com.example.project.models;

import java.io.Serializable;

//create Table orders(id TEXT primary key, departure TEXT, arrival TEXT, date TEXT, time TEXT, total_seats INT, seated INT, price INT, revenue INT)
public class ListOrderModel implements Serializable {
    int id;
    String adminname;
    String departure;
    String arrival;
    String date;
    String time;
    int total_seats;
    int seated;
    int price;
    int revenue;

    public ListOrderModel() {
    }

    public ListOrderModel(int id, String adminname, String departure, String arrival, String date, String time, int total_seats, int seated, int price, int revenue) {
        this.id = id;
        this.adminname = adminname;
        this.departure = departure;
        this.arrival = arrival;
        this.date = date;
        this.time = time;
        this.total_seats = total_seats;
        this.seated = seated;
        this.price = price;
        this.revenue = revenue;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public int getRevenue() {
        return revenue;
    }

    public void setRevenue(int revenue) {
        this.revenue = revenue;
    }
}
