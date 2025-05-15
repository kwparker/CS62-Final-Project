package teamsync;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.time.format.DateTimeFormatter;

// Puts the data from the course section schedule json into an arraylist and creates methods that converts a course to an event

/**
 * Represents a course and allows courses to be converted into events
 * Has methods to parse json file to read 5c course data and generate 
 * full semester course dates
 */
public class Course {
    
    // fields representing basic course details
    String courseSectionId;
    String classBeginningTime;
    String classEndingTime;
    String classMeetingDays;
    String room;

    public static List<Course> allCourses = new ArrayList<>();  // list for all courses

    /**
     * Constructs a Course object
     * 
     * @param courseSectionId courseSectionId of the course
     * @param classBeginningTime classBeginningTime start time
     * @param classEndingTime classEndingTime end time
     * @param classMeetingDays days of the week a course meets
     * @param room room course meets in
     */
    public Course(String courseSectionId, String classBeginningTime, String classEndingTime, String classMeetingDays, String room) {
        this.courseSectionId = courseSectionId;
        this.classBeginningTime = classBeginningTime;
        this.classEndingTime = classEndingTime;
        this.classMeetingDays = classMeetingDays;
        this.room = room;
    }

    /**
     * 
     * @return the course section ID
     */
    public String getCourseSectionId(){ 
        return courseSectionId; 
    }
    
    /**
     * 
     * @param courseSectionId sets the course section ID
     */
    public void setCourseSectionId(String courseSectionId){
        this.courseSectionId = courseSectionId; 
        }

    /**
     * Converts and returns class beginning time in LocalTime format
     * @return class beginning time
     */
    public LocalTime getClassBeginningTime(){
        if (classBeginningTime == null || classBeginningTime.length() < 3) {  // if beginning time is null or wrong format
            throw new IllegalArgumentException("Invalid classBeginningTime: " + classBeginningTime); // throws exception
        }    
        String correctTime = classBeginningTime;
        if (classBeginningTime.length() == 3){
            correctTime = "0" + classBeginningTime;  // reformats times with wrong structure
        }
        DateTimeFormatter format = DateTimeFormatter.ofPattern("HHmm");  // sets of format of time
        LocalTime time = LocalTime.parse(correctTime, format);  // changes to local time structure
        return time;  // returns time
    }

    /**
     * sets class beginning time
     * @param classBeginningTime beginning time of class
     */
    public void setClassBeginningTime(String classBeginningTime){
        this.classBeginningTime = classBeginningTime;
    }

    /**
     * Converts and returns class ending time in LocalTime format
     * @return ending time of class
     */
    public LocalTime getClassEndingTime(){
        if (classEndingTime == null || classEndingTime.length() < 3) { // if end time is null or wrong format
            throw new IllegalArgumentException("Invalid classEndingTime: " + classEndingTime); // throws exception
        }
        String correctTime = classEndingTime;
        if (classEndingTime.length() == 3){  // reformats times with wrong structure
            correctTime = "0" + classEndingTime;
        }
        DateTimeFormatter format = DateTimeFormatter.ofPattern("HHmm"); // sets of format of time
        LocalTime time = LocalTime.parse(correctTime, format); // changes to local time structure
        return time; // returns time
    }
    
    /**
     * sets the class ending time
     * @param classEndingTime ending time of class
     */
    public void setClassEndingTime(String classEndingTime){
        this.classEndingTime = classEndingTime;
    }

    /**
     * 
     * @return a string of class meeting days
     */
    public String getClassMeetingDays(){
        return classMeetingDays;
    }

    /**
     * sets the days the class meets
     * @param classMeetingDays string of class meeting days
     */
    public void setClassMeetingDays(String classMeetingDays){
        this.classMeetingDays = classMeetingDays;
    }

    /**
     * 
     * @return room class is in
     */
    public String getRoom(){
        return room;
    }

    /**
     * sets room name
     * @param room room where class is held
     */
    public void setInstructionSiteName(String room){
        this.room = room; }

    
    /**
     * parses a line in JSON file and extracts string
     * @param line line in JSON file
     * @return extracted string
     */
    public static String getLineValue(String line) {
       
        int colonIndex = line.indexOf(':'); // find index of colon char
        String value = line.substring(colonIndex + 1).trim(); // extract string after colon
        value = value.replace(",", ""); // remove commas
        value = value.replace("\"", ""); // remove quotes
        return value; // extracted string
    }


