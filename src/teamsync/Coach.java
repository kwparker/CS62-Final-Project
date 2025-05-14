package teamsync;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Coach {
    
    String team;
    String name;
    String username;
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

    public void addEventToCoach(Event event) {
        schedule.addEvent(event);
    }

    public Event removeEvent(Event event) {
        return schedule.removeEvent(event);
    }

    public void removeEventFromAthletes(Event event) {
        for (String user: athletes.keySet()) {
            Athlete athlete = athletes.get(user);
            athlete.removeEvent(event);
        }
    }

    public void removeEventFromAthlete(Event event, String user) {
        Athlete athlete = athletes.get(user);
        athlete.removeEvent(event);
    }

    public void removeEventFromTeam(Event event) {
        removeEventFromAthletes(event);
        removeEvent(event);
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
        addEventToCoach(event);
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

    public ArrayList<String> getAthletesWithConflicts() {
        ArrayList<Athlete> conflictList = new ArrayList<Athlete>();
        HashMap<String, ArrayList<ArrayList<Event>>> conflictMap = getAllConflicts();

        for (String user: conflictMap.keySet()) {
            conflictList.add(athletes.get(user));
        }
       
        ArrayList<String> userList = new ArrayList<String>();
        
        for (Athlete athlete: conflictList) {
            userList.add(athlete.username);
        }
        
        return userList;
    }

    public void clearCoachSched() {
        schedule.clearSchedule();
    }

    public ArrayList<Athlete> filterByYear(int year) {
        ArrayList<Athlete> athletesYear = new ArrayList<Athlete>();
        for (String user: athletes.keySet()) {
            int gradYear = (athletes.get(user)).gradYear;
            if (gradYear == year) {
                athletesYear.add(athletes.get(user));
            }
        }

        return athletesYear;

    }

    // assumes startDate and endDate in the form 2025-05-13, year-month-day
    // assume that the start date and end date are valid for fall 2024
    // this method take a file path that contains a weekly scedule of athletic practices/games/etc, a start date, end date, and creates an arralist of events which covers
    // the time interval with the weekly pratice schedule - this maps a weekly athletic schedule to a time interval, likely the fall 2024 semester 
    public ArrayList<Event> createPracticeSchedule(String filepath, LocalDate startDate, LocalDate endDate){

        ArrayList<Event> practiceSchedule = new ArrayList<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            String line;
            
            int linecounter = 0;

            while ((line = br.readLine()) != null) {
                if (linecounter != 0){

                        String[] row = line.split(",");
                        
                        String eventName = row[0].trim();
                        String dayOfWeekSt = row[1].trim();

                        DayOfWeek dayOfWeek = DayOfWeek.valueOf(dayOfWeekSt.toUpperCase());

                        String startTimeSt = row[2].trim();

                        LocalTime startTime = LocalTime.parse(startTimeSt);

                        String endTimeSt = row[3].trim();

                        LocalTime endTime = LocalTime.parse(endTimeSt);

                        for (LocalDate date = start; !date.isAfter(end); date = date.plusDays(1)) { // goes through all the days in date interval
                            if (date.getDayOfWeek().equals(dayOfWeek)){

                                dateTimePair startPair = new dateTimePair(date, startTime);
                                dateTimePair endPair = new dateTimePair(date, endTime);

                                Event athleticEvent = new Event(startPair, endPair, eventName, 2, "");

                                practiceSchedule.add(athleticEvent); // adds the date

                                }
                        }
                    }

                linecounter +=1;
                
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return practiceSchedule;
    }
        



    @Override
    public String toString(){
        StringBuilder output = new StringBuilder();
        output.append("\n\nCoach: " + name + "\nTeam: " + team + "\n: " + "\nSchedule:\n" + schedule + "\nAthletes:\n");
        for (String user: athletes.keySet()) {
            output.append(athletes.get(user).simpleToString());
        }
        return output.toString();
    }

    public static void main(String[] args) {

        
        // Create schedules
        Schedule schedule1 = new Schedule();
        Schedule schedule2 = new Schedule();
        Schedule schedule3 = new Schedule();
    
        // Create athletes
        Athlete athlete1 = new Athlete("Tiernan", "tiernan123", "Swim", "Math", schedule1, 2026);
        Athlete athlete2 = new Athlete("Guy", "guy123", "Swim", "Physics", schedule2, 2025);


    
        // Create map of athletes
        HashMap<String, Athlete> athleteMap = new HashMap<>();
        athleteMap.put(athlete1.getUserName(), athlete1);
        athleteMap.put(athlete2.getUserName(), athlete2);
    
        // Create coach
        Coach coach = new Coach("Coach Gowdy", "coachJPG", "Swim", schedule3, athleteMap);
    
        // Create an event to add to whole team
        Event practice = new Event( new dateTimePair(LocalDate.of(2024, 9, 4), LocalTime.of(15, 0)),
            new dateTimePair(LocalDate.of(2024, 9, 4), LocalTime.of(17, 0)),
            "Swim Practice", 2, "Haldeman Pool"
        );
    
        // Add event to all athletes + coach
        coach.addEventToTeam(practice);
    
        // Add a conflicting event for Guy
        Event meeting = new Event(
            new dateTimePair(LocalDate.of(2024, 9, 4), LocalTime.of(16, 30)),
            new dateTimePair(LocalDate.of(2024, 9, 4), LocalTime.of(18, 0)),
            "Meeting", 1, "Coach's Office"
        );
        coach.addEventToAthlete(meeting, "guy123");
    
        // Print out schedules and conflicts
        System.out.println("\nCoach Info ");
        System.out.println(coach);
    
        System.out.println("\nAthlete Schedules ");
        for (Athlete a : coach.getAllAthletes()) {
            System.out.println(a);
            a.printConflicts();
        }
    
        // Print all conflicts
        System.out.println("\nAll Conflicts ");
        HashMap<String, ArrayList<ArrayList<Event>>> allConflicts = coach.getAllConflicts();
        for (String user : allConflicts.keySet()) {
            System.out.println("Conflicts for " + user + ":");
            for (ArrayList<Event> conflictPair : allConflicts.get(user)) {
                for (Event event : conflictPair) {
                    System.out.println(event);
                }
            }
        }

        // prints the arrylist with the events for the time interval
        //System.out.println(coach.createPracticeScedule("Data/SoccerSampleSchedule.csv", "2024-09-11", "2024-11-01"));
        
        
        
    }


}
