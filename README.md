# CS62-Final-Project: TeamSync
## Project Overview
>This program was built to help student-athletes manage their schedules by combining academic and athletic commitments into one system. We created a program that allows users to build schedules with academic and athletic events, detect conflicts, and filter courses based on department and number. The program includes a role-based access system so coaches can view their athletes’ schedules. The program supports both student-athletes and coaches. It allows coaches to view their and their athlete’s schedules, add events to the team and to individuals, add athletes, and more. Student-athletes can register for courses, view their profile and schedule, view conflicts, and add events. Users are connected to their practice schedule based on the athletic schedule that the coach uploaded. Using data from Fall 2024 courses at the Claremont College, courses are filtered by athletic schedule and users are able to filter by department and course number.

## Table of Contents
- [Project Overview](#project-overview)
- [Public API](#public-api)
- [Running the Code](#running-the-code)
- [Final Note and Future Improvements](#final-note-and-future-improvements)
- [Authors](#authors)


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

### Launching the Program

<img src="https://github.com/user-attachments/assets/08c6643c-cfb5-4f24-a640-f11883ed6eef" width="200" alt="Main menu with user role options" />

Run `MainProgram` to launch the TeamSync application. You'll be prompted to log in as a Coach or Athlete.

---

### Logging in as the Coach

<img src="https://github.com/user-attachments/assets/8e750c93-14e1-4e27-a841-a79a67bab87d" width="400" alt="Coach menu options" />

The coach can manage schedules, view conflicts, add athletes, and input an athletic practice schedule.

---

### Uploading a Practice Schedule

<img src="https://github.com/user-attachments/assets/8e529894-4c17-44b6-9827-a738bf3feb57" width="600" alt="Practice schedule input and confirmation" />

After inputting the sample practice schedule file from the `Data/` folder and specifying the dates, the practice events are added to every athlete's schedule. 

---

### Switching to Athlete Login

<img src="https://github.com/user-attachments/assets/60ea2509-7898-474f-aab1-cac05e562cdf" width="500" alt="Athlete registration prompt" />

If the username is not found, you'll be prompted to create an account. This registers the user and adds them to the coach's list of athletes. This shows the role-based access feature.

---

### Course Registration with Filtering

<img src="https://github.com/user-attachments/assets/a1868b92-34fd-4193-aab8-24fa9f44c445" width="600" alt="Course filter options" />


Athletes can register for courses by filtering by academic department, the coach-inputted athletic schedule, or both. In this case, the athlete filters by both. This allows for the viewing of classes based on input parameters.

---

### Adding Courses to Schedule

<img src="https://github.com/user-attachments/assets/051638b0-8737-4b99-b8d5-e9410c79afe8" width="700" alt="Course list displayed for registration" />
<img src="https://github.com/user-attachments/assets/23eb59c9-be72-4052-896c-a29705a16c66" width="650" alt="Courses successfully added" />

Select courses by entering the number shown next to each listing. Courses are added to the athlete's schedule instantly. The image of courses above shows only a small portion of the courses that will show up when using this feature.

Additionally, since courses were filtered using the coach-inputted schedule, the athlete currently has no conflicts.

---

### Conflict Detection

<img src="https://github.com/user-attachments/assets/a1494585-8363-49a9-80de-789acb7fdd7b" width="550" alt="User manually adds overlapping event to test conflict" />

An athlete adds a new event that overlaps with a class. The program is able to detect this conflict, demonstrating our conflict-detection feature alongside our adding event feature, which both the athlete and coach can do.

---

### Viewing Athlete Profile

<img src="https://github.com/user-attachments/assets/44320d26-5197-45dd-bfa2-ac6e919c68bb" width="600" alt="Athlete profile and full semester schedule" />

The athlete can view their full profile and semester schedule. The image cuts off part of the athlete's schedule for the whole semester.

---

### Coach View of Athlete Conflicts

<img src="https://github.com/user-attachments/assets/33fa7115-514a-4107-97de-8d3e84055ba4" width="550" alt="Coach menu displaying athlete conflicts" />

The coach can view all team conflicts. In this example, only "Guy" has a conflict, while the auto-added "athlete1" does not.

---

### Additional Features

- Coaches can view the athlete roster
- You can experiment with adding/removing events, filtering courses, or printing team data

---

### Final Note and Future Improvements

In the future, we want to expand this program to allow for multiple teams or even universities. Additionally, there are many methods written in the source code that were not used when writing the main program. These additional methods would allow for a more extensive application and provide many additional features for the users. Some of these methods include, but are not limited to, a coach adding an event to a single athlete's schedule and removing an event from a schedule.

For now though, we hope you enjoy what we have built. Have fun navigating TeamSync!


### Authors
Kai Parker, Guy Fuchs, and Tiernan Colby


