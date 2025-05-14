package teamsync;

public class MainProgram{

    public static void main(String[] args){
        TeamSyncApp app = new TeamSyncApp();
        app.runProgram();
    }
}



// make hash map for valid teams and when team name is inputted, adds athlete to team or creates new team

// add an option in runAthlete to only view the schedule
// entering a string for gradYear breaks the program when creating a new user
// when we filter by department, make it so that no matter what the input is it goes to all
// need to be able to escape course registration

// need to make sure practice schedule parse work

// change it from inputting a course id to inputting a number that corresponds to a course, number each element in the arrayList (filtered)
// and then you can do like get(i) in order to access the course you want 

// get rid of team stuff in team sync app for runCoach()

// when adding a new athlete to a team, the previous practice should be added
// save practice schedule that the coach uploaded and initialize all new athletes with that schedule
