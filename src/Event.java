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
        this.participations = new ArrayList<>();
    }

    //Zajišťuje, že uživatel nebude v seznamu dvakrát
    public void addOrUpdateParticipation(Participation newParticipation) {
        for (int i = 0; i < participations.size(); i++) {
            if (participations.get(i).getUser().getName().equals(newParticipation.getUser().getName())) {
                participations.set(i, newParticipation);
                return;
            }
        }
        participations.add(newParticipation);
    }

    public String getTitle() { return title; }
    public String getDate() { return date; }
    public String getDescription() { return description; }
    public ArrayList<Participation> getParticipations() { return participations; }

    @Override
    public String toString() {
        return title + " (" + date + ")";
    }
}