package mappers;

import domain.*;
import org.springframework.jdbc.core.*;

import java.sql.*;

/**
 * Created by Lars on 15.04.2016.
 */
public class FormMapper implements RowMapper<Form> {

    public Form mapRow(ResultSet rs, int i) throws SQLException{
        Form form = new Form();
        return form;
    }
}