package repository;

import domain.*;
import org.springframework.beans.factory.annotation.*;

import java.util.*;

/**
 * Created by eiriksandberg on 01.04.2016.
 */
public class LoginRepositoryMock implements LoginRepository {

    private ArrayList<User> users = makeUserList();

    public User logIn(String username, String password){
        User u = findUser(username,password);
        if(u != null){
            return u;
        } else{
            return null;
        }
    }

    public boolean addUser(String username, String password){
        User u = new User(username, password);
        for (User user : users){
            if (user.getUsername() == username){
                return false;
            }
        }
        users.add(u);
        return true;
    }

    private ArrayList<User> makeUserList(){
        ArrayList<User> u = new ArrayList<User>();
        User testUser = new User("lars", "123");
        u.add(testUser);
        return u;
    }

    private User findUser(String user, String pass){
        for (User u : users){
            if (u.getUsername().equals(user)){
                if(u.getPassword().equals(pass)){
                    return u;
                }
            }
        }
        return null;
    }
}
