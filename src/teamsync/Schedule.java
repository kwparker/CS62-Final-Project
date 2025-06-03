package src.teamsync;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;

/**
 * The Schedule class manages a list of Events for an individual, either a coach or athlete.
 * Provides functionality to add, remove, edit, detect conflicts, and retrieve events in different ways.
 * Implements the ScheduleInterface
 * 
 * @author Guy Fuchs, Kai Parker, Tiernan Colby
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


    /**
     * checks if there is any scheduling conflict
     * 
     * @return true if at least one conflict exists
     */
    public boolean detectConflict() {
        return !getConflicts().isEmpty();
    }

    /**
     * replaces an existing event with a new event
     * 
     * @param event    the event to replace
     * @param newEvent the new event
     */
    public void editEvent(Event event, Event newEvent) {
        int index = schedule.indexOf(event); // get index of original event
        if (index != -1){ // making sure the event exists
            schedule.set(index, newEvent); // setting the new event in place of the old event
            sortSchedule(); // keep schedule sorted
        }
    }

    /**
     * returns a copy of the full schedule
     * 
     * @return ArrayList of all events
     */
    public ArrayList<Event> getSchedule() {
        return new ArrayList<>(schedule);
    }

    /**
     * clears all events from the schedule
     */
    public void clearSchedule() {
        schedule.clear();
    }

    /**
     * sorts the schedule chronologically based on start time of events
     */
    public void sortSchedule() {
        Collections.sort(schedule); // use Event's compareTo method
    }

    /**
     * returns all events that match a specified type (academic, athletic, other)
     * 
     * @param type the event type being 1, 2, or 3
     * @return ArrayList of events of that type
     */
    public ArrayList<Event> getEventsByType(int type) {
        ArrayList<Event> output = new ArrayList<>();
        for (Event event : schedule) { // loop through the events in the schedule
            if (event.eventType == type) {
                output.add(event); // adds the desired type event into the outputted arraylist
            }
        }
        return output;
    }

    /**
     * returns all events that occur on a specific date
     * 
     * @param date the date to search for the events that occured on it
     * @return list of events on that date
     */
    public ArrayList<Event> getEventsOnDate(LocalDate date) {
        ArrayList<Event> output = new ArrayList<>();
        for (Event event : schedule) { // loop through the events in the schedule
            if (event.startPair.date.equals(date)) {
                output.add(event); // adds the desired event that occurs on the inputted date into the outputted arraylist
            }
        }
        return output;
    }

    /**
     * checks whether a specific event exists in the schedule
     * 
     * @param event the event to search for
     * @return true if it exists, false otherwise
     */
    public boolean containsEvent(Event event) {
        return schedule.contains(event);
    }

    /**
     * returns the next upcoming event after a given dateTime
     * 
     * @param currentPair the reference date and time
     * @return the next event, or null if none remain
     */
    public Event getNextEvent(dateTimePair currentPair) {
        for (Event event : schedule) { // loops through the events in the schedule
            if (event.startPair.compareTo(currentPair) > 0 ) { // finds the earliest Event that starts after the inputted dateTime
                return event; // return the first Event that happens right after the inputted dateTime 
            }
        }
        return null; // no upcoming events
    }

    /**
     * creates a deep copy of the current schedule (not just references)
     * 
     * @return a new Schedule with duplicated event data
     */
    public Schedule clone() {
        Schedule clonedSchedule = new Schedule(); // new blank cloned schedule that will store the cloned events
        for (Event event : this.schedule) { // goes through the events in the schedule
            // create a deep copy of each event and add it to the newly clonedSchedule
            clonedSchedule.addEvent(new Event(new dateTimePair(event.getStart().date, event.getStart().time),
                    new dateTimePair(event.getEnd().date, event.getEnd().time), event.getName(), event.getType(), event.getExtraInfo()));
        }
        return clonedSchedule; // return the cloned schedule
    }
    
    /**
     * returns a string representation of the entire schedule
     * 
     * @return multiline string of each event in the schedule
     */
    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        for (Event event: schedule) { // loop through each event in the schedule
            output.append(event.toString() + "\n"); // append the string representation of the event with a newline
        }
        
        return output.toString(); // return the toString of the StringBuilder object
    }

}
