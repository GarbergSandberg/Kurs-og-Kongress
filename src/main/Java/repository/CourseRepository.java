package repository;

import domain.*;

/**
 * Created by eiriksandberg on 10.02.2016.
 */
public interface CourseRepository {
    public Course getCourses();

    public Course generateTemplate();
}
