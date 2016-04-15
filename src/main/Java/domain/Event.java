package domain;

import java.util.*;

/**
 * Created by Lars on 18.01.16.
 */
public class Event {
    private String title;
    private int maxNumber;
    private double price;
    private String location;
    private Date startTime;
    private Date endTime;
    private int id;

    public Event(String title, int maxNumber, double price, String location, Date start, Date end, int id) {
        this.title = title;
        this.maxNumber = maxNumber;
        this.price = price;
        this.location = location;
        this.startTime = start;
        this.endTime = end;
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

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Event{" +
                "title='" + title + '\'' +
                ", maxNumber=" + maxNumber +
                ", price=" + price +
                ", location='" + location + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", id=" + id +
                '}';
    }
}
