package mappers;

import java.sql.*;
import java.util.*;
import java.util.Date;

import domain.*;
import org.springframework.jdbc.core.RowMapper;

public class CourseMapper implements RowMapper<Course>{

    public Course mapRow(ResultSet rs, int i) throws SQLException {
        Course course = new Course();
        course.setId(rs.getInt("idCourse"));
        course.setTitle(rs.getString("title"));
        course.setLocation(rs.getString("location"));
        course.setDescription(rs.getString("description"));
        course.setStartDate(new Date(rs.getTimestamp("startDate").getTime()));
        course.setEndDate(new Date(rs.getTimestamp("endDate").getTime()));
        course.setCourseFee(rs.getDouble("courseFee"));
        course.setCourseSingleDayFee(rs.getDouble("courseSingleDayFee"));
        course.setDayPackage(rs.getDouble("dayPackage"));
        course.setMaxNumber(rs.getInt("maxNumber"));
        course.setPublicCourse(rs.getBoolean("publicCourse"));
        return course;
    }
}
