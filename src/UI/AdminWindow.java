package UI;

import Listener.DataChangedListener;
import Model.Event.Event;
import Model.Group.Group;
import Model.User.Admin;
import Model.User.Member;
import Model.User.User;

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
        setSize(600, 600);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // --- TOP PANEL: Title and Calendar ---
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        topPanel.setBackground(new Color(245, 245, 245));

        JLabel titleLabel = new JLabel("Management: " + group.getName());
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setForeground(new Color(44, 62, 80));

        JButton openCalendarBtn = new JButton("OPEN CALENDAR");
        openCalendarBtn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        openCalendarBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        openCalendarBtn.addActionListener(e -> new CalendarWindow(group, admin));

        topPanel.add(titleLabel, BorderLayout.WEST);
        topPanel.add(openCalendarBtn, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);

        // --- CENTER: Tabs ---
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Segoe UI", Font.BOLD, 13));
        tabbedPane.addTab("Events & Trainings", createEventsPanel());
        tabbedPane.addTab("Team Members", createMembersPanel());
        add(tabbedPane, BorderLayout.CENTER);

        onDataChanged();
        setVisible(true);
    }

    private JPanel createEventsPanel() {
        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));

        eventModel = new DefaultListModel<>();
        eventList = new JList<>(eventModel);
        eventList.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        eventList.setFixedCellHeight(40);
        eventList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        eventList.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    Event selectedEvent = eventList.getSelectedValue();
                    if (selectedEvent != null) new EventDetailWindow(selectedEvent, admin);
                }
            }
        });

        JButton addButton = new JButton("CREATE NEW EVENT");
        addButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        addButton.setBackground(new Color(46, 204, 113));
        addButton.setForeground(Color.WHITE);
        addButton.setFocusPainted(false);
        addButton.setMargin(new Insets(10, 20, 10, 20));
        addButton.addActionListener(e -> showAddEventDialog());

        panel.add(new JScrollPane(eventList), BorderLayout.CENTER);
        panel.add(addButton, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createMembersPanel() {
        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));

        memberModel = new DefaultListModel<>();
        JList<User> memberList = new JList<>(memberModel);
        memberList.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        panel.add(new JScrollPane(memberList), BorderLayout.CENTER);

        JPanel addMemberPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        addMemberPanel.setBorder(BorderFactory.createTitledBorder("Register New Person"));

        JTextField nameField = new JTextField(12);
        JComboBox<String> roleCombo = new JComboBox<>(new String[]{"Member", "Admin"});
        JButton addMemberBtn = new JButton("Add & Open Window");

        addMemberBtn.addActionListener(e -> {
            String name = nameField.getText().trim();
            if (!name.isEmpty()) {
                User newUser = roleCombo.getSelectedIndex() == 0 ? new Member(name) : new Admin(name);
                group.addMember(newUser);
                nameField.setText("");

                if (newUser.isAdmin()) new AdminWindow(group, newUser);
                else new MemberWindow(group, newUser);
            }
        });

        addMemberPanel.add(new JLabel("Name:"));
        addMemberPanel.add(nameField);
        addMemberPanel.add(new JLabel("Role:"));
        addMemberPanel.add(roleCombo);
        addMemberPanel.add(addMemberBtn);

        panel.add(addMemberPanel, BorderLayout.SOUTH);
        return panel;
    }

    private void showAddEventDialog() {
        JDialog dialog = new JDialog(this, "Create Event", true);
        dialog.setSize(400, 400);
        dialog.setLocationRelativeTo(this);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField titleField = new JTextField(15);
        JTextField dateField = new JTextField("dd.MM.yyyy HH:mm", 15);
        JTextArea descArea = new JTextArea(5, 15);
        descArea.setLineWrap(true);
        descArea.setWrapStyleWord(true);
        JScrollPane descScroll = new JScrollPane(descArea);

        // Styling
        titleField.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        dateField.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        // Add components back exactly as they were
        gbc.gridx = 0; gbc.gridy = 0; formPanel.add(new JLabel("Event Name:"), gbc);
        gbc.gridx = 1; formPanel.add(titleField, gbc);

        gbc.gridx = 0; gbc.gridy = 1; formPanel.add(new JLabel("Date & Time:"), gbc);
        gbc.gridx = 1; formPanel.add(dateField, gbc);

        gbc.gridx = 0; gbc.gridy = 2; formPanel.add(new JLabel("Short Description:"), gbc);
        gbc.gridx = 1; formPanel.add(descScroll, gbc);

        JButton saveBtn = new JButton("SAVE TO PLANNER");
        saveBtn.setBackground(new Color(52, 152, 219));
        saveBtn.setForeground(Color.WHITE);
        saveBtn.setFont(new Font("Segoe UI", Font.BOLD, 13));

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 8, 8, 8);
        formPanel.add(saveBtn, gbc);

        saveBtn.addActionListener(e -> {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
                LocalDateTime parsedDate = LocalDateTime.parse(dateField.getText().trim(), formatter);

                if (!titleField.getText().trim().isEmpty()) {
                    group.addEvent(new Event(titleField.getText(), parsedDate, descArea.getText()));
                    dialog.dispose();
                } else {
                    JOptionPane.showMessageDialog(dialog, "Title is mandatory!");
                }
            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(dialog, "Format Error!\nPlease use: dd.MM.yyyy HH:mm");
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