package mappers;

import domain.*;
import org.springframework.jdbc.core.*;

import java.sql.*;
import java.sql.Date;
import java.text.*;
import java.util.*;

/**
 * Created by eiriksandberg on 15.04.2016.
 */
public class SessionMapper implements RowMapper<Session> {

    public Session mapRow(ResultSet rs, int i) throws SQLException{
        Session session = new Session();
        session.setId(rs.getInt("idSession"));
        session.setTitle(rs.getString("title"));
        session.setDescription(rs.getString("description"));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try{
            java.util.Date date = simpleDateFormat.parse(rs.getTimestamp("date").toString());
            java.util.Date start = simpleDateFormat.parse(rs.getTimestamp("startTime").toString());
            java.util.Date end = simpleDateFormat.parse(rs.getTimestamp("endTime").toString());
            session.setDate(date);
            session.setStartTime(start);
            session.setEndTime(end);
        } catch (Exception e){
            System.out.println(e);
        }
        session.setLocation(rs.getString("location"));
        session.setMaxnumber(rs.getInt("maxNumber"));
        return session;
    }
}
