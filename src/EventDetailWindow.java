import javax.swing.*;
import java.awt.*;

public class EventDetailWindow extends JFrame {

    private Event event;
    private User user;

    private DefaultListModel<Participation> participationModel;
    private JList<Participation> participationList;

    public EventDetailWindow(Event event, User user) {

        this.event = event;
        this.user = user;

        setTitle(event.getTitle());
        setSize(500, 400);
        setLayout(new BorderLayout());

        JLabel infoLabel = new JLabel(
                "<html>" +
                        "<h2>" + event.getTitle() + "</h2>" +
                        "Datum: " + event.getDate() + "<br>" +
                        "Popis: " + event.getDescription() +
                        "</html>"
        );

        participationModel = new DefaultListModel<>();
        participationList = new JList<>(participationModel);

        refreshParticipations();

        JButton yesButton = new JButton("✔ Zúčastním se");
        JButton noButton = new JButton("❌ Nezúčastním se");

        yesButton.addActionListener(e -> addParticipation(true));
        noButton.addActionListener(e -> addParticipation(false));

        JPanel buttonPanel = new JPanel();

        buttonPanel.add(yesButton);
        buttonPanel.add(noButton);

        add(infoLabel, BorderLayout.NORTH);
        add(new JScrollPane(participationList), BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void addParticipation(boolean attending) {

        String note = JOptionPane.showInputDialog("Poznámka:");

        Participation participation =
                new Participation(user, event, attending, note);

        event.addParticipation(participation);

        refreshParticipations();
    }

    private void refreshParticipations() {

        participationModel.clear();

        for(Participation p : event.getParticipations()) {
            participationModel.addElement(p);
        }
    }
}