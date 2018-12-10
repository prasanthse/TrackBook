package com.se15026.prasanth.trackbook;

public class BookingInfo {

    private String name;
    private String phoneNumber;
    private String startingStation;
    private String endStation;
    private String time;
    private String date;
    private String bookedClass;
    private int seat;

    public BookingInfo() {
    }

    public BookingInfo(String startingStation, String endStation, String time, String date, String bookedClass, int seat, String name, String phoneNumber) {
        this.startingStation = startingStation;
        this.endStation = endStation;
        this.time = time;
        this.date = date;
        this.bookedClass = bookedClass;
        this.seat = seat;
        this.name = name;
        this.phoneNumber = phoneNumber;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getBookedClass() {
        return bookedClass;
    }

    public void setBookedClass(String bookedClass) {
        this.bookedClass = bookedClass;
    }

    public int getSeat() {
        return seat;
    }

    public void setSeat(int seat) {
        this.seat = seat;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return
                "name = " + name + "\n" +
                "phoneNumber = " + phoneNumber + "\n" +
                "startingStation = " + startingStation + "\n" +
                "endStation = " + endStation + "\n" +
                "time = " + time + "\n" +
                "date = " + date + "\n" +
                "bookedClass = " + bookedClass + "\n" +
                "seat = " + seat;
    }
}
