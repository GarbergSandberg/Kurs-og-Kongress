package domain;

import java.util.*;

/**
 * Created by Lars on 18.01.16.
 */
public class Event {
    private String name;
    private double price;
    private String place;
    private Date start;
    private Date end;

    public Event(String name, double price, String place, Date start, Date end) {
        this.name = name;
        this.price = price;
        this.place = place;
        this.start = start;
        this.end = end;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }
}
