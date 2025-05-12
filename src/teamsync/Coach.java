package teamsync;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class Coach {
    
    String team;
    String name;
    String username;
    String major;
    Schedule schedule;
    HashMap<String, Athlete> athletes;

    public Coach(String name, String username, String team, Schedule schedule, HashMap<String, Athlete> athletes){
        this.team = team;
        this.name = name;
        this.username = username;
        this.schedule = schedule;
        this.athletes = athletes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return username;
    }

    public void setUserName(String username) {
        this.username = username;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public Schedule getCoachSchedule() {
        return schedule;
    }

    public void addEvent(Event event) {
        schedule.addEvent(event);
    }

    public Event removeEvent(Event event) {
        return schedule.removeEvent(event);
    }

    
    public void editEvent(Event original, Event newEvent) {
        schedule.editEvent(original, newEvent);
    }
    
    // check if coach has conflicts
    public boolean hasConflicts(){
        return schedule.detectConflict();
    }

    // get coach's conflicts
    public ArrayList<ArrayList<Event>> getCoachConflicts(){
        return schedule.getConflicts();
    }

    // add an event to everyone's schedule
    public void addEventToTeam(Event event) {

    }

    // add an event to an athlete's schedule
    public void addEventToAthlete(Event event, String username) {

    }

    // get a particular athlete's schedule
    public Schedule getAthleteSchedule(String username) {
        return null;
    }

    // get all athletes
    public Collection<Athlete> getAllAthletes() {
        return null;
    }

    // remove athlete from team
    public Athlete removeAthlete(String username) {  // could also return null probably
        return null;
    }

    // get all athletes' conflicts
    public HashMap<String, ArrayList<ArrayList<Event>>> getAllConflicts() {  // hashmap that takes username of athlete and maps that to their conflicts
        return null;
    }

    // get a particular athlete's conflicts
    public ArrayList<ArrayList<Event>> getAthleteConflicts() {
        return null;
    }

    public String toString() {
        return null;
    }

}
