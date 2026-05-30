package Model.Event;

import Model.User.User;

public class Participation {

    private User user;
    private Event event;
    private boolean attending;
    private String note;

    public Participation(User user, Event event, boolean attending, String note) {
        this.user = user;
        this.event = event;
        this.attending = attending;
        this.note = note;
    }

    public User getUser() { return user; }
    public Event getEvent() { return event; }
    public boolean isAttending() { return attending; }
    public String getNote() { return note; }

    public void setAttending(boolean attending) { this.attending = attending; }
    public void setNote(String note) { this.note = note; }

    @Override
    public String toString() {
        String status = attending ? "YES" : "NO";
        return user.getName() + " - " + status + " - " + note;
    }
}