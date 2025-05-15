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
<img width="272" alt="PNG image" src="https://github.com/user-attachments/assets/8e750c93-14e1-4e27-a841-a79a67bab87d" /> Logging in as the coach. You can choose any of the options. For example, I will input an athletic schedule. 

<img width="470" alt="PNG image" src="https://github.com/user-attachments/assets/8e529894-4c17-44b6-9827-a738bf3feb57" /> After inputting an athletic schedule found in the data folder of this repo, you will be brought back to the menu. All the current athletes and any newly added athletes will have this schedule automatically added to their personal schedule.

Now we will go back and log in as an athlete.
<img width="450" alt="PNG image" src="https://github.com/user-attachments/assets/60ea2509-7898-474f-aab1-cac05e562cdf" /> If the username does not exist, you will be prompted to register as seen in this photo. Enter your information and your account will be created and you will be added to the coach's team. This is the skeleton of the role-based access feature, where you have a different option menu with different features depending on whether you are an athlete or a coach.

Now we will register for courses to demonstrate course registration and viewing classes based on input parameters. When registering for courses, you will be prompted to pick a filter option. In this demonstration, we filter by both academic department and the athletic practice schedule.
<img width="546" alt="PNG image" src="https://github.com/user-attachments/assets/a1868b92-34fd-4193-aab8-24fa9f44c445" />

At the top of the list of classes will be the courses filtered by department, and then the courses filtered by schedule will show up. Select the number to the left of the course to add it to your schedule.

<img width="758" alt="PNG image" src="https://github.com/user-attachments/assets/051638b0-8737-4b99-b8d5-e9410c79afe8" />
<img width="679" alt="PNG image" src="https://github.com/user-attachments/assets/23eb59c9-be72-4052-896c-a29705a16c66" />

There are no conflicts in the current schedule because we filtered courses by the coach-inputted practice schedule, so there will be guaranteed to have no conflicts. Now, we will add a different event as an athlete to our personal schedule to demonstrate the conflict detection feature. After inputting the event, we can view the conflict.

<img width="530" alt="PNG image" src="https://github.com/user-attachments/assets/a1494585-8363-49a9-80de-789acb7fdd7b" />

Now we can view our full profile with all of our events for the semester (the image does not show all the events).
<img width="570" alt="PNG image" src="https://github.com/user-attachments/assets/44320d26-5197-45dd-bfa2-ac6e919c68bb" />

If we return to the coach profile, we can view all the athletes' conflicts if/when they have one. In this case, the autopopulated athlete1 does not have a conflict, so we will only see Guy's conflicts. 
<img width="514" alt="PNG image" src="https://github.com/user-attachments/assets/33fa7115-514a-4107-97de-8d3e84055ba4" />
There are also some small features, such as viewing the athlete roster as seen at the bottom of the image, that are fun to explore!

There are many other features and pathways to visit when running this program. Hope you enjoy!

## Future work


