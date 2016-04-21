package service;

import domain.*;
import org.springframework.beans.factory.annotation.*;
import repository.*;

import java.security.*;

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


