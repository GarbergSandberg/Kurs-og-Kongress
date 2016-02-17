package repository;

import domain.*;

import java.util.*;

/**
 * Created by eiriksandberg on 10.02.2016.
 */
public interface CourseRepository {

    public ArrayList<Course> getCourses();

    public Course getMockCourse();

    public Course getCourse(int id);

    public Course generateTemplate();
}
