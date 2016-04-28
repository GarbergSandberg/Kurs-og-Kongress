package repository;

import domain.*;
import mappers.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.jdbc.core.*;

import javax.sql.*;
import java.lang.reflect.*;
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
    private final String sqlGetCourseRoles = "select role from CourseRole where COURSE_IDCOURSE = ? order by IDCOURSEROLE";
    private final String sqlGetHotels = "select * from Hotel where COURSE_IDCOURSE = ?";
    private final String sqlGetForm = "select * from Form where COURSE_IDCOURSE = ?";
    private final String sqlGetCourseIDs = "select idcourse from course";
    private final String sqlGetOptionaPersonaliaInputParameters = "select IDINPUTPARAMETER, parameter, type from INPUTPARAMETER, INPUTPARAMETER_HAS_OPTIONALPERSONALIA, OPTIONALPERSONALIA_HAS_FORM, FORM " +
            "where FORM.IDFORM = OPTIONALPERSONALIA_HAS_FORM.FORM_IDFORM AND " +
            "OPTIONALPERSONALIA_HAS_FORM.OPTIONALPERSONALIA_IDOPTIONALPERSONALIA = INPUTPARAMETER_HAS_OPTIONALPERSONALIA.OPTIONALPERSONALIA_IDOPTIONALPERSONALIA AND " +
            "INPUTPARAMETER_HAS_OPTIONALPERSONALIA.INPUTPARAMETER_IDINPUTPARAMETER = INPUTPARAMETER.IDINPUTPARAMETER and FORM_IDFORM = ?";
    private final String getSqlGetOptionaWorkplaceInputParameters = "select IDINPUTPARAMETER, parameter, type from INPUTPARAMETER, INPUTPARAMETER_HAS_OPTIONALWORKPLACE, OPTIONALWORKPLACE_HAS_FORM, FORM " +
            "where FORM.IDFORM = OPTIONALWORKPLACE_HAS_FORM.FORM_IDFORM AND " +
            "OPTIONALWORKPLACE_HAS_FORM.OPTIONALWORKPLACE_IDOPTIONALWORKPLACE = INPUTPARAMETER_HAS_OPTIONALWORKPLACE.OPTIONALWORKPLACE_IDOPTIONALWORKPLACE AND " +
            "INPUTPARAMETER_HAS_OPTIONALWORKPLACE.INPUTPARAMETER_IDINPUTPARAMETER = INPUTPARAMETER.IDINPUTPARAMETER and FORM_IDFORM = ?";
    private final String getSqlGetExtraInfoInputParameters = "select IDINPUTPARAMETER, parameter, type from INPUTPARAMETER, INPUTPARAMETER_HAS_EXTRAINFO, EXTRAINFO_HAS_FORM, FORM " +
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
    private final String setCourse = "INSERT INTO COURSE VALUES (?,?,?,?,?,?,?,?,?,?,?)";
    private final String setForm = "INSERT INTO FORM VALUES (?,?,?)";
    private final String getMaxIDForm = "SELECT max(idform) FROM form";
    private final String getMaxIDCourse = "select max(idcourse) from course";


    // Update course sqls
    private final String updateCourse = "update course set title = ?, location = ?, description = ?, startdate = ?, enddate = ?, coursefee = ?, coursesingledayfee = ?, daypackage = ?, maxnumber = ? where idcourse = ?";
    private final String updateSessions = "update session set title = ?, description = ?, date = ?, starttime = ?, endtime = ?, location = ?, maxnumber = ? where idsession = ?";
    private final String deleteSession = "update session set course_idcourse = null where idsession = ?";
    private final String updateEvents = "update event set title = ?, price = ?, maxnumber = ?, location = ?, date = ?, time = ? where idevent = ?";
    private final String deleteEvent = "update event set course_idcourse = null where idevent = ?";
    private final String deleteRole = "delete from courserole where idcourserole = ?";
    private final String getRoleID = "select idcourserole from courserole where course_idcourse = ? order by IDCOURSEROLE";
    private final String deleteHotel = "update hotel set course_idcourse = null where idhotel = ?";
    private final String updateHotel = "update hotel set name = ?, doubleprice = ?, singleprice = ?, address = ? where IDHOTEL = ?";
    private final String updateForm = "update form set airplane = ? where course_idcourse = ?";
    private final String deleteInputParameter = "delete from inputparameter where idinputparameter = ?";
    private final String deleteInputParameterHasOptionalPers = "delete from inputparameter_has_optionalpersonalia where inputparameter_idinputparameter = ?";
    private final String deleteInputParameterHasOptionalWork = "delete from inputparameter_has_optionalworkplace where inputparameter_idinputparameter = ?";
    private final String deleteInputParameterHasExtraInfo = "delete from inputparameter_has_extrainfo where inputparameter_idinputparameter = ?";
    private final String updateOptionalWork = "update OPTIONALWORKPLACE_HAS_FORM set OPTIONALWORKPLACE_IDOPTIONALWORKPLACE = ? where form_idForm = ?";
    private final String updateOptionalPers = "update optionalpersonalia_has_form set optionalpersonalia_idoptionalpersonalia = ? where form_idForm = ?";
    private final String updateExtra = "update EXTRAINFO_HAS_FORM set EXTRAINFO_IDEXTRAINFO = ? where form_idForm = ?";
    private final String getOptPersID = "select OPTIONALPERSONALIA_IDOPTIONALPERSONALIA from OPTIONALPERSONALIA_HAS_FORM where FORM_IDFORM = ?";
    private final String getOptWorkID = "select OPTIONALWORKPLACE_IDOPTIONALWORKPLACE From OPTIONALWORKPLACE_HAS_FORM WHERE FORM_IDFORM = ?";
    private final String getExtraID = "SELECT EXTRAINFO_IDEXTRAINFO from EXTRAINFO_HAS_FORM WHERE FORM_IDFORM = ?";
    private final String enableRegistration = "update course set publiccourse = ? where idcourse = ?";

    //Registration sqls
    private final String sqlGetRegistration = "select * from Registration where course_idcourse = ?";
    private final String sqlGetSingleRegistration = "select * from Registration where idregistration = ?";
    private final String sqlGetSessionsToAttend = "select sessionid from sessionid where registration_idregistration = ?";
    private final String sqlGetEventsToAttend = "select eventid from eventid where registration_idregistration = ?";
    private final String sqlGetAccomondation = "select IDACCOMONDATION, HOTEL_IDHOTEL, ROOMMATE, FROMDATE, TODATE, DOUBLEROOM  from accomondation join REGISTRATION on ACCOMONDATION.IDACCOMONDATION = REGISTRATION.ACCOMONDATION_IDACCOMONDATION where ACCOMONDATION.IDACCOMONDATION = ?";
    private final String sqlGetAccomondationID = "select accomondation_idaccomondation from registration where idregistration = ?";
    private final String sqlGetPerson = "select * from person where idperson = ?";
    private final String sqlGetForeignKeys = "select course_idcourse, accomondation_idaccomondation, person_idperson, workplace_idworkplace, extrainfo_idextrainfo, optionalworkplace_idoptionalworkplace, optionalpersonalia_idoptionalpersonalia from registration where idregistration = ?";
    private final String sqlGetWorkplace = "select * from workplace where idworkplace = ?";
    private final String sqlGetPayments = "select * from payment where registration_idregistration = ?";
    private final String sqlGetNumberOfPayments = "select count(*) from payment where REGISTRATION_IDREGISTRATION = ? and DESCRIPTION = ?";
    private final String sqlGetNumberOfEvents = "select count(REGISTRATION_IDREGISTRATION) from eventid where EVENTID = ?";
    private final String sqlGetDates = "select date from date where registration_idregistration = ?";
    private final String sqlGetCountRegistrations = "select count(*) from REGISTRATION where course_idcourse = ?";
    private final String sqlGetExtraInfoAnswers = "select IDINPUTPARAMETER, parameter, type from INPUTPARAMETER, INPUTPARAMETER_HAS_EXTRAINFO, REGISTRATION " +
            "where REGISTRATION.EXTRAINFO_IDEXTRAINFO =INPUTPARAMETER_HAS_EXTRAINFO.EXTRAINFO_IDEXTRAINFO and " +
            "INPUTPARAMETER_HAS_EXTRAINFO.INPUTPARAMETER_IDINPUTPARAMETER = INPUTPARAMETER.IDINPUTPARAMETER and REGISTRATION.IDREGISTRATION = ?";
    private final String sqlGetOptionalPersonaliaAnswers = "select IDINPUTPARAMETER, parameter, type from INPUTPARAMETER, INPUTPARAMETER_HAS_OPTIONALPERSONALIA, REGISTRATION " +
            "where REGISTRATION.OPTIONALPERSONALIA_IDOPTIONALPERSONALIA = INPUTPARAMETER_HAS_OPTIONALPERSONALIA.OPTIONALPERSONALIA_IDOPTIONALPERSONALIA and " +
            "INPUTPARAMETER_HAS_OPTIONALPERSONALIA.INPUTPARAMETER_IDINPUTPARAMETER = INPUTPARAMETER.IDINPUTPARAMETER and REGISTRATION.IDREGISTRATION = ?";
    private final String sqlGetOptionalWorkplaceAnswers = "select IDINPUTPARAMETER, parameter, type from INPUTPARAMETER, INPUTPARAMETER_HAS_OPTIONALWORKPLACE, REGISTRATION " +
            "where REGISTRATION.OPTIONALWORKPLACE_IDOPTIONALWORKPLACE = INPUTPARAMETER_HAS_OPTIONALWORKPLACE.OPTIONALWORKPLACE_IDOPTIONALWORKPLACE and " +
            "INPUTPARAMETER_HAS_OPTIONALWORKPLACE.INPUTPARAMETER_IDINPUTPARAMETER = INPUTPARAMETER.IDINPUTPARAMETER and REGISTRATION.IDREGISTRATION = ?";

    // saveRegistration sqls
    private final String setRegistration = "insert into registration values (?,?,?,?,?,?,?,?,?,?,?)";
    private final String setSessionsToAttend = "insert into sessionid values (default,?,?)";
    private final String setEventsToAttend = "insert into EVENTID values (default,?,?)";
    private final String setAccomondation = "insert into accomondation values(?,?,?,?,?,?)";
    private final String setPerson = "insert into person values (?,?,?,?,?,?,?,?)";
    private final String setWorkplace = "insert into workplace values(?,?,?,?,?)";
    private final String setPayment = "insert into PAYMENT VALUES (DEFAULT,?,?,?)";
    private final String setDate = "INSERT INTO DATE VALUES (DEFAULT, ?,?)";
    private final String getMaxRegistrationID = "select max(idregistration) from registration";
    private final String getMaxAccomondationID = "select max(idaccomondation) from accomondation";
    private final String getMaxPersonID = "select max(idperson) from person";
    private final String getMaxWorkplaceID= "select max(idworkplace) from workplace";

    // updateRegistration sqls
    private final String deleteOldSessionIDs = "delete from sessionid where registration_idregistration = ?";
    private final String deleteOldEventIDs = "delete from EVENTID where REGISTRATION_IDREGISTRATION = ?";
    private final String updateAccomondation = "update accomondation set roommate = ?, fromdate = ?, todate = ?, doubleroom = ?, Hotel_IDHOTEL = ? where idaccomondation = ?";
    private final String deleteAccomondation = "update registration set ACCOMONDATION_IDACCOMONDATION = null where IDREGISTRATION = ?";
    private final String updatePerson = "update person set firstname = ?, lastname = ?, birthyear = ?, phonenumber = ?, email = ?, gender = ?, mark = ? where idperson = ?";
    private final String updateWorkplace = "update workplace set companyname = ?, postalcode = ?, location = ?, address = ? where idworkplace = ?";
    private final String deletePayments = "delete from payment where registration_idregistration = ?";
    private final String deleteDates = "delete from date where REGISTRATION_IDREGISTRATION = ?";
    private final String updateInputParameterAnswer = "update INPUTPARAMETER set parameter = ?, TYPE = ? where IDINPUTPARAMETER = ?";
    private final String updateRegistration = "update registration set alternativeinvoiceaddress = ?, speaker = ?, role = ? where idregistration = ?";
    private final String updateRegNewAccomondation = "update registration set accomondation_idaccomondation = ? where idregistration = ?";



    // Other
    private final String getNumberOfParticipantsSession = "select count(registration_idregistration) from sessionID where sessionid = ?";
    private final String getNumberOfParticipantsEvent = "select count(registration_idregistration) from eventID where eventid = ?";


    @Autowired
    public void setDataSource(DataSource dataSource) {
        System.out.println("Database.setDataSource " + dataSource);
        this.dataSource = dataSource;
        this.jdbcTemplateObject = new JdbcTemplate(dataSource);
    }

    public boolean enableRegistration(int courseID, boolean value){
        try{
            jdbcTemplateObject.update(enableRegistration, new Object[]{
                    value, courseID
            });
            return true;
        } catch(Exception e){
            System.out.println("Error in enableRegistration");
            return false;
        }
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
                System.out.println("SessionsToAttend " + sessionIDs.toString());
                ArrayList<Integer> eventIDs = (ArrayList<Integer>) jdbcTemplateObject.query(sqlGetEventsToAttend, new Object[]{r.getRegistrationID()}, new SupportMapper());
                r.setEventsToAttend(eventIDs);
                System.out.println("EventsToAttend " + eventIDs.toString());
                RegistrationForeignKeys foreignKeys = getForeignKeys(r.getRegistrationID());
                int accomondationID = foreignKeys.getAccomondationID();
                if (accomondationID != 0){
                    r.setAccomondation(getAccomondation(foreignKeys.getAccomondationID()));
                }
                System.out.println("ACCOMONDATION ID = " + foreignKeys.getAccomondationID());
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

    public Registration getRegistration(int registrationID){
        Registration r = new Registration();
        try{
            r = jdbcTemplateObject.queryForObject(sqlGetSingleRegistration, new Object[]{registrationID}, new RegistrationMapper());
            RegistrationForeignKeys foreignKeys = getForeignKeys(r.getRegistrationID());
            Course course = getCourse(foreignKeys.getCourseID());
            r.setCourse(course);
            System.out.println(r.toString());
            System.out.println("REGISTRATION " + r.getRegistrationID());
            ArrayList<Integer> sessionIDs = (ArrayList<Integer>) jdbcTemplateObject.query(sqlGetSessionsToAttend, new Object[]{r.getRegistrationID()}, new SupportMapper());
            r.setSessionsToAttend(sessionIDs);
            System.out.println("SessionsToAttend " + sessionIDs.toString());
            ArrayList<Integer> eventIDs = (ArrayList<Integer>) jdbcTemplateObject.query(sqlGetEventsToAttend, new Object[]{r.getRegistrationID()}, new SupportMapper());
            r.setEventsToAttend(eventIDs);
            System.out.println("EventsToAttend " + eventIDs.toString());
            int accomondationID = foreignKeys.getAccomondationID();
            if (accomondationID != 0){
                if (getAccomondation(foreignKeys.getAccomondationID()).getHotelID() != -2){ // The accomondation is "deleted".
                    r.setAccomondation(getAccomondation(foreignKeys.getAccomondationID()));
                    System.out.println("ACCOMONDATION ID = " + foreignKeys.getAccomondationID());
                } else System.out.println("Accomondation eksisterer egentlig ikke.. ACCOMONDATION ID = " + foreignKeys.getAccomondationID());
            }
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
            System.out.println(r);
        } catch(Exception e){
            System.out.println("Error in getRegistrations() " + e);
            r = null;
        }
        return r;
    }

    public boolean saveRegistration(Registration registration){
        try{
            Integer personID = setPerson(registration.getPerson());
            Integer workplaceID = setWorkplace(registration.getWorkplace());
            Integer accomondationID = setAccomondation(registration.getAccomondation());
            Integer optionalPersonaliaID = saveOptionalPersonaliaAnswers(registration.getOptionalPersonalia());
            Integer optionalWorkplaceID = saveOptionalWorkplaceAnswers(registration.getOptionalWorkplace());
            Integer extraInfoID = saveExtraInfoAnswers(registration.getExtraInfo());
            Integer registrationID = jdbcTemplateObject.queryForObject(getMaxRegistrationID, new Object[]{}, Integer.class);
            if(registrationID != null){
                registrationID++;
            } else{
                registrationID = 1;
            }
            jdbcTemplateObject.update(setRegistration, new Object[]{
                    registrationID,
                    registration.getAlternativeInvoiceAddress(), registration.isSpeaker(),
                    registration.getRole(), registration.getCourse().getId(),
                    accomondationID,
                    personID,
                    workplaceID,
                    extraInfoID,
                    optionalWorkplaceID,
                    optionalPersonaliaID
            });
            setSessionsToAttend(registration.getSessionsToAttend(), registrationID);
            setEventsToAttend(registration.getEventsToAttend(), registrationID);
            setDates(registration.getDates(), registrationID);
            for (Payment p : registration.getCost()){
                setPayment(p, registrationID);
            }
        } catch (Exception e){
            System.out.println("Error in saveRegistration() " + e);
            return false;
        }
        return true;
    }

    public boolean updateRegistration(Registration registration){
        try{
            if (registration.getRegistrationID() != -1){
                jdbcTemplateObject.update(updateRegistration, new Object[]{
                        registration.getAlternativeInvoiceAddress(), registration.isSpeaker(), registration.getRole(), registration.getRegistrationID()
                });
                if(registration.getSessionsToAttend() != null){
                    System.out.println(registration.getSessionsToAttend());
                    updateSessionsToAttend(registration.getSessionsToAttend(), registration.getRegistrationID());
                }
                if(registration.getEventsToAttend() != null){
                    System.out.println(registration.getEventsToAttend());
                    updateEventsToAttend(registration.getEventsToAttend(), registration.getRegistrationID());
                }

                System.out.println(registration.getAccomondation());
                if(registration.getAccomondation() != null){
                    if (registration.getAccomondation().getId() == 0){ // new or delete Accomondation
                        if (registration.getAccomondation().getFromDate() == null && registration.getAccomondation().getToDate() == null){
                            System.out.println("//////////// ************* Sletter accomondation.. ");
                            deleteAccomondation(registration.getRegistrationID());
                        } else {
                            System.out.println("Legger til HELT NY accomondation. ");
                            Integer accomondationID = setAccomondation(registration.getAccomondation());
                            setRegistrationNewAccomondation(accomondationID, registration.getRegistrationID());
                        }
                    } else { // update old accomondation.
                        System.out.println("Oppdaterer gammel accomondation..");
                        updateAccomondation(registration.getAccomondation()
                        );
                    }
                }
                if(registration.getPerson() != null){
                    updatePerson(registration.getPerson());
                }
                if(registration.getWorkplace() != null){
                    updateWorkplace(registration.getWorkplace());
                }
                if(registration.getCost() != null){
                    updatePayment(registration.getCost(), registration.getRegistrationID());
                }
                if(registration.getDates() != null){
                    updateDates(registration.getDates(), registration.getRegistrationID());
                }
                if(registration.getOptionalPersonalia() != null){
                    updateOptionalPersonaliaAnswers(registration.getOptionalPersonalia(), registration.getRegistrationID());
                }
                if(registration.getOptionalWorkplace() != null){
                    updateOptionalWorkplaceAnswers(registration.getOptionalWorkplace(), registration.getRegistrationID());
                }
                if(registration.getExtraInfo() != null){
                    updateExtraInfoAnswers(registration.getExtraInfo(), registration.getRegistrationID());
                }
            } else{
                System.out.println("/////////////////////////////////   Tries to update course with id = -1. Try add it instead");
            }
        } catch(Exception e){
            System.out.println("Error in update registration " + e);
            return false;
        }
        return true;
    }


    public boolean saveCourse(Course course){
        try{
            System.out.println("ID TIL KURS FRA SERVER ER " + course.getId());
            if(course.getId() != -1){
                updateCourse(course);
            } else{
                Integer courseID = jdbcTemplateObject.queryForObject(getMaxIDCourse, new Object[]{}, Integer.class);
                if(courseID != null){
                    System.out.println(courseID);
                    courseID++;
                } else{
                    courseID = 1;
                }
                jdbcTemplateObject.update(setCourse, new Object[]{
                        courseID, course.getTitle(), course.getLocation(), course.getDescription(), course.getStartDate(), course.getEndDate(), course.getCourseFee(), course.getCourseSingleDayFee(), course.getDayPackage(), course.getMaxNumber(), course.isPublicCourse()
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
            }
        } catch(Exception e){
            System.out.println("Error in saveCourse() " + e);
            return false;
        }
        return true;
    }

    public boolean updateCourse(Course course){
        try{
            jdbcTemplateObject.update(updateCourse, new Object[]{
                    course.getTitle(), course.getLocation(), course.getDescription(), course.getStartDate(), course.getEndDate(), course.getCourseFee(), course.getCourseSingleDayFee(), course.getDayPackage(), course.getMaxNumber(), course.getId()
            });
            if(course.getSessions() != null){
                updateSessions(course.getSessions(), course.getId());
            }
            if(course.getEvents() != null){
                updateEvents(course.getEvents(), course.getId());
            }
            if(course.getRoles() != null){
                updateRoles(course.getId(), course.getRoles());
            }
            if(course.getHotels() != null){
                updateHotels(course.getId(), course.getHotels());
            }
            if(course.getForm() != null){
                updateForm(course.getForm(), course.getId());
            }
        }catch (Exception e){
            System.out.println("Error in updateCourse! " + e);
            return false;
        }
        return true;
    }

    public boolean saveForm(Form form, int courseID){
        try{
            Integer id = jdbcTemplateObject.queryForObject(getMaxIDForm, new Object[]{}, Integer.class);
            if(id != null){
                id++;
            } else{
                id = 1;
            }
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

    public boolean updateForm(Form form, int courseID){
        try{
            jdbcTemplateObject.update(updateForm, new Object[]{
                    form.isAirplane(), courseID
            });
            int optionalPersonaliaID = jdbcTemplateObject.queryForObject(getOptPersID, new Object[]{form.getId()}, Integer.class);
            int optionalWorkplaceID = jdbcTemplateObject.queryForObject(getOptWorkID, new Object[]{form.getId()}, Integer.class);
            int extraID = jdbcTemplateObject.queryForObject(getExtraID, new Object[]{form.getId()}, Integer.class);
            updateOptionalPersonalia(form.getOptionalPersonalia(), form.getId(), optionalPersonaliaID);
            updateOptionalWorkplace(form.getOptionalWorkplace(), form.getId(), optionalWorkplaceID);
            updateExtraInfo(form.getExtraInfo(), form.getId(), extraID);
        } catch(Exception e){
            System.out.println("Error in updateForm() " + e);
            return false;
        }
        return true;
    }

    public boolean saveSessions(ArrayList<Session> sessions, int courseID){
        try{
            for (Session s : sessions){
                jdbcTemplateObject.update(setSession, new Object[]{
                        s.getTitle(), s.getDescription(), s.getDate(), s.getStartTime(), s.getEndTime(), s.getLocation(), s.getMaxnumber(), courseID
                });
                System.out.println(s.getTitle() + " is saved (session)");
            }
        }catch (Exception e){
            System.out.println("Error in saveSessions() " + e);
            return false;
        }
        return true;
    }

    public boolean updateSessions(ArrayList<Session> sessions, int courseID){
        try{
            ArrayList<Session> sessionsToBeDeleted = getSessions(courseID);
            System.out.println("sessionsToBeDeleted.size() from DB " + sessionsToBeDeleted.size());
            for (int i = 0; i < sessions.size(); i++){
                for (int u = 0; u < sessionsToBeDeleted.size(); u++){
                    if(sessions.get(i).getId() == sessionsToBeDeleted.get(u).getId()){
                        sessionsToBeDeleted.remove(u);
                    }
                }
            }
            System.out.println("sessionsToBeDeleted.size() after removal " + sessionsToBeDeleted.size());
            if(!sessionsToBeDeleted.isEmpty()){
                System.out.println("SessionsToBeDeleted is not empty");
                for(Session s : sessionsToBeDeleted){
                    System.out.println("Deleting session " +  s.getTitle() + " with id " + s.getId());
                    jdbcTemplateObject.update(deleteSession, new Object[]{
                            s.getId()
                    });
                }
            }
            ArrayList<Session> sessionsToBeSaved = new ArrayList<Session>();
            for (Session s : sessions){
                if(s.getId() != -1){
                    jdbcTemplateObject.update(updateSessions, new Object[]{
                            s.getTitle(), s.getDescription(), s.getDate(), s.getStartTime(), s.getEndTime(), s.getLocation(), s.getMaxnumber(), s.getId()
                    });
                    System.out.println(s.getTitle() + " is updated (session)"
                    );
                } else{
                    sessionsToBeSaved.add(s);
                }
            }
            if (!sessionsToBeSaved.isEmpty()){
                System.out.println("Courseid i sessionsToBeSaved " + courseID);
                saveSessions(sessionsToBeSaved, courseID);
            }
        }catch (Exception e){
            System.out.println("Error in updateSessions() " + e);
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
                System.out.println(e.getTitle() + " is saved (event)");
            }
        }catch (Exception e){
            System.out.println("Error in saveEvents() " + e);
            return false;
        }
        return true;
    }

    public boolean updateEvents(ArrayList<Event> events, int courseID){
        try{
            ArrayList<Event> eventsToBeDeleted = getEvents(courseID);
            System.out.println("eventsToBeDeleted.size() from DB " + eventsToBeDeleted.size());
            for (int i = 0; i < events.size(); i++){
                for (int u = 0; u < eventsToBeDeleted.size(); u++){
                    if(events.get(i).getId() == eventsToBeDeleted.get(u).getId()){
                        eventsToBeDeleted.remove(u);
                    }
                }
            }
            System.out.println("eventsToBeDeleted.size() after removal " + eventsToBeDeleted.size());
            if(!eventsToBeDeleted.isEmpty()){
                System.out.println("EventsToBeDeleted is not empty");
                for(Event e : eventsToBeDeleted){
                    System.out.println("Deleting event " + e.getTitle() + " with id " + e.getId());
                    jdbcTemplateObject.update(deleteEvent, new Object[]{
                            e.getId()
                    });
                }
            }
            ArrayList<Event> eventsToBeSaved = new ArrayList<Event>();
            for (Event e : events){
                if(e.getId() != -1){
                    jdbcTemplateObject.update(updateEvents, new Object[]{
                            e.getTitle(), e.getPrice(), e.getMaxNumber(), e.getLocation(), e.getDate(), e.getTime(), e.getId()
                    });
                    System.out.println(e.getTitle() + " is updated (event)");
                } else{
                    eventsToBeSaved.add(e);
                }
            }
            if (!eventsToBeSaved.isEmpty()){
                saveEvents(eventsToBeSaved, courseID);
            }
        }catch (Exception e){
            System.out.println("Error in updateEvents() " + e);
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

    public boolean updateRoles(int courseID, ArrayList<String> newRoles){
        try{
            ArrayList<String> toBeDeleted = new ArrayList<String>();
            ArrayList<String> oldRoles = getRoles(courseID);
            ArrayList<Integer> oldRoleIDs = (ArrayList<Integer>) jdbcTemplateObject.query(getRoleID, new Object[]{courseID}, new SupportMapper());
            for (int i = 0; i < newRoles.size(); i++){
                for (int u = 0; u < oldRoles.size(); u++){
                    if(newRoles.get(i).equals(oldRoles.get(u))){
                        newRoles.remove(i);
                        oldRoles.remove(u);
                        oldRoleIDs.remove(u);
                    }
                }
            }
            if(!newRoles.isEmpty()){
                System.out.println("New roles is not empty. Setting new roles");
                setRoles(courseID, newRoles);
            }
            if(!oldRoles.isEmpty()){
                System.out.println("Old roles is not empty. deleting old roles");
                for(int i = 0; i < oldRoles.size(); i++){
                    jdbcTemplateObject.update(deleteRole, new Object[]{
                            oldRoleIDs.get(i)
                    });
                }
            }
        }catch (Exception e){
            System.out.println("Error in updateRoles() " + e);
            return false;
        }
        return true;
    }

    public boolean setHotels(int courseID, ArrayList<Hotel> hotels){
        try{
            for (Hotel hotel : hotels){
                jdbcTemplateObject.update(setHotel, new Object[]{
                        hotel.getName(), hotel.getDoubleprice(), hotel.getSingleprice(), hotel.getAddress(), courseID
                });
            }
        } catch(Exception e){
            System.out.println("Error in setHotels() " + e);
            return false;
        }
        return true;
    }

    public boolean updateHotels(int courseID, ArrayList<Hotel> hotels){
        try{
            ArrayList<Hotel> hotelsToBeDeleted = getHotels(courseID);
            for (int i = 0; i < hotels.size(); i++){
                for (int u = 0; u < hotelsToBeDeleted.size(); u++){
                    if(hotels.get(i).getId() == hotelsToBeDeleted.get(u).getId()){
                        hotelsToBeDeleted.remove(u);
                    }
                }
            }
            if(!hotelsToBeDeleted.isEmpty()){
                System.out.println("HotelsToBeDeleted is not empty");
                for(Hotel h : hotelsToBeDeleted){
                    System.out.println("Deleting hotel " + h.getName() + " with id " + h.getId());
                    jdbcTemplateObject.update(deleteHotel, new Object[]{
                            h.getId()
                    });
                }
            }
            ArrayList<Hotel> hotelsToBeAdded = new ArrayList<Hotel>();
            for (Hotel hotel : hotels){
                if (hotel.getId() != -1){
                    jdbcTemplateObject.update(updateHotel, new Object[]{
                            hotel.getName(), hotel.getDoubleprice(), hotel.getSingleprice(), hotel.getAddress(), hotel.getId()
                    });
                } else{
                    System.out.println("New hotel detected! Adding hotel " + hotel.getName());
                    hotelsToBeAdded.add(hotel);
                }
            }
            if(!hotelsToBeAdded.isEmpty()){
                setHotels(courseID, hotelsToBeAdded);
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
            if(id != null){
                id++;
            } else{
                id = 1;
            }
            jdbcTemplateObject.update(setOptionalPersonalia, new Object[]{
                    id
            });
            Integer inputid = jdbcTemplateObject.queryForObject(getMaxIDInputParameter, new Object[]{}, Integer.class);
            if(inputid == null){
                inputid = 0;
            }
            for (InputParameter ip : list){
                inputid++;
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

    public boolean updateOptionalPersonalia(ArrayList<InputParameter> newParameters, int formID, int optionalPersonaliaID){
        try{
            ArrayList<InputParameter> inputParametersToBeDeleted = getOptionalPersonalia(formID);
            ArrayList<InputParameter> inputParametersToBeAdded = new ArrayList<InputParameter>();
            System.out.println("Inputparameterstobedeleted before removal = " + inputParametersToBeDeleted.size());
            for (int i = 0; i < newParameters.size(); i++){
                boolean isNewIP = true;
                for (int u = 0; u < inputParametersToBeDeleted.size(); u++){
                    if(newParameters.get(i).getId() == inputParametersToBeDeleted.get(u).getId()){
                        System.out.println("Parameter to be ignored: " + newParameters.get(u).getParameter() + " and " + inputParametersToBeDeleted.get(u));
                        inputParametersToBeDeleted.remove(u);
                        isNewIP = false;
                    }
                }
                if(isNewIP){
                    inputParametersToBeAdded.add(newParameters.get(i));
                }
            }
            System.out.println("Inputparameterstobedeleted after removal = " + inputParametersToBeDeleted.size());
            if(!inputParametersToBeDeleted.isEmpty()){
                System.out.println("inputParametersToBeDeleted is not empty");
                for(InputParameter ip : inputParametersToBeDeleted){
                    System.out.println("Deleting parameter " + ip.getParameter() + " with id " + ip.getId());
                    jdbcTemplateObject.update(deleteInputParameterHasOptionalPers, new Object[]{
                            ip.getId()
                    });
                    jdbcTemplateObject.update(deleteInputParameter, new Object[]{
                            ip.getId()
                    });
                }
            }
            Integer inputid = jdbcTemplateObject.queryForObject(getMaxIDInputParameter, new Object[]{}, Integer.class);
            if(inputid == null){
                inputid = 0;
            }
            for (InputParameter ip : inputParametersToBeAdded){
                inputid++;
                insertInputParameter(ip, inputid, optionalPersonaliaID, "optionalpersonalia");
            }
        } catch (Exception e){
            System.out.println("Error in updateOptionalPersonalia");
            return false;
        }
        return true;
    }

    public boolean saveOptionalWorkplace(ArrayList<InputParameter> list, int formID){
        try{
            Integer id = jdbcTemplateObject.queryForObject(getMaxIDOptionalWorkplace, new Object[]{}, Integer.class);
            if(id != null){
                id++;
            } else{
                id = 1;
            }
            jdbcTemplateObject.update(setOptionalWorkplace, new Object[]{
                    id
            });
            Integer inputid = jdbcTemplateObject.queryForObject(getMaxIDInputParameter, new Object[]{}, Integer.class);
            if(inputid == null){
                inputid = 0;
            }
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

    public boolean updateOptionalWorkplace(ArrayList<InputParameter> newParameters, int formID, int optionalWorkplaceID){
        try{
            ArrayList<InputParameter> inputParametersToBeDeleted = getOptionalWorkplace(formID);
            ArrayList<InputParameter> inputParametersToBeAdded = new ArrayList<InputParameter>();
            System.out.println("Inputparameterstobedeleted before removal (Workplace) = " + inputParametersToBeDeleted.size());
            for (int i = 0; i < newParameters.size(); i++){
                boolean isNewIP = true;
                for (int u = 0; u < inputParametersToBeDeleted.size(); u++){
                    if(newParameters.get(i).getId() == inputParametersToBeDeleted.get(u).getId()){
                        System.out.println("Parameter to be ignored: " + newParameters.get(u).getParameter() + " and " + inputParametersToBeDeleted.get(u));
                        inputParametersToBeDeleted.remove(u);
                        isNewIP = false;
                    }
                }
                if(isNewIP){
                    inputParametersToBeAdded.add(newParameters.get(i));
                }
            }
            System.out.println("Inputparameterstobedeleted after removal = " + inputParametersToBeDeleted.size());
            if(!inputParametersToBeDeleted.isEmpty()){
                System.out.println("inputParametersToBeDeleted is not empty");
                for(InputParameter ip : inputParametersToBeDeleted){
                    System.out.println("Deleting parameter " + ip.getParameter() + " with id " + ip.getId());
                    jdbcTemplateObject.update(deleteInputParameterHasOptionalWork, new Object[]{
                            ip.getId()
                    });
                    jdbcTemplateObject.update(deleteInputParameter, new Object[]{
                            ip.getId()
                    });
                }
            }
            Integer inputid = jdbcTemplateObject.queryForObject(getMaxIDInputParameter, new Object[]{}, Integer.class);
            if(inputid == null){
                inputid = 0;
            }
            System.out.println("Inputparameters to be added.size() " + inputParametersToBeAdded.size());
            for (InputParameter ip : inputParametersToBeAdded){
                inputid++;
                System.out.println("Adding new inputparameter " + ip.toString());
                insertInputParameter(ip, inputid, optionalWorkplaceID, "optionalworkplace");
            }
        } catch (Exception e){
            System.out.println("Error in updateOptionalWorkplace");
            return false;
        }
        return true;
    }

    public boolean saveExtraInfo(ArrayList<InputParameter> list, int formID){
        try{
            Integer id = jdbcTemplateObject.queryForObject(getMaxIDExtraInfo, new Object[]{}, Integer.class);
            if(id != null){
                id++;
            } else{
                id = 1;
            }
            jdbcTemplateObject.update(setExtrainfo, new Object[]{
                    id
            });
            Integer inputid = jdbcTemplateObject.queryForObject(getMaxIDInputParameter, new Object[]{}, Integer.class);
            if(inputid == null){
                inputid = 0;
            }
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

    public boolean updateExtraInfo(ArrayList<InputParameter> newParameters, int formID, int ExtraInfoID){
        try{
            ArrayList<InputParameter> inputParametersToBeDeleted = getExtraInfo(formID);
            ArrayList<InputParameter> inputParametersToBeAdded = new ArrayList<InputParameter>();
            System.out.println("Inputparameterstobedeleted before removal (ExtraInfo) = " + inputParametersToBeDeleted.size());
            for (int i = 0; i < newParameters.size(); i++){
                boolean isNewIP = true;
                for (int u = 0; u < inputParametersToBeDeleted.size(); u++){
                    if(newParameters.get(i).getId() == inputParametersToBeDeleted.get(u).getId()){
                        System.out.println("Parameter to be ignored: " + newParameters.get(u).getParameter() + " and " + inputParametersToBeDeleted.get(u));
                        inputParametersToBeDeleted.remove(u);
                        isNewIP = false;
                    }
                }
                if(isNewIP){
                    inputParametersToBeAdded.add(newParameters.get(i));
                }
            }
            System.out.println("Inputparameterstobedeleted after removal = " + inputParametersToBeDeleted.size());
            if(!inputParametersToBeDeleted.isEmpty()){
                System.out.println("inputParametersToBeDeleted is not empty");
                for(InputParameter ip : inputParametersToBeDeleted){
                    System.out.println("Deleting parameter " + ip.getParameter() + " with id " + ip.getId());
                    jdbcTemplateObject.update(deleteInputParameterHasExtraInfo, new Object[]{
                            ip.getId()
                    });
                    jdbcTemplateObject.update(deleteInputParameter, new Object[]{
                            ip.getId()
                    });
                }
            }
            Integer inputid = jdbcTemplateObject.queryForObject(getMaxIDInputParameter, new Object[]{}, Integer.class);
            if(inputid == null){
                inputid = 0;
            }
            System.out.println("Inputparameters to be added.size() " + inputParametersToBeAdded.size());
            for (InputParameter ip : inputParametersToBeAdded){
                inputid++;
                System.out.println("Adding new inputparameter " + ip.toString());
                insertInputParameter(ip, inputid, ExtraInfoID, "extrainfo");
            }
        } catch (Exception e){
            System.out.println("Error in updateExtraInfo");
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

    public int getCountRegistrations(int courseId){
        int i = 0;
        try {
            i = (int)jdbcTemplateObject.queryForObject(sqlGetCountRegistrations, new Object[]{courseId}, Integer.class);
            System.out.println("******************************************************************" + i);
        } catch (Exception e) {
            System.out.println("Feil i getCountReg");
        }
        return i;
    }

    public boolean setSessionsToAttend(ArrayList<Integer> list, int registrationID){
        try{
            for (Integer i : list){
                System.out.println("Sesjon nr " + i + " skal være lagt til.");
                jdbcTemplateObject.update(setSessionsToAttend, new Object[]{i, registrationID});
            }
        }catch (Exception e){
            System.out.println("Error in setSessionsToAttend() " + e);
            return false;
        }
        return true;
    }

    public boolean updateSessionsToAttend(ArrayList<Integer> list, int registrationID){
        try{
            jdbcTemplateObject.update(deleteOldSessionIDs, new Object[]{registrationID});
            setSessionsToAttend(list, registrationID);
        } catch(Exception e){
            System.out.println("Error in updateSessions() " + e);
            return false;
        }
        return true;
    }

    public boolean setEventsToAttend(ArrayList<Integer> list, int registrationID){
        try{
            for (Integer i : list)
                jdbcTemplateObject.update(setEventsToAttend, new Object[]{
                        i, registrationID
                });
        }catch (Exception e){
            System.out.println("Error in setEventsToAttend() " + e);
            return false;
        }
        return true;
    }

    public boolean updateEventsToAttend(ArrayList<Integer> list, int registrationID){
        try{
            jdbcTemplateObject.update(deleteOldEventIDs, new Object[]{
                    registrationID
            });
            setEventsToAttend(list, registrationID);
        } catch(Exception e){
            System.out.println("Error in updateEvents() " + e);
            return false;
        }
        return true;
    }

    public Integer setAccomondation(Accomondation accomondation){
        if (accomondation != null){
            try{
                Integer accomondationID = jdbcTemplateObject.queryForObject(getMaxAccomondationID, new Object[]{}, Integer.class);
                if(accomondationID != null){
                    accomondationID++;
                } else{
                    accomondationID = 1;
                }
                jdbcTemplateObject.update(setAccomondation, new Object[]{
                        accomondationID, accomondation.getRoommate(), accomondation.getFromDate(), accomondation.getToDate(), accomondation.isDoubleroom(), accomondation.getHotelID()
                });
                return accomondationID;
            } catch(Exception e){
                System.out.println("Error in setAccomondation() " + e);
                return null;
            }
        } else return null;
    }


    public boolean updateAccomondation(Accomondation accomondation){
        try{
            jdbcTemplateObject.update(updateAccomondation, new Object[]{
                    accomondation.getRoommate(), accomondation.getFromDate(), accomondation.getToDate(), accomondation.isDoubleroom(), accomondation.getHotelID(), accomondation.getId()
            });
            return true;
        } catch (Exception e){
            System.out.println("Error in updateAccomondation() " + e);
            return false;
        }
    }

    public boolean setRegistrationNewAccomondation(int accomondationID, int registrationID){
        try{
            jdbcTemplateObject.update(updateRegNewAccomondation, new Object[]{accomondationID, registrationID});
            return true;
        } catch (Exception e){
            System.out.println("Error in setRegistrationNewAccomondation() " + e);
            return false;
        }
    }

    public boolean deleteAccomondation(int regID){
        try{
            jdbcTemplateObject.update(deleteAccomondation, new Object[]{regID});
            return true;
        } catch (Exception e){
            System.out.println("Error in deleteAccomondation() " + e);
            return false;
        }
    }

    public Integer setPerson(Person person){
        try{
            Integer personID = jdbcTemplateObject.queryForObject(getMaxPersonID, new Object[]{}, Integer.class);
            if(personID != null){
                personID++;
            } else{
                personID = 1;
            }
            jdbcTemplateObject.update(setPerson, new Object[]{
                    personID, person.getFirstname(), person.getLastname(), person.getBirthYear(), person.getPhonenumber(), person.getEmail(), person.getGender(), person.getMark()
            });
            return personID;
        } catch (Exception e){
            System.out.println("Error in setPerson " + e);
            return null;
        }
    }

    public boolean updatePerson(Person person){
        try{
            jdbcTemplateObject.update(updatePerson, new Object[]{
                    person.getFirstname(), person.getLastname(), person.getBirthYear(), person.getPhonenumber(), person.getEmail(), person.getGender(), person.getMark(), person.getPersonID()
            });
        } catch(Exception e){
            System.out.println("Error in updatePerson " + e);
            return false;
        }
        return true;
    }

    public Integer setWorkplace(Workplace workplace){
        try{
            Integer workplaceID = jdbcTemplateObject.queryForObject(getMaxWorkplaceID, new Object[]{}, Integer.class);
            if(workplaceID != null){
                workplaceID++;
            } else{
                workplaceID = 1;
            }
            jdbcTemplateObject.update(setWorkplace, new Object[]{
                    workplaceID, workplace.getCompanyName(), workplace.getPostalcode(), workplace.getLocation(), workplace.getAddress()
            });
            return workplaceID;
        } catch (Exception e){
            System.out.println("Error in setWorkplace " + e);
            return null;
        }
    }

    public boolean updateWorkplace(Workplace workplace){
        try{
            jdbcTemplateObject.update(updateWorkplace, new Object[]{
                    workplace.getCompanyName(), workplace.getPostalcode(), workplace.getLocation(), workplace.getAddress(), workplace.getWorkplaceID()
            });
        } catch(Exception e){
            System.out.println("Error in updateWorkplace " + e);
            return false;
        }
        return true;
    }

    public boolean setPayment(Payment payment, int registrationID){
        try{
            jdbcTemplateObject.update(setPayment, new Object[]{
                    payment.getAmount(), payment.getDescription(), registrationID
            });
        } catch (Exception e){
            System.out.println("Error in setPayment " + e);
            return false;
        }
        return true;
    }

    public boolean updatePayment(ArrayList<Payment> payments, int registrationID){
        try{
            jdbcTemplateObject.update(deletePayments, new Object[]{
                    registrationID
            });
            for(Payment p : payments){
                setPayment(p, registrationID);
            }
        } catch (Exception e){
            System.out.println("Error in updatePayment " + e);
            return false;
        }
        return true;
    }

    public boolean setDates(ArrayList<Date> dates, int registrationID){
        try{
            for(Date d : dates){
                jdbcTemplateObject.update(setDate, new Object[]{
                        d, registrationID
                });
            }
        } catch (Exception e){
            System.out.println("Error in setDates " + e);
            return false;
        }
        return true;
    }

    public boolean updateDates(ArrayList<Date> dates, int registrationID){
        try{
            jdbcTemplateObject.update(deleteDates, new Object[]{
                    registrationID
            });
            setDates(dates, registrationID);
        } catch (Exception e){
            System.out.println("Error in updateDates() " + e);
            return false;
        }
        return true;
    }

    public Integer saveOptionalPersonaliaAnswers(ArrayList<InputParameter> list){
        try{
            Integer id = jdbcTemplateObject.queryForObject(getMaxIDOptionalPersonalia, new Object[]{}, Integer.class);
            if(id != null){
                id++;
            } else{
                id = 1;
            }
            jdbcTemplateObject.update(setOptionalPersonalia, new Object[]{
                    id
            });
            System.out.println("OptionalPersonaliaID containing answers = " + id);
            Integer inputid = jdbcTemplateObject.queryForObject(getMaxIDInputParameter, new Object[]{}, Integer.class);
            if(inputid == null){
                inputid = 0;
            }
            for (InputParameter ip : list){
                inputid++;
                insertInputParameter(ip, inputid, id, "optionalpersonalia");
                System.out.println("ADDED OPTIONALPERSONALIA ANSWER " + ip);
            }
            return id;
        } catch(Exception e){
            System.out.println("Error in saveOptionalPersonaliaAnswers() " + e);
            return null;
        }
    }

    public boolean updateOptionalPersonaliaAnswers(ArrayList<InputParameter> list, int registrationID){
        try{
            ArrayList<InputParameter> oldAnswers = getOptionalPersonaliaAnswers(registrationID);
            if (list.size() == oldAnswers.size()){
                for (int i = 0; i < list.size(); i++){
                    jdbcTemplateObject.update(updateInputParameterAnswer, new Object[]{
                            list.get(i).getParameter(), list.get(i).getType(), oldAnswers.get(i).getId()
                    });
                }
            } else{
                System.out.println("Error!!! Number of old input parameters and new doesn't add up! ");
            }
        } catch(Exception e){
            System.out.println("Error in updateOptionalPersonaliaAnswers " + e);
            return false;
        }
        return true;
    }

    public Integer saveOptionalWorkplaceAnswers(ArrayList<InputParameter> list){
        try{
            Integer id = jdbcTemplateObject.queryForObject(getMaxIDOptionalWorkplace, new Object[]{}, Integer.class);
            if(id != null){
                id++;
            } else{
                id = 1;
            }
            jdbcTemplateObject.update(setOptionalWorkplace, new Object[]{
                    id
            });
            System.out.println("OptionalWorkplaceID containing answers = " + id);
            Integer inputid = jdbcTemplateObject.queryForObject(getMaxIDInputParameter, new Object[]{}, Integer.class);
            if(inputid == null){
                inputid = 0;
            }
            for (InputParameter ip : list){
                inputid++;
                insertInputParameter(ip, inputid, id, "optionalworkplace");
                System.out.println("ADDED OPTIONALWORKPLACE ANSWER " + ip);
            }
            return id;
        } catch(Exception e){
            System.out.println("Error in saveOptionalWorkplaceAnswers() " + e);
            return null;
        }
    }

    public boolean updateOptionalWorkplaceAnswers(ArrayList<InputParameter> list, int registrationID){
        try{
            ArrayList<InputParameter> oldAnswers = getOptionalWorkplaceAnswers(registrationID);
            if (list.size() == oldAnswers.size()){
                for (int i = 0; i < list.size(); i++){
                    jdbcTemplateObject.update(updateInputParameterAnswer, new Object[]{
                            list.get(i).getParameter(), list.get(i).getType(), oldAnswers.get(i).getId()
                    });
                }
            } else{
                System.out.println("Error!!! Number of old input parameters and new doesn't add up! ");
            }
        } catch(Exception e){
            System.out.println("Error in updateOptionalWorkplaceAnswers " + e);
            return false;
        }
        return true;
    }

    public Integer saveExtraInfoAnswers(ArrayList<InputParameter> list){
        try{
            Integer id = jdbcTemplateObject.queryForObject(getMaxIDExtraInfo, new Object[]{}, Integer.class);
            if(id != null){
                id++;
            } else{
                id = 1;
            }
            jdbcTemplateObject.update(setExtrainfo, new Object[]{
                    id
            });
            System.out.println("ExtraInfoID containing answers = " + id);
            Integer inputid = jdbcTemplateObject.queryForObject(getMaxIDInputParameter, new Object[]{}, Integer.class);
            if(inputid == null){
                inputid = 0;
            }
            for (InputParameter ip : list){
                inputid++;
                insertInputParameter(ip, inputid, id, "extrainfo");
                System.out.println("ADDED EXTRAINFO ANSWER " + ip);
            }
            return id;
        } catch(Exception e){
            System.out.println("Error in saveExtraInfo() " + e);
            return null;
        }
    }

    public boolean updateExtraInfoAnswers(ArrayList<InputParameter> list, int registrationID){
        try{
            ArrayList<InputParameter> oldAnswers = getExtraInfoAnswers(registrationID);
            if (list.size() == oldAnswers.size()){
                for (int i = 0; i < list.size(); i++){
                    jdbcTemplateObject.update(updateInputParameterAnswer, new Object[]{
                            list.get(i).getParameter(), list.get(i).getType(), oldAnswers.get(i).getId()
                    });
                }
            } else{
                System.out.println("Error!!! Number of old input parameters and new doesn't add up! ");
            }
        } catch(Exception e){
            System.out.println("Error in updateExtraInfoAnswers " + e);
            return false;
        }
        return true;
    }

    public int getNumberOfPayments(ArrayList<Integer> regID, String description){
        int a = 0;
        try{
            for (int i = 0; i<regID.size(); i++){
                a += jdbcTemplateObject.queryForObject(sqlGetNumberOfPayments, new Object[]{regID.get(i), description}, Integer.class);
            }
        } catch (Exception e) {
            System.out.println("Error in getNumberOfPayments. " + e);
        }
        return a;
    }


    public Integer getNumberOfEvents(int event_id){
        try{
            Integer numberOfParticipants = jdbcTemplateObject.queryForObject(sqlGetNumberOfEvents, new Object[]{event_id}, Integer.class);
            return numberOfParticipants;
        } catch (Exception e) {
            System.out.println("Error in getNumberOfPayments. " + e);
            return null;
        }
    }

    public ArrayList<HashMap> getNumberOfParticipants(int courseID){
        try{
            Course course = getCourse(courseID);
            HashMap<Integer, Integer> sessionMap = new HashMap<Integer, Integer>();
            HashMap<Integer, Integer> eventMap = new HashMap<Integer, Integer>();// first element is sessionID/eventID, second is number of participants
            for (Session s : course.getSessions()){
                Integer numberOfParticipants = jdbcTemplateObject.queryForObject(getNumberOfParticipantsSession, new Object[]{s.getId()}, Integer.class);
                sessionMap.put(s.getId(), numberOfParticipants);
            }
            for (Event e : course.getEvents()){
                Integer numberOfParticipants = jdbcTemplateObject.queryForObject(getNumberOfParticipantsEvent, new Object[]{e.getId()}, Integer.class);
                eventMap.put(e.getId(), numberOfParticipants);
            }
            ArrayList<HashMap> list = new ArrayList<HashMap>();
            list.add(sessionMap);
            list.add(eventMap);
            return list;
        } catch(Exception e){
            System.out.println("Error in getNumberOfParticipantsSession " + e);
            return null;
        }
    }

    public ArrayList<Course> getNotAdminCourses(ArrayList<Integer> courses){
        ArrayList<Course> coursesToReturn = new ArrayList<Course>();
        try{
            for(Integer i : courses){
                Course c = getCourse(i);
                coursesToReturn.add(c);
            }
            return coursesToReturn;
        } catch (Exception e) {
            System.out.println("Error in getNumberOfPayments. " + e);
            return null;
        }
    }
}
