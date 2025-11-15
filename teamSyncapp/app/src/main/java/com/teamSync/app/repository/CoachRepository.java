package com.teamSync.app.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.teamSync.app.model.CoachModel;
import org.springframework.stereotype.Repository;

@Repository
public interface CoachRepository extends JpaRepository<CoachModel, Long> {
    CoachModel findByUserId(Long userId);
    CoachModel findByTeam(String team);
    CoachModel findByUserIdAndTeam(Long userId, String team);
}
