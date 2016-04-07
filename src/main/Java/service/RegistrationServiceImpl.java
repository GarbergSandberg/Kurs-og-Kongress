package service;

import domain.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import repository.*;

import java.util.*;

/**
 * Created by eiriksandberg on 07.04.2016.
 */

public class RegistrationServiceImpl implements RegistrationService {

    @Autowired
    private RegistrationRepository registrationRepository;

    @Override
    public ArrayList<Registration> getRegistrations(int courseID) {
        return registrationRepository.getRegistrations(courseID);
    }
}
