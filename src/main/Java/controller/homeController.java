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

    @RequestMapping("/reg")
    public ModelAndView reg(){
        return new ModelAndView("reg");
    }

    @RequestMapping("/publicRegistrations")
    public ModelAndView publicRegistrations(){
        return new ModelAndView("publicRegistrations");
    }

    @RequestMapping("/createUser")
    public ModelAndView createUser(){
        return new ModelAndView("createUser");
    }

    @RequestMapping("/registerCourse")
    public ModelAndView registerCourse(){return new ModelAndView("registerCourse");}

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

    @RequestMapping("/courseEconomics")
    public ModelAndView courseEconomics(){return new ModelAndView("courseEconomics");}

    @RequestMapping("/attenderInfo")
    public ModelAndView attenderInfo(){return new ModelAndView("attenderInfo");}

    @RequestMapping("/singleRegistration")
    public ModelAndView singleRegistration(){return new ModelAndView("singleRegistration");}

    @RequestMapping("/groupRegistration")
    public ModelAndView groupRegistration(){return new ModelAndView("groupRegistration");}

    @RequestMapping("/courseStatistics")
    public ModelAndView courseStatistics(){return new ModelAndView("courseStatistics");}

    @RequestMapping("/courseOverview")
    public ModelAndView courseOverview(HttpSession session){
        User u = (User) session.getAttribute("user");
            if(u == null){
                System.out.println("Kommer ikke inn gitt");
                return new ModelAndView("index");
            } else{
                System.out.println("Logget inn");
                return new ModelAndView("courseOverview");
            }
    }

    @RequestMapping("/registration")
    public String registration(){
        return "registration";
    }

    @RequestMapping(value = "/saveRegistration", method = RequestMethod.POST)
    public ResponseEntity<Void> saveRegistration( @RequestBody Registration registration )   {
        courseService.saveRegistration(registration);
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/saveRegistrations", method = RequestMethod.POST)
    public ResponseEntity<Void> saveRegistrations(@RequestBody ArrayList<Registration> registrations)   {
        System.out.println("Kommer hit..");
        for (int i = 0; i<registrations.size(); i++){
            System.out.println("Sender et og et element inn..");
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
        System.out.println(user.isAdmin());
        boolean userAdded = loginService.addUser(user);
        System.out.println(userAdded + " = user added");
        if(userAdded){
            return new ResponseEntity<Void>(HttpStatus.CREATED);
        } else{
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/inputparameter", method = RequestMethod.POST)
    public ResponseEntity<Void> saveInputParameter( @RequestBody InputParameter inputParameter )   {
        System.out.println("DETTE FUNKET!" + inputParameter.getParameter());
        System.out.println(inputParameter.getType());
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

    @RequestMapping(value = "/getRegistrations", method = RequestMethod.GET)
    @ResponseBody
    public ArrayList<Registration> getRegistration(@RequestParam(value = "course_id") int id) {
        System.out.println("PÅMELDING HER: *****************");
        System.out.println(courseService.getRegistrations(id).toString());
        return courseService.getRegistrations(id);
    }

    @RequestMapping(value = "/getCountRegistrations", method = RequestMethod.GET)
    @ResponseBody
    public int getCountRegistrations(@RequestParam(value = "course_id") int id) {
        int i = courseService.getCountRegistrations(id);
        System.out.println("Controller: ************************* " + i);
        return i;
    }

    @RequestMapping(value = "/getNumberOfPayments", method = RequestMethod.GET)
    @ResponseBody
    public int getNumberOfPayments(@RequestParam(value = "registration_id") ArrayList<Integer> id, @RequestParam(value = "description") String description) {
        int i = courseService.getNumberOfPayments(id, description);
        return i;
    }

    @RequestMapping(value = "/getNumberOfEvents", method = RequestMethod.GET)
    @ResponseBody
    public Integer getNumberOfEvents(@RequestParam(value = "event_id") int event_id) {
        Integer i = courseService.getNumberOfEvents(event_id);
        System.out.println("Controller (getnumberofevents): ************************* " + i);
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
            System.out.println("SELECTED PERSON: " + selectedPerson);
                String actualID  = encryptor.decrypt(id);
                System.out.println("ACTUAL ID: " + actualID);
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

}
