package exercise;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


// BEGIN
@RestController
public class WelcomeController {
    
    @GetMapping("/")
    public String index(){
        return "Welcome to Spring";
    }

    @GetMapping("/hello")
    @ResponseBody
    public String hello(@RequestParam(defaultValue = "World")  String name){
        return "Hello, "+name+"!";
    }

}
// END
