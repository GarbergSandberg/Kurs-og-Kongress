package domain;

import java.util.*;

/**
 * Created by eiriksandberg on 07.04.2016.
 */
public class Registration {
    private int registrationID;
    private Course course;
    private ArrayList<Integer> sessionsToAttend;
    private Person person;
    private Workplace workplace;
    private ArrayList<Payment> cost;
    private ArrayList<Date> dates;
    private ArrayList<InputParameter> optionalPersonalia;
    private ArrayList<InputParameter> optionalWorkplace;
    private ArrayList<InputParameter> extraInfo;
    private String alternativeInvoiceAddress;
    private boolean speaker;

    public Registration(int registrationID, Course course, ArrayList<Integer> sessionsToAttend, Person person, Workplace workplace, ArrayList<Payment> cost, ArrayList<Date> dates, ArrayList<InputParameter> optionalPersonalia, ArrayList<InputParameter> optionalWorkplace, ArrayList<InputParameter> extraInfo, String alternativeInvoiceAddress) {
        this.registrationID = registrationID;
        this.course = course;
        this.sessionsToAttend = sessionsToAttend;
        this.person = person;
        this.workplace = workplace;
        this.cost = cost;
        this.dates = dates;
        this.optionalPersonalia = optionalPersonalia;
        this.optionalWorkplace = optionalWorkplace;
        this.extraInfo = extraInfo;
        this.alternativeInvoiceAddress = alternativeInvoiceAddress;
    }

    public Registration(Course course, ArrayList<Integer> sessionsToAttend, Person person, Workplace workplace, ArrayList<Payment> cost, ArrayList<Date> dates, ArrayList<InputParameter> optionalPersonalia, ArrayList<InputParameter> optionalWorkplace, ArrayList<InputParameter> extraInfo, String alternativeInvoiceAddress) {
        this.course = course;
        this.sessionsToAttend = sessionsToAttend;
        this.person = person;
        this.workplace = workplace;
        this.cost = cost;
        this.dates = dates;
        this.optionalPersonalia = optionalPersonalia;
        this.optionalWorkplace = optionalWorkplace;
        this.extraInfo = extraInfo;
        this.alternativeInvoiceAddress = alternativeInvoiceAddress;
    }

    public int getRegistrationID() {
        return registrationID;
    }

    public void setRegistrationID(int registrationID) {
        this.registrationID = registrationID;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public ArrayList<Integer> getSessionsToAttend() {
        return sessionsToAttend;
    }

    public void setSessionsToAttend(ArrayList<Integer> sessionsToAttend) {
        this.sessionsToAttend = sessionsToAttend;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Workplace getWorkplace() {
        return workplace;
    }

    public void setWorkplace(Workplace workplace) {
        this.workplace = workplace;
    }

    public ArrayList<Payment> getCost() {
        return cost;
    }

    public void setCost(ArrayList<Payment> cost) {
        this.cost = cost;
    }

    public ArrayList<Date> getDates() {
        return dates;
    }

    public void setDates(ArrayList<Date> dates) {
        this.dates = dates;
    }

    public ArrayList<InputParameter> getOptionalPersonalia() {
        return optionalPersonalia;
    }

    public void setOptionalPersonalia(ArrayList<InputParameter> optionalPersonalia) {
        this.optionalPersonalia = optionalPersonalia;
    }

    public ArrayList<InputParameter> getOptionalWorkplace() {
        return optionalWorkplace;
    }

    public void setOptionalWorkplace(ArrayList<InputParameter> optionalWorkplace) {
        this.optionalWorkplace = optionalWorkplace;
    }

    public ArrayList<InputParameter> getExtraInfo() {
        return extraInfo;
    }

    public void setExtraInfo(ArrayList<InputParameter> extraInfo) {
        this.extraInfo = extraInfo;
    }

    public String getAlternativeInvoiceAddress() {
        return alternativeInvoiceAddress;
    }

    public void setAlternativeInvoiceAddress(String alternativeInvoiceAddress) {
        this.alternativeInvoiceAddress = alternativeInvoiceAddress;
    }

    public boolean isSpeaker() {
        return speaker;
    }

    public void setSpeaker(boolean speaker) {
        this.speaker = speaker;
    }
}
