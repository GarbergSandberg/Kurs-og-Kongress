package repository;

import domain.*;
import mappers.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.jdbc.core.*;
import javax.sql.DataSource;
import java.util.*;

/**
 * Created by eiriksandberg on 14.04.2016.
 */
public class CourseRepositoryDB implements CourseRepository{
    private DataSource dataSource;
    JdbcTemplate jdbcTemplateObject;

    //Course sqls
    private final String sqlGetCourse = "select * from Course where idCourse = ?";
    private final String sqlGetSessions = "select * from Session where COURSE_IDCOURSE = ?";
    private final String sqlGetEvents = "select * from Event where COURSE_IDCOURSE = ?";
    private final String sqlGetCourseRoles = "select role from CourseRole where COURSE_IDCOURSE = ?";
    private final String sqlGetHotels = "select * from Hotel where COURSE_IDCOURSE = ?";
    private final String sqlGetForm = "select * from Form where COURSE_IDCOURSE = ?";
    private final String sqlGetCourseIDs = "select idcourse from course";

    private final String sqlGetOptionaPersonaliaInputParameters = "select parameter, type from INPUTPARAMETER, INPUTPARAMETER_HAS_OPTIONALPERSONALIA, OPTIONALPERSONALIA_HAS_FORM, FORM " +
            "where FORM.IDFORM = OPTIONALPERSONALIA_HAS_FORM.FORM_IDFORM AND " +
            "OPTIONALPERSONALIA_HAS_FORM.OPTIONALPERSONALIA_IDOPTIONALPERSONALIA = INPUTPARAMETER_HAS_OPTIONALPERSONALIA.OPTIONALPERSONALIA_IDOPTIONALPERSONALIA AND " +
            "INPUTPARAMETER_HAS_OPTIONALPERSONALIA.INPUTPARAMETER_IDINPUTPARAMETER = INPUTPARAMETER.IDINPUTPARAMETER and FORM_IDFORM = ?";

    private final String getSqlGetOptionaWorkplaceInputParameters = "select parameter, type from INPUTPARAMETER, INPUTPARAMETER_HAS_OPTIONALWORKPLACE, OPTIONALWORKPLACE_HAS_FORM, FORM " +
            "where FORM.IDFORM = OPTIONALWORKPLACE_HAS_FORM.FORM_IDFORM AND " +
            "OPTIONALWORKPLACE_HAS_FORM.OPTIONALWORKPLACE_IDOPTIONALWORKPLACE = INPUTPARAMETER_HAS_OPTIONALWORKPLACE.OPTIONALWORKPLACE_IDOPTIONALWORKPLACE AND " +
            "INPUTPARAMETER_HAS_OPTIONALWORKPLACE.INPUTPARAMETER_IDINPUTPARAMETER = INPUTPARAMETER.IDINPUTPARAMETER and FORM_IDFORM = ?";

    private final String getSqlGetExtraInfoInputParameters = "select parameter, type from INPUTPARAMETER, INPUTPARAMETER_HAS_EXTRAINFO, EXTRAINFO_HAS_FORM, FORM " +
            "where FORM.IDFORM = EXTRAINFO_HAS_FORM.FORM_IDFORM AND " +
            "EXTRAINFO_HAS_FORM.EXTRAINFO_IDEXTRAINFO = INPUTPARAMETER_HAS_EXTRAINFO.EXTRAINFO_IDEXTRAINFO AND " +
            "INPUTPARAMETER_HAS_EXTRAINFO.INPUTPARAMETER_IDINPUTPARAMETER = INPUTPARAMETER.IDINPUTPARAMETER and FORM_IDFORM = ?";


    //Registration sqls
    private final String sqlGetRegistration = "select * from Registration where course_idcourse = ?";
    private final String sqlGetSessionsToAttend = "select sessionid from sessionid where registration_idregistration = ?";
    private final String sqlGetEventsToAttend = "select eventid from eventid where registration_idregistration = ?";
    private final String sqlGetAccomondation = "select IDACCOMONDATION, HOTEL_IDHOTEL, ROOMMATE, FROMDATE, TODATE, DOUBLEROOM  from accomondation join REGISTRATION on ACCOMONDATION.IDACCOMONDATION = REGISTRATION.ACCOMONDATION_IDACCOMONDATION where ACCOMONDATION.IDACCOMONDATION = ?";
    private final String sqlGetAccomondationID = "select accomondation_idaccomondation from registration where idregistration = ?";
    private final String sqlGetPerson = "select * from person where idperson = ?";
    private final String sqlGetForeignKeys = "select accomondation_idaccomondation, person_idperson, workplace_idworkplace, extrainfo_idextrainfo, optionalworkplace_idoptionalworkplace, optionalpersonalia_idoptionalpersonalia from registration where idregistration = ?";
    private final String sqlGetWorkplace = "select * from workplace where idworkplace = ?";
    private final String sqlGetPayments = "select * from payment where registration_idregistration = ?";
    private final String sqlGetDates = "select date from date where registration_idregistration = ?";

