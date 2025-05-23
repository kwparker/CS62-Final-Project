package teamsync;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.HashMap;


/**
 * The class TeamSyncApp serves as the central application class for managing a coach and their athletes.
 * Provides CLI-based interactions to add, view, and manage academic and athletic schedules, register for courses, and
 * handle conflicts.
 * 
 * @author Kai Parker, Guy Fuchs, Tiernan Colby
 */
public class TeamSyncApp {

    Schedule defaultSchedule; // shared default schedule used to clone for new athletes
    public Coach coach; // the coach user
    public HashMap<String, Athlete> athleteMap; // maps usernames to athlete objects
    public Team testTeam; // default test team
    public Scanner scannerIn; // for CLI input

    /**
     * constructs the TeamSync application, loading default course data from hyperschedule and setting up an initial athlete
     */
    public TeamSyncApp() {
        this.scannerIn = new Scanner(System.in); // initialize scanner
        this.athleteMap = new HashMap<String, Athlete>(); // initialize athlete 
        this.defaultSchedule = new Schedule(); // initialize a blank default schedule
        this.coach = new Coach("Test coach", "coach123", "Test team", defaultSchedule, athleteMap); // create a default coach
        this.testTeam = new Team("Test team", this.coach, athleteMap); // create a default team
        
        loadCourseData("Data/course-section-schedule.json"); // load course data from file
        // add a placeholder athlete
        Athlete athlete1 = new Athlete("athlete1", "athlete1", "Not yet specified", "Not yet specified", defaultSchedule.clone(), 2027);
        athleteMap.put("athlete1", athlete1);
    }

    /**
     * starts the main program loop and user type selection menu
     */
    public void runProgram() {
        System.out.println("Welcome to TeamSync!");
        while(true) { // infinite loop for the CLI
            // options to choose
            System.out.println("\nLog in as:");
            System.out.println("1. Coach");
            System.out.println("2. Athlete");
            System.out.println("3. Exit");
            String userType = scannerIn.nextLine();

            if (userType.equals("1")) {
                runCoach(); // launch the coach menu
            } else if (userType.equals("2")) {
                runAthlete(); // launch the athlete menu
            } else if (userType.equals("3")) {
                System.out.println("Goodbye! Please come back soon.");
                return; // exit the app
            } else {
                System.out.println("Invalid input. Please input 1, 2, or 3."); // handles an invalid input
            }
        }
    }

