package domain;

/**
 * Created by eiriksandberg on 10.02.2016.
 */
public class InputParameter {
    private int id;
    private String parameter;
    private String type;

    public InputParameter(int id, String parameter, String type) {
        this.id = id;
        this.parameter = parameter;
        this.type = type;
    }

    public InputParameter() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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
                "id=" + id +
                ", parameter='" + parameter + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