    /**
     * generated the dates (localdate) for a course according to fall 2024 semester
     * @return list of generated dates
     */
    public ArrayList<LocalDate> generateDates(){

        ArrayList<LocalDate> dates = new ArrayList<>();
        Set<DayOfWeek> classDays = new HashSet<>();

        // Map meeting letters to DayOfWeek using if-statements
        for (int i = 0; i < classMeetingDays.length(); i++) {
            char c = classMeetingDays.charAt(i);

            if (c == 'U') {
                classDays.add(DayOfWeek.SUNDAY);
            }
            if (c == 'M') {
                classDays.add(DayOfWeek.MONDAY);
            }
            if (c == 'T') {
                classDays.add(DayOfWeek.TUESDAY);
            }
            if (c == 'W') {
                classDays.add(DayOfWeek.WEDNESDAY);
            }
            if (c == 'R') {
                classDays.add(DayOfWeek.THURSDAY);
            }
            if (c == 'F') {
                classDays.add(DayOfWeek.FRIDAY);
            }
            if (c == 'S') {
                classDays.add(DayOfWeek.SATURDAY);
            }
        }

        // Loop through dates between Aug 26 and Dec 4, 2024
        LocalDate start = LocalDate.of(2024, 8, 26); // start date of classes for fall 2024 semester
        LocalDate end = LocalDate.of(2024, 12, 4); // end data of classes for fall 2024 semester

        for (LocalDate date = start; !date.isAfter(end); date = date.plusDays(1)) { // goes through all the days in the semester
            if (classDays.contains(date.getDayOfWeek())) { // if the course is on that day
                dates.add(date); // adds the date
            }
        }

        return dates;
    }

    // collects all the dates, creates an arraylist of event that correspond to the days that the course happens in fall of 2024
    /**
     * Converts course into a list of events for whole semester
     * @return list of events for each date the class meets
     */
    public ArrayList<Event> courseToEvent(){

        ArrayList<Event> courseEvents = new ArrayList<>();
        ArrayList<LocalDate> dates = this.generateDates(); // generates dates for this course 

        for (LocalDate day : dates){ // goes through all the dates 
            dateTimePair startPair = new dateTimePair(day, this.getClassBeginningTime()); // creates start pair
            dateTimePair endPair = new dateTimePair(day, this.getClassEndingTime()); // creates end pair

            Event eventInstance = new Event(startPair, endPair, courseSectionId, 1, room); // creates event

            courseEvents.add(eventInstance);  // add event to the list of course events
        }
    
         return courseEvents;  // list of courseEvents

    }

    // filter by department, then it will go through the 10 cs classes at pomona for example, then loop through those 10 classes,
    // and then check each one of those classes to see if they have a confliction/overlap, if they DO NOT have one then
    // print those to show them as they are the ones that are in the major department and have no overlap and fit in the schedule
    /**
     * returns list of courses filtered by the department prefix 
     * @param deptPrefix
     * @return
     */
    public static ArrayList<Course> filterByDept(String deptPrefix) {
        ArrayList<Course> filtered = new ArrayList();
        for (Course course: allCourses){  // for each course in all the courses
            if (course.classBeginningTime.equals("0") || course.classEndingTime.equals("0") || course.classMeetingDays.equals("-------")) { // Skip if no valid time or meeting days
                continue;
            }
            if (course.getCourseSectionId().startsWith(deptPrefix)){  // if course starts with the prefix
                filtered.add(course);  // add course to filtered list
            }
        }
        return filtered;  // return list of filtered courses
    }
    
