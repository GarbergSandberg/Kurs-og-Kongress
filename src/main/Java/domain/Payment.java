package domain;

/**
 * Created by eiriksandberg on 07.04.2016.
 */
public class Payment {
    private int id;
    private double amount;
    String description;

    public Payment(int id, double amount, String description) {
        this.id = id;
        this.amount = amount;
        this.description = description;
    }

    public Payment() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "Payment{" +
                "amount=" + amount +
                ", description='" + description + '\'' +
                '}';
    }
}
