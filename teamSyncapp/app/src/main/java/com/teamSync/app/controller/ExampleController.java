package com.teamSync.app.controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import com.teamSync.app.service.ExampleService;

@RestController
public class ExampleController {

    @Autowired
    private ExampleService exampleService;

    @GetMapping("/example")
    public String getExample() {
        return exampleService.getExample();
    }

    @PostMapping("/example")
    public String createExample(@RequestParam String name, 
                               @RequestParam int age, 
                               @RequestParam String email) {
        return exampleService.createExample(name, age, email);
    }

    @PutMapping("/example")
    public String updateExample(@RequestParam String name, 
                               @RequestParam int age, 
                               @RequestParam String email) {
        return exampleService.updateExample(name, age, email);
    }

    @DeleteMapping("/example")
    public String deleteExample(@RequestParam String name) {
        return exampleService.deleteExample(name);
    }
}
