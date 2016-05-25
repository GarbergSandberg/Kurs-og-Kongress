package controller;

import domain.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.beans.propertyeditors.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.*;
import resources.*;
import service.*;

import javax.servlet.http.*;
import java.util.*;

/**
 * Created by Lars on 13.01.16.
 */
@Controller
public class homeController {
    AESencrp encryptor = new AESencrp();
    String selectedPerson = "-1";

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String[].class, new StringArrayPropertyEditor(null));
    }

    @Autowired
    private CourseService courseService;

    @Autowired
    private LoginService loginService;

    @Autowired
    private RegistrationService registrationService;

    @RequestMapping("/")
    public ModelAndView home(){
        selectedPerson = "-1";
        return new ModelAndView("index");
    }

    @RequestMapping("/publicRegistrations")
    public ModelAndView publicRegistrations(){
        return new ModelAndView("publicRegistrations");
    }

    @RequestMapping("/createUser")
    public ModelAndView createUser(HttpSession session){
        User u = (User) session.getAttribute("user");
        if(u == null) {
            return new ModelAndView("index");
        } else{
            return new ModelAndView("createUser");
        }
    }

    @RequestMapping("/registerCourse")
    public ModelAndView registerCourse(HttpSession session){
        User u = (User) session.getAttribute("user");
        if(u == null) {
            return new ModelAndView("index");
        } else{
            return new ModelAndView("registerCourse");
        }
    }

    @RequestMapping("/personInfo")
    public ModelAndView fullPersonInfo(HttpSession session) { //
        User u = (User) session.getAttribute("user");
        if (u == null){
            return new ModelAndView("index");
        } else if (u.isAdmin()){
            return new ModelAndView("fullPersonInfo");
        } else {
            return new ModelAndView("personInfo");
        }
    }

    @RequestMapping("/invoice")
    public ModelAndView invoice(HttpSession session){
        User u = (User) session.getAttribute("user");
        if (u == null){
            return new ModelAndView("index");
        } else if (u.isAdmin()){
            return new ModelAndView("invoice");
        } else {
            return new ModelAndView("personInfo");
        }
    }

    @RequestMapping("/groupInvoice")
    public ModelAndView groupInvoice(HttpSession session){
        User u = (User) session.getAttribute("user");
        if (u == null){
            return new ModelAndView("index");
        } else if (u.isAdmin()){
            return new ModelAndView("groupInvoice");
        } else {
            return new ModelAndView("personInfo");
        }
    }

    @RequestMapping("/courseEconomics")
    public ModelAndView courseEconomics(HttpSession session){
        User u = (User) session.getAttribute("user");
        if (u == null){
            return new ModelAndView("index");
        } else{
            return new ModelAndView("courseEconomics");
        }

    }

    @RequestMapping("/attenderInfo")
    public ModelAndView attenderInfo(HttpSession session){
        User u = (User) session.getAttribute("user");
        if (u == null){
            return new ModelAndView("index");
        } else{
            return new ModelAndView("attenderInfo");
        }
    }

    @RequestMapping("/singleRegistration")
    public ModelAndView singleRegistration(){return new ModelAndView("singleRegistration");}

    @RequestMapping("/groupRegistration")
    public ModelAndView groupRegistration(){return new ModelAndView("groupRegistration");}

    @RequestMapping("/courseStatistics")
    public ModelAndView courseStatistics(HttpSession session){
        User u = (User) session.getAttribute("user");
        if (u == null){
            return new ModelAndView("index");
        } else{
            return new ModelAndView("courseStatistics");
        }
    }

    @RequestMapping("/courseOverview")
    public ModelAndView courseOverview(HttpSession session){
        User u = (User) session.getAttribute("user");
            if(u == null){
                return new ModelAndView("index");
            } else{
                return new ModelAndView("courseOverview");
            }
    }

    // HTTP-request handling

    @RequestMapping(value = "/saveRegistration", method = RequestMethod.POST)
    public ResponseEntity<Void> saveRegistration( @RequestBody Registration registration )   {
        int courseID = registration.getCourse().getId();
        Boolean isFull = courseService.checkIfCourseGetsFull(courseID, 1);
        System.out.println("isFull: " + isFull);
        if(isFull == null){
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        }
        if(isFull){
            return new ResponseEntity<Void>(HttpStatus.NOT_ACCEPTABLE); // 406
        }
        ArrayList<Boolean> check = courseService.getStatus(courseID, registration.getSessionsToAttend(), registration.getEventsToAttend(), 1);
        if(check != null){
            if(check.get(0).equals(true)){
                return new ResponseEntity<Void>(HttpStatus.CONFLICT); // 409
            }
            if(check.get(1).equals(true)){
                return new ResponseEntity<Void>(HttpStatus.FORBIDDEN); // 403
            }
        }
        courseService.saveRegistration(registration);
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/saveRegistrations", method = RequestMethod.POST)
    public ResponseEntity<Void> saveRegistrations(@RequestBody ArrayList<Registration> registrations)   {
        System.out.println(registrations.get(0).getPerson().getPhonenumber() + " DETTE ER TELEFONNUMMER!");
        int courseID = registrations.get(0).getCourse().getId();
        Boolean isFull = courseService.checkIfCourseGetsFull(courseID, registrations.size());
        System.out.println("isFull: " + isFull);
        if(isFull == null){
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        }
        if(isFull){
            return new ResponseEntity<Void>(HttpStatus.NOT_ACCEPTABLE); // 406
        }
        ArrayList<Boolean> check = courseService.getStatus(courseID, registrations.get(0).getSessionsToAttend(), registrations.get(0).getEventsToAttend(), registrations.size());
        if(check != null){
            if(check.get(0).equals(true)){
                return new ResponseEntity<Void>(HttpStatus.CONFLICT); // 409
            }
            if(check.get(1).equals(true)){
                return new ResponseEntity<Void>(HttpStatus.FORBIDDEN); // 403
            }
        }
        int a = courseService.getMaxIdGroupRegistration();
        for (int i = 0; i<registrations.size(); i++){
            if (a != -1){
                registrations.get(i).setIdGroupregistration(a);
            }
            courseService.saveRegistration(registrations.get(i));
        }
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/sendPerson", method = RequestMethod.POST)
    public ResponseEntity<Void> sendPersonHTTP(@RequestBody Person person)   {
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/saveinformation_json", method = RequestMethod.POST)
    public ResponseEntity<Void> saveInformation_JSON(@RequestBody Course course )   {
        courseService.saveCourse(course);
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/addNewUser", method = RequestMethod.POST)
    public ResponseEntity<Void> saveInformation_JSON(@RequestBody User user)   {
        boolean userAdded = loginService.addUser(user);
        if(userAdded){
            return new ResponseEntity<Void>(HttpStatus.CREATED);
        } else{
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/inputparameter", method = RequestMethod.POST) // Hva brukes denne til?
    public ResponseEntity<Void> saveInputParameter( @RequestBody InputParameter inputParameter )   {
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/getCourses", method = RequestMethod.GET)
    @ResponseBody
    public ArrayList<Course> getCourses() {
        return courseService.getCourses();
    }

    @RequestMapping(value = "/getPublicCourses", method = RequestMethod.GET)
    @ResponseBody
    public ArrayList<Course> getPublicCourses() {
        ArrayList<Course> courses = courseService.getCourses();
        ArrayList<Course> coursesToReturn = new ArrayList<Course>();
        for (Course c : courses){
            if(c.isPublicCourse()){
                coursesToReturn.add(c);
            }
        }
        return coursesToReturn;
    }

    @RequestMapping(value = "/getCourse", method = RequestMethod.GET)
    @ResponseBody
    public Course getCourse(@RequestParam(value = "course_id") int id) {
        return courseService.getCourse(id);
    }

    @RequestMapping(value = "/loginUser", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity getUser(@RequestParam(value = "username") String username, @RequestParam(value = "password") String password, HttpSession session) {
        User u = loginService.logIn(username,password);
        if (u != null){
            session.setAttribute("user", u);
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
    }

    @RequestMapping(value = "/updateRegistration", method = RequestMethod.POST)
    public ResponseEntity<Void> updateRegistration( @RequestBody Registration registration )   {
        System.out.println("***************  OPPDATERER REGISTRERING!!! *****************");
        courseService.updateRegistration(registration);
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/deleteRegistration", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Void> deleteRegistration(@RequestBody Registration registration){
        if(courseService.deleteRegistration(registration)){
            return new ResponseEntity<Void>(HttpStatus.OK);
        } else{
            return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/getRegistrations", method = RequestMethod.GET)
    @ResponseBody
    public ArrayList<Registration> getRegistration(@RequestParam(value = "course_id") int id) {
        return courseService.getRegistrations(id);
    }

    @RequestMapping(value = "/getCountRegistrations", method = RequestMethod.GET)
    @ResponseBody
    public int getCountRegistrations(@RequestParam(value = "course_id") int id) {
        int i = courseService.getCountRegistrations(id);
        return i;
    }

    @RequestMapping(value = "/getNumberOfPayments", method = RequestMethod.GET)
    @ResponseBody
    public int getNumberOfPayments(@RequestParam(value = "registration_id") ArrayList<Integer> id, @RequestParam(value = "description") String description) {
        int i = courseService.getNumberOfPayments(id, description);
        return i;
    }

    @RequestMapping(value = "/getGroupNumberOfPayments", method = RequestMethod.GET)
    @ResponseBody
    public int getGroupNumberOfPayments(@RequestParam(value = "idGroupregistration") Integer id, @RequestParam(value = "description") String description) {
        return courseService.getGroupNumberOfPayments(id, description);
    }

    @RequestMapping(value = "/getNumberOfEvents", method = RequestMethod.GET)
    @ResponseBody
    public Integer getNumberOfEvents(@RequestParam(value = "event_id") int event_id) {
        return courseService.getNumberOfEvents(event_id);
    }

    @RequestMapping(value = "/getGroupNumberOfEvents", method = RequestMethod.GET)
    @ResponseBody
    public Integer getGroupNumberOfEvents(@RequestParam(value = "idGroupregistration") int idGroupregistration, @RequestParam(value = "event_id") int event_id) {
        Integer i = courseService.getGroupNumberOfEvents(idGroupregistration, event_id);
        return i;
    }

    @RequestMapping(value = "/setSessionStorageID", method = RequestMethod.GET, produces="text/plain")
    @ResponseBody
    public String setSessionStorageID(@RequestParam(value = "id", required = false) String id) {
        try{
            selectedPerson = encryptor.encrypt(id);
        } catch(Exception e){
            System.out.println("Error in setSessionStorrageID EXCEPTION " + e);
        }
        return selectedPerson;
    }

    @RequestMapping(value = "/getSessionStorageID", method = RequestMethod.GET, produces="text/plain")
    @ResponseBody
    public String getSessionStorageID(@RequestParam(value = "id", required = false) String id) {
        try{
                String actualID  = encryptor.decrypt(id);
                return actualID;
        } catch(Exception e){
            System.out.println("Error in getSessionStorageID " + e);
        }
        return null;
    }

    @RequestMapping(value = "/getRegistration", method = RequestMethod.GET)
    @ResponseBody
    public Registration getSingleRegistration(@RequestParam(value = "registration_id") int id) {
        return courseService.getRegistration(id);
    }

    @RequestMapping(value = "/getUsers", method = RequestMethod.GET)
    @ResponseBody
    public ArrayList<User> getUsers() {
        return loginService.getUsers();
    }

    @RequestMapping(value = "/addAccess", method = RequestMethod.POST)
    @ResponseBody
    public boolean addAccess(@RequestBody CourseUserResolver cur){
        return loginService.addAccess(cur.getUser(), cur.getCourse());
    }

    @RequestMapping(value = "/removeAccess", method = RequestMethod.POST)
    @ResponseBody
    public boolean removeAccess(@RequestBody CourseUserResolver cur){
        return loginService.removeCourseAccess(cur.getUser(), cur.getCourse());
    }

    @RequestMapping(value = "/getCourseAccess", method = RequestMethod.GET)
    @ResponseBody
    public ArrayList<Integer> getCourseAccess(@RequestParam(value = "username") String username) {
        return loginService.getCourseAccess(username);
    }

    @RequestMapping(value = "/deleteUser", method = RequestMethod.POST)
    @ResponseBody
    public boolean deleteUser(@RequestBody User user){
        return loginService.deleteUser(user);
    }

    @RequestMapping(value = "/enableRegistration", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity enableRegistration(@RequestParam(value = "id") int courseID, @RequestParam(value = "value") boolean value) {
        courseService.enableRegistration(courseID,value);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/checkParticipantStatus", method = RequestMethod.GET)
    @ResponseBody
    public ArrayList<HashMap> checkParticipantStatus(@RequestParam(value = "courseID") int courseID) {
        return courseService.getNumberOfParticipants(courseID);
    }

    @RequestMapping(value = "/getNotAdminCourses", method = RequestMethod.GET)
    @ResponseBody
    public ArrayList<Course> getNotAdminCourses(HttpSession session) {
        User u = (User) session.getAttribute("user");
        ArrayList<Integer> courseAccess = getCourseAccess(u.getUsername());
        return courseService.getNotAdminCourses(courseAccess);
    }

    @RequestMapping(value = "/checkIfCourseIsFull", method = RequestMethod.GET)
    @ResponseBody
    public boolean checkIfCourseIsFull(@RequestParam(value = "courseID") int courseID, @RequestParam(value = "numberOfRegistrations") int numberOfRegistrations) {
        return courseService.checkIfCourseGetsFull(courseID,numberOfRegistrations);
    }

    @RequestMapping(value = "/getStatus", method = RequestMethod.GET)
    @ResponseBody
    public ArrayList<Boolean> getStatus(@RequestParam(value = "courseID") int courseID, @RequestParam(value = "sessionsToAttend") ArrayList<Integer> sessionsToAttend,@RequestParam(value = "eventsToAttend") ArrayList<Integer> eventsToAttend, @RequestParam(value = "numberOfRegistrations") int numberOfRegistrations) {
        return courseService.getStatus(courseID, sessionsToAttend, eventsToAttend, numberOfRegistrations);
    }

    @RequestMapping(value = "/getEmails", method = RequestMethod.GET)
    @ResponseBody
    public ArrayList<String> getEmails(@RequestParam(value = "course_id") int courseID) {
        return courseService.getEmails(courseID);
    }

    @RequestMapping(value = "/changePassword", method = RequestMethod.GET)
    @ResponseBody
    public boolean getEmails(@RequestParam(value = "username") String username, @RequestParam(value = "oldPassword") String oldPassword, @RequestParam(value = "newPassword") String newPassword) {
        return loginService.changePassword(username,oldPassword,newPassword);
    }

}
