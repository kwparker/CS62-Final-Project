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

/**
 * Class representation of a coach
 * 
 * @author Kai Parker, Guy Fuchs, Tiernan Colby
 */
public class Coach {
    
    String team;  // team the coach is part of
    String name;  // coach's name
    String username;  // coach's username
    Schedule schedule;  // coach's schedule
    HashMap<String, Athlete> athletes;  // map of all the coach's athletes

    public Coach(String name, String username, String team, Schedule schedule, HashMap<String, Athlete> athletes){
        this.name = name;
        this.team = team;
        this.username = username;
        this.schedule = schedule;
        this.athletes = athletes;
    }

    /**
     * adds an athlete to the coach's athlete map
     * @param athlete being added to the team
     */
    public void addAthlete(Athlete athlete){
        this.athletes.put(athlete.username, athlete);
    }

    /**
     * gets the coach's name
     * @return name of coach
     */
    public String getName() {
        return name;
    }

    /**
     * sets the coach's name
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * gets the coach's username
     * @return the username of coach
     */
    public String getUserName() {
        return username;
    }

    /**
     * sets the coach's username
     * @param username
     */
    public void setUserName(String username) {
        this.username = username;
    }

    /**
     * gets the name of the coach's team
     * @return name of coach's team
     */
    public String getTeam() {
        return team;
    }

    /**
     * sets the name of coach's team
     * @param team name
     */
    public void setTeam(String team) {
        this.team = team;
    }

    /**
     * gets the coach's schedule
     * @return coach's schedule
     */
    public Schedule getCoachSchedule() {
        return schedule;
    }

    /**
     * add event to the coach's schedule
     * @param event being added to schedule
     */
    public void addEventToCoach(Event event) {
        schedule.addEvent(event);
    }

    /**
     * removes an event from coach's schedule
     * @param event to remove from schedule
     * @return event removed from schedule
     */
    public Event removeEvent(Event event) {
        return schedule.removeEvent(event);
    }

    /**
     * remove an event from all the coach's athletes
     * @param event to remove
     */
    public void removeEventFromAthletes(Event event) {
        for (String user: athletes.keySet()) {  // for username in the athlete map
            Athlete athlete = athletes.get(user);  // get the athlete corresponding to the username
            athlete.removeEvent(event);  // remove the event from that athlete's schedule
        }
    }

    /**
     * remove an event from a particular athlete
     * @param event to remove
     * @param user to remove event from
     */
    public void removeEventFromAthlete(Event event, String user) {
        Athlete athlete = athletes.get(user); // get the athlete
        athlete.removeEvent(event); // remove event from schedule
    }

    /**
     * removes an event from the entire team
     * @param event to remove from the team
     */
    public void removeEventFromTeam(Event event) {
        removeEventFromAthletes(event);  // from event from athletes
        removeEvent(event);  // remove event from coach
    }

    /**
     * replaces an event with a different event
     * @param original event to change
     * @param newEvent event to replace old event with
     */
    public void editEvent(Event original, Event newEvent) {
        schedule.editEvent(original, newEvent);
    }
    
    /**
     * check if coach has conflicts
     * @return boolean of whether coach has conflicts
     */
    public boolean hasConflicts(){
        return schedule.detectConflict();
    }

    /**
     * get coach's conflicts
     * @return ArrayList<ArrayList<Event>> of the coach's conflicts
     */
    public ArrayList<ArrayList<Event>> getCoachConflicts(){
        return schedule.getConflicts();
    }

    /**
     * add an event to every athlete's schedule
     * @param event to add to every athlete
     */
    public void addEventToAllAthletes(Event event) {
        for (String user: athletes.keySet()) {  // for every username in the athlete map
            Athlete athlete = athletes.get(user); // get the athlete corresponding to username
            athlete.addEvent(event); // add event to athlete
        }
    }

    /**
     * adds an event to the whole team
     * @param event being added to team
     */
    public void addEventToTeam(Event event) {
        addEventToAllAthletes(event);  // adds events to athletes
        addEventToCoach(event); // adds events to coach
    }

    /**
     * add an event to an athlete's schedule
     * @param event to add
     * @param username to add event to
     */
    public void addEventToAthlete(Event event, String username) {
        athletes.get(username).addEvent(event);  // gets athlete and adds event to their schedule
    }

    /**
     * get a particular athlete's schedule
     * @param username of athlete
     * @return schedule of given athlete
     */
    public Schedule getAthleteSchedule(String username) {
        return athletes.get(username).schedule;
    }