    /**
     * launches the coach-specific menu and handles coach operations
     */
    public void runCoach() {
        Schedule coachSchedule = new Schedule(); // new schedule for this session
        coach = new Coach("Coach Name", "coach123", "TeamName", coachSchedule, athleteMap); // assign coach object
        
        while(true){ // loop for coach menu
            // options to choose as a coach
            System.out.println("\n*** Coach Menu ***");
            System.out.println("1. View your schedule");
            System.out.println("2. Add event to team");
            System.out.println("3. View an athlete's schedule");
            System.out.println("4. View athlete conflicts");
            System.out.println("5. Input or change athletic schedule");
            System.out.println("6. View team roster");
            System.out.println("7. Add an athlete to the team");
            System.out.println("8. Go back");

            String userChoice = scannerIn.nextLine(); // read the menu choice

            if (userChoice.equals("1")) { // to view coach's sc
                if (coach.getCoachSchedule().isEmpty()){ // checks if the coach has an empty schedule
                    System.out.println("You currently have no events in your schedule."); // states that coach has empty schedule
                }
                else{
                    System.out.println(coach.getCoachSchedule()); // show coach schedule
                }

            } else if (userChoice.equals("2")) {
                Event event = eventFromInput(); // prompts user to create an event
                coach.addEventToTeam(event); // add to coache and team
                defaultSchedule.addEvent(event); // to make sure all new athletes will also have this newly added event
                System.out.println("Event added to athletes and coach.");
            } else if (userChoice.equals("3")) { // to view an athlete's schedule
                while (true) { // loop until a valid username is entered or user goes back
                    System.out.println("Enter the athlete's username");
                    String user = scannerIn.nextLine();

                    if (!coach.athletes.containsKey(user)) { // if the username doesn't exist
                        boolean resolved = false; // flag to control the nested loop
                        while(!resolved){ // keep prompting until resolved
                            System.out.println("Athlete doesn't exist. Select an option below.");
                            System.out.println("1. Enter a different username"); // option to re-enter username
                            System.out.println("2. Go back"); // option to 
                            String input = scannerIn.nextLine();
    
                            if (input.equals("1")) { // chooses to re-enter username
                                resolved = true; // break inner loop, start outer loop
                            } 
                            else if (input.equals("2")) { // chooses to go back
                                return; // back to coach menu
                            } else {
                                System.out.println("Invalid input. Please input 1 or 2."); // prompt again, stay in inner loop
                            }
                        }
                    } else { // athlete exists
                        Athlete athlete = coach.athletes.get(user); // retrieve the athlete object
                        System.out.println("Schedule for: " + athlete.getName());
                        if (athlete.getAthleteSchedule().isEmpty()){ // if the athlete's schedule is empty
                            System.out.println("Schedule is currently empty.");
                        }
                        else{ // if schedule has events
                            System.out.println(athlete.getAthleteSchedule()); // print the full schedule
                        }
                        break; // exit loop
                    }
                }
            } else if (userChoice.equals("4")) { // chooses to view athlete conflicts
                for (Athlete athlete: coach.getAllAthletes()) { // loop through all conflicts
                    athlete.printConflicts(); // print each athlete's co
                } 
                
            } else if (userChoice.equals("5")) { // chooses to input or change athletic schedule
                if (!defaultSchedule.isEmpty()) { // if schedule has events
                    System.out.println("Do you want to clear the current athletic schedule? (Y/N)");
                    String decision = scannerIn.nextLine().toUpperCase();
                    if (decision.equals("Y")) { // if yes, clear schedule
                        for (Athlete athlete: coach.getAllAthletes()){ // loop through athletes
                            // get athletic events for the athlete
                            ArrayList<Event> athleticEvents = new ArrayList<>(athlete.getAthleteSchedule().getEventsByType(2));
                            for (Event event: athleticEvents){ // loop through the events in the athletic events list for the athlete
                                athlete.removeEvent(event); // remove each event
                            }
                        }

                        coach.getCoachSchedule().clearSchedule(); // clear coach schedule
                        defaultSchedule.clearSchedule(); // clear default schedule
                        System.out.println("Old athletic schedule cleared."); 
                    }
                    else{ // if not clearing
                        System.out.println("Athletic schedule not cleared.");
                    }
                }
                
                String filePath = null; // path to the file
                while (filePath == null) { // loop until valid file inputted
                    System.out.println("Input athletic schedule filename.csv (Part of path after 'Data/') ");
                    String fileName = scannerIn.nextLine(); // read filename
                    filePath = "Data/" + fileName; // build the full path by adding Data/
                    if (!(new File(filePath)).exists()) { // check if file exists
                        System.out.println("File not found. Please enter a valid file name"); // error message
                        filePath = null; // reset path
                    }
                }
                
                System.out.println("Input season start date in form YYYY-MM-DD"); // start date
                LocalDate startDate = null; 
                while (startDate == null) { // loop until valid date
                    String date = scannerIn.nextLine();
                    try {
                        startDate = LocalDate.parse(date); // try parsing the date
                    } catch (DateTimeException e) { // handle bad inputs
                        System.out.println("Invalid date. Please use YYYY-MM-DD format.");
                    } 
                }

                System.out.println("Input season end date in form YYYY-MM-DD"); // end date
                LocalDate endDate = null;
                while (endDate == null) { // loop until valid date
                    String date = scannerIn.nextLine();
                    try {
                        endDate = LocalDate.parse(date); // try parsing the date
                    } catch (DateTimeException e) { // handle bad inputs
                        System.out.println("Invalid date. Please use YYYY-MM-DD format");
                    }
                }

                // generate practice schedule
                ArrayList<Event> practiceSchedule = coach.createPracticeSchedule(filePath, startDate, endDate);
                // loop through the events
                for (Event event: practiceSchedule) {
                    coach.addEventToTeam(event); // add to team
                    this.defaultSchedule.addEvent(event); // add to default for new athletes
                }

                System.out.println("Athletic schedule uploaded.");

            }
            else if (userChoice.equals("6")) { // chooses to view team roster
                coach.printTeamRoster(); // print the athletes on the team roster
            }
            
            else if (userChoice.equals("7")) { // chooses to add a new athlete
                System.out.println("\n*** Add a New Athlete ***");

                System.out.println("Enter the athlete's name: ");
                String name = scannerIn.nextLine(); // read name

                String username;
                while (true) { // loop until valid username
                    System.out.println("Enter a username for the athlete: ");
                    username = scannerIn.nextLine();
                    if (athleteMap.containsKey(username)) { // check if the username already exists
                        System.out.println("Username already exists. Please choose a different one."); // prompt for a new username
                    } else {
                        break; // exit loop if valid unique username
                    }
                }

                System.out.println("Enter the athlete's team: ");
                String team = scannerIn.nextLine(); // read the team

                System.out.println("Enter the athlete's major: ");
                String major = scannerIn.nextLine(); // read the major

                int gradYear = 0;
                while (gradYear == 0) { // loop until valid year
                    System.out.println("Enter the athlete's grad year: ");
                    try {
                        gradYear = Integer.parseInt(scannerIn.nextLine()); // try parsing the grad year
                    } catch (NumberFormatException e) { // handle bad inputs
                        System.out.println("Invalid input. Please enter a valid year.");
                    }
                }

                // create the athlete
                Athlete newAthlete = new Athlete(name, username, team, major, defaultSchedule.clone(), gradYear);
                athleteMap.put(username, newAthlete); // add the athlete to the map
                coach.addAthlete(newAthlete); // add the athlete to coach's tea
                System.out.println("\n" + name + " added to your team."); // 
            }
            else if (userChoice.equals("8")) {
                return;
            }
            else {
                System.out.println("Invalid input. Please choice a number between 1 and 8.");
            }
        }
    }

