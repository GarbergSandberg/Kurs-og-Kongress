package domain;

import java.util.*;

/**
 * Created by eiriksandberg on 10.02.2016.
 */
public class Form {
    private int id;
    private ArrayList<InputParameter> optionalPersonalia;
    private ArrayList<InputParameter> optionalWorkplace;
    private ArrayList<InputParameter> extraInfo;
    private boolean airplane;

    public Form(int id, ArrayList<InputParameter> optionalPersonalia, ArrayList<InputParameter> optionalWorkplace, ArrayList<InputParameter> extraInfo, boolean airplane) {
        this.id = id;
        this.optionalPersonalia = optionalPersonalia;
        this.optionalWorkplace = optionalWorkplace;
        this.extraInfo = extraInfo;
        this.airplane = airplane;
    }

    public Form() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<InputParameter> getOptionalPersonalia() {
        return optionalPersonalia;
    }

    public void setOptionalPersonalia(ArrayList<InputParameter> optionalPersonalia) {
        this.optionalPersonalia = optionalPersonalia;
    }

    public ArrayList<InputParameter> getOptionalWorkplace() {
        return optionalWorkplace;
    }

    public void setOptionalWorkplace(ArrayList<InputParameter> optionalWorkplace) {
        this.optionalWorkplace = optionalWorkplace;
    }

    public ArrayList<InputParameter> getExtraInfo() {
        return extraInfo;
    }

    public void setExtraInfo(ArrayList<InputParameter> extraInfo) {
        this.extraInfo = extraInfo;
    }

    public boolean isAirplane() {
        return airplane;
    }

    public void setAirplane(boolean airplane) {
        this.airplane = airplane;
    }

    @Override
    public String toString() {
        return "Form{" +
                "optionalPersonalia=" + optionalPersonalia +
                ", optionalWorkplace=" + optionalWorkplace +
                ", extraInfo=" + extraInfo +
                ", airplane=" + airplane +
                '}';
    }
}
