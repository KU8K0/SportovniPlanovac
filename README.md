# Sportovní Plánovač (Sports Model.Event.Event Planner)

A Java Swing application designed for sports teams or groups to organize trainings, matches, and meetings. It allows administrators to manage events and members, while members can sign up for events and leave notes.

## Key Features
- **Dynamic Team Creation:** Set your team name and primary admin upon startup.
- **Role-Based Access:** - **Admins** can create events and add new members.
    - **Members** can view the schedule and mark their attendance.
- **Model.Event.Event Management:** Create events with specific dates (validated format `dd.MM.yyyy HH:mm`).
- **Attendance Tracking:** Real-time statistics (Attending/Absent) with personal notes for each event.
- **Interactive Calendar:** Visual monthly overview of all planned activities.
- **Real-time Updates:** Thanks to the Observer pattern, all open windows update instantly when data changes.

## Technical Details
- **Language:** Java (Swing for UI).
- **OOP Principles:** - Inheritance & Abstraction (Model.User.User, Model.User.Admin, Model.User.Member).
    - Interfaces (Listener.DataChangedListener for UI synchronization).
    - Exception Handling (Date parsing validation).
    - Collections (ArrayLists for data management).

## How to Run
1. Ensure you have **Java 8 or higher** installed.
2. Compile all `.java` files in the `src` folder.
3. Run the `App.Main` class.
4. On startup, enter your Team Name and Model.User.Admin Name to begin.

## Control Guide
- **Adding Events:** As an Model.User.Admin, click "Create New Model.Event.Event" and use the required date format.
- **Joining Events:** Double-click any event in the list to open its details and save your participation status.
- **Calendar:** Click "Open Calendar" to see a monthly grid. Click on event names inside the calendar to see details.
- **Adding Members:** In the Members tab (Model.User.Admin only), enter a name, select a role, and click "Add". A new window for that user will automatically open.