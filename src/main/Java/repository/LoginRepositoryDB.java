package repository;

import domain.*;
import mappers.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.jdbc.core.*;

import javax.sql.*;
import java.util.*;

/**
 * Created by eiriksandberg on 21.04.2016.
 */
public class LoginRepositoryDB implements LoginRepository {
    private DataSource dataSource;
    JdbcTemplate jdbcTemplateObject;

    private final String checkIfUserExists = "select count(username) from account where username = ?";
    private final String setUser = "insert into account values (?,?,?)";
    private final String getUser = "select * from account where username = ?";
    private final String getUsers = "select * from account";
    private final String setAccess = "insert into account_has_access values (?,?)";
    private final String getCourseAccess = "select course_idcourse from account_has_access where user_idusername = ?";
    private final String removeAccess = "delete from account_has_access where user_idusername = ? and course_idcourse = ?";
    private final String deleteUser = "delete from account where username = ?";
    private final String deleteAllAccess = "delete from account_has_access where user_idusername = ?";
    private final String getPassword = "select password from account where username = ?";
    private final String changePassword = "update account set password = ? where username = ?";

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
                    user.getUsername(), user.getPassword(), user.isAdmin()
            });
            System.out.println("User " + user.getUsername() + " has been created");
        } catch(Exception e){
            System.out.println("Error in addUser " + e);
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteUser(User user) {
        try{
            int check = jdbcTemplateObject.queryForObject(checkIfUserExists, new Object[]{user.getUsername()}, Integer.class);
            if (check == 0){
                System.out.println("User doesn't exists");
                return false;
            }
            jdbcTemplateObject.update(deleteAllAccess, new Object[]{
                    user.getUsername()
            });
            jdbcTemplateObject.update(deleteUser, new Object[]{
                    user.getUsername()
            });
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

    public boolean addAccess(User user, Course course){
        try{
            jdbcTemplateObject.update(setAccess, new Object[]{
                    user.getUsername(), course.getId()
            });
        } catch(Exception e){
            System.out.println("Error in addAccess " + e);
            return false;
        }
        return true;
    }

    public boolean changePassword(String username, String oldPassword, String newPassword){
        try{
            String currentPassword =  jdbcTemplateObject.queryForObject(getPassword, new Object[]{username}, String.class);
            if(currentPassword.equals(oldPassword)){
                jdbcTemplateObject.update(changePassword, new Object[]{
                        newPassword, username
                });
                return true;
            } else{
                return false;
            }
        } catch(Exception e){
            System.out.println("Error in addAccess " + e);
            return false;
        }
    }

    public ArrayList<User> getUsers(){
        ArrayList<User> users = new ArrayList<User>();
        try{
            users = (ArrayList<User>) jdbcTemplateObject.query(getUsers, new Object[]{}, new UserMapper());
            ArrayList<User> usersToDisplay = new ArrayList<User>();
            for (int i = 0; i < users.size(); i++){
                if(!users.get(i).isAdmin()){
                    users.get(i).setPassword(null);
                    usersToDisplay.add(users.get(i));
                }
            }
            users = usersToDisplay;
        } catch(Exception e){
            System.out.println("Error in getUsers() " + e);
        }
        return users;
    }

    public ArrayList<Integer> getCourseAccess(String username){
        try{
            ArrayList<Integer> courseIDs = (ArrayList<Integer>) jdbcTemplateObject.query(getCourseAccess, new Object[]{username}, new SupportMapper());
            return courseIDs;
        } catch(Exception e){
            System.out.println("Error in getCourseAccess");
            return null;
        }
    }

    public boolean removeCourseAccess(User user, Course course){
        try{
            jdbcTemplateObject.update(removeAccess, new Object[]{
                    user.getUsername(), course.getId()
            });
            return true;
        } catch(Exception e){
            System.out.println("Error in removeCourseAccess() " + e);
            return false;
        }
    }
}
