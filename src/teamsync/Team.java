package teamsync;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * Class that combines coach and their athletes into the same object
 * 
 * @author Kai Parker, Guy Fuchs, Tiernan Colby
 */
public class Team {

    // fields representing basic team details
    String teamName;
    Coach coach;
    HashMap<String, Athlete> athleteMap;
    ArrayList<Athlete> athletes;
    

    public Team(String teamName, Coach coach, HashMap<String, Athlete> athleteMap) {
        this.teamName = teamName.toLowerCase();
        this.coach = coach;         
        this.athleteMap = athleteMap;
        
        // add every athlete in athlete map to the athlete list
        for (String user: athleteMap.keySet()) { 
            this.athletes.add(athleteMap.get(user));
        }

    }

    /**
     * returns name of team
     * @return the name of the name
     */
    public String getTeamName() {
        return this.teamName;
    }

    /**
     * sets the name of the team
     * @param teamName name of the team
     */
    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    /**
     * gets coach of team
     * @return the coach object of the team
     */
    public Coach getCoach() {
        return this.coach;
    }

    /**
     * sets coach of team to be the given coach
     * @param coach coach of team
     */
    public void setCoach(Coach coach) {
        this.coach = coach;
    }

    /**
     * gets the list of athletes
     * @return list of athletes on the team
     */
    public ArrayList<Athlete> getAthleteList() {
        return this.athletes;
    }

    /**
     * sets the athletes to be the given athlete list
     * @param athleteList list of athletes
     */
    public void setAthleteList(ArrayList<Athlete> athleteList) {
        this.athletes = athleteList;
    }

    /**
     * gets the map of athletes on team
     * @return map of athletes with username as key and athlete as value
     */
    public HashMap<String, Athlete> getAthleteMap() {
        return this.athleteMap;
    }

    /**
     * sets the athlete map to the given one
     * @param athleteMap athleteMap that represents athletes on team
     */
    public void setAthleteMap(HashMap<String, Athlete> athleteMap) {
        this.athleteMap = athleteMap;
    }

    /**
     * get a specific athlete given their username
     * @param user username of the athlete
     * @return athlete corresponding to the username
     */
    public Athlete getAthlete(String user) {
        return athleteMap.get(user);
    }

    /**
     * adds an athlete to the team
     * @param athlete an athlete object
     * @param user username of athlete
     */
    public void addAthlete(Athlete athlete, String user) {
        this.athletes.add(athlete);
        this.athleteMap.put(user, athlete);
    }

    /**
     * removes an athlete from the team
     * @param athlete an athlete object
     * @param user username of athlete
     */
    public void removeAthlete(Athlete athlete, String user) {
        this.athletes.remove(athlete);  // removes athlete from athlete list
        this.athleteMap.remove(user);  // removes athlete from athlete map
    }

    /**
     * adds an event to the team
     * @param event evennt to add to team
     */
    public void addEventToTeam(Event event) {
        coach.addEventToTeam(event);
    }

    /**
     * removes an event from the team
     * @param event event to remove from team schedule
     */
    public void removeEventFromTeam(Event event) {
        coach.removeEventFromTeam(event);
    }

    /**
     * adds an event to an athlete's schedule
     * @param event event to add to athlete
     * @param user username of athlete
     */
    public void addEventToAthlete(Event event, String user) {
        coach.addEventToAthlete(event, user);
    }

    /**
     * removes an event from an athlete's schedule
     * @param event event to remove from an athlete
     * @param user username of athlete
     */
    public void removeEventFromAthlete(Event event, String user) {
        coach.removeEventFromAthlete(event, user);
    }

    /**
     * gets a map of the athletes with conflicts
     * @return a map of athletes with conflicts
     */
    public HashMap<String, ArrayList<ArrayList<Event>>> getAllAthleteConflicts() {
        return coach.getAllConflicts();
    }

    /**
     * gets list of conflicts for an athlete 
     * @param user username of athlete
     * @return list of conflicts
     */
    public ArrayList<ArrayList<Event>> getAthleteConflicts(String user) {
        return coach.getAthleteConflicts(user);
    }

    /**
     * 
     * @return
     */
    public ArrayList<String> getAthletesWithConflicts() {  // should we return a set or convert to an ArrayList
        return coach.getAthletesWithConflicts();
        
    }

    /**
     * 
     * @return
     */
    public int teamSize() {
        return 1 + this.athletes.size();
    }

    /**
     * 
     * @return
     */
    public ArrayList<String> getAllAthletesUsernames() {
        ArrayList<String> userList = new ArrayList<String>();

        for (Athlete athlete: athletes) {
            userList.add(athlete.username);
        }

        return userList;
    }

    /**
     * 
     * @return
     */
    public String getCoachUsername() {
        return coach.username;
    }
    
    // is this gonna properly update schedules?
    public void clearSchedules() {
        coach.clearCoachSched();

        for (Athlete athlete: athletes) {
            athlete.clearAthleteSched();
        }
    }

    /**
     * 
     * @param practiceSchedule
     */
    public void addPracticeSchedule(Schedule practiceSchedule) {
        for (Event event: practiceSchedule.getSchedule()) {
            this.addEventToTeam(event);
        }
    }

    /**
     * 
     */
    public String toString() {
        return null;
    }

} 