    /**
     * launches the athlete-specific menu and handles athlete operations
     */
    public void runAthlete() {
        System.out.print("Enter your username: ");
        String username = scannerIn.nextLine();  // gets athlete's username

        Athlete athlete = null;  // creates null athlete

        while (athlete == null) {
            if (athleteMap.get(username) != null) {  // if athlete already exists
                athlete = athleteMap.get(username);  // gets the athlete
                break;
            }
            
            // if username not found
            System.out.println("User not found. Please enter a username to create an account.");
            String inputUser = scannerIn.nextLine(); // saves athlete username
            System.out.println("Enter your name.");
            String inputName = scannerIn.nextLine(); // saves athlete name
            System.out.println("Enter your team");
            String inputTeam = scannerIn.nextLine();  // saves athlete's team
            System.out.println("Enter your major");
            String inputMajor = scannerIn.nextLine();  // saves athlete's major

            int inputGradYear;

            while (true){
                System.out.println("Enter your grad year");
                try {
                    inputGradYear = scannerIn.nextInt();  // saves grad year
                    break;
                }
                catch (Exception e){  // catches exception
                    System.out.println("Invalid grad year. Please enter a graduation year as an integer.");
                    scannerIn.nextLine();
                }
            }
            
            scannerIn.nextLine();
            
            athlete = new Athlete(inputName, inputUser, inputTeam, inputMajor, defaultSchedule.clone(), inputGradYear);
            athleteMap.put(inputUser, athlete);  // adds athlete to the athleteM map
            
            System.out.println("Account created.");
        }

        while (true) {

            // displays athlete menu
            System.out.println("\n*** Athlete Menu (" + athlete.getName() + ") ***"); 
            System.out.println("1. View full profile and semester schedule.");
            System.out.println("2. View conflicts");
            System.out.println("3. Add event");
            System.out.println("4. Register for courses");
            System.out.println("5. Back to main menu");
            String choice = scannerIn.nextLine();

            // prints athlete menu
            if (choice.equals("1")){
                System.out.println(athlete);
                continue;
            }
            
            // prints athlete conflicts
            else if (choice.equals("2")){
                athlete.printConflicts();
                continue;
            }

            // adds events to athlete's schedule
            else if (choice.equals("3")){
                Event event = eventFromInput();  // gets event from input
                athlete.addEvent(event);  // adds event
                System.out.println("Event added.");
                continue;
            }

            // handles athlete course registration
            else if (choice.equals("4")) {
                ArrayList<Event> currentAcademicEvents = new ArrayList<>(athlete.getAthleteSchedule().getEventsByType(1)); // gets list of type 1 events
                int currentCourseCount = athlete.enrolledCourses.size();  // gets current number of courses

                if (currentCourseCount == 0){  // if athlete is in no courses
                    System.out.println("You currently have no academic courses.");
                }
                else{
                    ArrayList<Course> enrolledCourses = athlete.getEnrolledCourses(); // gets athlete's enrolled courses
                    System.out.println("Your current academic courses:");
                    int num = 1;
                    for (Course course: enrolledCourses){  // for each course
                        System.out.println(num + ": " + course);  // prints number and course
                        num++;
                    }
                }

                int maxCoursesToAdd = 0;
                boolean validInput = false;

                while(!validInput){

                    if (currentCourseCount == 0) {  // if user is currently in no courses

                        // prompts user with course registration options
                        System.out.println("\nWhat would you like to do?");
                        System.out.println("1. Register for academic courses");
                        System.out.println("2. Go back to the previous menu");

                        String input = scannerIn.nextLine();

                        // moves to course registration
                        if (input.equals("1")){
                            maxCoursesToAdd = 5;
                            validInput = true;
                            break;
                        }

                        // returns user to menu
                        else if (input.equals("2")){
                            System.out.println("Returning to menu.");
                            break;
                        }
                        
                        // handles invalid inputs
                        else{
                            System.out.println("Invalid input. Please enter 1 or 2.");
                            continue;
                        }
                    }

                    else{
                        
                        // provides option menu if athlete is in at least 1 course
                        System.out.println("\nWhat would you like to do?");
                        System.out.println("1. Clear all academic courses and register from scratch.");
                        System.out.println("2. Add to current academic schedule (5 courses max).");
                        System.out.println("3. Go back to the previous menu.");

                        String modeChoice = scannerIn.nextLine();

                        // clears all courses and begins registration
                        if (modeChoice.equals("1")){
                            for (Event event : currentAcademicEvents) {
                                athlete.removeEvent(event);  // removes all events
                            }
                            athlete.getEnrolledCourses().clear();  // clears enrolled courses
                            System.out.println("Academic schedule cleared.");
                            maxCoursesToAdd = 5;  // ccan only register for 5 courses
                            validInput = true;
                        }

                        // handles course registration building from current schedule
                        else if (modeChoice.equals("2")){
                            while (currentCourseCount >= 5){
                                System.out.println("You already have 5 academic courses. Remove one in order to add more.");  // can't add more than 5 events
                                System.out.println("Do you want to remove an academic course? (Y/N)");
                                String response = scannerIn.nextLine().toUpperCase();  // saves response 

                                if (response.equals("Y")){

                                    // printing the courses the athlete is currently enrolled in with the index + 1 number for easy reference
                                    ArrayList<Course> enrolledCourses = athlete.getEnrolledCourses();
                                    System.out.println("Your current academic courses:");
                                    int num = 1;
                                    for (Course course: enrolledCourses){ // prints the courses where they are numbered
                                        System.out.println(num + ": " + course);
                                        num++;
                                    }

                                    System.out.println("Enter the number of the course you'd like to remove: ");
                                    String courseRemovalInput = scannerIn.nextLine();  // saves course remove index

                                    try{
                                        int removeIndex = Integer.parseInt(courseRemovalInput); // parse the index
                                        if (removeIndex >= 1 && removeIndex <= currentAcademicEvents.size()){ // make sure valid index
                                            Event toRemove = currentAcademicEvents.get(removeIndex - 1);  // gets the event to remove
                                            athlete.removeEvent(toRemove);  // removes event from schedule
                                            System.out.println("Removed course: " + toRemove); // removed the course
                                        }
                                        else{
                                            System.out.println("Invalid number. No course was removed.");
                                        }
                                    }
                                    catch (NumberFormatException e){ // handle non-numeric inputs
                                        System.out.println("Invalid input. No course was removed.");
                                    }

                                    // updates the list and the count after removal
                                    currentAcademicEvents = new ArrayList<>(athlete.getAthleteSchedule().getEventsByType(1));
                                    currentCourseCount = currentAcademicEvents.size();
                                }
                                else if (response.equals("N")){ // choose not to remove a course
                                    System.out.println("You selected not to remove a course. Now returning to menu.");
                                    return; // exit to the previous menu
                                }
                                else{
                                    System.out.println("Invalid response. Please enter Y or N."); // handle invalid inputs
                                }
                            }

                            // here we have fewer than 5 courses enrolled in
                            maxCoursesToAdd = 5 - currentCourseCount;  // computes max courses to add
                            System.out.println("You can now add up to: " + maxCoursesToAdd + " more academic course(s)."); // allow course additions
                            validInput = true; // mark true to allow the input to proceed
                        }

                        // returns to menu
                        else if (modeChoice.equals("3")){ // chooses to return
                            System.out.println("Returning to menu now.");
                            break; // exut the current block
                        }
                        
                        // handles invalid input
                        else{
                            System.out.println("Invalid input. Please enter 1, 2, or 3.");
                        }
                    }
                }

                if (!validInput){
                    continue; // return to start of loop if input was invalid
                }

                // prompts filter options for course registration
                System.out.println("Course filter options");
                System.out.println("1. Filter by department");
                System.out.println("2. Filter by athletic schedule");
                System.out.println("3. Filter by both");

                String filterChoice = scannerIn.nextLine(); // read filter choice

                if (filterChoice.equals("1")) { // chooses to filter by department

                    System.out.println("Input department prefix and/or first digits of course number with no space (ex: CSCI1): ");
                    String dept = null;
                    while (dept == null){ // keeps on looping until a valid department prefix
                        dept = scannerIn.nextLine().toUpperCase(); // read and normalize the input
                        ArrayList<Course> filtered = Course.filterByDept(dept); // get filtered courses
                        if (filtered.size() == 0) {
                            System.out.println("There are no courses with this prefix. Please try again.");
                            dept = null; // loop again
                        } else {
                            courseRegistration(filtered, athlete, maxCoursesToAdd); // now can register for classes
                        }
                    }

                }
                else if (filterChoice.equals("2")) { // chooses to filter by conflicts with athletic schedule

                    ArrayList<Course> filtered = Course.filterBySchedule(athlete.getAthleteSchedule()); // flter non-conflicting
                    courseRegistration(filtered, athlete, maxCoursesToAdd); // now can register for classes

                }
                else if (filterChoice.equals("3")) { // chooses to apply both filters

                    System.out.println("Input department prefix"); // prompt department
                    ArrayList<Course> filtered = null;
                    String dept = null;
                    while (dept == null){
                        dept = scannerIn.nextLine().toUpperCase(); // read and nornmalize the input
                        filtered = Course.filterByBoth(dept, athlete.getAthleteSchedule()); // filter both ways
                        if (filtered.size() == 0) {
                            System.out.println("There are no courses with this prefix. Please try again."); // no match
                            dept = null; // repeat loop
                        } else {
                            courseRegistration(filtered, athlete, maxCoursesToAdd); // now can register for classes
                        }
                    }
                    
                }
                else{ // if user input is invalid
                    System.out.println("Invalid filter option. Now returning to athlete menu.");
                }
            }
            else if (choice.equals("5")){ // chooses to go back to main menu
                return;
            }
            else{ // handles invalid inputs
                System.out.println("Invalid input. Pick a number between 1 and 5.");
            }

        }
    }

