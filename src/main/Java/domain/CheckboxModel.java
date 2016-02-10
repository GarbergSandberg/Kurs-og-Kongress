package domain;

/**
 * Created by eiriksandberg on 10.02.2016.
 */
public class CheckboxModel {
    private boolean hotel;
    private boolean airplane;

    public CheckboxModel(){};

    public CheckboxModel(boolean hotel, boolean airplane) {
        this.hotel = hotel;
        this.airplane = airplane;
    }

    public boolean isHotel() {
        return hotel;
    }

    public void setHotel(boolean hotel) {
        this.hotel = hotel;
    }

    public boolean isAirplane() {
        return airplane;
    }

    public void setAirplane(boolean airplane) {
        this.airplane = airplane;
    }
}
