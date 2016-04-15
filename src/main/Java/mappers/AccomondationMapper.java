package mappers;

import domain.*;
import org.springframework.jdbc.core.*;

import java.sql.*;


public class AccomondationMapper implements RowMapper<Accomondation>{

    public Accomondation mapRow(ResultSet rs, int i) throws SQLException{
        Accomondation accomondation = new Accomondation();
        accomondation.setId(rs.getInt("idAccomondation"));
        accomondation.setRoommate(rs.getString("roommate"));
        accomondation.setHotelID(rs.getInt("hotelID"));
        accomondation.setFromDate((rs.getDate("fromDate")));
        accomondation.setToDate(rs.getDate("toDate"));
        accomondation.setDoubleroom(rs.getBoolean("doubleroom"));
        return accomondation;
    }
}