    /**
     * Converts inputs into an event object
     * @return event representation of inputs
     */
    public Event eventFromInput() {
        System.out.print("Event name: ");
        String eventName = scannerIn.nextLine(); // gets name of event

        System.out.print("Date (YYYY-MM-DD): ");
        LocalDate date = null;
        while (date == null) {
            try {
                date = LocalDate.parse(scannerIn.nextLine()); // gets date if format is valid
            } catch (DateTimeParseException e) {
                System.out.println("Please enter date with format YYYY-MM-DD");  // prompts to input again if wrong format
            }
        }
        
        LocalTime start = null;
        System.out.print("Start time (HH:MM): ");
        while(start == null) {
            try {
                start = LocalTime.parse(scannerIn.nextLine());  // gets start time if format is valid
            } catch (DateTimeParseException e) {
                System.out.println("Please enter time w/ format HH:MM");  // prompts to input again if wrong format
            }
        }
    
        LocalTime end = null;
        System.out.print("End time (HH:MM): ");
        while (end == null) {
            try {
                end = LocalTime.parse(scannerIn.nextLine());  // gets end time if format is valid
                if (end.isBefore(start) || end.equals(start)) {
                    System.out.println("Please enter an time that is after the start time");  // enter again if start time after end time
                    end = null;
                }
            } catch (DateTimeParseException e) {
                System.out.println("Please enter time w/ format HH:MM");  // enter again if wrong format
            }
        }

        int type = 0;
        System.out.print("Event type (1 = Academic, 2 = Athletic, 3 = Other): ");
        while (type == 0) {
            try {
                type = Integer.parseInt(scannerIn.nextLine());  // saves type of event
                if (type != 1 && type != 2 && type !=3) {  // checks that type is valid
                    type = 0;
                    System.out.println("Please enter a 1, 2, or 3.");
                }
            } catch (NumberFormatException e) {  // catches non-integer input
                System.out.println("Please enter a 1, 2, or 3.");
            }
            
        }

        System.out.print("Extra info (e.g., location): ");
        String info = scannerIn.nextLine();  // collects e xtra information

        return new Event(new dateTimePair(date, start), new dateTimePair(date, end), eventName, type, info);  // returns event from inputs
    }

