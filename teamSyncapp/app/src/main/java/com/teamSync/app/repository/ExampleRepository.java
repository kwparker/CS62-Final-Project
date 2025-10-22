package com.teamSync.app.repository;
import com.teamSync.app.model.Example;
import org.springframework.stereotype.Repository;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository @Transactional
public interface ExampleRepository extends JpaRepository<Example, Long> {
    Example findByName(String name);
    Example findByAge(int age);
    Example findByEmail(String email);
}
