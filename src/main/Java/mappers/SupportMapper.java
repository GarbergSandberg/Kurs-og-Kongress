package mappers;

import org.springframework.jdbc.core.*;

import java.sql.*;

/**
 * Created by eiriksandberg on 15.04.2016.
 */
public class SupportMapper implements RowMapper<Integer> {

    public Integer mapRow(ResultSet rs, int i) throws SQLException {
        ResultSetMetaData meta = rs.getMetaData();
        String name = meta.getTableName(1).toLowerCase();
        if (name.equals("course")) {
            return rs.getInt("idCourse");
        } else if (name.equals("sessionid")) {
            return rs.getInt("sessionid");
        } else if (name.equals("eventid")) {
            return rs.getInt("eventid");
        } else{
            return null;
        }
    }
}