package service;

import domain.*;
import org.springframework.beans.factory.annotation.*;
import repository.*;

import java.security.*;
import java.util.*;

/**
 * Created by eiriksandberg on 01.04.2016.
 */
public class LoginServiceImpl implements LoginService {

    @Autowired
    private LoginRepository loginRepository;

    public boolean addUser(User user) {
        user.setPassword(hash(user.getPassword()));
        return loginRepository.addUser(user);
    }

    public boolean changePassword(String username, String oldPassword, String newPassword){
        return loginRepository.changePassword(username, hash(oldPassword), hash(newPassword));
    }

    public User logIn(String username, String password) {
        User u = loginRepository.logIn(username);
        if (u != null){
            if(compare(u.getPassword(), hash(password))){
                return u;
            } else{
                return null;
            }
        } else{
            return null;
        }
    }

    public boolean addAccess(User user, Course course){return loginRepository.addAccess(user,course);}

    @Override
    public ArrayList<User> getUsers() {
        return loginRepository.getUsers();
    }

    @Override
    public ArrayList<Integer> getCourseAccess(String username) {
        return loginRepository.getCourseAccess(username);
    }

    @Override
    public boolean removeCourseAccess(User user, Course course) {
        return loginRepository.removeCourseAccess(user,course);
    }

    @Override
    public boolean deleteUser(User user) {
        return loginRepository.deleteUser(user);
    }

    @Override
    public String hash(String input) {
        // initial content:
        String generatedPass1 = null;
        try {
            // Create MessageDigest instance for MD5
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            //Add password bytes to digest
            md.update(input.getBytes());
            //Get the hash's bytes
            byte[] bytes = md.digest();
            //This bytes[] has bytes in decimal format;
            //Convert it to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            //Get complete hashed password in hex format
            generatedPass1 = sb.toString();

        } catch (Exception e) {
        }
        return generatedPass1;
    }

    private boolean compare(String fromDB, String fromClient){
        if (fromDB.equals(fromClient)){
            System.out.println("Accepted");
            return true;
        } else{
            System.out.println("Denied");
            return false;
        }
    }
}


