package com.se15026.prasanth.trackbook;

import java.util.Date;

public class BookingInfo {

    private String startingStation;
    private String endStation;
    private String time;
    private Date date;
    private int bookedClass;
    private int seat;

    public BookingInfo() {
    }

    public BookingInfo(String startingStation, String endStation, String time, Date date, int bookedClass, int seat) {
        this.startingStation = startingStation;
        this.endStation = endStation;
        this.time = time;
        this.date = date;
        this.bookedClass = bookedClass;
        this.seat = seat;
    }

    public String getStartingStation() {
        return startingStation;
    }

    public void setStartingStation(String startingStation) {
        this.startingStation = startingStation;
    }

    public String getEndStation() {
        return endStation;
    }

    public void setEndStation(String endStation) {
        this.endStation = endStation;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getBookedClass() {
        return bookedClass;
    }

    public void setBookedClass(int bookedClass) {
        this.bookedClass = bookedClass;
    }

    public int getSeat() {
        return seat;
    }

    public void setSeat(int seat) {
        this.seat = seat;
    }
}
