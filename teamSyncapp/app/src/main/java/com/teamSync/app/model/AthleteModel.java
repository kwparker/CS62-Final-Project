package com.teamSync.app.model;
import jakarta.persistence.*;
import com.teamSync.app.model.Year;
import com.teamSync.app.model.UsersModel;

@Entity
@Table(name = "athletes")
public class AthleteModel {

  @Id
  private Long id;

  @OneToOne
  @JoinColumn(name = "id")
  private UsersModel user;

  private String team;
  private String major;
 
  @Enumerated(EnumType.STRING)
  private Year year;

  public AthleteModel() {}

  public AthleteModel(String team, String major, Year year) {
    this.team = team;
    this.major = major;
    this.year = year;
  }

  //getters and setters
  public Long getId() {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public UsersModel getUser() {
    return user;
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

  public String getMajor() {
    return this.major;
  }

  public void setMajor(String Major) {
    this.major = major;
  }

  public Year getYear() {
    return this.year;
  }

  public void setYear(Year year) {
    this.year = year;
  }

  @Override
  public String toString() {
    return "AthleteModel [" +
      "team=" + team +
      "major=" + major +
      "year=" + year +
      "]";
  }
}