    private final String sqlGetExtraInfoAnswers = "select parameter, type from INPUTPARAMETER, INPUTPARAMETER_HAS_EXTRAINFO, REGISTRATION " +
            "where REGISTRATION.EXTRAINFO_IDEXTRAINFO =INPUTPARAMETER_HAS_EXTRAINFO.EXTRAINFO_IDEXTRAINFO and " +
            "INPUTPARAMETER_HAS_EXTRAINFO.INPUTPARAMETER_IDINPUTPARAMETER = INPUTPARAMETER.IDINPUTPARAMETER and REGISTRATION.IDREGISTRATION = ?";

    private final String sqlGetOptionalPersonaliaAnswers = "select parameter, type from INPUTPARAMETER, INPUTPARAMETER_HAS_OPTIONALPERSONALIA, REGISTRATION " +
            "where REGISTRATION.OPTIONALPERSONALIA_IDOPTIONALPERSONALIA = INPUTPARAMETER_HAS_OPTIONALPERSONALIA.OPTIONALPERSONALIA_IDOPTIONALPERSONALIA and " +
            "INPUTPARAMETER_HAS_OPTIONALPERSONALIA.INPUTPARAMETER_IDINPUTPARAMETER = INPUTPARAMETER.IDINPUTPARAMETER and REGISTRATION.IDREGISTRATION = ?";

    private final String sqlGetOptionalWorkplaceAnswers = "select parameter, type from INPUTPARAMETER, INPUTPARAMETER_HAS_OPTIONALWORKPLACE, REGISTRATION " +
            "where REGISTRATION.OPTIONALWORKPLACE_IDOPTIONALWORKPLACE = INPUTPARAMETER_HAS_OPTIONALWORKPLACE.OPTIONALWORKPLACE_IDOPTIONALWORKPLACE and " +
            "INPUTPARAMETER_HAS_OPTIONALWORKPLACE.INPUTPARAMETER_IDINPUTPARAMETER = INPUTPARAMETER.IDINPUTPARAMETER and REGISTRATION.IDREGISTRATION = ?";


    @Autowired
    public void setDataSource(DataSource dataSource) {
        System.out.println("Database.setDataSource " + dataSource);
        this.dataSource = dataSource;
        this.jdbcTemplateObject = new JdbcTemplate(dataSource);
    }

    public ArrayList<Course> getCourses() {
        ArrayList<Course> courses = new ArrayList<Course>();
        List<Integer> courseIDs = jdbcTemplateObject.query(sqlGetCourseIDs, new Object[]{}, new SupportMapper());
        for (Integer i : courseIDs){
            System.out.println(i);
            courses.add(getCourse(i));
            System.out.println("Course with id " + courses.get(i).getId() + " is called " + courses.get(i).getTitle());
        }
        System.out.println("Number of courses: " + courses.size());
        return courses;
    }

    public Course getCourse(int id) {
        Course course = new Course();
        try{
            course = (Course) jdbcTemplateObject.queryForObject(sqlGetCourse, new Object[]{id}, new CourseMapper());
            course.setSessions(getSessions(id));
            course.setEvents(getEvents(id));
            course.setRoles(getRoles(id));
            course.setHotels(getHotels(id));
            course.setForm(getForm(id));
            System.out.println("Course.sessions: " + course.getSessions().toString());
            System.out.println("Course.events: " + course.getEvents().toString());
            System.out.println("Course.roles: " + course.getRoles().toString());
            System.out.println("Course.form: " + course.getForm().toString());
        } catch (Exception e){
            System.out.println("Error in getCourse() " + e);
            course = null;
        }
        System.out.println(course.toString());
        return course;
    }

    public Form getForm(int courseID){
        Form form = new Form();
        try{
            form = (Form) jdbcTemplateObject.queryForObject(sqlGetForm, new Object[]{courseID}, new FormMapper());
            form.setOptionalPersonalia(getOptionalPersonalia(form.getId()));
            form.setOptionalWorkplace(getOptionalWorkplace(form.getId()));
            form.setExtraInfo(getExtraInfo(form.getId()));
        } catch(Exception e){
            System.out.println("Error in getForm! " + e);
            form = null;
        }
        System.out.println(form.toString());
        return form;
    }

