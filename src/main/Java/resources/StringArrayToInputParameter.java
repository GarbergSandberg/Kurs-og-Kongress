
package resources;

import domain.*;
import org.json.*;
import org.springframework.core.convert.converter.*;

import javax.persistence.criteria.*;
import java.util.*;


/**
 * Created by eiriksandberg on 04.04.2016.
 */

public class StringArrayToInputParameter{

    public ArrayList<InputParameter> convertStringToInputParameter(ArrayList<String> source) {
        ArrayList<InputParameter> array = new ArrayList<InputParameter>();
        if (source != null) {
            for (String s : source){
                JSONObject obj = new JSONObject(s);
                String parameter = obj.getString("parameter");
                String type = obj.getString("type");
                InputParameter ip = new InputParameter(parameter,type);
                array.add(ip);
            }
        }
        return array;
    }

    public CheckboxModel convertToCheckboxModel(String s){
        JSONObject obj = new JSONObject(s);
        boolean hotel = obj.getBoolean("hotel");
        boolean airplane =obj.getBoolean("airplane");
        return new CheckboxModel(hotel,airplane);
    }

    public Form convert(ArrayList<InputParameter> requiredPersonalia,
                        ArrayList<InputParameter> optionalPersonalia,
                        ArrayList<InputParameter> requiredWorkplace,
                        ArrayList<InputParameter> optionalWorkplace,
                        ArrayList<InputParameter> extraInfo,
                        CheckboxModel cm)
    {
        return new Form(requiredPersonalia,optionalPersonalia,requiredWorkplace,optionalWorkplace,extraInfo,cm);
    }
}

