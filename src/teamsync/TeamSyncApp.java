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


public class TeamSyncApp {

    Schedule defaultSchedule;

    public Coach coach;
    public HashMap<String, Athlete> athleteMap;
    public Team testTeam;
    public Scanner scannerIn;

    public TeamSyncApp() {
        this.scannerIn = new Scanner(System.in);
        this.athleteMap = new HashMap<String, Athlete>();
        this.defaultSchedule = new Schedule();
        this.coach = new Coach("Test coach", "coach123", "Test team", defaultSchedule, athleteMap);
        this.testTeam = new Team("Test team", this.coach, athleteMap);
        
        loadCourseData("Data/course-section-schedule.json");
        Athlete athlete1 = new Athlete("athlete1", "athlete1", "Not yet specified", "Not yet specified", defaultSchedule.clone(), 2027);
        athleteMap.put("athlete1", athlete1);
    }


    public void runProgram() {
        System.out.println("Welcome to TeamSync!");
        while(true) {
            System.out.println("\nLog in as:");
            System.out.println("1. Coach");
            System.out.println("2. Athlete");
            System.out.println("3. Exit");
            String userType = scannerIn.nextLine();

            if (userType.equals("1")) {
                runCoach();
            } else if (userType.equals("2")) {
                runAthlete();
            } else if (userType.equals("3")) {
                System.out.println("Goodbye! Please come back soon.");
                return;
            } else {
                System.out.println("Invalid input. Please input 1, 2, or 3.");
            }
        }
    }

