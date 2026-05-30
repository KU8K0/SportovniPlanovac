import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class AdminWindow extends JFrame implements DataChangedListener {

    private Group group;
    private User admin;
    private DefaultListModel<Event> eventModel;
    private JList<Event> eventList;
    private DefaultListModel<User> memberModel;

    public AdminWindow(Group group, User admin) {
        this.group = group;
        this.admin = admin;

        group.addListener(this);

        setTitle("ADMIN - " + admin.getName());
        setSize(550, 550);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // --- TOP PANEL WITH TITLE AND CALENDAR BUTTON ---
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel("Management: " + group.getName());
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setForeground(new Color(44, 62, 80));

        JButton openCalendarBtn = new JButton("📅 OPEN CALENDAR");
        openCalendarBtn.addActionListener(e -> new CalendarWindow(group, admin));

        topPanel.add(titleLabel, BorderLayout.WEST);
        topPanel.add(openCalendarBtn, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Segoe UI", Font.BOLD, 14));

        tabbedPane.addTab("Events", createEventsPanel());
        tabbedPane.addTab("Members", createMembersPanel());

        add(tabbedPane, BorderLayout.CENTER);

        onDataChanged();
        setVisible(true);
    }

    private JPanel createEventsPanel() {
        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        eventModel = new DefaultListModel<>();
        eventList = new JList<>(eventModel);
        eventList.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        eventList.setFixedCellHeight(40);

        eventList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    Event selectedEvent = eventList.getSelectedValue();
                    if (selectedEvent != null) new EventDetailWindow(selectedEvent, admin);
                }
            }
        });

        JButton addButton = new JButton("➕ CREATE NEW EVENT");
        addButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        addButton.setBackground(new Color(46, 204, 113));
        addButton.setForeground(Color.WHITE);
        addButton.setFocusPainted(false);
        addButton.addActionListener(e -> showAddEventDialog());

        panel.add(new JScrollPane(eventList), BorderLayout.CENTER);
        panel.add(addButton, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createMembersPanel() {
        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        memberModel = new DefaultListModel<>();
        JList<User> memberList = new JList<>(memberModel);
        memberList.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        panel.add(new JScrollPane(memberList), BorderLayout.CENTER);
        return panel;
    }

    private void showAddEventDialog() {
        JDialog dialog = new JDialog(this, "Create Event", true);
        dialog.setSize(400, 350);
        dialog.setLocationRelativeTo(this);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField titleField = new JTextField(15);
        JTextField dateField = new JTextField("dd.MM.yyyy HH:mm", 15);
        JTextArea descArea = new JTextArea(4, 15);
        descArea.setLineWrap(true);
        JScrollPane descScroll = new JScrollPane(descArea);

        gbc.gridx = 0; gbc.gridy = 0; formPanel.add(new JLabel("Name:"), gbc);
        gbc.gridx = 1; formPanel.add(titleField, gbc);
        gbc.gridx = 0; gbc.gridy = 1; formPanel.add(new JLabel("Date:"), gbc);
        gbc.gridx = 1; formPanel.add(dateField, gbc);
        gbc.gridx = 0; gbc.gridy = 2; formPanel.add(new JLabel("Desc:"), gbc);
        gbc.gridx = 1; formPanel.add(descScroll, gbc);

        JButton saveBtn = new JButton("Save Event");
        saveBtn.setBackground(new Color(52, 152, 219));
        saveBtn.setForeground(Color.WHITE);
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        formPanel.add(saveBtn, gbc);

        saveBtn.addActionListener(e -> {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
                LocalDateTime parsedDate = LocalDateTime.parse(dateField.getText().trim(), formatter);
                if (!titleField.getText().trim().isEmpty()) {
                    group.addEvent(new Event(titleField.getText(), parsedDate, descArea.getText()));
                    dialog.dispose();
                }
            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(dialog, "Invalid date format!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        dialog.add(formPanel);
        dialog.setVisible(true);
    }

    @Override
    public void onDataChanged() {
        eventModel.clear();
        for (Event e : group.getEvents()) eventModel.addElement(e);
        memberModel.clear();
        for (User u : group.getMembers()) memberModel.addElement(u);
    }
}