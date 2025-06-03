package src.teamsync;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

/**
 * Class to represent an athlete
 * 
 * @author Kai Parker, Guy Fuchs, Tiernan Colby
 */
public class Athlete {

    // fields representing basic athlete details
    String team;
    String name;
    String username;
    String major;
    Schedule schedule; // contains all events 
    int gradYear;
    ArrayList<Course> enrolledCourses;;
    

    /**
     * constructs an Athlete object with given parameters
     * @param name      the full name of the athlete
     * @param username  the username used to identify the athlete
     * @param team      the name of the athletic team the athlete belongs to
     * @param major     the athlete's major field of study
     * @param schedule  the athlete's schedule of events
     * @param gradYear  expected graduation year
     */
    public Athlete(String name, String username, String team, String major, Schedule schedule, int gradYear){
        this.team = team;
        this.name = name;
        this.username = username;    
        this.major = major;
        this.schedule = schedule;
        this.gradYear = gradYear;
        enrolledCourses = new ArrayList<Course>(); // start with no courses
    }

    /**
     * gets and returns the name of the athlete
     * @return name of athlete
     */
    public String getName() {
        return name;
    }

    /**
     * sets the name of the athlete
     * @param name of the athlete
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * gets and returns the athlete's username 
     * @return username of athlete
     */
    public String getUserName() {
        return username;
    }
    
    /**
     * sets the athlete's username
     * @param username of the athlete
     */
    public void setUserName(String username) {
        this.username = username;
    }

    /**
     * gets and returns the name of the athlete's team
     * @return name of athlete's team
     */
    public String getTeam() {
        return team;
    }

    /**
     * sets the athlete's team
     * @param team athlete is on
     */
    public void setTeam(String team) {
        this.team = team;
    }

    /**
     * gets and returns the athlete's schedule
     * @return athlete's schedule
     */
    public Schedule getAthleteSchedule() {
        return schedule;
    }

    /**
     * adds an event to the athlete's schedule
     * @param event athlete wants to add
     */
    public void addEvent(Event event) {
        schedule.addEvent(event);
    }

    /**
     * removes event from athlete's schedule
     * @param event to removed from schedule
     * @return the event that was removed
     */
    public Event removeEvent(Event event) {
        return schedule.removeEvent(event);
    }

    /**
     * replaces an event with a different event
     * @param original old event
     * @param newEvent new event
     */
    public void editEvent(Event original, Event newEvent) {
        schedule.editEvent(original, newEvent);
    }
     
    /**
     * checks if there are conflicts in the athlete's schedule
     * @return a boolean
     */
    public boolean hasConflicts(){
        return schedule.detectConflict();
    }

    /**
     * gets all of the athlete's conflicts
     * @return an ArrayList<ArrayList<Event>>> of the athlete's conflicts
     */
    public ArrayList<ArrayList<Event>> getAthleteConflicts(){
        return schedule.getConflicts();
    }

    /**
     * gets academic events in the athlete's schedule
     * @return an ArrayList<Event> of the academic events
     */
    public ArrayList<Event> getAcademicEvents(){
        return schedule.getEventsByType(1);
    }

    /**
     * gets athletic events in the athlete's schedule
     * @return ArrayList<Event> of athletic events
     */
    public ArrayList<Event> getAthleticEvents(){
        return schedule.getEventsByType(2);
    }

    /**
     * prints all conflicts in an athlete's schedule
     */
    public void printConflicts() {
        ArrayList<ArrayList<Event>> conflicts = getAthleteConflicts();  // create a list of conflicts
        System.out.println("Conflicts for " + name + ":");
        if (conflicts.isEmpty()) {  // if there are no conflcits
            System.out.println("None");
            return;
        }

        int count = 1;
        for (ArrayList<Event> conflictPair : conflicts) {  // for each conflict pair in the list of conflicts
            System.out.println("Conflict " + count + ":");  // print which number conflict it is
            for (Event e : conflictPair) {
                System.out.println("\t" + e); // print the conflicting events
            }
            count++;  // increment the count of conflicts
        }
    }

