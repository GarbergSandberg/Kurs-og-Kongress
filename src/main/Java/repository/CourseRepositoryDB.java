package repository;

import domain.*;
import mappers.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.jdbc.core.*;
import javax.sql.DataSource;
import java.util.*;

/**
 * Created by eiriksandberg on 14.04.2016.
 */
public class CourseRepositoryDB implements CourseRepository{
    private DataSource dataSource;
    JdbcTemplate jdbcTemplateObject;

    private final String sqlGetCourse = "select * from Course where idCourse = ?";
    private final String sqlGetSession = "select * from Session where COURSE_IDCOURSE = ?";

    @Autowired
    public void setDataSource(DataSource dataSource) {
        System.out.println("Database.setDataSource " + dataSource);
        this.dataSource = dataSource;
        this.jdbcTemplateObject = new JdbcTemplate(dataSource);
    }

    public Course getCourse(int id) {
        Course course = new Course();
        try{
            course = (Course) jdbcTemplateObject.queryForObject(sqlGetCourse, new Object[]{id}, new CourseMapper());
            course.setSessions(getSessions(id));
            System.out.println(course.getSessions().get(0).toString());
        } catch (Exception e){
            System.out.println("Feil i getCourse! Finner ikke kurs " + e);
            course = null;
        }
        System.out.println(course.toString());
        return course;
    }

    public ArrayList<Session> getSessions(int courseID){
        return (ArrayList<Session>) jdbcTemplateObject.query(sqlGetSession, new Object[]{courseID}, new SessionMapper());
    }

    public ArrayList<Course> getCourses() {
        return null;
    }
}
