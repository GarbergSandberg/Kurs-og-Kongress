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

    //Get Course sqls
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

    // Save course sqls
    private final String getMaxIDOptionalPersonalia = "select max(idoptionalpersonalia) from optionalpersonalia";
    private final String getMaxIDOptionalWorkplace = "select max(IDOPTIONALWORKPLACE) from OPTIONALWORKPLACE";
    private final String getMaxIDExtraInfo = "SELECT max(IDEXTRAINFO) FROM  EXTRAINFO";
    private final String getMaxIDInputParameter = "SELECT max(IDINPUTPARAMETER) FROM  INPUTPARAMETER";
    private final String setInputParameter = "insert into inputparameter values (?,?,?)";
    private final String setInputParameterHasOptionalPersonalia = "insert into inputparameter_has_optionalpersonalia values (?,?)";
    private final String setInputParameterHasOptionalWorkplace = "insert into inputparameter_has_optionalworkplace values (?,?)";
    private final String setInputParameterHasExtraInfo = "insert into inputparameter_has_extrainfo values (?,?)";
    private final String setOptionalPersonalia = "insert into optionalpersonalia values (?)";
    private final String setOptionalWorkplace = "insert into optionalworkplace values (?)";
    private final String setExtrainfo = "insert into extrainfo values (?)";
    private final String setOptionalPersonaliaHasForm = "insert into optionalpersonalia_has_form values (?,?)";
    private final String setOptionalWorkplaceHasForm = "insert into optionalworkplace_has_form values (?,?)";
    private final String setExtraInfoHasForm = "insert into extrainfo_has_form values (?,?)";
    private final String setSession = "INSERT INTO SESSION VALUES (DEFAULT ,?,?,?,?,?,?,?,?)";
    private final String setEvent = "INSERT INTO EVENT VALUES (DEFAULT,?,?,?,?,?,?,?)";
    private final String setRoles = "INSERT INTO COURSEROLE VALUES (DEFAULT,?,?)";
    private final String setHotel = "insert into hotel values (DEFAULT,?,?,?,?,?)";
    private final String setCourse = "INSERT INTO COURSE VALUES (?,?,?,?,?,?,?,?,?,?)";
    private final String setForm = "INSERT INTO FORM VALUES (?,?,?)";
    private final String getMaxIDForm = "SELECT max(idform) FROM form";
    private final String getMaxIDCourse = "select max(idcourse) from course";


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
        System.out.println("ANTALL KURS = " + courseIDs.size());
        for (Integer i : courseIDs){
            System.out.println("KURSNUMMMER " + i);
            courses.add(getCourse(i));
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
            System.out.println(course.toString());
        } catch (Exception e){
            System.out.println("Error in getCourse() " + e);
            course = null;
        }
        return course;
    }

    public Form getForm(int courseID){
        Form form = new Form();
        try{
            form = (Form) jdbcTemplateObject.queryForObject(sqlGetForm, new Object[]{courseID}, new FormMapper());
            form.setOptionalPersonalia(getOptionalPersonalia(form.getId()));
            form.setOptionalWorkplace(getOptionalWorkplace(form.getId()));
            form.setExtraInfo(getExtraInfo(form.getId()));
            System.out.println(form.toString());
        } catch(Exception e){
            System.out.println("Error in getForm! " + e);
            form = null;
        }
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

    public boolean saveCourse(Course course){
        try{
            System.out.println("Kommer hit");
            Integer courseID = jdbcTemplateObject.queryForObject(getMaxIDCourse, new Object[]{}, Integer.class);
            System.out.println(courseID);
            courseID++;
            jdbcTemplateObject.update(setCourse, new Object[]{
                    courseID, course.getTitle(), course.getLocation(), course.getDescription(), course.getStartDate(), course.getEndDate(), course.getCourseFee(), course.getCourseSingleDayFee(), course.getDayPackage(), course.getMaxNumber()
            });
            if(course.getSessions() != null){
                saveSessions(course.getSessions(), courseID);
            }
            if(course.getEvents() != null){
                saveEvents(course.getEvents(), courseID);
            }
            if(course.getRoles() != null){
                setRoles(courseID, course.getRoles());
            }
            if(course.getHotels() != null){
                setHotels(courseID, course.getHotels());
            }
            if(course.getForm() != null){
                saveForm(course.getForm(), courseID);
            }
        } catch(Exception e){
            System.out.println("Error in saveCourse() " + e);
            return false;
        }
        return true;
    }

    public boolean saveForm(Form form, int courseID){
        try{
            int id = jdbcTemplateObject.queryForObject(getMaxIDForm, new Object[]{}, Integer.class);
            System.out.println("OLD FORM ID: " + id);
            id++;
            System.out.println("NEW FORM ID: " + id);
            jdbcTemplateObject.update(setForm, new Object[]{
                    id, form.isAirplane(), courseID
            });
            saveOptionalPersonalia(form.getOptionalPersonalia(),id);
            saveOptionalWorkplace(form.getOptionalWorkplace(),id);
            saveExtraInfo(form.getExtraInfo(), id);
        } catch(Exception e){
            System.out.println("Error in saveForm() " + e);
            return false;
        }
        return true;
    }

    public boolean saveSessions(ArrayList<Session> sessions, int courseID){
        System.out.println("COURSE ID I SAVE SESSION: " + courseID);
        try{
            for (Session s : sessions){
                jdbcTemplateObject.update(setSession, new Object[]{
                        s.getTitle(), s.getDescription(), s.getDate(), s.getStartTime(), s.getEndTime(), s.getLocation(), s.getMaxnumber(), courseID
                });
            }
        }catch (Exception e){
            System.out.println("Error in saveSessions() " + e);
            return false;
        }
        return true;
    }

    public boolean saveEvents(ArrayList<Event> events, int courseID){
        try{
            for (Event e : events){
                jdbcTemplateObject.update(setEvent, new Object[]{
                        e.getTitle(), e.getPrice(), e.getMaxNumber(), e.getLocation(), e.getDate(), e.getTime(), courseID
                });
            }
        }catch (Exception e){
            System.out.println("Error in saveEvents() " + e);
            return false;
        }
        return true;
    }

    public boolean setRoles(int courseID, ArrayList<String> roles){
        try{
            for (String role : roles){
                jdbcTemplateObject.update(setRoles, new Object[]{
                        role, courseID
                });
            }
        } catch(Exception e){
            System.out.println("Error in setRoles() " + e);
            return false;
        }
        return true;
    }

    public boolean setHotels(int courseID, ArrayList<Hotel> hotels){
        try{
            for (Hotel hotel : hotels){
                jdbcTemplateObject.update(setHotel, new Object[]{
                        courseID, hotel.getName(), hotel.getDoubleprice(), hotel.getSingleprice(), hotel.getAddress()
                });
            }
        } catch(Exception e){
            System.out.println("Error in setHotels() " + e);
            return false;
        }
        return true;
    }

    public boolean saveOptionalPersonalia(ArrayList<InputParameter> list, int formID){
        try{
            Integer id = jdbcTemplateObject.queryForObject(getMaxIDOptionalPersonalia, new Object[]{}, Integer.class);
            System.out.println("OLD OPTIONALPERSONALIA ID: " + id);
            id++;
            System.out.println("NEW OPTIONALPERSONALIA ID: " + id);
            jdbcTemplateObject.update(setOptionalPersonalia, new Object[]{
                    id
            });
            int inputid = jdbcTemplateObject.queryForObject(getMaxIDInputParameter, new Object[]{}, Integer.class);
            for (InputParameter ip : list){
                System.out.println("OLD OPTIONALPERSONALIA INPUTPARAMETER ID: " + inputid);
                inputid++;
                System.out.println("NEW OPTIONALPERSONALIA INPUTPARAMETER ID: " + inputid);
                insertInputParameter(ip, inputid, id, "optionalpersonalia");
            }
            jdbcTemplateObject.update(setOptionalPersonaliaHasForm, new Object[]{
                    id,
                    formID
            });
        } catch(Exception e){
            System.out.println("Error in saveOptionalPersonalia() " + e);
            return false;
        }
        return true;
    }

    public boolean saveOptionalWorkplace(ArrayList<InputParameter> list, int formID){
        try{
            Integer id = jdbcTemplateObject.queryForObject(getMaxIDOptionalWorkplace, new Object[]{}, Integer.class);
            System.out.println("OLD OPTIONALWORKPLACE ID: " + id);
            id++;
            System.out.println("NEW OPTIONALWORKPLACE ID: " + id);
            jdbcTemplateObject.update(setOptionalWorkplace, new Object[]{
                    id
            });
            int inputid = jdbcTemplateObject.queryForObject(getMaxIDInputParameter, new Object[]{}, Integer.class);
            for (InputParameter ip : list){
                System.out.println("OLD OPTIONALWORKPLACE INPUTPARAMETER ID: " + inputid);
                inputid++;
                System.out.println("NEW OPTIONALWORKPLACE INPUTPARAMETER ID: " + inputid);
                insertInputParameter(ip, inputid, id, "optionalworkplace");
            }
            jdbcTemplateObject.update(setOptionalWorkplaceHasForm, new Object[]{
                    id,
                    formID
            });
        } catch(Exception e){
            System.out.println("Error in saveOptionalWorkplace() " + e);
            return false;
        }
        return true;
    }

    public boolean saveExtraInfo(ArrayList<InputParameter> list, int formID){
        try{
            Integer id = jdbcTemplateObject.queryForObject(getMaxIDExtraInfo, new Object[]{}, Integer.class);
            System.out.println("OLD EXTRAINFO ID: " + id);
            id++;
            System.out.println("NEW EXTRAINFO ID: " + id);
            jdbcTemplateObject.update(setExtrainfo, new Object[]{
                    id
            });
            int inputid = jdbcTemplateObject.queryForObject(getMaxIDInputParameter, new Object[]{}, Integer.class);
            for (InputParameter ip : list){
                System.out.println("OLD EXTRAINFO INPUTPARAMETER ID: " + inputid);
                inputid++;
                System.out.println("NEW  EXTRAINFOINPUTPARAMETER ID: " + inputid);
                insertInputParameter(ip, inputid, id, "extrainfo");
            }
            jdbcTemplateObject.update(setExtraInfoHasForm, new Object[]{
                    id,
                    formID
            });
        } catch(Exception e){
            System.out.println("Error in saveExtraInfo() " + e);
            return false;
        }
        return true;
    }

    public boolean insertInputParameter(InputParameter ip, int inputParameterID, int optionalTableID, String optionalTable){
        jdbcTemplateObject.update(setInputParameter, new Object[]{
                inputParameterID,
                ip.getParameter(),
                ip.getType()
        });
        if (optionalTable.equals("optionalpersonalia")){
            jdbcTemplateObject.update(setInputParameterHasOptionalPersonalia, new Object[]{
                    inputParameterID,
                    optionalTableID
            });
        }
        if (optionalTable.equals("optionalworkplace")){
            jdbcTemplateObject.update(setInputParameterHasOptionalWorkplace, new Object[]{
                    inputParameterID,
                    optionalTableID
            });
        }
        if (optionalTable.equals("extrainfo")){
            jdbcTemplateObject.update(setInputParameterHasExtraInfo, new Object[]{
                    inputParameterID,
                    optionalTableID
            });
        }
        return true;
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
