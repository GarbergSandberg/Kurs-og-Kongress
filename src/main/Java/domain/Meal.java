package domain;



/**
 * Created by eiriksandberg on 18.01.2016.
 */

public class Meal {
    private String type;
    private String description;
    private double price;

    public Meal(String type, double price) {
        this.type = type;
        this.price = price;
    }

    public Meal(String type, String description, double price) {
        this.type = type;
        this.description = description;
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
