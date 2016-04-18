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
}
