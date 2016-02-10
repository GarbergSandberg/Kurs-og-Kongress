package service;

import domain.*;
import org.springframework.beans.factory.annotation.*;
import repository.*;

/**
 * Created by eiriksandberg on 10.02.2016.
 */

public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository courseRepositoryMock;

    public Course getCourses() {
        return courseRepositoryMock.getCourses();
    }
}
