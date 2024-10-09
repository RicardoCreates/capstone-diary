package de.ricardo.backend.BookController;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/book")
public class BookController {

    @GetMapping
    public String test(){
        return "Hello";
    }


    @GetMapping("/secret")
    public String test2(){
        return "Secret Book!";
    }
}
