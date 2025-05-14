package teamsync;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.HashMap;


public class TeamSyncApp {

    public Coach coach;
    public HashMap<String, Athlete> athleteMap;
    public Scanner scannerIn;

    public TeamSyncApp() {
        this.scannerIn = new Scanner(System.in);
        this.athleteMap = new HashMap<String, Athlete>();

        // add some sort of sample data here so the athletes and coach are here

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
            System.out.println("File Error");
        }

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
        
        while(true)
            System.out.println("\n*** Coach Menu ***");
            
            System.out.println("State your team.");
            String coachTeam = scannerIn.nextLine();
            coach.setTeam(coachTeam);

            System.out.println("1. View your schedule");
            System.out.println("2. Add event to team");
            System.out.println("3. View an athlete's schedule");
            System.out.println("4. View athlete conflicts");
            System.out.println("5. Input practice schedule");
            // System.out.println("6. View more options"); // figure out what to do with this
            System.out.println("6. Go back");

            String userChoice = scannerIn.nextLine();

            if (userChoice.equals("1")) {
                System.out.println(coach.getCoachSchedule());
                break;
            } else if (userChoice.equals("2")) {
                Event event = eventFromInput();
                coach.addEventToTeam(event);
                System.out.println("Event added to athletes and coach.");
            } else if (userChoice.equals("3")) {
                System.out.println("Enter the athlete's username");
                String user = scannerIn.nextLine();
                coach.getAthleteSchedule(user);
            } else if (userChoice.equals("4")) {
                for (Athlete athlete: coach.getAllAthletes()) {
                    athlete.printConflicts();
                } break;
            } else if (userChoice.equals("5")) {
                System.out.println("Input practice schedule file path"); // should we have a try to make sure this is valid
                String fileName = scannerIn.nextLine();
                
                System.out.println("Input season start date in form YYYY-MM-DD");
                LocalDate startDate = null;
                while (startDate == null) {
                    String date = scannerIn.nextLine();
                    try {
                        startDate = LocalDate.parse(date);
                    } catch (DateTimeException e) {
                        System.out.println("Invalid date. Please use YYYY-MM-DD format.")
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

                ArrayList<Event> practiceSchedule = coach.createPracticeSchedule(fileName, startDate, endDate);
                for (Event event: practiceSchedule) {
                    coach.addEventToTeam(event);
                }
                
            } else if (userChoice.equals("6")) {
                return;
            } 
            // else if (userChoice.equals("7")) {
                
            //
            else {
                System.out.println("Invalid input. Please choice a number between 1 and 7.");
            }
    
    }

    public void runAthlete() {
        System.out.print("Enter your username: ");
        String username = scannerIn.nextLine();

        Athlete athlete = Athlete();
        athleteMap.get(username);

        if (athlete == null) {
            System.out.println("User not found.");
            return;
        }

        while (true) {
            System.out.println("\n*** Athlete Menu (" + athlete.getName() + ") ***");
            System.out.println("1. View schedule");
            System.out.println("2. View conflicts");
            System.out.println("3. Add event");
            System.out.println("4. Register for courses");
            System.out.println("5. Back");
            String choice = scannerIn.nextLine();

            if (choice.equals("1")){
                System.out.println(athlete);
                break;
            }
            else if (choice.equals("2")){
                athlete.printConflicts();
                break;
            }
            else if (choice.equals("3")){
                Event event = eventFromInput();
                athlete.addEvent(event);
                System.out.println("Event added.");
                break;
            }
            else if (choice.equals("4")) {
                System.out.println("Course filter options");
                System.out.println("1. Filter by department");
                System.out.println("2. Filter by athletic schedule");
                System.out.println("3. Filter by both");

                String input = scannerIn.nextLine();

                if (input.equals("1")) {
                    System.out.println("Input department prefix");
                    String dept = scannerIn.nextLine();

                    ArrayList<Course> filtered = Course.filterByDept(dept);

                    ArrayList<Course> coursesEnrolledIn = new ArrayList<>();
                    
                    int courseAmount = 0;
                    while (courseAmount == 0) {
                        System.out.println("How many courses do you want to want to register for? Pick between 1 and 5.");
                        try {
                        courseAmount = scannerIn.nextInt();
                        if (courseAmount > 5 || courseAmount < 1){
                            System.out.println("Invalid input. Pick a number between 1 and 5.");
                            courseAmount = 0;
                        }
                        }
                        catch (InputMismatchException e) {
                            System.out.println("Please pick an integer between 1 and 5.");
                        }
                    }
                    
                    while (coursesEnrolledIn.size() < courseAmount) {
                        int currentSize = coursesEnrolledIn.size();
                        System.out.println("Choose a course section ID to add");
                        String chosenCourseID = scannerIn.nextLine();
                        
                        for (Course course: filtered){
                            if (course.getCourseSectionId().equals(chosenCourseID)){
                                coursesEnrolledIn.add(course);
                                System.out.println("Enrolled in:\n " + course);
                            }
                        }
                        if (currentSize == coursesEnrolledIn.size()) {
                            System.out.println("Course not found. Enter a valid course section ID.");
                        }
                    }

                    for (Course course: coursesEnrolledIn) {
                        ArrayList<Event> eventList = course.courseToEvent();
                        for (Event event: eventList) {
                            athlete.addEvent(event);
                        }
                    }

                    System.out.println("Course registration complete.");

                }
                else if (input.equals("2")) {

                    ArrayList<Course> filtered = Course.filterBySchedule(athlete.getAthleteSchedule());

                    ArrayList<Course> coursesEnrolledIn = new ArrayList<>();
                    
                    int courseAmount = 0;
                    while (courseAmount == 0) {
                        System.out.println("How many courses do you want to want to register for? Pick between 1 and 5.");
                        try {
                        courseAmount = scannerIn.nextInt();
                        if (courseAmount > 5 || courseAmount < 1){
                            System.out.println("Invalid input. Pick a number between 1 and 5.");
                            courseAmount = 0;
                        }
                        }
                        catch (InputMismatchException e) {
                            System.out.println("Please pick an integer between 1 and 5.");
                        }
                    }
                    
                    while (coursesEnrolledIn.size() < courseAmount) {
                        int currentSize = coursesEnrolledIn.size();
                        System.out.println("Choose a course section ID to add");
                        String chosenCourseID = scannerIn.nextLine();
                        
                        for (Course course: filtered){
                            if (course.getCourseSectionId().equals(chosenCourseID)){
                                coursesEnrolledIn.add(course);
                                System.out.println("Enrolled in:\n " + course);
                            }
                        }
                        if (currentSize == coursesEnrolledIn.size()) {
                            System.out.println("Course not found. Enter a valid course section ID.");
                        }
                    }

                    for (Course course: coursesEnrolledIn) {
                        ArrayList<Event> eventList = course.courseToEvent();
                        for (Event event: eventList) {
                            athlete.addEvent(event);
                        }
                    }

                    System.out.println("Course registration complete.");

                }
                else if (input.equals("3")) {
                    System.out.println("Input department prefix");
                    String dept = scannerIn.nextLine();

                    ArrayList<Course> filtered = Course.filterByBoth(dept, athlete.getAthleteSchedule());

                    ArrayList<Course> coursesEnrolledIn = new ArrayList<>();
                    
                    int courseAmount = 0;
                    while (courseAmount == 0) {
                        System.out.println("How many courses do you want to want to register for? Pick between 1 and 5.");
                        try {
                        courseAmount = scannerIn.nextInt();
                        if (courseAmount > 5 || courseAmount < 1){
                            System.out.println("Invalid input. Pick a number between 1 and 5.");
                            courseAmount = 0;
                        }
                        }
                        catch (InputMismatchException e) {
                            System.out.println("Please pick an integer between 1 and 5.");
                        }
                    }
                    
                    while (coursesEnrolledIn.size() < courseAmount) {
                        int currentSize = coursesEnrolledIn.size();
                        System.out.println("Choose a course section ID to add");
                        String chosenCourseID = scannerIn.nextLine();
                        
                        for (Course course: filtered){
                            if (course.getCourseSectionId().equals(chosenCourseID)){
                                coursesEnrolledIn.add(course);
                                System.out.println("Enrolled in:\n " + course);
                            }
                        }
                        if (currentSize == coursesEnrolledIn.size()) {
                            System.out.println("Course not found. Enter a valid course section ID.");
                        }
                    }

                    for (Course course: coursesEnrolledIn) {
                        ArrayList<Event> eventList = course.courseToEvent();
                        for (Event event: eventList) {
                            athlete.addEvent(event);
                        }
                    }

                    System.out.println("Course registration complete.");
                }

            }
            else if (choice.equals("5")){
                return;
            }
            else{
                System.out.println("Invalid input. Pick a number between 1 and 4.");
            }
        }

    }

    public Event eventFromInput() {
        System.out.print("Event name:");
        String eventName = scannerIn.nextLine();

        System.out.print("Date (YYYY-MM-DD): ");
        LocalDate date = LocalDate.parse(scannerIn.nextLine());

        System.out.print("Start time (HH:MM): ");
        LocalTime start = LocalTime.parse(scannerIn.nextLine());

        System.out.print("End time (HH:MM): ");
        LocalTime end = LocalTime.parse(scannerIn.nextLine());

        System.out.print("Event type (1 = Academic, 2 = Athletic, 3 = Other): ");
        int type = Integer.parseInt(scannerIn.nextLine());

        System.out.print("Extra info (e.g., location): ");
        String info = scannerIn.nextLine();

        return new Event(new dateTimePair(date, start), new dateTimePair(date, end), eventName, type, info);
    }
}


