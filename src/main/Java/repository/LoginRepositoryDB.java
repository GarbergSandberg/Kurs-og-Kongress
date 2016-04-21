package repository;

import domain.*;
import mappers.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.jdbc.core.*;

import javax.sql.*;

/**
 * Created by eiriksandberg on 21.04.2016.
 */
public class LoginRepositoryDB implements LoginRepository {
    private DataSource dataSource;
    JdbcTemplate jdbcTemplateObject;

    private final String checkIfUserExists = "select count(username) from account where username = ?";
    private final String setUser = "insert into account values (?,?, false)";
    private final String getUser = "select * from account where username = ?";

    @Autowired
    public void setDataSource(DataSource dataSource) {
        System.out.println("Database.setDataSource " + dataSource);
        this.dataSource = dataSource;
        this.jdbcTemplateObject = new JdbcTemplate(dataSource);
    }

    @Override
    public boolean addUser(User user) {
        try{
            int check = jdbcTemplateObject.queryForObject(checkIfUserExists, new Object[]{user.getUsername()}, Integer.class);
            if (check == 1){
                System.out.println("User with username " + user.getUsername() + " already exists");
                return false;
            }
            jdbcTemplateObject.update(setUser, new Object[]{
                    user.getUsername(), user.getPassword()
            });
            System.out.println("User " + user.getUsername() + " has been created");
        } catch(Exception e){
            System.out.println("Error in addUser " + e);
            return false;
        }
        return true;
    }

    @Override
    public User logIn(String username) {
        try{
            System.out.println("Username: " + username);
            int check = jdbcTemplateObject.queryForObject(checkIfUserExists, new Object[]{username}, Integer.class);
            if(check == 0){
                System.out.println("No user with username " + username);
                return null;
            }
            User user = jdbcTemplateObject.queryForObject(getUser, new Object[]{username}, new UserMapper());
            return user;
        } catch(Exception e){
            System.out.println("Error in login " + e);
            return null;
        }
    }
}
