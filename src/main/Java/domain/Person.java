package domain;

import java.util.*;

/**
 * import org.hibernate.validator.constraints.Email;
 import org.hibernate.validator.constraints.NotEmpty;
 * Created by Lars on 18.01.16.
 */
public class Person {
    private String firstname;
    private String lastname;
    private Date birtdate;
    private String title;
    private int phonenumber;
    private Workplace workplace;
    private int postalcode;
    private String email;
    private String facturaAdress;
    private String mark;

    public Person(String lastname, String firstname, int phonenumber, Workplace workplace, int postalcode, String email) {
        this.lastname = lastname;
        this.firstname = firstname;
        this.phonenumber = phonenumber;
        this.workplace = workplace;
        this.postalcode = postalcode;
        this.email = email;
    }


    // Getters and setters
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(int phonenumber) {
        this.phonenumber = phonenumber;
    }

    public Workplace getWorkplace() {
        return workplace;
    }

    public void setWorkplace(Workplace workplace) {
        this.workplace = workplace;
    }

    public int getPostalcode() {
        return postalcode;
    }

    public void setPostalcode(int postalcode) {
        this.postalcode = postalcode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
