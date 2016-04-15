package mappers;

import domain.*;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.core.RowMapper;

import javax.swing.tree.*;
import java.sql.*;

/**
 * Created by eiriksandberg on 15.04.2016.
 */
public class RegistrationMapper implements RowMapper<Registration> {

    public Registration mapRow(ResultSet rs, int i) throws SQLException {
        Registration registration = new Registration();
        registration.setRegistrationID(rs.getInt("idRegistration"));
        registration.setAlternativeInvoiceAddress(rs.getString("alternativeinvoiceaddress"));
        registration.setSpeaker(rs.getBoolean("speaker"));
        registration.setRole(rs.getString("role"));
        return registration;
    }
}
