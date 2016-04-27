package mappers;

import domain.*;
import org.springframework.jdbc.core.*;

import java.sql.*;
import java.text.*;

public class EventMapper implements RowMapper<Event> {

    public Event mapRow(ResultSet rs, int i) throws SQLException{
        Event event = new Event();
        event.setId(rs.getInt("idEvent"));
        event.setTitle(rs.getString("title"));
        event.setPrice(rs.getDouble("price"));
        event.setMaxNumber(rs.getInt("maxNumber"));
        event.setLocation(rs.getString("location"));
        event.setDate(new Date(rs.getTimestamp("date").getTime()));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try{
            java.util.Date time = simpleDateFormat.parse(rs.getTimestamp("time").toString());
            event.setTime(time);
        } catch (Exception e){
            System.out.println(e);
        }
        return event;
    }
}