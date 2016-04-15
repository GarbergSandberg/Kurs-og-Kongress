package mappers;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by eiriksandberg on 15.04.2016.
 */
public class CourseRoleMapper implements RowMapper<String> {

    public String mapRow(ResultSet rs, int i) throws SQLException {
        return rs.getString("role");
    }
}
