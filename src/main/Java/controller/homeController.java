package controller;

import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Lars on 13.01.16.
 */
@Controller
public class homeController {
    @RequestMapping("/")
    public String home(){
        return "registerCourse";
    }

    @RequestMapping("/event")
    public String event(){ return "event";}
}
