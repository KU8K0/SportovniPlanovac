import java.util.ArrayList;
import java.util.List;

public class Group {

    private String name;
    private ArrayList<User> members;
    private ArrayList<Event> events;

    // List of listeners (windows) to update when data changes
    private List<DataChangedListener> listeners;

    public Group(String name) {
        this.name = name;
        this.members = new ArrayList<>();
        this.events = new ArrayList<>();
        this.listeners = new ArrayList<>();
    }

    public void addListener(DataChangedListener listener) {
        listeners.add(listener);
    }

    private void notifyListeners() {
        for (DataChangedListener listener : listeners) {
            listener.onDataChanged();
        }
    }

    public void addMember(User user) {
        members.add(user);
        notifyListeners();
    }

    public void removeMember(User user) {
        members.remove(user);
        notifyListeners();
    }

    public void addEvent(Event event) {
        events.add(event);
        notifyListeners();
    }

    public ArrayList<User> getMembers() { return members; }
    public ArrayList<Event> getEvents() { return events; }
    public String getName() { return name; }
}