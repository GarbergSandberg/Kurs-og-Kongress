package mappers;

import domain.*;
import org.springframework.jdbc.core.*;

import java.sql.*;
import java.util.*;

/**
 * Created by eiriksandberg on 16.04.2016.
 */
public class RegistrationForeignKeyMapper implements RowMapper<RegistrationForeignKeys> {

    public RegistrationForeignKeys mapRow(ResultSet rs, int i) throws SQLException {
        RegistrationForeignKeys fk = new RegistrationForeignKeys();
        fk.setCourseID(rs.getInt("course_idcourse"));
        fk.setAccomondationID(rs.getInt("accomondation_idaccomondation"));
        fk.setPersonID(rs.getInt("person_idperson"));
        fk.setWorkplaceID(rs.getInt("workplace_idworkplace"));
        fk.setExtrainfoID(rs.getInt("extrainfo_idextrainfo"));
        fk.setOptionalWorkplaceID(rs.getInt("optionalworkplace_idoptionalworkplace"));
        fk.setOptionalPersonaliaID(rs.getInt("optionalpersonalia_idoptionalpersonalia"));
        return fk;
    }
}
