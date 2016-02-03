package domain;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by eiriksandberg on 18.01.2016.
 */
public class Course {
    private ArrayList<Session> sessions;
    private ArrayList<Events> events;
    private String title;
    private String location;
    private String description;
    private Date startDate;
    private Date endDate;
    private int maxNumber;

    public Course(ArrayList<Session> sessions, ArrayList<Events> events, String title, String location, String description, Date startDate, Date endDate, int maxNumber) {
        this.sessions = sessions;
        this.events = events;
        this.title = title;
        this.location = location;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.maxNumber = maxNumber;
    }
    public Course(){};

    public ArrayList<Session> getSessions() {
        return sessions;
    }

    public void setSessions(ArrayList<Session> sessions) {
        this.sessions = sessions;
    }

    public ArrayList<Events> getEvents() {
        return events;
    }

    public void setEvents(ArrayList<Events> events) {
        this.events = events;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getMaxNumber() {
        return maxNumber;
    }

    public void setMaxNumber(int maxNumber) {
        this.maxNumber = maxNumber;
    }
}
