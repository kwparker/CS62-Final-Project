package src.teamsync;

import java.util.Arrays;
import java.util.List;

/**
 * Class representation of an Event
 * 
 * @author Kai Parker, Guy Fuchs, Tiernan Colby
 */
public class Event implements Comparable<Event> { // since when was this a comparable?

    dateTimePair startPair;  // dateTime pair of start time and date
    dateTimePair endPair; // end time of event
    String eventName;  // name of event
    int eventType;  // 1 is academic-related, 2 is athletic-related, 3 is other
    String info; // extra information
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

    /**
     * determines whether this event overlaps with another event on the same day. 
     * 
     * @param event the event to compare against
     * @return true if the events overlap; false otherwise
     */
    public boolean detectOverlap(Event event){
        // check if events are on the same day
        boolean sameDay = this.startPair.date.equals(event.startPair.date);
        // in order to have overlap, one event must start before the other ends
        boolean thisStartsBeforeOtherEnds = this.startPair.time.compareTo(event.endPair.time) < 0;
        boolean otherStartsBeforeThisEnds = event.startPair.time.compareTo(this.endPair.time) < 0;
    
        // events overlap only if they occur on the same day and time intervals intersect, this checks that
        return sameDay && thisStartsBeforeOtherEnds && otherStartsBeforeThisEnds;
    }


    /**
     * compare this event to another event to be able to sort by start time
     * 
     * @param otherEvent the other event to compare to
     * @return a negative integer if this event starts earlier, 0 if same, positive if later
     */
    public int compareTo(Event otherEvent) {
        return this.startPair.compareTo(otherEvent.startPair);  // uses dateTimePair compare method to indicate which event comes first
    }


    /**
     * getter method to retrieve the event type
     * 
     * @return integer representing event type, 1, 2, or 3
     */
    public int getType(){
        return this.eventType;
    }

    /**
     * getter method to retrieve the event's start date/time.
     * 
     * @return the start dateTimePair
     */
    public dateTimePair getStart() {
        return this.startPair;
    }
    
    /**
     * fetter method to retrieve the event's end date/time
     * 
     * @return the end dateTimePair
     */
    public dateTimePair getEnd() {
        return this.endPair;
    }
    
    /**
     * getter method to retrieve the event's name
     * 
     * @return the name of the event
     */
    public String getName() {
        return this.eventName;
    }
    
    /**
     * getter method to retrieve extra information about the event
     * 
     * @return the extra info string
     */
    public String getExtraInfo() {
        return this.info;
    }
    
    /**
     * creates a readable string representation of the event including start and end time, name, and event type
     * label (academic, athletic, or other).
     * 
     * @return a string describing the event
     */
    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        
        // append start and end date/time and the event name
        output.append(this.startPair.toString() + " - " + this.endPair.toString() + ", " + eventName + " (");
        
        // append type lables and words
        if (this.eventType == 1) {
            output.append("Academic);");
        } else if (this.eventType == 2) {
            output.append("Athletic);");
        } else {
            output.append("Other);");
        }

        return output.toString(); // return final string
    }

}
