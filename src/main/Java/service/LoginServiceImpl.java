package service;

import domain.*;
import org.springframework.beans.factory.annotation.*;
import repository.*;

/**
 * Created by eiriksandberg on 01.04.2016.
 */
public class LoginServiceImpl implements LoginService {

    @Autowired
    private LoginRepository loginRepository;

    public boolean addUser(String username, String password){return loginRepository.addUser(username, password);}

    public User logIn(String username, String password){return loginRepository.logIn(username,password);}


}
