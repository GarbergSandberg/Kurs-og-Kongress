package mappers;

import domain.*;
import org.springframework.jdbc.core.*;

import java.sql.*;

/**
 * Created by eiriksandberg on 15.04.2016.
 */
public class SessionMapper implements RowMapper<Session> {

    public Session mapRow(ResultSet rs, int i) throws SQLException{
        Session session = new Session();
        session.setId(rs.getInt("idSession"));
        session.setTitle(rs.getString("title"));
        session.setDescription(rs.getString("description"));
        session.setDate(rs.getDate("date"));
        return session;
    }
}
