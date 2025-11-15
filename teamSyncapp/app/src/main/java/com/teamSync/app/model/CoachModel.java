package com.teamSync.app.model;
import jakarta.persistence.*;
import com.teamSync.app.model.UsersModel;
import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name = "coaches")
public class CoachModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UsersModel user;

    @Column(name = "team", nullable = false)
    private String team;

    //Add later after athlete model is finished
    // @OneToMany(mappedBy = "coach_id", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    // private List<AthleteModel> athletes = new ArrayList<>();

    public CoachModel() {}

    public CoachModel(UsersModel user, String team) {
        this.user = user;
        this.team = team;
    }

    //getters and setters
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UsersModel getUser() {
        return this.user;
    }

    public void setUser(UsersModel user) {
        this.user = user;
    }

    public String getTeam() {
        return this.team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    // public List<AthleteModel> getAthletes() {
    //     return this.athletes;
    // }
    
    // public void setAthletes(List<AthleteModel> athletes) {
    //     this.athletes = athletes;
    // }

    @Override
    public String toString() {
        return "CoachModel [" +
          "user=" + user.getFirstName() + " " + user.getLastName() +
          "team=" + team +
        //   "athletesCount=" + (athletes != null ? athletes.size() : 0) +
          "]";
    }
}