    /**
     * get list all athletes
     * @return ArrayList<Athlete> of the coach's athletes
     */
    public ArrayList<Athlete> getAllAthletes() {
        ArrayList<Athlete> athleteList = new ArrayList<Athlete>(); // array list of athletes
        for (String user: athletes.keySet()) {  // for each user in the athlete map
            athleteList.add(athletes.get(user)); // get the athlete and add them to the list
        } 
        return athleteList;  // full list of athletes
    }

    /**
     * remove athlete from team
     * @param username of athlete coach wants to remove
     */
    public void removeAthlete(String username) {  // do we want this method to be void?
        athletes.remove(username);
    }

    /**
     * get all athletes' conflicts
     * @return map of the athletes' usernames and conflicts
     */
    public HashMap<String, ArrayList<ArrayList<Event>>> getAllConflicts() {  
        HashMap<String, ArrayList<ArrayList<Event>>> conflictMap = new HashMap<String, ArrayList<ArrayList<Event>>>(); // hashmap w/ username of athlete that maps to their conflicts
        for (String user: athletes.keySet()) {  // for each user in the map of athletes
            Athlete athlete = athletes.get(user);  // get the athlete
            if (athlete.hasConflicts()) {  // if athlete has conflicts
                conflictMap.put(user, athlete.getAthleteConflicts());  // put the athlete and conflicts in the conflict map
            }
        }

        return conflictMap; // map of athletes and their conflicts
    }

    /**
     * get a particular athlete's conflicts
     * @param username of the athlete
     * @return the athlete's conflicts
     */
    public ArrayList<ArrayList<Event>> getAthleteConflicts(String username) {
        return athletes.get(username).getAthleteConflicts();
    }

    /**
     * get all the athletes with conflicts
     * @return list of athlete's usernames with conflicts
     */
    public ArrayList<String> getAthletesWithConflicts() {
        ArrayList<Athlete> conflictList = new ArrayList<Athlete>();
        HashMap<String, ArrayList<ArrayList<Event>>> conflictMap = getAllConflicts();  // gets the conflict map

        for (String user: conflictMap.keySet()) {  // for every user in the conflict map
            conflictList.add(athletes.get(user));  // adds athlete to the conflict list 
        }
       
        ArrayList<String> userList = new ArrayList<String>();
        
        for (Athlete athlete: conflictList) {  // for each athlete in the conflict list
            userList.add(athlete.username);  // add their username to the user list
        }
        
        return userList;  // the list of usernames with conflicts
    }

    /**
     * Clears the coach's schedule
     */
    public void clearCoachSched() {
        schedule.clearSchedule();
    }

    /**
     * Prints the team roster
     */
    public void printTeamRoster(){
        ArrayList<Athlete> teamAthletes = getAllAthletes(); // get the athletes on the team
        if (teamAthletes.isEmpty()){ // check if there are no athletes
            System.out.println("\nThere are currently no athletes on your team.");
            return; // exit the method
        }

        System.out.println("\nThe athletes on your team are: ");
        for (Athlete athlete : teamAthletes) {
            System.out.println(athlete.simpleToString()); // prints each athlete on the team
        }
    }

    /**
     * 
     * @param year graduation year of the athlete
     * @return ArrayList<Athlete> with the corresponding graduation year
     */
    public ArrayList<Athlete> filterByYear(int year) {
        ArrayList<Athlete> athletesYear = new ArrayList<Athlete>();  // arrayList of athletes
        for (String user: athletes.keySet()) {  // for each user in the athlete map
            int gradYear = (athletes.get(user)).gradYear;  // gets their grad year
            if (gradYear == year) {
                athletesYear.add(athletes.get(user));  // adds athletes to list if they have the given grad year
            }
        }

        return athletesYear;  // list of athletes with given grad year

    }

    
    /**
     * assumes startDate and endDate in the form 2025-05-13, year-month-day
     * assume that the start date and end date are valid for fall 2024
     * this method take a file path that contains a weekly scedule of athletic practices/games/etc, a start date, end date, and creates
     * an arralist of events which covers the time interval with the weekly pratice schedule - this maps a weekly athletic schedule to a 
     * time interval, likely the fall 2024 semester
     * 
     * @param filepath path of practice schedule file
     * @param startDate start date of the athletic season
     * @param endDate end date of the athleteic season
     * @return ArrayList<Event> with the schedule for the whole season
     */
    public ArrayList<Event> createPracticeSchedule(String filepath, LocalDate startDate, LocalDate endDate){

        ArrayList<Event> practiceSchedule = new ArrayList<>();  // practice schedule array list
        
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

                    for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) { // goes through all the days in date interval
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
            System.out.println("Invalid file.");
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

    }


}
