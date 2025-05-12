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
        this.name = name;
        this.team = team;
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

    // add an event to every athlete's schedule
    public void addEventToAllAthletes(Event event) {
        for (String user: athletes.keySet()) {  // for every username in the 
            Athlete athlete = athletes.get(user);
            athlete.addEvent(event);
        }

    }

    // want to add event to all athletes and add event to coach's schedule 
    public void addEventToTeam(Event event) {  // this might not be right
        addEventToAllAthletes(event);
        addEvent(event);
    }

    // add an event to an athlete's schedule
    public void addEventToAthlete(Event event, String username) {
        athletes.get(username).addEvent(event);
    }

    // get a particular athlete's schedule
    public Schedule getAthleteSchedule(String username) {
        return athletes.get(username).schedule;
    }

    // get all athletes
    public ArrayList<Athlete> getAllAthletes() {  // is it fine for this to be an arrayList of athlete objects?
        ArrayList<Athlete> athleteList = new ArrayList<Athlete>();
        for (String user: athletes.keySet()) {
            athleteList.add(athletes.get(user));
        } 
        return athleteList;
    }

    // remove athlete from team
    public void removeAthlete(String username) {  // do we want this method to be void?
        athletes.remove(username);
    }

    // get all athletes' conflicts
    public HashMap<String, ArrayList<ArrayList<Event>>> getAllConflicts() {  // hashmap that takes username of athlete and maps that to their conflicts
        HashMap<String, ArrayList<ArrayList<Event>>> conflictMap = new HashMap<String, ArrayList<ArrayList<Event>>>();
        for (String user: athletes.keySet()) {
            Athlete athlete = athletes.get(user);
            if (athlete.hasConflicts()) {
                conflictMap.put(user, athlete.getAthleteConflicts());
            }
        }

        return conflictMap;
    }

    // get a particular athlete's conflicts
    public ArrayList<ArrayList<Event>> getAthleteConflicts(String username) {
        return athletes.get(username).getAthleteConflicts();
    }


    @Override
    public String toString(){
        StringBuilder output = new StringBuilder();
        output.append("\n\nCoach: " + name + "\nTeam: " + team + "\n: " + major + "\nSchedule:\n" + schedule + "\nAthletes:\n");
        for (String user: athletes.keySet()) {
            output.append(athletes.get(user).simpleToString());
        }
        return output.toString();
    }

    public static void main(String[] args) {
        
    }

}
