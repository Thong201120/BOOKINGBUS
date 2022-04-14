package com.example.project.models;
//id INTEGER primary key,username TEXT, departure TEXT, arrival TEXT, date TEXT, time TEXT, price INT, number_seat INT, revenue INT)"
public class ListTravelModel {
    int id;
    int busid;
    String username;
    String departure;
    String arrival;
    String date;
    String time;
    int price;
    int number_seat;
    int revenue;

    public ListTravelModel() {
    }

    public ListTravelModel(int id, int busid, String username, String departure, String arrival, String date, String time, int price, int number_seat, int revenue) {
        this.id = id;
        this.busid = busid;
        this.username = username;
        this.departure = departure;
        this.arrival = arrival;
        this.date = date;
        this.time = time;
        this.price = price;
        this.number_seat = number_seat;
        this.revenue = revenue;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBusid() {
        return busid;
    }

    public void setBusid(int busid) {
        this.busid = busid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getNumber_seat() {
        return number_seat;
    }

    public void setNumber_seat(int number_seat) {
        this.number_seat = number_seat;
    }

    public int getRevenue() {
        return revenue;
    }

    public void setRevenue(int revenue) {
        this.revenue = revenue;
    }
}
