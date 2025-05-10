package teamsync;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

public class Event implements Comparable<Event> {

    dateTimePair pair;  // dateTime pair of start time and date
    dateTimePair endPair; // end time of event
    String eventName;  // name of event
    int eventType;  // 1 is academic-related, 2 is athletic-related, 3 is other
    String info;
    static final List<Integer> EVENT_TYPES = Arrays.asList(1,2,3);  // options for eventType

    /**
     * constructs new event
     * @param pair of date and time of event
     * @param eventName name of event
     * @param eventType indicates if event is academic, athletics, or other
     */
    public Event (dateTimePair pair, dateTimePair endPair, String eventName, int eventType, String info) {
        this.pair = pair;
        this.endPair = endPair;
        this.eventName = eventName;
        this.info = info;
        
        

        if (EVENT_TYPES.contains(eventType)) {  // if type of event is valid
            this.eventType = eventType;  // sets this.eventType to be inputted eventType
        } else {
            throw new IllegalArgumentException("Invalid eventType: must be 1, 2, or 3");  // throws exception if type is invalid
        }
    }

    public int compareTo(Event otherEvent) {
        return this.pair.compareTo(otherEvent.pair);  // uses dateTimePair compare method to indicate which event comes first
    }

    public String toString() {
        StringBuilder output = new StringBuilder();
        output.append("<" + this.pair.toString() + ", " + eventName + ", ");
        if (this.eventType == 1) {
            output.append("academic (1)");
        } else if (this.eventType == 2) {
            output.append("athletic (2)");
        } else {
            output.append("other (3)");
        }
        return output.toString();
    }


    
}
