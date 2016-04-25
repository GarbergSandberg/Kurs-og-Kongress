package service;

import domain.*;

import java.util.*;

/**
 * Created by eiriksandberg on 01.04.2016.
 */
public interface LoginService {

    public boolean addUser(User user);

    public User logIn(String username, String password);

    public String hash(String input);

    public boolean addAccess(User user, Course course);

    public ArrayList<User> getUsers();

    public ArrayList<Integer> getCourseAccess(String username);

    public boolean removeCourseAccess(User user, Course course);

    public boolean deleteUser(User user);
}

