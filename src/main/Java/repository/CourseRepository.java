package repository;

import domain.*;

import java.util.*;

/**
 * Created by eiriksandberg on 10.02.2016.
 */
public interface CourseRepository {

    public ArrayList<Course> getCourses();

    public Course getCourse(int id);

    public ArrayList<Registration> getRegistrations(int id);

    public boolean saveCourse(Course course);

    public boolean saveRegistration(Registration registration);

    public boolean updateRegistration(Registration registration);

    public int getCountRegistrations(int id);

    public Registration getRegistration(int registrationID);

    public boolean enableRegistration(int courseID, boolean value);

    public int getNumberOfPayments(ArrayList<Integer> regID, String description);

    public ArrayList<HashMap> getNumberOfParticipants(int courseID);

    public Integer getNumberOfEvents(int event_id);

    public ArrayList<Course> getNotAdminCourses(ArrayList<Integer> courses);

    public ArrayList<Boolean> getStatus(int courseID, ArrayList<Integer> sessionsToAttend, ArrayList<Integer> eventsToAttend, int numberOfRegistrations);

    public Boolean checkIfCourseGetsFull(int courseID, int numberOfRegistrations);
}
