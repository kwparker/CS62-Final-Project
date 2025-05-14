package teamsync;

import java.io.BufferedReader;
import java.io.File;
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
    }

        // add some sort of sample data here so the athletes and coach are here
        // Course.main(null); // this should work
        // Schedule schedule = new Schedule();
        // Athlete athlete1 = new Athlete("athlete1", "athlete11", "Not yet specified", "Not yet specified", schedule, 2027);
        // Athlete athlete2 = new Athlete("athlete2", "athlete12", "Not yet specified", "Not yet specified", schedule, 2027);
        // Athlete athlete3 = new Athlete("athlete3", "athlete13", "Not yet specified", "Not yet specified", schedule, 2027);
        // Athlete athlete4 = new Athlete("athlete4", "athlete14", "Not yet specified", "Not yet specified", schedule, 2027);
        // Athlete athlete5 = new Athlete("athlete5", "athlete15", "Not yet specified", "Not yet specified", schedule, 2027);


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
            // System.out.println("6. View more options"); // figure out what to do with this
            System.out.println("6. Go back");

            String userChoice = scannerIn.nextLine();

            if (userChoice.equals("1")) {
                System.out.println(coach.getCoachSchedule());

            } else if (userChoice.equals("2")) {
                Event event = eventFromInput();
                coach.addEventToTeam(event);
                System.out.println("Event added to athletes and coach.");
            } else if (userChoice.equals("3")) {
                System.out.println("Enter the athlete's username");
                String user = scannerIn.nextLine();
                System.out.println(coach.getAthleteSchedule(user));
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

            } else if (userChoice.equals("6")) {
                return;
            } 
            // else if (userChoice.equals("7")) {
                
            //
            else {
                System.out.println("Invalid input. Please choice a number between 1 and 7.");
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
                // for event in schedule, if type of event is 1 (academic), remove event
                Schedule currentSchedule =  athlete.getAthleteSchedule();
                for (Event event : currentSchedule.getEventsByType(1)) {
                        athlete.removeEvent(event);
                }
                
                System.out.println("Previous academic events removed from your schedule.");
                System.out.println("Course filter options");
                System.out.println("1. Filter by department");
                System.out.println("2. Filter by athletic schedule");
                System.out.println("3. Filter by both");

                String input = scannerIn.nextLine();

                if (input.equals("1")) {

                    System.out.println("Input department prefix");
                    String dept = scannerIn.nextLine().toUpperCase();
                    ArrayList<Course> filtered = Course.filterByDept(dept);
                    courseRegistration(filtered, athlete);

                }
                else if (input.equals("2")) {

                    ArrayList<Course> filtered = Course.filterBySchedule(athlete.getAthleteSchedule());
                    courseRegistration(filtered, athlete);

                }
                else if (input.equals("3")) {

                    System.out.println("Input department prefix");
                    String dept = scannerIn.nextLine().toUpperCase();
                    ArrayList<Course> filtered = Course.filterByBoth(dept, athlete.getAthleteSchedule());
                    courseRegistration(filtered, athlete);
                    
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
    

    public Event eventFromInput() {
        System.out.print("Event name: ");
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


    public void courseRegistration(ArrayList<Course> filtered, Athlete athlete) {
        ArrayList<Course> coursesEnrolledIn = new ArrayList<>();   
        int courseAmount = 0;
        
        while (courseAmount == 0) {
            System.out.println("How many courses do you want to want to register for? Pick between 1 and 5.");
            try {
                courseAmount = scannerIn.nextInt();
                scannerIn.nextLine();
                if (courseAmount > 5 || courseAmount < 1){
                    System.out.println("Invalid input. Pick a number between 1 and 5.");
                    courseAmount = 0;
                }
            } catch (InputMismatchException e) {
                System.out.println("Please pick an integer between 1 and 5.");
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
            while (courseNumber == 0) {
                System.out.println("Input number next to course you want to add");
            
                try {
                    courseNumber = scannerIn.nextInt();
                    scannerIn.nextLine();
                    if (courseNumber > filtered.size() || courseNumber < 1) {
                        System.out.println("Invalid input. Please choose the number next to the course you want");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Please enter a number next to a course");
                }
            }
            
            Course course = filtered.get(courseNumber - 1);
            coursesEnrolledIn.add(course);

            System.out.println("Enrolled in:\n " + course);
        }

        for (Course course: coursesEnrolledIn) {
            ArrayList<Event> eventList = course.courseToEvent();
            for (Event event: eventList) {
                athlete.addEvent(event);
            }
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


