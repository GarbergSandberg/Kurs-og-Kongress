package domain;

/**
 * Created by Lars on 12.04.2016.
 */
public class Hotel {
    int id;
    private String name;
    private double doubleprice;
    private double singleprice;
    private String address;

    public Hotel(int id, String name, double doubleprice, double singleprice, String address) {
        this.id = id;
        this.name = name;
        this.doubleprice = doubleprice;
        this.singleprice = singleprice;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public int getId() {return id;}

    public void setId(int id) {this.id = id;}

    public void setName(String name) {
        this.name = name;
    }

    public double getDoubleprice() {
        return doubleprice;
    }

    public void setDoubleprice(double doubleprice) {
        this.doubleprice = doubleprice;
    }

    public double getSingleprice() {
        return singleprice;
    }

    public void setSingleprice(double singleprice) {
        this.singleprice = singleprice;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
