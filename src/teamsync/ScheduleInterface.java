package teamsync;

import java.util.ArrayList;

public interface ScheduleInterface {
    
    // add an event to the schedule
    void addEvent(Event event);

    // remove an event from schedule
    Event removeEvent(Event event);  

    // check if schedule is empty
    boolean isEmpty();

    // return number of events in schedule
    int size();

    // detect conflicts within schedule 
    boolean detectConflict();

    // edit a pre-existing event in the schedule
    void editEvent(Event event, Event newEvent);

    // return the whole schedule
    ArrayList<Event> getSchedule();

    // clear the whole schedule
    void clearSchedule();

}
