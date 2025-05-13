package teamsync;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

// combine coach and their athletes into the same arrayList
// DIFFERENT CLASS maybe school/college? : hashmap where buckets are the teams, like soccer, swim, track, etc. 
    // inside the buckets is the coach / players of that team
public class Team {

    String teamName;
    Coach coach;
    HashMap<String, Athlete> athleteMap;
    ArrayList<Athlete> athleteList;

    public Team(String teamName, Coach coach, ArrayList<Athlete> athletes) {  // idk whether or not to pass the athletes in as a list or map
        this.teamName = teamName.toLowerCase();
        this.coach = coach;
        this.athleteList = new ArrayList<>(athletes);
        this.athleteMap = new HashMap<>();            

        for (Athlete athlete : athletes) {
            this.athleteMap.put(athlete.getUserName(), athlete);
        }

        coach.athletes = this.athleteMap;
    }

    public String getTeamName() {
        return this.teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public Coach getCoach() {
        return this.coach;
    }

    public void setCoach(Coach coach) {
        this.coach = coach;
    }

    public ArrayList<Athlete> getAthleteList() {
        return this.athleteList;
    }

    public void setAthleteList(ArrayList<Athlete> athleteList) {
        this.athleteList = athleteList;
    }

    public HashMap<String, Athlete> getAthleteMap() {
        return this.athleteMap;
    }

    public void setAthleteMap(HashMap<String, Athlete> athleteMap) {
        this.athleteMap = athleteMap;
    }

    public Athlete getAthlete(String user) {
        return athleteMap.get(user);
    }

    public void addEventToTeam(Event event) {  // is this chill
        coach.addEventToTeam(event);
    }

    public void removeEventFromTeam(Event event) {
        coach.removeEventFromTeam(event);
    }

    public void addEventToAthlete(Event event, String user) {
        coach.addEventToAthlete(event, user);
    }

    public void removeEventFromAthlete(Event event, String user) {
        coach.removeEventFromAthlete(event, user);
    }

    public HashMap<String, ArrayList<ArrayList<Event>>> getAllAthleteConflicts() {
        return coach.getAllConflicts();
    }

    public ArrayList<ArrayList<Event>> getAthleteConflicts(String user) {
        return coach.getAthleteConflicts(user);
    }

    public Set<String> getAthletesWithConflicts() {  // should we return a set or convert to an ArrayList
        return coach.getAthletesWithConflicts();
    }
    

} 