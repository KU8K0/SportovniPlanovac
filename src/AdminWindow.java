import javax.swing.*;
import java.awt.*;

public class AdminWindow extends JFrame {

    private Group group;

    private DefaultListModel<Event> eventModel;
    private JList<Event> eventList;

    public AdminWindow(Group group, User admin) {

        this.group = group;

        setTitle("ADMIN - " + admin.getName());
        setSize(500, 400);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        eventModel = new DefaultListModel<>();
        eventList = new JList<>(eventModel);

        refreshEvents();

        JButton addButton = new JButton("Přidat akci");

        addButton.addActionListener(e -> {

            String title = JOptionPane.showInputDialog("Název akce:");
            String date = JOptionPane.showInputDialog("Datum:");
            String desc = JOptionPane.showInputDialog("Popis:");

            if(title != null && date != null) {

                Event event = new Event(title, date, desc);

                group.addEvent(event);

                refreshEvents();
            }
        });

        add(new JScrollPane(eventList), BorderLayout.CENTER);
        add(addButton, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void refreshEvents() {

        eventModel.clear();

        for(Event e : group.getEvents()) {
            eventModel.addElement(e);
        }
    }
}