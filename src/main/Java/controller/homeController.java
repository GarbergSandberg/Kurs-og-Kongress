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
        courseService.getCourses();
        selectedPerson = "-1";
        return new ModelAndView("courseStatistics");
    }

    @RequestMapping("/reg")
    public ModelAndView reg(){
        return new ModelAndView("reg");
    }

    @RequestMapping("/registerCourse")
    public ModelAndView registerCourse(){return new ModelAndView("registerCourse");}

    @RequestMapping("/personInfo")
    public ModelAndView personInfo(){return new ModelAndView("personInfo");}

    @RequestMapping("/invoice")
    public ModelAndView invoice(){return new ModelAndView("invoice");}

    @RequestMapping("/attenderInfo")
    public ModelAndView attenderInfo(){return new ModelAndView("attenderInfo");}

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

    @RequestMapping(value = "/saveReg", method = RequestMethod.POST)
    public ResponseEntity<Void> saveRegistration( @RequestBody Registration registration )   {
        System.out.println(registration.toString());
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

    @RequestMapping(value = "/getRegistrations", method = RequestMethod.GET)
    @ResponseBody
    public ArrayList<Registration> getRegistration(@RequestParam(value = "course_id") int id) {
        return courseService.getRegistrations(id);
    }

    @RequestMapping(value = "/setSessionStorageID", method = RequestMethod.GET, produces="text/plain")
    @ResponseBody
    public String setSessionStorageID(@RequestParam(value = "id", required = false) String id) {
        try{
            selectedPerson = encryptor.encrypt(id);
        } catch(Exception e){
            System.out.println("Error in setSessionStorrageID EXCEPTION");
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
            System.out.println("Error in getSessionStorageID");
        }
        return null;
    }
}
