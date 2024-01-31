package exercise.controller;

import exercise.daytime.Daytime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/welcome")
public class WelcomeController {

    @Autowired
    private Daytime daytime;

    @GetMapping
    public String index(){
        var dayName = daytime.getName();
        return "It is "+dayName+" now! Welcome to Spring!";
    }

}