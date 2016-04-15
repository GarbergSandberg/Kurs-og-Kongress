package mappers;

import org.springframework.jdbc.core.*;

import java.sql.*;

/**
 * Created by eiriksandberg on 15.04.2016.
 */
public class SupportMapper implements RowMapper<Integer> {

    public Integer mapRow(ResultSet rs, int i) throws SQLException{
        return rs.getInt("idCourse");
    }
}