    public ArrayList<Registration> getRegistrations(int courseID){
        ArrayList<Registration> registrations = new ArrayList<Registration>();
        try{
            registrations = (ArrayList<Registration>) jdbcTemplateObject.query(sqlGetRegistration, new Object[]{courseID}, new RegistrationMapper());
            System.out.println("Registration.size = "+ registrations.size());
            Course course = getCourse(courseID);
            System.out.println(course.getForm().getOptionalPersonalia().toString());
            for (int i = 0; i < registrations.size();i++){
                Registration r = registrations.get(i);
                r.setCourse(course);
                System.out.println(r.toString());
                System.out.println("REGISTRATION " + r.getRegistrationID());
                ArrayList<Integer> sessionIDs = (ArrayList<Integer>) jdbcTemplateObject.query(sqlGetSessionsToAttend, new Object[]{r.getRegistrationID()}, new SupportMapper());
                r.setSessionsToAttend(sessionIDs);
                ArrayList<Integer> eventIDs = (ArrayList<Integer>) jdbcTemplateObject.query(sqlGetEventsToAttend, new Object[]{r.getRegistrationID()}, new SupportMapper());
                r.setSessionsToAttend(eventIDs);
                RegistrationForeignKeys foreignKeys = getForeignKeys(r.getRegistrationID());
                r.setAccomondation(getAccomondation(foreignKeys.getAccomondationID()));
                System.out.println(r.getAccomondation().toString());
                r.setPerson(getPerson(foreignKeys.getPersonID()));
                System.out.println(r.getPerson().toString());
                r.setWorkplace(getWorkplace(foreignKeys.getWorkplaceID()));
                System.out.println(r.getWorkplace().toString());
                ArrayList<Payment> payments = (ArrayList<Payment>) jdbcTemplateObject.query(sqlGetPayments, new Object[]{r.getRegistrationID()}, new PaymentMapper());
                r.setCost(payments);
                System.out.println(r.getCost().toString());
                ArrayList<Date> dates = (ArrayList<Date>) jdbcTemplateObject.query(sqlGetDates, new Object[]{r.getRegistrationID()}, new DateMapper());
                r.setDates(dates);
                System.out.println(dates.toString());
                r.setOptionalPersonalia(getOptionalPersonaliaAnswers(r.getRegistrationID()));
                System.out.println(r.getOptionalPersonalia().toString());
                r.setOptionalWorkplace(getOptionalWorkplaceAnswers(r.getRegistrationID()));
                System.out.println(r.getOptionalWorkplace().toString());
                r.setExtraInfo(getExtraInfoAnswers(r.getRegistrationID()));
                System.out.println(r.getExtraInfo().toString());
            }
        } catch(Exception e){
            System.out.println("Error in getRegistrations() " + e);
            registrations = null;
        }
        return registrations;
    }

    public Accomondation getAccomondation (int accomondationID){
        return jdbcTemplateObject.queryForObject(sqlGetAccomondation, new Object[]{accomondationID}, new AccomondationMapper());
    }

    public Person getPerson (int personID){
        return jdbcTemplateObject.queryForObject(sqlGetPerson, new Object[]{personID}, new PersonMapper());
    }

    public Workplace getWorkplace (int workplaceID){
        return jdbcTemplateObject.queryForObject(sqlGetWorkplace, new Object[]{workplaceID}, new WorkplaceMapper());
    }

    public RegistrationForeignKeys getForeignKeys (int registrationID){
        return jdbcTemplateObject.queryForObject(sqlGetForeignKeys, new Object[]{registrationID}, new RegistrationForeignKeyMapper());
    }

    public ArrayList<Session> getSessions(int courseID){
        return (ArrayList<Session>) jdbcTemplateObject.query(sqlGetSessions, new Object[]{courseID}, new SessionMapper());
    }

    public ArrayList<Event> getEvents (int courseID){
        return (ArrayList<Event>) jdbcTemplateObject.query(sqlGetEvents, new Object[]{courseID}, new EventMapper());
    }

    public ArrayList<String> getRoles (int courseID){
        return (ArrayList<String>) jdbcTemplateObject.query(sqlGetCourseRoles, new Object[]{courseID}, new CourseRoleMapper());
    }

    public ArrayList<Hotel> getHotels(int courseID){
        return (ArrayList<Hotel>) jdbcTemplateObject.query(sqlGetHotels, new Object[]{courseID}, new HotelMapper());
    }

    public ArrayList<InputParameter> getOptionalPersonalia(int formID) {
        return (ArrayList<InputParameter>) jdbcTemplateObject.query(sqlGetOptionaPersonaliaInputParameters, new Object[]{formID}, new InputParameterMapper());
    }

    public ArrayList<InputParameter> getOptionalWorkplace(int formID) {
        return (ArrayList<InputParameter>) jdbcTemplateObject.query(getSqlGetOptionaWorkplaceInputParameters, new Object[]{formID}, new InputParameterMapper());
    }

    public ArrayList<InputParameter> getExtraInfo(int formID) {
        return (ArrayList<InputParameter>) jdbcTemplateObject.query(getSqlGetExtraInfoInputParameters, new Object[]{formID}, new InputParameterMapper());
    }

    public ArrayList<InputParameter> getOptionalPersonaliaAnswers(int registrationID) {
        return (ArrayList<InputParameter>) jdbcTemplateObject.query(sqlGetOptionalPersonaliaAnswers, new Object[]{registrationID}, new InputParameterMapper());
    }

    public ArrayList<InputParameter> getOptionalWorkplaceAnswers(int registrationID) {
        return (ArrayList<InputParameter>) jdbcTemplateObject.query(sqlGetOptionalWorkplaceAnswers, new Object[]{registrationID}, new InputParameterMapper());
    }

    public ArrayList<InputParameter> getExtraInfoAnswers(int registrationID) {
        return (ArrayList<InputParameter>) jdbcTemplateObject.query(sqlGetExtraInfoAnswers, new Object[]{registrationID}, new InputParameterMapper());
    }
}
