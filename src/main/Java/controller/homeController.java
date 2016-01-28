package controller;

import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Lars on 13.01.16.
 */
@Controller
public class homeController {
    @RequestMapping("/")
    public String home(){
        return "test";
    }

    @RequestMapping("/event")
    public String event(){ return "event";}
}
