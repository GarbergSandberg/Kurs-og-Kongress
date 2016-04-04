package service;

import domain.*;

/**
 * Created by eiriksandberg on 01.04.2016.
 */
public interface LoginService {

    public boolean addUser(String username, String password);

    public User logIn(String username, String password);

    public String hash(String input);
}
