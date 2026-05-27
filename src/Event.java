import java.util.ArrayList;

public class Event {

    private String title;
    private String date;
    private String description;

    private ArrayList<Participation> participations;

    public Event(String title, String date, String description) {

        this.title = title;
        this.date = date;
        this.description = description;

        participations = new ArrayList<>();
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public ArrayList<Participation> getParticipations() {
        return participations;
    }

    public void addParticipation(Participation participation) {
        participations.add(participation);
    }

    @Override
    public String toString() {
        return title + " | " + date;
    }
}