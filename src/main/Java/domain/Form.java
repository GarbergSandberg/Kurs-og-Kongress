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
    private ArrayList<InputParameter> inputQuestions;
    private CheckboxModel checkboxModel;

    public Form(ArrayList<InputParameter> requiredPersonalia, ArrayList<InputParameter> optionalPersonalia, ArrayList<InputParameter> requiredWorkplace, ArrayList<InputParameter> optionalWorkplace, ArrayList<InputParameter> inputQuestions, CheckboxModel checkboxModel) {
        this.requiredPersonalia = requiredPersonalia;
        this.optionalPersonalia = optionalPersonalia;
        this.requiredWorkplace = requiredWorkplace;
        this.optionalWorkplace = optionalWorkplace;
        this.inputQuestions = inputQuestions;
        this.checkboxModel = checkboxModel;
    }

    public Form() {
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

    public ArrayList<InputParameter> getInputQuestions() {
        return inputQuestions;
    }

    public void setInputQuestions(ArrayList<InputParameter> inputQuestions) {
        this.inputQuestions = inputQuestions;
    }

    public CheckboxModel getCheckboxModel() {
        return checkboxModel;
    }

    public void setCheckboxModel(CheckboxModel checkboxModel) {
        this.checkboxModel = checkboxModel;
    }
}