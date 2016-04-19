package mappers;

import domain.*;
import org.springframework.jdbc.core.*;

import java.sql.*;

public class InputParameterMapper implements RowMapper<InputParameter> {

    public InputParameter mapRow(ResultSet rs, int i) throws SQLException{
        InputParameter inputParameter = new InputParameter();
        inputParameter.setId(rs.getInt("idinputparameter"));
        inputParameter.setParameter(rs.getString("parameter"));
        inputParameter.setType(rs.getString("type"));
        return inputParameter;
    }
}