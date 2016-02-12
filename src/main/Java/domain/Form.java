package domain;

import java.util.*;

/**
 * Created by eiriksandberg on 10.02.2016.
 */
public class Form {
    private ArrayList<InputParameter> requiredPersonalia;
    private ArrayList<InputParameter> optionalPersonalia;
    private ArrayList<InputParameter> requiredWorkplace;
    private ArrayList<InputParameter> optionalWorkplace;
    private ArrayList<InputParameter> extraInfo;
    private CheckboxModel checkboxModel;

    public Form(ArrayList<InputParameter> requiredPersonalia, ArrayList<InputParameter> optionalPersonalia, ArrayList<InputParameter> requiredWorkplace, ArrayList<InputParameter> optionalWorkplace, ArrayList<InputParameter> extraInfo, CheckboxModel checkboxModel) {
        this.requiredPersonalia = requiredPersonalia;
        this.optionalPersonalia = optionalPersonalia;
        this.requiredWorkplace = requiredWorkplace;
        this.optionalWorkplace = optionalWorkplace;
        this.extraInfo = extraInfo;
        this.checkboxModel = checkboxModel;
    }

    public ArrayList<InputParameter> getRequiredPersonalia() {
        return requiredPersonalia;
    }

    public void setRequiredPersonalia(ArrayList<InputParameter> requiredPersonalia) {
        this.requiredPersonalia = requiredPersonalia;
    }

    public ArrayList<InputParameter> getOptionalPersonalia() {
        return optionalPersonalia;
    }

    public void setOptionalPersonalia(ArrayList<InputParameter> optionalPersonalia) {
        this.optionalPersonalia = optionalPersonalia;
    }

    public ArrayList<InputParameter> getRequiredWorkplace() {
        return requiredWorkplace;
    }

    public void setRequiredWorkplace(ArrayList<InputParameter> requiredWorkplace) {
        this.requiredWorkplace = requiredWorkplace;
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