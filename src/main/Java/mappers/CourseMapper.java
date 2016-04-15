package mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import domain.*;
import org.springframework.jdbc.core.RowMapper;

public class CourseMapper implements RowMapper<Course>{

    public Course mapRow(ResultSet rs, int i) throws SQLException{
        Course course = new Course();
        course.setId(rs.getInt("idCourse"));
        course.setTitle(rs.getString("title"));
        course.setLocation(rs.getString("location"));
        course.setDescription(rs.getString("description"));
        course.setStartDate(rs.getDate("startDate"));
        course.setEndDate(rs.getDate("endDate"));
        course.setCourseFee(rs.getDouble("courseFee"));
        course.setCourseSingleDayFee(rs.getDouble("courseSingleDayFee"));
        course.setDayPackage(rs.getDouble("dayPackage"));
        course.setMaxNumber(rs.getInt("maxNumber"));
        return course;
    }
}
