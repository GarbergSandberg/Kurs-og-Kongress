package repository;

import domain.*;

/**
 * Created by eiriksandberg on 01.04.2016.
 */
public interface LoginRepository {
    public boolean addUser(String username, String password);
    public User logIn(String username);
}
