package teamsync;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Event implements Comparable<Event> {

    dateTimePair startPair;  // dateTime pair of start time and date
    dateTimePair endPair; // end time of event
    String eventName;  // name of event
    int eventType;  // 1 is academic-related, 2 is athletic-related, 3 is other
    String info;
    static final List<Integer> EVENT_TYPES = Arrays.asList(1,2,3);  // options for eventType

    /**
     * constructs new event
     * @param startPair of start date and time of event
     * @param endPair of end date and time of event
     * @param eventName name of event
     * @param eventType indicates if event is academic, athletics, or other
     */
    public Event (dateTimePair startPair, dateTimePair endPair, String eventName, int eventType, String info) {
        this.startPair = startPair;
        this.endPair = endPair;
        this.eventName = eventName;
        this.info = info;
        
        

        if (EVENT_TYPES.contains(eventType)) {  // if type of event is valid
            this.eventType = eventType;  // sets this.eventType to be inputted eventType
        } else {
            throw new IllegalArgumentException("Invalid eventType: must be 1, 2, or 3");  // throws exception if type is invalid
        }
    }

    // detect overlap between two events
    public boolean detectOverlap(Event event){
        boolean sameDay = this.startPair.date.equals(event.startPair.date); // check if events are on the same day
        
        // in order to have overlap, one event must start before the other ends
        boolean thisStartsBeforeOtherEnds = this.startPair.time.compareTo(event.endPair.time) < 0;
        boolean otherStartsBeforeThisEnds = event.startPair.time.compareTo(this.endPair.time) < 0;
    
        return sameDay && thisStartsBeforeOtherEnds && otherStartsBeforeThisEnds;
    }


    public int compareTo(Event otherEvent) {
        return this.startPair.compareTo(otherEvent.startPair);  // uses dateTimePair compare method to indicate which event comes first
    }

    public String toString() {
        StringBuilder output = new StringBuilder();
        output.append(this.startPair.toString() + " - " + this.endPair.toString() + ", " + eventName + " (");
        if (this.eventType == 1) {
            output.append("Academic);");
        } else if (this.eventType == 2) {
            output.append("Athletic);");
        } else {
            output.append("Other);");
        }

        return output.toString();
    }

}
