package controller;

import domain.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.*;
import service.*;
import ui.*;

import javax.servlet.http.*;
import java.util.*;

/**
 * Created by Lars on 13.01.16.
 */
@Controller
public class homeController {

    @Autowired
    private PersonService personService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private LoginService loginService;

    @RequestMapping("/")
    public ModelAndView home(){
        return new ModelAndView("registerCourse");
    }

    @RequestMapping("/reg")
    public ModelAndView reg(){
        return new ModelAndView("reg");
    }

    @RequestMapping("/registerCourse")
    public ModelAndView registerCourse(){return new ModelAndView("registerCourse");}

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

    @RequestMapping("/event")
    //public String event(){ return "event";}
    public String event(@ModelAttribute PersonFormBackingBean backingBean) {
        backingBean.setAllePersoner(personService.getAllePersoner());
        System.out.println("** ControllerClass.person() ******");
        return "event";
    }

    @RequestMapping(value = "/saveinformation_json", method = RequestMethod.POST)
    public ResponseEntity<Void> saveInformation_JSON( @RequestBody Course course )   {
        System.out.println(course.getTitle());
        System.out.println(course.getDescription());
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/getCourseMock", method = RequestMethod.GET)
    @ResponseBody
    public Course getMockCourse() {
        System.out.println(courseService.getMockCourse());
        return courseService.getMockCourse();
    }

    @RequestMapping(value = "/getTemplate", method = RequestMethod.GET)
    @ResponseBody
    public Course getTemplate() {
        return courseService.generateTemplate();
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
}
