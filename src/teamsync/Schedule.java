package teamsync;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;


public class Schedule implements ScheduleInterface {

    ArrayList<Event> schedule;  // arraylist of events to represent schedule

    public Schedule() {
        schedule = new ArrayList<Event>();
    }

    // add an event to the schedule
    public void addEvent(Event event) {
        schedule.add(event); // add event to the schedule arrayList
        sortSchedule();  // sort the schedule
    }

    // remove an event from schedule and return the event that was removed
    public Event removeEvent(Event event) {
        boolean removed = schedule.remove(event); // check if there is an event to be returned
        if (removed){
            return event;
        }

        System.out.println("Error: Event could not be removed because event was not found"); // error message if event not fond
        return null;
    }
    
    // check if schedule is empty
    public boolean isEmpty() {
        return schedule.isEmpty(); 
    }

    // return number of events in schedule
    public int size() {
        return schedule.size();
    }


    public ArrayList<ArrayList<Event>> getConflicts() {  // how will this method work if there are 3 events that all conflict with each other?
        sortSchedule(); // sort schedule
        ArrayList<ArrayList<Event>> conflicts = new ArrayList<>();
    
        // compare each event with all events that start later due to initially sorting the schedule
        for (int i = 0; i < schedule.size() - 1; i++){
            Event current = schedule.get(i);
            
            // check current event against all later events
            for (int j = i + 1; j < schedule.size(); j++){
                Event later = schedule.get(j);
                
                if (current.detectOverlap(later)) {
                    ArrayList<Event> tempList = new ArrayList<Event>();
                    tempList.add(current);
                    tempList.add(later);
                    conflicts.add(tempList);
                }
            }
        }

        return conflicts;
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

    public String toString() {
        StringBuilder output = new StringBuilder();
        for (Event event: schedule) {
            output.append(event.toString() + "\n");
        }
        
        return output.toString();
    }

}