    /**
     * 
     * @param filtered filtered list of courses
     * @param athlete athlete registering for courses
     * @param maxCourses max number of courses to register for
     */
    public void courseRegistration(ArrayList<Course> filtered, Athlete athlete, int maxCourses) {
        ArrayList<Course> coursesEnrolledIn = new ArrayList<>(); // list of courses enrolled in
        int courseAmount = 0;
        
        while (courseAmount == 0) {
            int numCoursesToEnroll = 5 - athlete.enrolledCourses.size();  // computes number of courses athlete can enroll in
            System.out.println("How many courses do you want to want to register for? Pick between 1 and " + numCoursesToEnroll + ".");
            try {
                courseAmount = scannerIn.nextInt();  // parses input
                scannerIn.nextLine();
                if (courseAmount > maxCourses || courseAmount < 1){  // if input is invalid
                    System.out.println("Invalid input. Pick a number between 1 and " + numCoursesToEnroll + ".");
                    courseAmount = 0;  // resets courseAmount to 0
                }
            } catch (InputMismatchException e) {  // catches InputMismatchExcpetion
                System.out.println("Please pick an integer between 1 and " + numCoursesToEnroll + "."); 
                scannerIn.nextLine(); // clear bad input
            }
        }
    
        StringBuilder coursePrint = new StringBuilder();  // creates string builder
        int index = 1;
        for (Course course : filtered) {  // for each course in filtered list of courses
            coursePrint.append(index + ": ");  // adds course index
            coursePrint.append(course + "\n");  // adds course object
            index++;
        }

        System.out.println(coursePrint.toString());  // prints courses

        while (coursesEnrolledIn.size() < courseAmount) {  // while user still needs to register for courses 

            int courseNumber = 0;
            System.out.println("Input number next to course you want to add");
            while (courseNumber == 0) {
                try {
                    courseNumber = scannerIn.nextInt();  // gets index of course to add
                    scannerIn.nextLine();
                    if (courseNumber > filtered.size() || courseNumber < 1) {  // if invalid input
                        System.out.println("Invalid input. Please choose the number next to the course you want");
                        courseNumber = 0;
                    } else if (athlete.enrolledCourses.contains(filtered.get(courseNumber - 1))) {  // if course is already in schedule
                        System.out.println("Course already in schedule. Please choose a different one.");
                        courseNumber = 0;
                    }
                } catch (InputMismatchException e) {  // catches error
                    System.out.println("Please enter a number next to a course");
                    scannerIn.nextLine(); // clear the previous input
                    courseNumber = 0;
                }
            }
            
            Course course = filtered.get(courseNumber - 1);  // gets course to add from filtered list
            coursesEnrolledIn.add(course);  // adds course to courses enrolled in

            System.out.println("Enrolled in:\n " + course);
        }

        for (Course course: coursesEnrolledIn) {  // enroll athlete in desired courses
            athlete.enrollCourse(course);
        }

        System.out.println("Course registration complete.");
    }

