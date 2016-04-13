package domain;

import java.util.*;

/**
 * Created by eiriksandberg on 10.02.2016.
 */
public class Form {
    private ArrayList<InputParameter> optionalPersonalia;
    private ArrayList<InputParameter> optionalWorkplace;
    private ArrayList<InputParameter> extraInfo;
    private CheckboxModel checkboxModel;

    public Form(ArrayList<InputParameter> optionalPersonalia, ArrayList<InputParameter> optionalWorkplace, ArrayList<InputParameter> extraInfo, CheckboxModel checkboxModel) {
        this.optionalPersonalia = optionalPersonalia;
        this.optionalWorkplace = optionalWorkplace;
        this.extraInfo = extraInfo;
        this.checkboxModel = checkboxModel;
    }

    public Form() {
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

    public CheckboxModel getCheckboxModel() {
        return checkboxModel;
    }

    public void setCheckboxModel(CheckboxModel checkboxModel) {
        this.checkboxModel = checkboxModel;
    }
}