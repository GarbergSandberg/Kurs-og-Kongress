package service;

import domain.*;

import java.util.*;

/**
 * Created by eiriksandberg on 10.02.2016.
 */
public interface CourseService {

    public ArrayList<Course> getCourses();

    public Course getCourse(int id);

    public ArrayList<Registration> getRegistrations(int id);

    public int getCountRegistrations(int id);

    public boolean saveCourse(Course course);

    public boolean saveRegistration(Registration registration);

    public boolean updateRegistration(Registration registration);

    public Registration getRegistration(int registrationID);

    public boolean enableRegistration(int courseID, boolean value);

    public int getNumberOfPayments(ArrayList<Integer> registrationID, String description);

    public HashMap<Integer, Integer> getNumberOfParticipantsSession(int courseID);
}
