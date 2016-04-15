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
    private final String sqlGetSession = "select * from Session where idSession = ?";

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
        } catch (Exception e){
            System.out.println("Feil i getCourse! Finner ikke kurs " + e);
            course = null;
        }
        System.out.println(course.toString());
        return course;
    }

/*    public Session getSession(int id){
        Session session = new Session();
        try{
            session = (Session) jdbcTemplateObject.queryForObject(sqlGetSession, new Object[]{id}, new)
        } catch(Exception e){

        }
    }*/

    public ArrayList<Course> getCourses() {
        return null;
    }
}
