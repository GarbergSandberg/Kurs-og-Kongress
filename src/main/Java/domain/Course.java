package domain;


import java.util.*;

/**
 * Created by eiriksandberg on 18.01.2016.
 */
public class Course {
    private ArrayList<Session> sessions;
    private ArrayList<Event> events;
    private ArrayList<String> roles;
    private ArrayList<Hotel> hotels;
    private Form form;
    private String title;
    private String location;
    private String description;
    private Date startDate;
    private Date endDate;
    private double courseFee;
    private double courseSingleDayFee;
    private double dayPackage;
    private int maxNumber;
    private int id;

    public Course(ArrayList<Session> sessions, ArrayList<Event> events, ArrayList<String> roles, ArrayList<Hotel> hotels, Form form, String title, String location, String description, Date startDate, Date endDate, double courseFee, double courseSingleDayFee, double dayPackage, int maxNumber, int id) {
        this.sessions = sessions;
        this.events = events;
        this.roles = roles;
        this.hotels = hotels;
        this.form = form;
        this.title = title;
        this.location = location;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.courseFee = courseFee;
        this.courseSingleDayFee = courseSingleDayFee;
        this.dayPackage = dayPackage;
        this.maxNumber = maxNumber;
        this.id = id;
    }

    public Course(ArrayList<Session> sessions, ArrayList<Event> events, ArrayList<String> roles, Form form, String title, String location, String description, Date startDate, Date endDate, double courseFee, double courseSingleDayFee, double dayPackage, int maxNumber, int id) {
        this.sessions = sessions;
        this.events = events;
        this.roles = roles;
        this.form = form;
        this.title = title;
        this.location = location;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.courseFee = courseFee;
        this.courseSingleDayFee = courseSingleDayFee;
        this.dayPackage = dayPackage;
        this.maxNumber = maxNumber;
        this.id = id;
    }

    public Course(ArrayList<Session> sessions, ArrayList<Event> events, ArrayList<String> roles, String title, String location, String description, Date startDate, Date endDate, double courseFee, double courseSingleDayFee, double dayPackage, int maxNumber, int id) {
        this.sessions = sessions;
        this.events = events;
        this.roles = roles;
        this.title = title;
        this.location = location;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.courseFee = courseFee;
        this.courseSingleDayFee = courseSingleDayFee;
        this.dayPackage = dayPackage;
        this.maxNumber = maxNumber;
        this.id = id;
    }

    public Course(){};

    public ArrayList<Session> getSessions() {
        return sessions;
    }

    public void setSessions(ArrayList<Session> sessions) {
        this.sessions = sessions;
    }

    public ArrayList<Event> getEvents() {
        return events;
    }

    public void setEvents(ArrayList<Event> events) {
        this.events = events;
    }

    public ArrayList<String> getRoles() {
        return roles;
    }

    public void setRoles(ArrayList<String> roles) {
        this.roles = roles;
    }

    public ArrayList<Hotel> getHotels() {return hotels;}

    public void setHotels(ArrayList<Hotel> hotels) {this.hotels = hotels;}

    public Form getForm() {
        return form;
    }

    public void setForm(Form form) {
        this.form = form;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getCourseFee() {
        return courseFee;
    }

    public void setCourseFee(double courseFee) {
        this.courseFee = courseFee;
    }

    public double getCourseSingleDayFee() {
        return courseSingleDayFee;
    }

    public void setCourseSingleDayFee(double courseSingleDayFee) {
        this.courseSingleDayFee = courseSingleDayFee;
    }

    public double getDayPackage() {
        return dayPackage;
    }

    public void setDayPackage(double dayPackage) {
        this.dayPackage = dayPackage;
    }
}