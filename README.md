# CS62-Final-Project: TeamSync
## Project Overiew
> This program was built to help student-athletes manage their schedules by combining academic and athletic commitments into one system. We created a program that allows users to build schedules with academic and athletic events, detect conflicts, and filter courses based on department and number. The program includes a role-based access system so coaches can view their athletes’ schedules. The program supports both student-athletes and coaches. It allows coaches to view their and their athlete’s schedules, add events to the team and to individuals, add athletes, and more. Student-athletes can register for courses, view their profile and schedule, view conflicts, and add events. Users are connected to their practice schedule based on the athletic schedule that the coach uploaded. Using data from Fall 2024 courses at the Claremont College, courses are filtered by athletic schedule and users are able to filter by department and course number. 

### External Libraries
We did rely on external libraries for the development of this program.

## Public API
Below is a summary of some of the public methods and constructors for the major classes in TeamSync. These methods represent the user-facing functionality of the application.

---

### `Athlete`

| Method Name and Inputs/Outputs | Description | Example Use |
|------------------|-------------|----------------|
| `Athlete(String name, String username, String team, String major, Schedule schedule, int gradYear)` | Constructs an `Athlete` object. | `new Athlete("Cecil", "cecil47", "Swim", "CS", new Schedule(), 2027)` |
| `void addEvent(Event e)` | Adds an event to the athlete’s schedule. | `athlete.addEvent(event)` |
| `boolean hasConflicts()` | Returns true if the athlete has any overlapping events. | `athlete.hasConflicts()` |
| `ArrayList<Course> getEnrolledCourses()` | Returns the list of currently enrolled courses. | `athlete.getEnrolledCourses()` |
| `void enrollCourse(Course course)` | Adds a course and all its events to the athlete's schedule. | `athlete.enrollCourse(course)` |

---

### `Coach`

| Method Signature | Description | Example Usage |
|------------------|-------------|----------------|
| `Coach(String name, String username, String team, Schedule schedule, HashMap<String, Athlete> athletes)` | Constructs a `Coach` object. | `new Coach("Coach Sagehen", "sagehen47", "Swim", schedule, athleteMap)` |
| `void addEventToTeam(Event sampleEvent)` | Adds an event to the coach and all athletes. | `coach.addEventToTeam(event)` |
| `void addAthlete(Athlete sampleAthlete)` | Adds an athlete to the coach’s team. | `coach.addAthlete(athlete)` |
| `ArrayList<ArrayList<Event>> getAthleteConflicts(String username)` | Returns a list of event conflicts for a specific athlete. | `coach.getAthleteConflicts("kai456")` |
| `ArrayList<Event> createPracticeSchedule(String filePath, LocalDate start, LocalDate end)` | Reads a CSV and generates a semester-long practice schedule. | `coach.createPracticeSchedule("Data/swim.csv", start, end)` |

---

### `Schedule`

| Method Signature | Description | Example Usage |
|------------------|-------------|----------------|
| `void addEvent(Event event)` | Adds an event and sorts the schedule. | `schedule.addEvent(event)` |
| `Event removeEvent(Event event)` | Removes the given event if found. | `schedule.removeEvent(event)` |
| `ArrayList<ArrayList<Event>> getConflicts()` | Returns all conflicting event pairs. | `schedule.getConflicts()` |
| `boolean detectConflict()` | Returns true if any overlapping events exist. | `schedule.detectConflict()` |

---

### `Event`

| Method Signature | Description | Example Usage |
|------------------|-------------|----------------|
| `Event(dateTimePair start, dateTimePair end, String name, int type, String info)` | Constructs an event. | `new Event(start, end, "Practice", 2, "Pool")` |
| `boolean detectOverlap(Event other)` | Checks if two events overlap in time. | `event1.detectOverlap(event2)` |
| `int getType()` | Returns the event type (1 = academic, 2 = athletic, 3 = other). | `event.getType()` |
| `String getName()` | Returns the name of the event. | `event.getName()` |

---

### `Course`

| Method Signature | Description | Example Usage |
|------------------|-------------|----------------|
| `ArrayList<Event> courseToEvent()` | Converts the course into recurring class events. | `course.courseToEvent()` |
| `static ArrayList<Course> filterByDept(String prefix)` | Filters all courses by department prefix. | `Course.filterByDept("CSCI")` |
| `static ArrayList<Course> filterBySchedule(Schedule schedule)` | Returns only courses that don’t conflict with a schedule. | `Course.filterBySchedule(schedule)` |
| `static ArrayList<Course> filterByBoth(String prefix, Schedule schedule)` | Filters courses by dept and conflict-freeness. | `Course.filterByBoth("CSCI", schedule)` |

---

### `dateTimePair`

| Method Signature | Description | Example Usage |
|------------------|-------------|----------------|
| `dateTimePair(LocalDate date, LocalTime time)` | Constructs a new date-time pair. | `new dateTimePair(LocalDate.now(), LocalTime.now())` |
| `int compareTo(dateTimePair other)` | Compares two dateTimePairs. | `pair1.compareTo(pair2)` |
| `boolean equals(Object other)` | Checks equality of two dateTimePairs. | `pair1.equals(pair2)` |
| `String toString()` | String format `<date, time>`. | `System.out.println(pair)` |

---
## Running the code
<img width="111" alt="PNG image" src="https://github.com/user-attachments/assets/08c6643c-cfb5-4f24-a640-f11883ed6eef" /> Run MainProgram to see this starting menu.


