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

public class Course {
    
    String courseSectionId;
    String classBeginningTime;
    String classEndingTime;
    String classMeetingDays;
    String room;

    public static List<Course> allCourses = new ArrayList<>();

    public Course(String courseSectionId, String classBeginningTime, String classEndingTime, String classMeetingDays, String room) {
        this.courseSectionId = courseSectionId;
        this.classBeginningTime = classBeginningTime;
        this.classEndingTime = classEndingTime;
        this.classMeetingDays = classMeetingDays;
        this.room = room;
    }

    public String getCourseSectionId(){ 
        return courseSectionId; 
    }
    
    public void setCourseSectionId(String courseSectionId){
        this.courseSectionId = courseSectionId; 
        }

    public LocalTime getClassBeginningTime(){

        DateTimeFormatter format = DateTimeFormatter.ofPattern("HHmm");
        LocalTime time = LocalTime.parse(classBeginningTime, format);
        return time;
    }

    public void setClassBeginningTime(String classBeginningTime){
        this.classBeginningTime = classBeginningTime;
    }

    public LocalTime getClassEndingTime(){
        DateTimeFormatter format = DateTimeFormatter.ofPattern("HHmm");
        LocalTime time = LocalTime.parse(classEndingTime, format);
        return time;
    }

    public void setClassEndingTime(String classEndingTime){
        this.classEndingTime = classEndingTime;
    }

    public String getClassMeetingDays(){
        return classMeetingDays;
    }

    public void setClassMeetingDays(String classMeetingDays){
        this.classMeetingDays = classMeetingDays;
    }

    public String getRoom(){
        return room;
    }

    public void setInstructionSiteName(String room){
        this.room = room; }

    
    // Get value between the quotes in a line of the json
    public static String getLineValue(String line) {
       
        int colonIndex = line.indexOf(':');
        String value = line.substring(colonIndex + 1).trim();
        value = value.replace(",", "");
        value = value.replace("\"", "");
        return value;
    }

    // generates the dates (localdate) for a course according to the fall 2024 semester
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
    public ArrayList<Event> courseToEvent(){

        ArrayList<Event> courseEvents = new ArrayList<>();
        ArrayList<LocalDate> dates = this.generateDates(); // generates dates for this course 

        for (LocalDate day : dates){ // goes through all the dates 
            dateTimePair startPair = new dateTimePair(day, this.getClassBeginningTime());
            dateTimePair endPair = new dateTimePair(day, this.getClassEndingTime());

            Event eventInstance = new Event(startPair, endPair, courseSectionId, 1, room);

            courseEvents.add(eventInstance);
        }
    
         return courseEvents;

    }

    // filter by department, then it will go through the 10 cs classes at pomona for example, then loop through those 10 classes,
    // and then check each one of those classes to see if they have a confliction/overlap, if they DO NOT have one then
    // print those to show them as they are the ones that are in the major department and have no overlap and fit in the schedule
    public static ArrayList<Course> filterByDept(String deptPrefix) {
        ArrayList<Course> filtered = new ArrayList();
        for (Course course: allCourses){
            if (course.getCourseSectionId().startsWith(deptPrefix)){
                filtered.add(course);
            }
        }
        return filtered;
    }
    
    // filter by current schedule, taks in a schedule as the parameter, and will return an arraylist of any course that does not 
    // conflict with any event in the inputted schedule
    public static ArrayList<Course> filterBySchedule(Schedule schedule) {
        ArrayList<Course> nonConflicting = new ArrayList<>();
        for (Course course: allCourses){
            boolean hasConflict = false;
            
            for (Event courseEvent: course.courseToEvent()){

                for (Event scheduledEvent: schedule.getSchedule()){

                    if (courseEvent.detectOverlap(scheduledEvent)) {
                        hasConflict = true;
                        break;
                }
            }
            if (hasConflict){
                break;
            }  
        }
        if (!hasConflict){
            nonConflicting.add(course);
        }  
     }
     
     return nonConflicting;

    }  

    // filter by both 
    public static ArrayList<Course> filterByBoth(String deptPrefix, Schedule schedule) {
        ArrayList<Course> filteredCourses = new ArrayList<Course>();
        filteredCourses.addAll(filterByDept(deptPrefix));
        filteredCourses.addAll(filterBySchedule(schedule));
        return filteredCourses;
        
    }


    public String toString() {
        return "Course ID: " + courseSectionId +
               ", Time: " + classBeginningTime + "-" + classEndingTime +
               ", Days: " + classMeetingDays +
               ", Location: " + room;
    }



    public static void main(String[] args) {
        

        try (BufferedReader reader = new BufferedReader(new FileReader("Data/course-section-schedule.json"))) {
            String line;
            Course aCourse = null;

        while ((line = reader.readLine()) != null) {
            line = line.trim();

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

        //for (Course courses : allCourses) {
//
          //  Event courses.courseSectionId = new <Event>;

        //    System.out.println(courses);
        //}


        // Printing out all courses
        for (Course courses : allCourses) {
            System.out.println(courses.classMeetingDays);
        }


        // Use this line of code to get this to run to populate allCourses:
        //  Course.main(null);
    }

            
}