    /**
     * returns list of courses filtered by the given schedule
     * @param schedule schedule to compare against
     * @return list of courses that don't conflict with given schedule
     */
    public static ArrayList<Course> filterBySchedule(Schedule schedule) {
        ArrayList<Course> nonConflicting = new ArrayList<>();
        for (Course course: allCourses){ // for each course in all courses
            
            if (course.classBeginningTime.equals("0") || course.classEndingTime.equals("0") || course.classMeetingDays.equals("-------")) { // skip if no valid time or no meeting days
                continue;
            }
            boolean hasConflict = false;
            ArrayList<Event> courseEvents;
            
            try{
                courseEvents = course.courseToEvent();  // try converting course to an event
            }
            catch (Exception e){
                System.out.println("Skipping course with invalid time: " + course.courseSectionId);
                continue;
            }
            for (Event courseEvent: course.courseToEvent()){ // for each event in list of course events
                for (Event scheduledEvent: schedule.getSchedule()){
                    if (courseEvent.detectOverlap(scheduledEvent)) {  // if there's overlap
                        hasConflict = true;
                        break; // break out of loop
                }
            }
            if (hasConflict){
                break;
            }  
        }
        if (!hasConflict){
            nonConflicting.add(course);  // add conflicting course to list
        }  
     }
     
     return nonConflicting;  // return filtered list

    }  

    /**
     * filters course list by department and schedule
     * @param deptPrefix prefix of department
     * @param schedule given schedule
     * @return filtered list
     */
    public static ArrayList<Course> filterByBoth(String deptPrefix, Schedule schedule) {
        ArrayList<Course> filteredCourses = new ArrayList<Course>();
        filteredCourses.addAll(filterByDept(deptPrefix));  // filter by department
        filteredCourses.addAll(filterBySchedule(schedule)); // filter by schedule
        return filteredCourses;  // return double filtered course list
        
    }
    /**
     * string representation of a course
     */
    @Override
    public String toString() {
        return "Course ID: " + courseSectionId + ", Time: " + classBeginningTime + "-" + classEndingTime + ", Days: " + classMeetingDays +
               ", Location: " + room;
    }


    // testing course methods
    public static void main(String[] args) {

        // loads in JSON file
        try (BufferedReader reader = new BufferedReader(new FileReader("Data/course-section-schedule.json"))) {
            String line;
            Course aCourse = null;

        while ((line = reader.readLine()) != null) {
            line = line.trim();

            // gets course info
            if (line.startsWith("{")) {
                    aCourse = new Course("", "", "", "", "");
                }
                else if (line.startsWith("\"courseSectionId\"")) {
                    aCourse.setCourseSectionId(getLineValue(line));
                } 
                else if (line.startsWith("\"classBeginningTime\"")) {
                    aCourse.setClassBeginningTime(getLineValue(line));
                } 
                else if (line.startsWith("\"classEndingTime\"")) {
                    aCourse.setClassEndingTime(getLineValue(line));
                } 
                else if (line.startsWith("\"classMeetingDays\"")) {
                    aCourse.setClassMeetingDays(getLineValue(line));
                } 
                else if (line.startsWith("\"instructionSiteName\"")) {
                    aCourse.setInstructionSiteName(getLineValue(line));
                } 
                else if (line.startsWith("}")) {
                    allCourses.add(aCourse);
                }
            }

        } catch (IOException e) {
            System.out.println("File Error");
        }
       

        Schedule testSchedule = new Schedule();  // creates schedule object
        LocalDate start = LocalDate.of(2024, 8, 26);  // creates start date
        LocalDate end = LocalDate.of(2024, 12, 4);  // creates end date

        // go through each weekday in date range and create event to block student's time and simulate conflicts
        for (LocalDate date = start; !date.isAfter(end); date = date.plusDays(1)) {
            DayOfWeek weekDay = date.getDayOfWeek();
            if (weekDay != DayOfWeek.SATURDAY && weekDay != DayOfWeek.SUNDAY) {
                Event dummyEvent = new Event(
                    new dateTimePair(date, LocalTime.of(9, 0)),
                    new dateTimePair(date, LocalTime.of(14, 0)),
                    "Daily Conflict Blocker", 1, "Schedule"
                );
                testSchedule.addEvent(dummyEvent);
            }
        }
        
        // Print filtered courses when filtering by schedule
        System.out.println("\n Testing filterBySchedule");
        ArrayList<Course> nonConflicting = Course.filterBySchedule(testSchedule);
        for (Course c : nonConflicting) {
            System.out.println(c);
        }
    }

}
