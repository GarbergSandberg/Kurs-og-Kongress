package controller;

import domain.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
import service.*;
import ui.*;

/**
 * Created by Lars on 13.01.16.
 */
@Controller
public class homeController {

    @Autowired
    private PersonService personService;

    @Autowired
    private CourseService courseService;

    @RequestMapping("/registration")
    public String home(){return "registration";}

    @RequestMapping("/registerCourse")
    public String registerCourse(){return "registerCourse";}

    @RequestMapping("/")
    public String registration(){
        return "registerCourse";
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
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/getCourseMock", method = RequestMethod.GET)
    @ResponseBody
    public Course getMockCourse() {
        System.out.println(courseService.getCourses());
        return courseService.getCourses();
    }


}
