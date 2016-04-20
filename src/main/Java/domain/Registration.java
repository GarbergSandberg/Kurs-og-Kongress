package domain;

import java.util.*;


public class Registration {
    private int registrationID;
    private Course course;
    private ArrayList<Integer> sessionsToAttend;
    private ArrayList<Integer> eventsToAttend;
    private Accomondation accomondation;
    private Person person;
    private Workplace workplace;
    private ArrayList<Payment> cost;
    private ArrayList<Date> dates;
    private ArrayList<InputParameter> optionalPersonalia;
    private ArrayList<InputParameter> optionalWorkplace;
    private ArrayList<InputParameter> extraInfo;
    private String alternativeInvoiceAddress;
    private boolean speaker; // no constructor. Needs to be set via method.
    private String role;

    public Registration(int registrationID, Course course, ArrayList<Integer> sessionsToAttend, ArrayList<Integer> eventsToAttend, Accomondation accomondation, Person person, Workplace workplace, ArrayList<Payment> cost, ArrayList<Date> dates, ArrayList<InputParameter> optionalPersonalia, ArrayList<InputParameter> optionalWorkplace, ArrayList<InputParameter> extraInfo, String alternativeInvoiceAddress, String role) {
        this.registrationID = registrationID;
        this.course = course;
        this.sessionsToAttend = sessionsToAttend;
        this.eventsToAttend = eventsToAttend;
        this.accomondation = accomondation;
        this.person = person;
        this.workplace = workplace;
        this.cost = cost;
        this.dates = dates;
        this.optionalPersonalia = optionalPersonalia;
        this.optionalWorkplace = optionalWorkplace;
        this.extraInfo = extraInfo;
        this.alternativeInvoiceAddress = alternativeInvoiceAddress;
        this.role = role;
    }

    public Registration() {}

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

    public ArrayList<Integer> getEventsToAttend() {
        return eventsToAttend;
    }

    public void setEventsToAttend(ArrayList<Integer> eventsToAttend) {
        this.eventsToAttend = eventsToAttend;
    }

    public Accomondation getAccomondation() {
        return accomondation;
    }

    public void setAccomondation(Accomondation accomondation) {
        this.accomondation = accomondation;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "Registration{" +
                "registrationID=" + registrationID +
                ", course=" + course +
                ", sessionsToAttend=" + sessionsToAttend +
                ", eventsToAttend=" + eventsToAttend +
                ", accomondation=" + accomondation +
                ", person=" + person +
                ", workplace=" + workplace +
                ", cost=" + cost +
                ", dates=" + dates +
                ", optionalPersonalia=" + optionalPersonalia +
                ", optionalWorkplace=" + optionalWorkplace +
                ", extraInfo=" + extraInfo +
                ", alternativeInvoiceAddress='" + alternativeInvoiceAddress + '\'' +
                ", speaker=" + speaker +
                ", role='" + role + '\'' +
                '}';
    }
}
