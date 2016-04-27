package domain;


/*
 * Created by Lars on 18.01.16.
 */
public class Person {
    private int personID;
    private String firstname;
    private String lastname;
    private int birthYear;
    private int phonenumber;
    private String email;
    private String gender;
    private String mark; // Not necessary in constructor.

    public Person(int personID, String firstname, String lastname, int birthYear, int phonenumber, String email, String gender, String mark) {
        this.personID = personID;
        this.firstname = firstname;
        this.lastname = lastname;
        this.birthYear = birthYear;
        this.phonenumber = phonenumber;
        this.email = email;
        this.gender = gender;
        this.mark = mark;
    }

    public Person() {
    }

    public int getPersonID() {
        return personID;
    }

    public void setPersonID(int personID) {
        this.personID = personID;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }

    public int getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(int phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    @Override
    public String toString() {
        return "Person{" +
                "personID=" + personID +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", birthYear=" + birthYear +
                ", phonenumber=" + phonenumber +
                ", email='" + email + '\'' +
                ", gender='" + gender + '\'' +
                ", mark='" + mark + '\'' +
                '}';
    }
}
