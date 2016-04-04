package repository;

import domain.*;
import org.springframework.beans.factory.annotation.*;

import java.util.*;

/**
 * Created by eiriksandberg on 01.04.2016.
 */
public class LoginRepositoryMock implements LoginRepository {

    private ArrayList<User> users = makeUserList();

    public User logIn(String username){
        User u = findUser(username);
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
        User testUser = new User("lars", "3c9909afec25354d551dae21590bb26e38d53f2173b8d3dc3eee4c047e7ab1c1eb8b85103e3be7ba613b31bb5c9c36214dc9f14a42fd7a2fdb84856bca5c44c2");
        u.add(testUser);
        return u;
    }

    private User findUser(String user){
        for (User u : users){
            if (u.getUsername().equals(user)){
                return u;
            }
        }
        return null;
    }
}