    public void runCoach() {
        Schedule coachSchedule = new Schedule();
        coach = new Coach("Coach Name", "coach123", "TeamName", coachSchedule, athleteMap);
        
        // System.out.println("State your team.");
        // String coachTeam = scannerIn.nextLine();
        // coach.setTeam(coachTeam);

        while(true){
            System.out.println("\n*** Coach Menu ***");

            System.out.println("1. View your schedule");
            System.out.println("2. Add event to team");
            System.out.println("3. View an athlete's schedule");
            System.out.println("4. View athlete conflicts");
            System.out.println("5. Input or change athletic schedule");
            System.out.println("6. View team roster");
            System.out.println("7. Add an athlete to the team");
            System.out.println("8. Go back");

            String userChoice = scannerIn.nextLine();

            if (userChoice.equals("1")) {
                if (coach.getCoachSchedule().isEmpty()){
                    System.out.println("You currently have no events in your schedule.");
                }
                else{
                    System.out.println(coach.getCoachSchedule());
                }

            } else if (userChoice.equals("2")) {
                Event event = eventFromInput();
                coach.addEventToTeam(event);
                defaultSchedule.addEvent(event); // to make sure all new athletes will also have this newly added event
                System.out.println("Event added to athletes and coach.");
            } else if (userChoice.equals("3")) {
                while (true) {
                    System.out.println("Enter the athlete's username");
                    String user = scannerIn.nextLine();

                    if (!coach.athletes.containsKey(user)) {
                        boolean resolved = false;
                        while(!resolved){
                            System.out.println("Athlete doesn't exist. Select an option below.");
                            System.out.println("1. Enter a different username");
                            System.out.println("2. Go back");
                            String input = scannerIn.nextLine();
    
                            if (input.equals("1")) {
                                resolved = true; // break inner loop, start outer loop
                            } 
                            else if (input.equals("2")) {
                                return; // back to coach menu
                            } else {
                                System.out.println("Invalid input. Please input 1 or 2."); // stay in inner loop
                            }
                        }
                    } else {
                        Athlete athlete = coach.athletes.get(user);
                        System.out.println("Schedule for: " + athlete.getName());
                        if (athlete.getAthleteSchedule().isEmpty()){
                            System.out.println("Schedule is currently empty.");
                        }
                        else{
                            System.out.println(athlete.getAthleteSchedule());
                        }
                        break;
                    }
                }
            } else if (userChoice.equals("4")) {
                for (Athlete athlete: coach.getAllAthletes()) {
                    athlete.printConflicts();
                } 
                
            } else if (userChoice.equals("5")) {
                if (!defaultSchedule.isEmpty()) {
                    System.out.println("Do you want to clear the current athletic schedule? (Y/N)");
                    String decision = scannerIn.nextLine().toUpperCase();
                    if (decision.equals("Y")) {
                        for (Athlete athlete: coach.getAllAthletes()){
                            ArrayList<Event> athleticEvents = new ArrayList<>(athlete.getAthleteSchedule().getEventsByType(2));
                            
                            for (Event event: athleticEvents){
                                athlete.removeEvent(event);
                            }
                        }

                        coach.getCoachSchedule().clearSchedule();
                        defaultSchedule.clearSchedule();
                        System.out.println("Old athletic schedule cleared.");
                    }
                    else{
                        System.out.println("Athletic schedule not cleared.");
                    }
                }
                
                String filePath = null;
                while (filePath == null) {
                    System.out.println("Input athletic schedule filename.csv (Part of path after 'Data/') "); // should we have a try to make sure this is valid
                    String fileName = scannerIn.nextLine();
                    filePath = "Data/" + fileName;
                    if (!(new File(filePath)).exists()) {
                        System.out.println("File not found. Please enter a valid file name");
                        filePath = null;
                    }
                }
                
                System.out.println("Input season start date in form YYYY-MM-DD");
                LocalDate startDate = null;
                while (startDate == null) {
                    String date = scannerIn.nextLine();
                    try {
                        startDate = LocalDate.parse(date);
                    } catch (DateTimeException e) {
                        System.out.println("Invalid date. Please use YYYY-MM-DD format.");
                    } 
                }

                System.out.println("Input season end date in form YYYY-MM-DD");
                LocalDate endDate = null;
                while (endDate == null) {
                    String date = scannerIn.nextLine();
                    try {
                        endDate = LocalDate.parse(date);
                    } catch (DateTimeException e) {
                        System.out.println("Invalid date. Please use YYYY-MM-DD format");
                    }
                }

                ArrayList<Event> practiceSchedule = coach.createPracticeSchedule(filePath, startDate, endDate);
                for (Event event: practiceSchedule) {
                    coach.addEventToTeam(event);
                    this.defaultSchedule.addEvent(event);
                }

                System.out.println("Athletic schedule uploaded.");

            }
            else if (userChoice.equals("6")) {
                coach.printTeamRoster();
            }
            
            else if (userChoice.equals("7")){
                System.out.println("\n*** Add a New Athlete ***");

                System.out.println("Enter the athlete's name: ");
                String name = scannerIn.nextLine();

                String username;
                while (true) {
                    System.out.println("Enter a username for the athlete: ");
                    username = scannerIn.nextLine();
                    if (athleteMap.containsKey(username)) {
                        System.out.println("Username already exists. Please choose a different one.");
                    } else {
                        break;
                    }
                }

                System.out.println("Enter the athlete's team: ");
                String team = scannerIn.nextLine();

                System.out.println("Enter the athlete's major: ");
                String major = scannerIn.nextLine();

                int gradYear = 0;
                while (gradYear == 0) {
                    System.out.println("Enter the athlete's grad year: ");
                    try {
                        gradYear = Integer.parseInt(scannerIn.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please enter a valid year.");
                    }
                }

                Athlete newAthlete = new Athlete(name, username, team, major, defaultSchedule.clone(), gradYear);
                athleteMap.put(username, newAthlete);
                coach.addAthlete(newAthlete);

                System.out.println("\n" + name + " added to your team.");
            }
            else if (userChoice.equals("8")) {
                return;
            }
            else {
                System.out.println("Invalid input. Please choice a number between 1 and 8.");
            }
        }
    }

    
    public void runAthlete() {
        System.out.print("Enter your username: ");
        String username = scannerIn.nextLine();

        Athlete athlete = null;

        while (athlete == null) {
            if (athleteMap.get(username) != null) {
                athlete = athleteMap.get(username);
                break;
            }
            
            System.out.println("User not found. Please enter a username to create an account.");
            String inputUser = scannerIn.nextLine();
            System.out.println("Enter your name.");
            String inputName = scannerIn.nextLine();
            System.out.println("Enter your team");
            String inputTeam = scannerIn.nextLine();
            System.out.println("Enter your major");
            String inputMajor = scannerIn.nextLine();

            
            int inputGradYear;

            while (true){
                System.out.println("Enter your grad year");
                try {
                    inputGradYear = scannerIn.nextInt();
                    break;
                }
                catch (Exception e){
                    System.out.println("Invalid grad year. Please enter a graduation year as an integer.");
                    scannerIn.nextLine();
                }
            }
            
            scannerIn.nextLine();
            
            // Athlete createdAthlete = new Athlete(inputName, inputUser, inputTeam, inputMajor, new Schedule(), inputGradYear);
            athlete = new Athlete(inputName, inputUser, inputTeam, inputMajor, defaultSchedule, inputGradYear);
            athleteMap.put(inputUser, athlete);
            
            System.out.println("Account created.");
        }

        while (true) {
            System.out.println("\n*** Athlete Menu (" + athlete.getName() + ") ***");
            System.out.println("1. View full profile and semester schedule.");
            System.out.println("2. View conflicts");
            System.out.println("3. Add event");
            System.out.println("4. Register for courses");
            System.out.println("5. Back");
            String choice = scannerIn.nextLine();

            if (choice.equals("1")){
                System.out.println(athlete);
                continue;
            }
            else if (choice.equals("2")){
                athlete.printConflicts();
                continue;
            }
            else if (choice.equals("3")){
                Event event = eventFromInput();
                athlete.addEvent(event);
                System.out.println("Event added.");
                continue;
            }
            else if (choice.equals("4")) {
                ArrayList<Event> currentAcademicEvents = new ArrayList<>(athlete.getAthleteSchedule().getEventsByType(1));
                int currentCourseCount = athlete.enrolledCourses.size();

                if (currentCourseCount == 0){
                    System.out.println("You currently have no academic courses.");
                }
                else{
                    ArrayList<Course> enrolledCourses = athlete.getEnrolledCourses();
                    System.out.println("Your current academic courses:");
                    int num = 1;
                    for (Course course: enrolledCourses){
                        System.out.println(num + ": " + course);
                        num++;
                    }
                }

                int maxCoursesToAdd = 0;
                boolean validInput = false;

                while(!validInput){

                    if (currentCourseCount == 0) {
                        System.out.println("\nWhat would you like to do?");
                        System.out.println("1. Register for academic courses");
                        System.out.println("2. Go back to the previous menu");

                        String input = scannerIn.nextLine();
                        if (input.equals("1")){
                            maxCoursesToAdd = 5;
                            validInput = true;
                            break;
                        }
                        else if (input.equals("2")){
                            System.out.println("Returning to menu.");
                            break;
                        }
                        else{
                            System.out.println("Invalid input. Please enter 1 or 2.");
                            continue;
                        }
                    }
                    else{
                        System.out.println("\nWhat would you like to do?");
                        System.out.println("1. Clear all academic courses and register from scratch.");
                        System.out.println("2. Edit/add to current academic schedule (5 courses max).");
                        System.out.println("3. Go back to the previous menu.");

                        String modeChoice = scannerIn.nextLine();

                        if (modeChoice.equals("1")){
                            for (Event event : currentAcademicEvents) {
                                athlete.removeEvent(event);
                            }
                            athlete.getEnrolledCourses().clear();
                            System.out.println("Academic schedule cleared.");
                            maxCoursesToAdd = 5;
                            validInput = true;
                        }
                        else if (modeChoice.equals("2")){
                            while (currentCourseCount >= 5){
                                System.out.println("You already have 5 academic courses. Remove one in order to add more.");
                                System.out.println("Do you want to remove an academic course? (Y/N)");
                                String response = scannerIn.nextLine().toUpperCase();

                                if (response.equals("Y")){
                                    // printing the courses the athlete is currently enrolled in with the index + 1 number for easy reference
                                    ArrayList<Course> enrolledCourses = athlete.getEnrolledCourses();
                                    System.out.println("Your current academic courses:");
                                    int num = 1;
                                    for (Course course: enrolledCourses){
                                        System.out.println(num + ": " + course);
                                        num++;
                                    }

                                    System.out.println("Enter the number of the course you'd like to remove: ");
                                    String courseRemovalInput = scannerIn.nextLine();

                                    try{
                                        int removeIndex = Integer.parseInt(courseRemovalInput);
                                        if (removeIndex >= 1 && removeIndex <= currentAcademicEvents.size()){
                                            Event toRemove = currentAcademicEvents.get(removeIndex - 1);
                                            athlete.removeEvent(toRemove);
                                            System.out.println("Removed course: " + toRemove);
                                        }
                                        else{
                                            System.out.println("Invalid number. No course was removed.");
                                        }
                                    }
                                    catch (NumberFormatException e){
                                        System.out.println("Invalid input. No course was removed.");
                                    }
                                    // updates the list and the count after removal
                                    currentAcademicEvents = new ArrayList<>(athlete.getAthleteSchedule().getEventsByType(1));
                                    currentCourseCount = currentAcademicEvents.size();
                                }
                                else if (response.equals("N")){
                                    System.out.println("You selected not to remove a course. Now returning to menu.");
                                    return;
                                }
                                else{
                                    System.out.println("Invalid response. Please enter Y or N.");
                                }
                            }
                            // here we have fewer than 5 courses enrolled in
                            maxCoursesToAdd = 5 - currentCourseCount;
                            System.out.println("You can now add up to: " + maxCoursesToAdd + " more academic course(s).");
                            validInput = true;  
                        }
                        else if (modeChoice.equals("3")){
                            System.out.println("Returning to menu now.");
                            break;
                        }
                        else{
                            System.out.println("Invalid input. Please enter 1, 2, or 3.");
                        }
                    }
                }

                if (!validInput){
                    continue;
                }

                System.out.println("Course filter options");
                System.out.println("1. Filter by department");
                System.out.println("2. Filter by athletic schedule");
                System.out.println("3. Filter by both");

                String filterChoice = scannerIn.nextLine();

                if (filterChoice.equals("1")) {

                    System.out.println("Input department prefix");
                    String dept = null;
                    while (dept == null){
                        dept = scannerIn.nextLine().toUpperCase();
                        ArrayList<Course> filtered = Course.filterByDept(dept);
                        if (filtered.size() == 0) {
                            System.out.println("There are no courses with this prefix. Please try again.");
                            dept = null;
                        } else {
                            courseRegistration(filtered, athlete, maxCoursesToAdd);
                        }
                    }

                }
                else if (filterChoice.equals("2")) {

                    ArrayList<Course> filtered = Course.filterBySchedule(athlete.getAthleteSchedule());
                    courseRegistration(filtered, athlete, maxCoursesToAdd);

                }
                else if (filterChoice.equals("3")) {

                    System.out.println("Input department prefix");
                    ArrayList<Course> filtered = null;
                    String dept = null;
                    while (dept == null){
                        dept = scannerIn.nextLine().toUpperCase();
                        filtered = Course.filterByBoth(dept, athlete.getAthleteSchedule());
                        if (filtered.size() == 0) {
                            System.out.println("There are no courses with this prefix. Please try again.");
                            dept = null;
                        } else {
                            courseRegistration(filtered, athlete, maxCoursesToAdd);
                        }
                    }
                    
                }
                else{
                    System.out.println("Invalid filter option. Now returning to menu.");
                }
            }
            else if (choice.equals("5")){
                return;
            }
            else{
                System.out.println("Invalid input. Pick a number between 1 and 5.");
            }

        }
    }
    

    // if you have no academic courses, you shouldn't even see the first option asking clear all academic courses, it would just say
    // like 1 to register and 2 to go back, and then once you added a class you will see the 1-3 options of clearing, editing, going back
    // 

    public Event eventFromInput() {
        System.out.print("Event name: ");
        String eventName = scannerIn.nextLine();

        System.out.print("Date (YYYY-MM-DD): ");
        LocalDate date = null;
        while (date == null) {
            try {
                date = LocalDate.parse(scannerIn.nextLine());
            } catch (DateTimeParseException e) {
                System.out.println("Please enter date with format YYYY-MM-DD");
            }
        }
        
        LocalTime start = null;
        System.out.print("Start time (HH:MM): ");
        while(start == null) {
            try {
                start = LocalTime.parse(scannerIn.nextLine());
            } catch (DateTimeParseException e) {
                System.out.println("Please enter time w/ format HH:MM");
            }
        }
    
        LocalTime end = null;
        System.out.print("End time (HH:MM): ");
        while (end == null) {
            try {
                end = LocalTime.parse(scannerIn.nextLine());
                if (end.isBefore(start) || end.equals(start)) {
                    System.out.println("Please enter an time that is after the start time");
                    end = null;
                }
            } catch (DateTimeParseException e) {
                System.out.println("Please enter time w/ format HH:MM");
            }
        }

        int type = 0;
        System.out.print("Event type (1 = Academic, 2 = Athletic, 3 = Other): ");
        while (type == 0) {
            try {
                type = Integer.parseInt(scannerIn.nextLine());
                if (type != 1 && type != 2 && type !=3) {
                    type = 0;
                    System.out.println("Please enter a 1, 2, or 3.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a 1, 2, or 3.");
            }
            
        }

        System.out.print("Extra info (e.g., location): ");
        String info = scannerIn.nextLine();

        return new Event(new dateTimePair(date, start), new dateTimePair(date, end), eventName, type, info);
    }


    public void courseRegistration(ArrayList<Course> filtered, Athlete athlete, int maxCourses) {
        ArrayList<Course> coursesEnrolledIn = new ArrayList<>();   
        int courseAmount = 0;
        
        while (courseAmount == 0) {
            int numCoursesToEnroll = 5 - athlete.enrolledCourses.size();
            System.out.println("How many courses do you want to want to register for? Pick between 1 and " + numCoursesToEnroll + ".");
            try {
                courseAmount = scannerIn.nextInt();
                scannerIn.nextLine();
                if (courseAmount > maxCourses || courseAmount < 1){
                    System.out.println("Invalid input. Pick a number between 1 and " + numCoursesToEnroll + ".");
                    courseAmount = 0;
                }
            } catch (InputMismatchException e) {
                System.out.println("Please pick an integer between 1 and " + numCoursesToEnroll + ".");
                scannerIn.nextLine(); // clear bad input
            }
        }
    
        StringBuilder coursePrint = new StringBuilder();
        int index = 1;
        for (Course course : filtered) {
            coursePrint.append(index + ": ");
            coursePrint.append(course + "\n");
            index++;
        }

        System.out.println(coursePrint.toString());

        while (coursesEnrolledIn.size() < courseAmount) {

            int courseNumber = 0;
            System.out.println("Input number next to course you want to add");
            while (courseNumber == 0) {
            
                try {
                    courseNumber = scannerIn.nextInt();
                    scannerIn.nextLine();
                    if (courseNumber > filtered.size() || courseNumber < 1) {
                        System.out.println("Invalid input. Please choose the number next to the course you want");
                        courseNumber = 0;
                    } else if (athlete.enrolledCourses.contains(filtered.get(courseNumber - 1))) {
                        System.out.println("Course already in schedule. Please choose a different one.");
                        courseNumber = 0;
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Please enter a number next to a course");
                    scannerIn.nextLine(); // clear the previous input
                    courseNumber = 0;
                }
            }
            
            Course course = filtered.get(courseNumber - 1);
            coursesEnrolledIn.add(course);

            System.out.println("Enrolled in:\n " + course);
        }

        for (Course course: coursesEnrolledIn) {
            athlete.enrollCourse(course);
        }

        System.out.println("Course registration complete.");
    }

    public void loadCourseData(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader("Data/course-section-schedule.json"))) {
            String line;
            Course aCourse = null;

            while ((line = reader.readLine()) != null) {
                line = line.trim();

                if (line.startsWith("{")) {
                    aCourse = new Course("", "", "", "", "");
                } 
                else if (line.startsWith("\"courseSectionId\"")) {
                    aCourse.setCourseSectionId(Course.getLineValue(line));
                } 
                else if (line.startsWith("\"classBeginningTime\"")) {
                    aCourse.setClassBeginningTime(Course.getLineValue(line));
                } 
                else if (line.startsWith("\"classEndingTime\"")) {
                    aCourse.setClassEndingTime(Course.getLineValue(line));
                } 
                else if (line.startsWith("\"classMeetingDays\"")) {
                    aCourse.setClassMeetingDays(Course.getLineValue(line));
                } 
                else if (line.startsWith("\"instructionSiteName\"")) {
                    aCourse.setInstructionSiteName(Course.getLineValue(line));
                } 
                else if (line.startsWith("}")) {
                    Course.allCourses.add(aCourse);
                }
            }

        } catch (IOException e) {
                System.out.println("File Error, file not found");
            }
    }
}


