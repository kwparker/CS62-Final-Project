package teamsync;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class Athlete {

    String team;
    String name;
    String username;
    String major;
    Schedule schedule;
    
    public Athlete(String name, String username, String team, String major, Schedule schedule){
        this.team = team;
        this.name = name;
        this.username = username;    
        this.major = major;
        this.schedule = schedule;
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

    public Schedule getAthleteSchedule() {
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
     
    public boolean hasConflicts(){
        return schedule.detectConflict();
    }

    public ArrayList<ArrayList<Event>> getAthleteConflicts(){
        return schedule.getConflicts();
    }

    public ArrayList<Event> getAcademicEvents(){
        return schedule.getEventsByType(1);
    }

    public ArrayList<Event> getAthleticEvents(){
        return schedule.getEventsByType(2);
    }

    public void printConflicts() {
        ArrayList<ArrayList<Event>> conflicts = getAthleteConflicts();
        System.out.println("Conflicts for " + name + ":");
        if (conflicts.isEmpty()) {
            System.out.println("None");
            return;
        }

        int count = 1;
        for (ArrayList<Event> conflictPair : conflicts) {
            System.out.println("Conflict " + count + ":");
            for (Event e : conflictPair) {
                System.out.println("\t" + e);
            }
            count++;
        }
    }

    @Override
    public String toString(){
        return "\n\nAthlete: " + name + "\nTeam: " + team + "\nMajor: " + major + "\nSchedule:\n" + schedule;
    }

    public static void main(String[] args) { // testing toString
        Schedule schedule1 = new Schedule();
        Schedule schedule2 = new Schedule();
        Schedule schedule3 = new Schedule();
        
        Athlete athlete = new Athlete("Guy", "guy123", "Soccer", "CS", schedule1);
        Athlete athlete2 = new Athlete("Kai", "kai456", "Swim", "CS", schedule2);
        Athlete athlete3 = new Athlete("Tiernan", "tiernan789", "XC", "Math", schedule3);

        // create guy's schedule
        Event classEvent = new Event(
            new dateTimePair(LocalDate.of(2024, 9, 2), LocalTime.of(10, 0)),  // start pair
            new dateTimePair(LocalDate.of(2024, 9, 2), LocalTime.of(11, 15)), // end pair
            "Calculus Class", 1, "Room 101"
        );

        
        Event practiceEvent = new Event(
            new dateTimePair(LocalDate.of(2024, 9, 2), LocalTime.of(16, 0)),  // start pair
            new dateTimePair(LocalDate.of(2024, 9, 2), LocalTime.of(18, 0)),  // end pair
            "Soccer Practice",
            2,
            "Field A"
        );

        // add events to guy's schedule
        athlete.addEvent(classEvent);
        athlete.addEvent(practiceEvent);

        // Kai's events
        Event swimClass = new Event(
            new dateTimePair(LocalDate.of(2024, 9, 2), LocalTime.of(14, 30)),
            new dateTimePair(LocalDate.of(2024, 9, 2), LocalTime.of(15, 30)),
            "Swim Techniques", 1, "Pool Room"
        );
        Event weightTraining = new Event(
            new dateTimePair(LocalDate.of(2024, 9, 2), LocalTime.of(15, 15)),
            new dateTimePair(LocalDate.of(2024, 9, 2), LocalTime.of(16, 30)),
            "Weight Training", 2, "Gym"
        );
        Event teamMeeting = new Event(
            new dateTimePair(LocalDate.of(2024, 9, 2), LocalTime.of(17, 00)),
            new dateTimePair(LocalDate.of(2024, 9, 2), LocalTime.of(17, 45)),
            "Team Strategy Meeting", 2, "Locker Room"
        );

        Event recoverySession = new Event(
            new dateTimePair(LocalDate.of(2024, 9, 2), LocalTime.of(17, 30)),
            new dateTimePair(LocalDate.of(2024, 9, 2), LocalTime.of(18, 15)),
            "Recovery Session", 2, "Wellness Center"
        );

        athlete2.addEvent(swimClass);
        athlete2.addEvent(weightTraining);
        athlete2.addEvent(teamMeeting);
        athlete2.addEvent(recoverySession);

        // Tiernan's events (with a conflict)
        Event mathClass = new Event(
            new dateTimePair(LocalDate.of(2024, 9, 3), LocalTime.of(9, 0)),
            new dateTimePair(LocalDate.of(2024, 9, 3), LocalTime.of(10, 30)),
            "Linear Algebra", 1, "Room 205"
        );
        Event morningPractice = new Event(
            new dateTimePair(LocalDate.of(2024, 9, 3), LocalTime.of(10, 15)),
            new dateTimePair(LocalDate.of(2024, 9, 3), LocalTime.of(11, 30)),
            "XC Morning Practice", 2, "Track"
        );
        Event teamLunch = new Event(
            new dateTimePair(LocalDate.of(2024, 9, 3), LocalTime.of(12, 0)),
            new dateTimePair(LocalDate.of(2024, 9, 3), LocalTime.of(13, 0)),
            "Team Lunch", 2, "Dining Hall"
        );

        athlete3.addEvent(mathClass);
        athlete3.addEvent(morningPractice);
        athlete3.addEvent(teamLunch);

        // list of all athletes
        ArrayList<Athlete> allAthletes = new ArrayList<>();
        allAthletes.add(athlete);
        allAthletes.add(athlete2);
        allAthletes.add(athlete3);

        // iterate through each athlete and print conflicts if any
        for (Athlete a : allAthletes) {
            System.out.println(a);
                a.printConflicts();
                // System.out.println("\n");
            }
    }
}
