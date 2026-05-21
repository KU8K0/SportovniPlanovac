import java.util.ArrayList;

public class Group {

    private String name;
    private ArrayList<User> members;
    private ArrayList<Event> events;

    public Group(String name) {
        this.name = name;

        members = new ArrayList<>();
        events = new ArrayList<>();
    }

    public void addMember(User user) {
        members.add(user);
    }

    public void removeMember(User user) {
        members.remove(user);
    }

    public void addEvent(Event event) {
        events.add(event);
    }

    public ArrayList<User> getMembers() {
        return members;
    }

    public ArrayList<Event> getEvents() {
        return events;
    }

    public String getName() {
        return name;
    }
}