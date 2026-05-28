import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MemberWindow extends JFrame {

    private Group group;
    private User user;
    private DefaultListModel<Event> eventModel;
    private JList<Event> eventList;

    public MemberWindow(Group group, User user) {
        this.group = group;
        this.user = user;

        setTitle("ČLEN - " + user.getName());
        setSize(450, 400);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(240, 242, 245));

        JLabel titleLabel = new JLabel("Tvoje tréninky: " + group.getName());
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        eventModel = new DefaultListModel<>();
        eventList = new JList<>(eventModel);
        eventList.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        eventList.setFixedCellHeight(35);

        eventList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    Event selectedEvent = eventList.getSelectedValue();
                    if (selectedEvent != null) new EventDetailWindow(selectedEvent, user);
                }
            }
        });

        JLabel helpLabel = new JLabel("<html><center>Nové akce se zobrazí automaticky.<br>Dvojklikem se přihlásíš.</center></html>");
        helpLabel.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        helpLabel.setForeground(Color.GRAY);
        helpLabel.setHorizontalAlignment(SwingConstants.CENTER);

        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.add(new JScrollPane(eventList), BorderLayout.CENTER);
        mainPanel.add(helpLabel, BorderLayout.SOUTH);

        add(mainPanel);

        // AUTOMATICKÝ REFRESH (synchronizace s adminem)
        new Timer(1000, e -> refreshEvents()).start();

        setVisible(true);
    }

    private void refreshEvents() {
        if (eventModel.getSize() != group.getEvents().size()) {
            eventModel.clear();
            for (Event e : group.getEvents()) eventModel.addElement(e);
        }
    }
}