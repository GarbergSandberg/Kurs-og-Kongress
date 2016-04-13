package domain;

import java.util.*;

/**
 * Created by eiriksandberg on 13.04.2016.
 */
public class Accomondation {
    private int id;
    private String roommate; // can be null || ""
    private int hotelID;
    private Date fromDate;
    private Date toDate;
    private boolean doubleroom; // false = singleroom; true =

    public Accomondation(int id, String roommate, int hotelID, Date fromDate, Date toDate, boolean doubleroom) {
        this.id = id;
        this.roommate = roommate;
        this.hotelID = hotelID;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.doubleroom = doubleroom;
    }

    public Accomondation() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRoommate() {
        return roommate;
    }

    public void setRoommate(String roommate) {
        this.roommate = roommate;
    }

    public int getHotelID() {
        return hotelID;
    }

    public void setHotelID(int hotelID) {
        this.hotelID = hotelID;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public boolean isDoubleroom() {
        return doubleroom;
    }

    public void setDoubleroom(boolean doubleroom) {
        this.doubleroom = doubleroom;
    }

    @Override
    public String toString() {
        return "Accomondation{" +
                "id=" + id +
                ", roommate='" + roommate + '\'' +
                ", hotelID=" + hotelID +
                ", fromDate=" + fromDate +
                ", toDate=" + toDate +
                ", doubleroom=" + doubleroom +
                '}';
    }
}
