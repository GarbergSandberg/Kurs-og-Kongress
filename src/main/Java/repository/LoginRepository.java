package repository;

import domain.*;

/**
 * Created by eiriksandberg on 01.04.2016.
 */
public interface LoginRepository {
    public boolean addUser(User user);
    public User logIn(String username);
}
