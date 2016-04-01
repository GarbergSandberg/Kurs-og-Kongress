/*
package controller;

import domain.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.*;
import service.*;
import ui.*;

import java.util.*;

*/
/**
 * Created by eiriksandberg on 31.03.2016.
 *//*

@Controller
public class loginController {
    private ArrayList<User> users = makeUserList();

    private ArrayList<User> makeUserList(){
        ArrayList<User> u = new ArrayList<User>();
        User testUser = new User("Lars", "Garberg", "lars", "123", 13);
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

    @RequestMapping(value = "/loginUser", method = RequestMethod.GET)
    @ResponseBody
    public User getCourse(@RequestParam(value = "user") User user) {
        String recievedPassword = user.getPassword();
        String recievedUsername = user.getUsername();
        return findUser(recievedUsername, recievedPassword);
    }
}
*/
