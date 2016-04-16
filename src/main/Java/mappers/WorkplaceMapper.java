package mappers;

import domain.*;
import org.springframework.jdbc.core.*;

import java.sql.*;

/**
 * Created by eiriksandberg on 16.04.2016.
*/
public class WorkplaceMapper implements RowMapper<Workplace> {

    public Workplace mapRow(ResultSet rs, int i) throws SQLException {
        Workplace workplace = new Workplace();
        workplace.setWorkplaceID(rs.getInt("idworkplace"));
        workplace.setCompanyName(rs.getString("companyname"));
        workplace.setPostalcode(rs.getInt("postalcode"));
        workplace.setLocation(rs.getString("location"));
        workplace.setAddress(rs.getString("address"));
        return workplace;
    }
}