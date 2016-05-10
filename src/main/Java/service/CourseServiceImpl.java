package service;

import domain.*;
import org.springframework.beans.factory.annotation.*;
import repository.*;

import java.util.*;

/**
 * Created by eiriksandberg on 10.02.2016.
 */

public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository courseRepository;

    public ArrayList<Course> getCourses(){return courseRepository.getCourses(); }

    public Course getCourse(int id){return courseRepository.getCourse(id); }

    public ArrayList<Registration> getRegistrations(int id){return courseRepository.getRegistrations(id);}

    public int getCountRegistrations(int id) {return courseRepository.getCountRegistrations(id);}

    public boolean saveCourse(Course course){return courseRepository.saveCourse(course);}

    public boolean saveRegistration(Registration registration){return courseRepository.saveRegistration(registration);}

    public boolean updateRegistration(Registration registration){return courseRepository.updateRegistration(registration);}

    public Registration getRegistration(int registrationID){return courseRepository.getRegistration(registrationID);}

    public int getNumberOfPayments(ArrayList<Integer> registrationID, String description) {return courseRepository.getNumberOfPayments(registrationID, description);}

    public int getGroupNumberOfPayments(int idGroupregistration, String description) {return courseRepository.getGroupNumberOfPayments(idGroupregistration, description);}

    public Integer getNumberOfEvents(int eventID) {return courseRepository.getNumberOfEvents(eventID);}

    public Integer getGroupNumberOfEvents(int idGroupregistration, int eventID) {return courseRepository.getGroupNumberOfEvents(idGroupregistration, eventID);}

    public int getMaxIdGroupRegistration() {return courseRepository.getMaxIdGroupRegistration();}

    @Override
    public boolean enableRegistration(int courseID, boolean value) {
        return courseRepository.enableRegistration(courseID,value);
    }

    @Override
    public ArrayList<HashMap> getNumberOfParticipants(int courseID) {
        return courseRepository.getNumberOfParticipants(courseID);
    }

    @Override
    public ArrayList<Course> getNotAdminCourses(ArrayList<Integer> courses) {
        return courseRepository.getNotAdminCourses(courses);
    }

    @Override
    public ArrayList<Boolean> getStatus(int courseID, ArrayList<Integer> sessionsToAttend, ArrayList<Integer> eventsToAttend, int numberOfRegistrations) {
        return courseRepository.getStatus(courseID, sessionsToAttend, eventsToAttend, numberOfRegistrations);
    }

    @Override
    public Boolean checkIfCourseGetsFull(int courseID, int numberOfRegistrations) {
        return courseRepository.checkIfCourseGetsFull(courseID, numberOfRegistrations);
    }

    @Override
    public boolean deleteRegistration(Registration registration) {
        return courseRepository.deleteRegistration(registration);
    }

    @Override
    public ArrayList<String> getEmails(int courseID) {
        return courseRepository.getEmails(courseID);
    }
}
