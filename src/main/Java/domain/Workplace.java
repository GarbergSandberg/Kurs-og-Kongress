package domain;

/**
 * Created by Lars on 18.01.16.
 */
public class Workplace {
    private String name;
    private int postalcode;
    private String adress;

    public Workplace(String name, int postalcode, String adress) {
        this.name = name;
        this.postalcode = postalcode;
        this.adress = adress;
    }

    // Getters and setters.
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdress() {

        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public int getPostalcode() {
        return postalcode;
    }

    public void setPostalcode(int postalcode) {
        this.postalcode = postalcode;
    }
}

