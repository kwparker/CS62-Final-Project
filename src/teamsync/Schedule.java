package teamsync;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;

/**
 * The Schedule class manages a list of Events for an individual, either a coach or athlete.
 * Provides functionality to add, remove, edit, detect conflicts, and retrieve events in different ways.
 * Implements the ScheduleInterface
 */
public class Schedule implements ScheduleInterface {

    ArrayList<Event> schedule;  // arraylist of events to represent schedule

    /**
     * constructs a new empty Schedule
     */
    public Schedule() {
        schedule = new ArrayList<Event>();
    }

    /**
     * adds an event to the schedule and automatically sorts it
     * 
     * @param event the event to add
     */
    public void addEvent(Event event) {
        schedule.add(event); // add event to the schedule arrayList
        sortSchedule();  // sort the schedule
    }

    /**
     * removes an event from the schedule
     * 
     * @param event the event to remove
     * @return the event if removed, or null if not found
     */
    public Event removeEvent(Event event) {
        boolean removed = schedule.remove(event); // check if there is an event to be returned / removed
        if (removed){
            return event;
        }

        System.out.println("Error: Event could not be removed because event was not found"); // error message if event not fond
        return null;
    }
    
    /**
     * checks if the schedule is empty
     * 
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return schedule.isEmpty(); 
    }

    /**
     * returns the number of events currently in the schedule
     * 
     * @return the number of scheduled events
     */
    public int size() {
        return schedule.size();
    }

    /**
     * returns a list of all conflicting event pairs in the schedule
     * 
     * @return list of ArrayLists, each containing two overlapping events
     */
    public ArrayList<ArrayList<Event>> getConflicts() {
        sortSchedule(); // sort schedule to make sure events are in order
        ArrayList<ArrayList<Event>> conflicts = new ArrayList<>();
    
        // compare each event with all events that start later due to initially sorting the schedule
        for (int i = 0; i < schedule.size() - 1; i++){
            Event current = schedule.get(i);
            
            // check current event against all later events
            for (int j = i + 1; j < schedule.size(); j++){
                Event later = schedule.get(j);
                
                // if there is an overlap, store the pair
                if (current.detectOverlap(later)) {
                    ArrayList<Event> tempList = new ArrayList<Event>();
                    tempList.add(current);
                    tempList.add(later);
                    conflicts.add(tempList);
                }
            }
        }

        return conflicts; // return the list of ArrayLists, each containing two overlapping events
    }


    // detect conflicts within schedule 
    public boolean detectConflict() {
        return !getConflicts().isEmpty();
    }

    // edit a pre-existing event in the schedule
    public void editEvent(Event event, Event newEvent) {
        int index = schedule.indexOf(event);
        if (index != -1){ // making sure the event exists
            schedule.set(index, newEvent); // setting the new event in place of the old event
            sortSchedule();
        }
    }

    // return the whole schedule
    public ArrayList<Event> getSchedule() {
        return new ArrayList<>(schedule);
    }

    // clear the whole schedule
    public void clearSchedule() {
        schedule.clear();
    }

    // sorts schdule based on time/date
    public void sortSchedule() {
        Collections.sort(schedule);
    }

    // return list of events of a specific type
    public ArrayList<Event> getEventsByType(int type) {
        ArrayList<Event> output = new ArrayList<>();
        for (Event event : schedule) {
            if (event.eventType == type) {
                output.add(event);
            }
        }
        return output;
    }

    // return list of events on a specific date
    public ArrayList<Event> getEventsOnDate(LocalDate date) {
        ArrayList<Event> output = new ArrayList<>();
        for (Event event : schedule) {
            if (event.startPair.date.equals(date)) {
                output.add(event);
            }
        }
        return output;
    }

    // check if schedule contains a specific event
    public boolean containsEvent(Event event) {
        return schedule.contains(event);
    }

    // return the next upcoming event based on current date and time
    public Event getNextEvent(dateTimePair currentPair) {
        for (Event event : schedule) {
            if (event.startPair.compareTo(currentPair) > 0 ) {
                return event;
            }
        }
        return null; // no upcoming events
    }

    public Schedule clone() {
        Schedule clonedSchedule = new Schedule();
        for (Event event : this.schedule) {
            clonedSchedule.addEvent(new Event(new dateTimePair(event.getStart().date, event.getStart().time),
                    new dateTimePair(event.getEnd().date, event.getEnd().time), event.getName(), event.getType(), event.getExtraInfo()));
        }
        return clonedSchedule;
    }
    

    public String toString() {
        StringBuilder output = new StringBuilder();
        for (Event event: schedule) {
            output.append(event.toString() + "\n");
        }
        
        return output.toString();
    }

}
