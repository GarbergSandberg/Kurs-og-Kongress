package mappers;

import domain.*;
import org.springframework.jdbc.core.*;

import java.sql.*;

public class PersonMapper implements RowMapper<Person> {

    public Person mapRow(ResultSet rs, int i) throws SQLException{
        Person person = new Person();
        person.setPersonID(rs.getInt("idperson"));
        person.setFirstname(rs.getString("firstname"));
        person.setLastname(rs.getString("lastname"));
        person.setBirthYear(rs.getInt("birthYear"));
        person.setPhonenumber(rs.getInt("phonenumber"));
        person.setEmail(rs.getString("email"));
        person.setGender(rs.getString("gender"));
        return person;
    }
}