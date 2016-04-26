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

    public int getNumberOfEvents(ArrayList<Integer> registrationID, int eventID) {return courseRepository.getNumberOfEvents(registrationID, eventID);}

    @Override
    public boolean enableRegistration(int courseID, boolean value) {
        return courseRepository.enableRegistration(courseID,value);
    }
}
