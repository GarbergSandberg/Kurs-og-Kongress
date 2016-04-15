package domain;

/**
 * Created by eiriksandberg on 10.02.2016.
 */
public class InputParameter {
    private String parameter;
    private String type;

    public InputParameter(String parameter, String type) {
        this.parameter = parameter;
        this.type = type;
    }

    public InputParameter(){};

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "InputParameter{" +
                "parameter='" + parameter + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
