package com.teamSync.app.service;
import org.springframework.beans.factory.annotation.Autowired;
import com.teamSync.app.repository.ExampleRepository;
import com.teamSync.app.model.Example;
import org.springframework.stereotype.Service;

@Service
public class ExampleService {
    
    @Autowired
    private ExampleRepository exampleRepository;
    public String getExample() {
        Example example = exampleRepository.findByName("example");
        if (example != null) {
            return example.getName();
        } else {
            return "example not found";
        }
    }   

    public String createExample(String name, int age, String email) {
        // Logic to check if name starts with 'A' and change it
        String processedName = name;
        if (name != null && name.startsWith("A")) {
            processedName = "Modified_" + name;
        }
        Example example = new Example(processedName, age, email);
        exampleRepository.save(example);
        return "example created with name: " + processedName;
    }

    public String updateExample(String name, int age, String email) {
        // Logic to check if name starts with 'A' and change it
        String processedName = name;
        if (name != null && name.startsWith("A")) {
            processedName = "Modified_" + name;
        }
        
        Example example = exampleRepository.findByName(name);
        if (example != null) {
            example.setName(processedName);
            example.setAge(age);
            example.setEmail(email);
            exampleRepository.save(example);
            return "example updated with name: " + processedName;
        } else {
            return "example not found";
        }
    }
    
    public String deleteExample(String name) {
        Example example = exampleRepository.findByName(name);
        if (example != null) {
            exampleRepository.delete(example);
            return "example deleted";
        } else {
            return "example not found";
        }
    }
}
