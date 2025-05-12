package teamsync;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

// combine coach and their athletes into the same arrayList
// DIFFERENT CLASS maybe school/college? : hashmap where buckets are the teams, like soccer, swim, track, etc. 
    // inside the buckets is the coach / players of that team
public class Team {

    String team;
    Coach coach;
    HashMap<String, Athlete> athleteMap;
    ArrayList<Athlete> athleteList;

    public Team(String teamName, Coach coach, ArrayList<Athlete> athletes) {
        this.team = team.toLowerCase();
        this.coach = coach;
        this.athleteList = new ArrayList<>(athletes);
        this.athleteMap = new HashMap<>();            

        for (Athlete athlete : athletes) {
            this.athleteMap.put(athlete.getUserName(), athlete);
        }

        coach.athletes = this.athleteMap;
    }

    // write the methods for this class later

    

    
} 