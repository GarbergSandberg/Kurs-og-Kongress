package mappers;

import domain.*;
import org.springframework.jdbc.core.*;

import java.sql.*;

public class HotelMapper implements RowMapper<Hotel> {

    public Hotel mapRow(ResultSet rs, int i) throws SQLException{
        Hotel hotel = new Hotel();
        hotel.setId(rs.getInt("idhotel"));
        hotel.setName(rs.getString("name"));
        hotel.setDoubleprice(rs.getDouble("doubleprice"));
        hotel.setSingleprice(rs.getDouble("singleprice"));
        hotel.setAddress(rs.getString("address"));
        return hotel;
    }
}