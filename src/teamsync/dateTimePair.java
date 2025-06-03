package src.teamsync;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

/**
 * Class representation of a dateTimePair
 * 
 * @author Kai Parker, Guy Fuchs, Tiernan Colby
 */
public class dateTimePair {
    
    // instance variables
    LocalDate date;
    LocalTime time;
    
    /**
     * constructs new dateTime pair
     * @param date
     * @param time
     */
    public dateTimePair(LocalDate date, LocalTime time) {
        this.date = date;
        this.time = time;
    }

    /**
     * @param other object to check equality with
     * @return boolean indicating equality
     */
    public boolean equals(Object other) {
        if (this == other){  // if the objects are the same
            return true;  // return true
        } else if (other == null) {  // if other is null
            return false;  // return false
        } else if (other.getClass() == this.getClass()) {  // if classes are the same
            dateTimePair otherPair = (dateTimePair) other;  // cast other object as a dateTimePair
            return (this.date.equals(otherPair.date) && this.time.equals(otherPair.time));  // check if date and time are same in both objects
        }
        return false;  // otherwise return false
    }

    /**
     * @param other object to compare with
     * @return int corresponding to to whether it's greater, equal to, or less than other object
     */
    public int compareTo(dateTimePair otherPair) {
        if (this.equals(otherPair)) {  // if the pairs are equal return 0
            return 0;
        } else if (this.date.compareTo(otherPair.date) > 0) { // if this date is after other date return 1
            return 1;
        } else if (this.date.compareTo(otherPair.date) < 0) { // if this date is before other date return -1
            return -1;
        } else if (this.time.compareTo(otherPair.time) > 0) { // if this time after other time return 1
            return 1;
        } else { // if this time is before other time return -1
            return -1;
        }
    }

    
    /**
     * @return hashcode of dateTimePair
     */
    public int hashCode() {
        return Objects.hash(this.date, this.time);  // hash date and time
    }

    /**
     * @return String representation of the dateTimePair
     */
    public String toString() {
        return "<" + date + ", " + time + ">";
    }
    
    
}
