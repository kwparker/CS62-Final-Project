package src.teamsync;

/**
 * starting point for the TeamSync scheduling application
 * 
 * This class simply initializes the TeamSyncApp and runs the main program logic
 * 
 * @author: Guy Fuchs, Kai Parker, Tiernen Colby
 */
public class MainProgram{

    // main method that launches the TeamSync program
    public static void main(String[] args){
        // create an instance of the main program logic
        TeamSyncApp app = new TeamSyncApp();
        // start the program
        app.runProgram();
    }
}