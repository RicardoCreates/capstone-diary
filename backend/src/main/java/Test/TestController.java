package Test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @GetMapping
    public String test(){
        return "Hello";
    }

    @GetMapping("/secret")
    public String test2(){
        return "Secret Book!";
    }
}
