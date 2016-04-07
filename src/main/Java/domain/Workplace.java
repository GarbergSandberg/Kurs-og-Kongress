package domain;

import java.util.*;

/**
 * Created by Lars on 18.01.16.
 */
public class Workplace {
    private int workplaceID;
    private String companyName;
    private int postalcode;
    private String location;
    private String address;

    public Workplace(int workplaceID, String companyName, int postalcode, String location, String address) {
        this.workplaceID = workplaceID;
        this.companyName = companyName;
        this.postalcode = postalcode;
        this.location = location;
        this.address = address;
    }

    public Workplace(String companyName, int postalcode, String location, String address) {
        this.companyName = companyName;
        this.postalcode = postalcode;
        this.location = location;
        this.address = address;
    }

    public int getWorkplaceID() {
        return workplaceID;
    }

    public void setWorkplaceID(int workplaceID) {
        this.workplaceID = workplaceID;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public int getPostalcode() {
        return postalcode;
    }

    public void setPostalcode(int postalcode) {
        this.postalcode = postalcode;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}

