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
            System.out.println("Dette funker!!!! " + rs.getInt("idCourse"));
            return rs.getInt("idCourse");
        } else if (name.equals("sessionid")) {
            return rs.getInt("sessionid");
        } else if (name.equals("eventid")) {
            return rs.getInt("eventid");
        } else if (name.equals("optionalpersonalia")){
            return rs.getInt("idoptionalpersonalia");
        } else if (name.equals("optionalworkplace")){
            return rs.getInt("idoptionalworkplace");
        } else if (name.equals("extrainfo")){
            return rs.getInt("idextrainfo");
        } else if (name.equals("inputparameter")){
            return rs.getInt("idinputparameter");
        } else if(name.equals("form")){
            return rs.getInt("idform");
        } else if (name.equals("courserole")){
            return rs.getInt("idcourserole");
        }else if (name.equals("account_has_access")){
            return rs.getInt("course_idcourse");
        } else{
            return null;
        }
    }
}