    /**
     * loads course data from the the Fall 2024 5c course information
     * @param filePath inputted file path
     */
    public void loadCourseData(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader("Data/course-section-schedule.json"))) {   // try reading the path
            String line;
            Course aCourse = null;

            while ((line = reader.readLine()) != null) {  // while the line is not null
                line = line.trim();  // trims the line

                if (line.startsWith("{")) {
                    aCourse = new Course("", "", "", "", "");  // new blank course
                } 
                else if (line.startsWith("\"courseSectionId\"")) {  // parses course section ID
                    aCourse.setCourseSectionId(Course.getLineValue(line));
                } 
                else if (line.startsWith("\"classBeginningTime\"")) {  // parses course beginning time
                    aCourse.setClassBeginningTime(Course.getLineValue(line));
                } 
                else if (line.startsWith("\"classEndingTime\"")) {  // parses course ending time
                    aCourse.setClassEndingTime(Course.getLineValue(line));
                }
                else if (line.startsWith("\"classMeetingDays\"")) {  // parses course meeting days
                    aCourse.setClassMeetingDays(Course.getLineValue(line));
                } 
                else if (line.startsWith("\"instructionSiteName\"")) {  // parses meeting location
                    aCourse.setInstructionSiteName(Course.getLineValue(line));
                } 
                else if (line.startsWith("}")) {
                    Course.allCourses.add(aCourse);
                }
            }

        } catch (IOException e) {  // error if file's not found
                System.out.println("File Error, file not found");
            }
    }
}


