package teamsync;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// combine coach and their athletes into the same arrayList
// DIFFERENT CLASS maybe school/college? : hashmap where buckets are the teams, like soccer, swim, track, etc. 
    // inside the buckets is the coach / players of that team
public class Team {

    String teamName;
    int type;  // do we need this?
    ArrayList<Athlete> athletes;
    Coach coach;
    static final List<Integer> PERSON_TYPES = Arrays.asList(1,2);  // options for the type of person on team

    public Team(String teamName, Coach coach, ArrayList<Athlete> athletes) {  // this is just assuming there's only 1 coach
        this.teamName = teamName.toLowerCase();
        this.coach = coach;
        this.athletes = athletes;
        
        // if (PERSON_TYPES.contains(type)) {  // if type of event is valid
        //     this.type = type;  // sets this.eventType to be inputted eventType
        // } else {
        //     throw new IllegalArgumentException("Invalid eventType: must be 1 (coach) or 2 (athlete)");  // throws exception if type is invalid
        // }

    }

    

    

    
} 