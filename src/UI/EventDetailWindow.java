package UI;

import Model.Event.Event;
import Model.Event.Participation;
import Model.User.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class EventDetailWindow extends JFrame {

    private Event event;
    private User user;
    private DefaultListModel<Participation> participationModel;
    private JList<Participation> participationList;
    private JLabel statsLabel;

    public EventDetailWindow(Model.Event.Event event, User user) {
        this.event = event;
        this.user = user;

        setTitle("Model.Event.Event Detail: " + event.getTitle());
        setSize(500, 600);
        setLayout(new BorderLayout(10, 10));
        setLocationRelativeTo(null);

        // --- TOP PANEL: Model.Event.Event Information ---
        JPanel infoPanel = new JPanel(new GridLayout(0, 1, 5, 5));
        infoPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        infoPanel.setBackground(new Color(236, 240, 241));

        JLabel titleLbl = new JLabel(event.getTitle());
        titleLbl.setFont(new Font("Segoe UI", Font.BOLD, 22));

        JLabel dateLbl = new JLabel(event.getFormattedDate());
        dateLbl.setFont(new Font("Segoe UI", Font.ITALIC, 14));

        JTextArea descArea = new JTextArea(event.getDescription());
        descArea.setEditable(false);
        descArea.setBackground(null);
        descArea.setLineWrap(true);
        descArea.setWrapStyleWord(true);
        descArea.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        infoPanel.add(titleLbl);
        infoPanel.add(dateLbl);
        infoPanel.add(new JSeparator());
        infoPanel.add(descArea);

        // --- CENTER PANEL: Participants List and Statistics ---
        JPanel centerPanel = new JPanel(new BorderLayout(5, 5));
        centerPanel.setBorder(new EmptyBorder(0, 15, 0, 15));

        statsLabel = new JLabel("Loading stats...");
        statsLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        statsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        statsLabel.setBorder(new EmptyBorder(10, 0, 10, 0));

        participationModel = new DefaultListModel<>();
        participationList = new JList<>(participationModel);
        participationList.setCellRenderer(new ParticipationRenderer());

        centerPanel.add(statsLabel, BorderLayout.NORTH);
        centerPanel.add(new JScrollPane(participationList), BorderLayout.CENTER);

        // --- BOTTOM PANEL: Buttons ---
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        buttonPanel.setBorder(new EmptyBorder(15, 15, 15, 15));

        JButton yesButton = new JButton("I will attend");
        yesButton.setBackground(new Color(46, 204, 113));
        yesButton.setForeground(Color.WHITE);
        yesButton.setFont(new Font("Segoe UI", Font.BOLD, 14));

        JButton noButton = new JButton("I cannot attend");
        noButton.setBackground(new Color(231, 76, 60));
        noButton.setForeground(Color.WHITE);
        noButton.setFont(new Font("Segoe UI", Font.BOLD, 14));

        yesButton.addActionListener(e -> handleParticipation(true));
        noButton.addActionListener(e -> handleParticipation(false));

        buttonPanel.add(yesButton);
        buttonPanel.add(noButton);

        add(infoPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        refreshView();
        setVisible(true);
    }

    private void handleParticipation(boolean attending) {
        String note = JOptionPane.showInputDialog(this, "Add a note (optional):", "Model.Event.Participation Note", JOptionPane.PLAIN_MESSAGE);
        if (note == null) note = ""; // If user clicks Cancel

        Participation p = new Participation(user, event, attending, note);
        event.addOrUpdateParticipation(p);
        refreshView();
    }

    private void refreshView() {
        participationModel.clear();
        int yesCount = 0;

        for (Participation p : event.getParticipations()) {
            participationModel.addElement(p);
            if (p.isAttending()) yesCount++;
        }

        int total = event.getParticipations().size();
        int noCount = total - yesCount;

        statsLabel.setText(String.format("ATTENDING: %d  |  ABSENT: %d  |  TOTAL RESPONSES: %d", yesCount, noCount, total));
    }

    // Inner class for custom rendering of list items
    private static class ParticipationRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            Participation p = (Participation) value;

            if (p.isAttending()) {
                label.setForeground(new Color(39, 174, 96));
                label.setText("● " + p.getUser().getName() + " (ATTENDING) - " + p.getNote());
            } else {
                label.setForeground(new Color(192, 57, 43));
                label.setText("○ " + p.getUser().getName() + " (ABSENT) - " + p.getNote());
            }

            label.setBorder(new EmptyBorder(5, 5, 5, 5));
            return label;
        }
    }
}