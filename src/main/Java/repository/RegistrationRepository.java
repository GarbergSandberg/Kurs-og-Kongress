package repository;

import domain.*;

import java.util.*;

/**
 * Created by eiriksandberg on 07.04.2016.
 */
public interface RegistrationRepository {

    public ArrayList<Registration> getRegistrations(int courseID);
}