    /**
     * clears the athlete's schedule
     */
    public void clearAthleteSched() {
        schedule.clearSchedule();
    }

    /**
     * adds the course to the athlete's enrolled courses
     * @param course to enroll in
     */
    public void enrollCourse(Course course){
        enrolledCourses.add(course);  // adds the course to the list of schedule
        for (Event event: course.courseToEvent()){  // for each event in the list of course to event
            addEvent(event);  // add the event to the schedule
        }
    }

    /**
     * gets all teh courses the athlete is enrolled in
     * @return ArrayList<Course> of courses the athlete is enrolled in
     */
    public ArrayList<Course> getEnrolledCourses(){
        return enrolledCourses;
    }

    /**
     * a simple representation of the athlete
     * @return a string representing some of athlete's info
     */
    public String simpleToString() {
        return "\nAthlete: " + name + "\nGrad Year: " + gradYear;
    }

    /**
     * more complex representation of the athlete
     * @return a string representing all of the athlete's info
     */
    @Override
    public String toString(){
        return "\n\nAthlete: " + name + "\nTeam: " + team + "\nMajor: " + major + "\nGrad Year: " + gradYear + "\nSchedule:\n" + schedule;
    }

    // testing athlete methods
    public static void main(String[] args) {

        // create schedule objects
        Schedule schedule1 = new Schedule();
        Schedule schedule2 = new Schedule();
        Schedule schedule3 = new Schedule();
        
        // create athlete objects
        Athlete athlete = new Athlete("Guy", "guy123", "Soccer", "CS", schedule1, 2027);
        Athlete athlete2 = new Athlete("Kai", "kai456", "Swim", "CS", schedule2, 2027);
        Athlete athlete3 = new Athlete("Tiernan", "tiernan789", "XC", "Math", schedule3, 2027);

        // create a class event
        Event classEvent = new Event(
            new dateTimePair(LocalDate.of(2024, 9, 2), LocalTime.of(10, 0)),  // start pair
            new dateTimePair(LocalDate.of(2024, 9, 2), LocalTime.of(11, 15)), // end pair
            "Calculus", 1, "Estella"
        );

        // create a practice event
        Event practiceEvent = new Event(
            new dateTimePair(LocalDate.of(2024, 9, 2), LocalTime.of(16, 0)),  // start pair
            new dateTimePair(LocalDate.of(2024, 9, 2), LocalTime.of(18, 0)),  // end pair
            "Soccer Practice",
            2,
            "Pomona Soccer Field"
        );

        // add events to guy's schedule
        athlete.addEvent(classEvent);
        athlete.addEvent(practiceEvent);

        // Kai's events
        Event dataStructures = new Event(
            new dateTimePair(LocalDate.of(2024, 9, 2), LocalTime.of(14, 30)),
            new dateTimePair(LocalDate.of(2024, 9, 2), LocalTime.of(15, 30)),
            "Data Structures", 1, "Edmunds"
        );
        Event weightTraining = new Event(
            new dateTimePair(LocalDate.of(2024, 9, 2), LocalTime.of(15, 15)),
            new dateTimePair(LocalDate.of(2024, 9, 2), LocalTime.of(16, 30)),
            "Weight Training", 2, "Varsity Gym"
        );
        Event teamMeeting = new Event(
            new dateTimePair(LocalDate.of(2024, 9, 2), LocalTime.of(17, 00)),
            new dateTimePair(LocalDate.of(2024, 9, 2), LocalTime.of(17, 45)),
            "Team Strategy Meeting", 2, "Carw"
        );

        Event recoverySession = new Event(
            new dateTimePair(LocalDate.of(2024, 9, 2), LocalTime.of(17, 30)),
            new dateTimePair(LocalDate.of(2024, 9, 2), LocalTime.of(18, 15)),
            "Recovery Session", 2, "Training Room"
        );

        // add events to Kai's schedule
        athlete2.addEvent(dataStructures);
        athlete2.addEvent(weightTraining);
        athlete2.addEvent(teamMeeting);
        athlete2.addEvent(recoverySession);

        // Tiernan's events
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

        // add events to Tiernan's schedule
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
            }
    }
}
