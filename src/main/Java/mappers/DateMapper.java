package mappers;

import org.springframework.jdbc.core.*;

import java.sql.*;
import java.util.*;
import java.util.Date;

/**
 * Created by eiriksandberg on 16.04.2016.
 */
public class DateMapper implements RowMapper<Date> {

    public Date mapRow(ResultSet rs, int i) throws SQLException {
        return rs.getDate("date");
    }
}
