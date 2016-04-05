package domain;

import java.util.*;

/**
 * Created by Lars on 18.01.16.
 */
public class
Event {
    private String title;
    private int maxNumber;
    private double price;
    private String location;
    private Date date;
    private Date time;
    private int id;

    public Event(String title, int maxNumber, double price, String location, Date date, Date time, int id) {
        this.title = title;
        this.maxNumber = maxNumber;
        this.price = price;
        this.location = location;
        this.date = date;
        this.time = time;
        this.id = id;
    }

    public Event(){};

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getMaxNumber() {
        return maxNumber;
    }

    public void setMaxNumber(int maxNumber) {
        this.maxNumber = maxNumber;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
