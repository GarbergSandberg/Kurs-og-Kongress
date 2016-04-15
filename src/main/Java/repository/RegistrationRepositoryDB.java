package repository;

import domain.*;
import mappers.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.jdbc.core.*;

import javax.sql.*;
import java.util.*;

/**
 * Created by eiriksandberg on 15.04.2016.
 */
public class RegistrationRepositoryDB implements RegistrationRepository {
    private DataSource dataSource;
    JdbcTemplate jdbcTemplateObject;
    private CourseRepositoryDB courseRepositoryDB = new CourseRepositoryDB();

    private final String sqlGetRegistration = "select * from Registration where course_idcourse = ?";
    private final String sqlGetSessionsToAttend = "select sessionid from sessionid where registration_idregistration = ?";
    private final String sqlGetEventsToAttend = "select eventid from eventid where registration_idregistration = ?";
    private final String sqlGetAccomondation = "select IDACCOMONDATION, HOTEL_IDHOTEL, ROOMMATE, FROMDATE, TODATE, DOUBLEROOM  from accomondation join REGISTRATION on ACCOMONDATION.IDACCOMONDATION = REGISTRATION.ACCOMONDATION_IDACCOMONDATION where ACCOMONDATION.IDACCOMONDATION = ?";
    private final String sqlGetAccomondationID = "select accomondation_idaccomondation from registration where idregistration = ?";

    @Autowired
    public void setDataSource(DataSource dataSource) {
        System.out.println("Database.setDataSource " + dataSource);
        this.dataSource = dataSource;
        this.jdbcTemplateObject = new JdbcTemplate(dataSource);
    }

    public ArrayList<Registration> getRegistrations(int courseID){
        ArrayList<Registration> registrations = new ArrayList<Registration>();
        try{
            registrations = (ArrayList<Registration>) jdbcTemplateObject.query(sqlGetRegistration, new Object[]{courseID}, new RegistrationMapper());
            System.out.println(registrations.size());
            for (int i = 0; i < registrations.size();i++){
                Registration r = registrations.get(i);
                System.out.println(r.toString());
                System.out.println("REGISTRATION " + r.getRegistrationID());
                Course c = courseRepositoryDB.getCourse(0);
                System.out.println("COURSE = " + c.toString());
                r.setCourse(courseRepositoryDB.getCourse(courseID));
                ArrayList<Integer> sessionIDs = (ArrayList<Integer>) jdbcTemplateObject.query(sqlGetSessionsToAttend, new Object[]{r.getRegistrationID()}, new SupportMapper());
                r.setSessionsToAttend(sessionIDs);
                ArrayList<Integer> eventIDs = (ArrayList<Integer>) jdbcTemplateObject.query(sqlGetEventsToAttend, new Object[]{r.getRegistrationID()}, new SupportMapper());
                r.setSessionsToAttend(eventIDs);
                r.setAccomondation(getAccomondation(r.getRegistrationID()));
                System.out.println(r.toString());
            }
        } catch(Exception e){
            System.out.println("Error in getRegistrations() " + e);
            registrations = null;
        }
        return registrations;
    }

    public Accomondation getAccomondation (int registrationID){
        int accomondationID = jdbcTemplateObject.queryForObject(sqlGetAccomondationID, new Object[]{registrationID}, new SupportMapper());
        return jdbcTemplateObject.queryForObject(sqlGetAccomondation, new Object[]{accomondationID}, new AccomondationMapper());
    }
}
