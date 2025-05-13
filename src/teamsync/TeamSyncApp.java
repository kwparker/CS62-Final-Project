package teamsync;

import java.util.HashMap;
import java.util.Scanner;
import java.util.HashMap;


public class TeamSyncApp {

    public Coach coach;
    public HashMap<String, Athlete> athleteMap;
    public Scanner scannerIn;

    public TeamSyncApp() {
        this.scannerIn = new Scanner(System.in);
        this.athleteMap = new HashMap<String, Athlete>();

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
                // runAthlete();
            } else if (userType.equals("3")) {
                System.out.println("Goodbye! Please come back soon.");
                return;
            } else {
                System.out.println("Invalid input. Please input 1, 2, or 3.");
            }
        }
    }

    public void runCoach() {
        while(true)
            System.out.println("\n*** Coach Menu ***");
            System.out.println("1. View your schedule");
            System.out.println("2. Add event to team");
            System.out.println("3. View an athlete's schedule");
            System.out.println("4. View athlete conflicts");
            System.out.println("5. Input practice schedule");
            System.out.println("6. View more options"); // figure out what to do with this
            System.out.println("7. Go back");

            String userChoice = scannerIn.nextLine();

            if (userChoice.equals("1")) {
                System.out.println(coach.getCoachSchedule());
                break;
            } else if (userChoice.equals("2")) {
                Event event = eventFromInput();  // create this method
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
                
            } else if (userChoice.equals("6")) {
                
            } else if (userChoice.equals("7")) {
                
            } else {
                System.out.println("Invalid input. Please choice a number b/w 1 and 7.")
            }

    }
}
 

