package mappers;

import domain.*;
import org.springframework.jdbc.core.*;

import java.sql.*;

public class PaymentMapper implements RowMapper<Payment> {

    public Payment mapRow(ResultSet rs, int i) throws SQLException{
        Payment payment = new Payment();
        payment.setAmount(rs.getDouble("amount"));
        payment.setDescription(rs.getString("description"));
        return payment;
    }
}