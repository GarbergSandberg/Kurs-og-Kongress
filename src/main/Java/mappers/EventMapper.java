package mappers;

import domain.*;
import org.springframework.jdbc.core.*;

import java.sql.*;

public class EventMapper implements RowMapper<Event> {

    public Event mapRow(ResultSet rs, int i) throws SQLException{
        Event event = new Event();
        event.setId(rs.getInt("idevent"));
        event.setTitle(rs.getString("title"));
        event.setPrice(rs.getDouble("price"));
        event.setMaxNumber(rs.getInt("startTime"));
        event.setTime((rs.getDate("endTime")));
        return event;
    }
}