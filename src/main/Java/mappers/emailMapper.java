package mappers;

import org.springframework.jdbc.core.*;

import java.sql.*;

/**
 * Created by eiriksandberg on 10.05.2016.
 */
public class EmailMapper implements RowMapper<String> {

    public String mapRow(ResultSet rs, int i) throws SQLException {
        String s = rs.getString("email");
        return s;
    }
}