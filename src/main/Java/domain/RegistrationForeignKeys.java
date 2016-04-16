package domain;

/**
 * Created by eiriksandberg on 16.04.2016.
 */
public class RegistrationForeignKeys {
    private int accomondationID;
    private int personID;
    private int workplaceID;
    private int extrainfoID;
    private int optionalWorkplaceID;
    private int optionalPersonaliaID;

    public RegistrationForeignKeys(int accomondationID, int personID, int workplaceID, int extrainfoID, int optionalWorkplaceID, int optionalPersonaliaID) {
        this.accomondationID = accomondationID;
        this.personID = personID;
        this.workplaceID = workplaceID;
        this.extrainfoID = extrainfoID;
        this.optionalWorkplaceID = optionalWorkplaceID;
        this.optionalPersonaliaID = optionalPersonaliaID;
    }

    public RegistrationForeignKeys() {
    }

    public int getAccomondationID() {
        return accomondationID;
    }

    public void setAccomondationID(int accomondationID) {
        this.accomondationID = accomondationID;
    }

    public int getPersonID() {
        return personID;
    }

    public void setPersonID(int personID) {
        this.personID = personID;
    }

    public int getWorkplaceID() {
        return workplaceID;
    }

    public void setWorkplaceID(int workplaceID) {
        this.workplaceID = workplaceID;
    }

    public int getExtrainfoID() {
        return extrainfoID;
    }

    public void setExtrainfoID(int extrainfoID) {
        this.extrainfoID = extrainfoID;
    }

    public int getOptionalWorkplaceID() {
        return optionalWorkplaceID;
    }

    public void setOptionalWorkplaceID(int optionalWorkplaceID) {
        this.optionalWorkplaceID = optionalWorkplaceID;
    }

    public int getOptionalPersonaliaID() {
        return optionalPersonaliaID;
    }

    public void setOptionalPersonaliaID(int optionalPersonaliaID) {
        this.optionalPersonaliaID = optionalPersonaliaID;
    }
}
