import javax.swing.*;
import java.awt.*;

public class MemberWindow extends JFrame {

    private Group group;

    private DefaultListModel<Event> eventModel;
    private JList<Event> eventList;

    public MemberWindow(Group group, User user) {

        this.group = group;

        setTitle("ČLEN - " + user.getName());
        setSize(400, 300);
        setLayout(new BorderLayout());

        eventModel = new DefaultListModel<>();
        eventList = new JList<>(eventModel);

        refreshEvents();

        add(new JScrollPane(eventList), BorderLayout.CENTER);

        setVisible(true);
    }

    private void refreshEvents() {

        eventModel.clear();

        for(Event e : group.getEvents()) {
            eventModel.addElement(e);
        }
    }
}