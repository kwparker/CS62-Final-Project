package teamsync;
import java.util.ArrayList;


public class Schedule implements ScheduleInterface {

    ArrayList<Event> schedule;
    int numEvents;

    public Schedule() {
        schedule = new ArrayList<Event>();
        numEvents = 0;
    }

    // add an event to the schedule
    public void addEvent(Event event) {

    }

    // remove an event from schedule
    public Event removeEvent(Event event) {
        return null;
    } 

    // check if schedule is empty
    public boolean isEmpty() {
        return false;
    }

    // return number of events in schedule
    public int size() {
        return 0;
    }

    // detect conflicts within schedule 
    public boolean detectConflict() {
        return false;
    }

    // edit a pre-existing event in the schedule
    public void editEvent(Event event, Event newEvent) {

    }

    // return the whole schedule
    public ArrayList<Event> getSchedule() {
        return null;
    }

    // clear the whole schedule
    public void clearSchedule() {

    }
}
