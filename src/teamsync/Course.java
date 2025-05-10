package teamsync;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    public String getClassBeginningTime(){
        return classBeginningTime;
    }

    public void setClassBeginningTime(String classBeginningTime){
        this.classBeginningTime = classBeginningTime;
    }

    public String getClassEndingTime(){
        return classEndingTime;
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
