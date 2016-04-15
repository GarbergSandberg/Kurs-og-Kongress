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
    private CourseRepository courseRepositoryMock;

    public ArrayList<Course> getCourses(){return courseRepositoryMock.getCourses(); }

    public Course getCourse(int id){return courseRepositoryMock.getCourse(id); }
}
