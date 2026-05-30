package Model.Event;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Event {

    private String title;
    private LocalDateTime dateTime;
    private String description;
    private ArrayList<Participation> participations;

    public Event(String title, LocalDateTime dateTime, String description) {
        this.title = title;
        this.dateTime = dateTime;
        this.description = description;
        this.participations = new ArrayList<>();
    }

    /**
     * Ensures that a user is not in the participation list twice.
     */
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
    public LocalDateTime getDateTime() { return dateTime; }
    public String getDescription() { return description; }
    public ArrayList<Participation> getParticipations() { return participations; }

    /**
     * Formats the LocalDateTime to a readable String.
     */
    public String getFormattedDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        return dateTime.format(formatter);
    }

    @Override
    public String toString() {
        return title + " (" + getFormattedDate() + ")";
    }
}