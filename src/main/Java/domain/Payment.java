package domain;

/**
 * Created by eiriksandberg on 07.04.2016.
 */
public class Payment {
    private double amount;
    String description;

    public Payment(double amount, String description) {
        this.amount = amount;
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
