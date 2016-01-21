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
    private Date startDateCourse;
    private Date endDateCourse;
    private int maxNumberOfParticipants;
    private ArrayList<Meal> meals;
    private boolean hotel;

    public Course(ArrayList<Session> sessions, ArrayList<Events> events, String title, Date startDateCourse, Date endDateCourse, int maxNumberOfParticipants, ArrayList<Meal> meals, boolean hotel) {
        this.sessions = sessions;
        this.title = title;
        this.startDateCourse = startDateCourse;
        this.endDateCourse = endDateCourse;
        this.maxNumberOfParticipants = maxNumberOfParticipants;
        this.meals = meals;
        this.hotel = hotel;
        this.events = events;
    }

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

    public Date getStartDateCourse() {
        return startDateCourse;
    }

    public void setStartDateCourse(Date startDateCourse) {
        this.startDateCourse = startDateCourse;
    }

    public Date getEndDateCourse() {
        return endDateCourse;
    }

    public void setEndDateCourse(Date endDateCourse) {
        this.endDateCourse = endDateCourse;
    }

    public int getMaxNumberOfParticipants() {
        return maxNumberOfParticipants;
    }

    public void setMaxNumberOfParticipants(int maxNumberOfParticipants) {
        this.maxNumberOfParticipants = maxNumberOfParticipants;
    }

    public ArrayList<Meal> getMeals() {
        return meals;
    }

    public void setMeals(ArrayList<Meal> meals) {
        this.meals = meals;
    }

    public boolean isHotel() {
        return hotel;
    }

    public void setHotel(boolean hotel) {
        this.hotel = hotel;
    }
}
