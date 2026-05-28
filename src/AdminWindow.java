import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AdminWindow extends JFrame {

    private Group group;
    private User admin;
    private DefaultListModel<Event> eventModel;
    private JList<Event> eventList;

    public AdminWindow(Group group, User admin) {
        this.group = group;
        this.admin = admin;

        setTitle("ADMIN - " + admin.getName());
        setSize(500, 500);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Hlavní panel s šedým pozadím
        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(245, 245, 245));

        JLabel titleLabel = new JLabel("Správa tréninků: " + group.getName());
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setForeground(new Color(44, 62, 80));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        eventModel = new DefaultListModel<>();
        eventList = new JList<>(eventModel);
        eventList.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        eventList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        eventList.setFixedCellHeight(40);
        eventList.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));

        // Otevření detailu dvojklikem
        eventList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    Event selectedEvent = eventList.getSelectedValue();
                    if (selectedEvent != null) new EventDetailWindow(selectedEvent, admin);
                }
            }
        });

        // Tlačítko pro přidání - OPRAVENO: Insets místo Padding
        JButton addButton = new JButton("➕ VYTVOŘIT NOVÝ TRÉNINK");
        addButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        addButton.setBackground(new Color(46, 204, 113));
        addButton.setForeground(Color.WHITE);
        addButton.setFocusPainted(false);
        addButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        addButton.setMargin(new Insets(10, 20, 10, 20)); // Správné nastavení okrajů ve Swingu

        addButton.addActionListener(e -> showAddEventDialog());

        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.add(new JScrollPane(eventList), BorderLayout.CENTER);
        mainPanel.add(addButton, BorderLayout.SOUTH);

        add(mainPanel);

        // Automatický refresh každou sekundu
        new Timer(1000, e -> refreshEvents()).start();

        setVisible(true);
    }

    private void showAddEventDialog() {
        JDialog dialog = new JDialog(this, "Vytvořit trénink", true);
        dialog.setSize(380, 350);
        dialog.setLocationRelativeTo(this);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField titleField = new JTextField(15);
        JTextField dateField = new JTextField(15);
        JTextArea descArea = new JTextArea(4, 15);
        descArea.setLineWrap(true);
        descArea.setWrapStyleWord(true);
        JScrollPane descScroll = new JScrollPane(descArea);

        // Styling políček
        titleField.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        dateField.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        // Přidání prvků do mřížky
        gbc.gridx = 0; gbc.gridy = 0; formPanel.add(new JLabel("Název akce:"), gbc);
        gbc.gridx = 1; formPanel.add(titleField, gbc);

        gbc.gridx = 0; gbc.gridy = 1; formPanel.add(new JLabel("Datum a čas:"), gbc);
        gbc.gridx = 1; formPanel.add(dateField, gbc);

        gbc.gridx = 0; gbc.gridy = 2; formPanel.add(new JLabel("Krátký popis:"), gbc);
        gbc.gridx = 1; formPanel.add(descScroll, gbc);

        JButton saveBtn = new JButton("Uložit do plánovače");
        saveBtn.setBackground(new Color(52, 152, 219));
        saveBtn.setForeground(Color.WHITE);
        saveBtn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        saveBtn.setFocusPainted(false);

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 8, 8, 8);
        formPanel.add(saveBtn, gbc);

        saveBtn.addActionListener(e -> {
            if (!titleField.getText().trim().isEmpty() && !dateField.getText().trim().isEmpty()) {
                group.addEvent(new Event(titleField.getText(), dateField.getText(), descArea.getText()));
                dialog.dispose();
            } else {
                JOptionPane.showMessageDialog(dialog, "Musíš vyplnit alespoň název a datum!", "Chyba", JOptionPane.ERROR_MESSAGE);
            }
        });

        dialog.add(formPanel);
        dialog.setVisible(true);
    }

    private void refreshEvents() {
        if (eventModel.getSize() != group.getEvents().size()) {
            eventModel.clear();
            for (Event e : group.getEvents()) {
                eventModel.addElement(e);
            }
        }
    }
}