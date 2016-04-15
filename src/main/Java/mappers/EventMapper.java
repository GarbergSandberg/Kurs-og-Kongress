package mappers;

import domain.*;
import org.springframework.jdbc.core.*;

import java.sql.*;

public class EventMapper implements RowMapper<Event> {

    public Event mapRow(ResultSet rs, int i) throws SQLException{
        Event event = new Event();
        event.setId(rs.getInt("idEvent"));
        event.setTitle(rs.getString("title"));
        event.setPrice(rs.getDouble("price"));
        event.setMaxNumber(rs.getInt("maxNumber"));
        event.setLocation(rs.getString("location"));
        event.setStartTime(rs.getDate("startTime"));
        event.setEndTime(rs.getDate("endTime"));
        return event;
    }
}