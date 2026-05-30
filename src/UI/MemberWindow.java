package UI;

import Listener.DataChangedListener;
import Model.Event.Event;
import Model.Group.Group;
import Model.User.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MemberWindow extends JFrame implements DataChangedListener {

    private Group group;
    private User user;
    private DefaultListModel<Model.Event.Event> eventModel;
    private JList<Event> eventList;

    public MemberWindow(Group group, User user) {
        this.group = group;
        this.user = user;

        group.addListener(this);

        setTitle("MEMBER - " + user.getName());
        setSize(450, 450);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel("Events: " + group.getName());
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));

        JButton openCalendarBtn = new JButton("📅 CALENDAR");
        openCalendarBtn.addActionListener(e -> new CalendarWindow(group, user));

        topPanel.add(titleLabel, BorderLayout.WEST);
        topPanel.add(openCalendarBtn, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);

        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBorder(new EmptyBorder(10, 20, 20, 20));

        eventModel = new DefaultListModel<>();
        eventList = new JList<>(eventModel);
        eventList.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        eventList.setFixedCellHeight(35);

        eventList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    Model.Event.Event selectedEvent = eventList.getSelectedValue();
                    if (selectedEvent != null) new EventDetailWindow(selectedEvent, user);
                }
            }
        });

        mainPanel.add(new JScrollPane(eventList), BorderLayout.CENTER);
        add(mainPanel, BorderLayout.CENTER);

        onDataChanged();
        setVisible(true);
    }

    @Override
    public void onDataChanged() {
        eventModel.clear();
        for (Model.Event.Event e : group.getEvents()) eventModel.addElement(e);
    }
}