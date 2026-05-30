import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Initial window to setup the Team Name and the first Admin.
 */
public class WelcomeWindow extends JFrame {

    public WelcomeWindow() {
        setTitle("SportovniPlanovac - Setup");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("Create Your Team", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(title, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 1; panel.add(new JLabel("Team Name:"), gbc);
        JTextField teamField = new JTextField("", 15);
        gbc.gridx = 1; panel.add(teamField, gbc);

        gbc.gridx = 0; gbc.gridy = 2; panel.add(new JLabel("Admin Name:"), gbc);
        JTextField adminField = new JTextField("", 15);
        gbc.gridx = 1; panel.add(adminField, gbc);

        JButton startBtn = new JButton("START APPLICATION");
        startBtn.setBackground(new Color(46, 204, 113));
        startBtn.setForeground(Color.WHITE);
        startBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        panel.add(startBtn, gbc);

        startBtn.addActionListener(e -> {
            String tName = teamField.getText().trim();
            String aName = adminField.getText().trim();

            if (!tName.isEmpty() && !aName.isEmpty()) {
                Group group = new Group(tName);
                Admin admin = new Admin(aName);
                group.addMember(admin);

                new AdminWindow(group, admin);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Please fill in all fields!");
            }
        });

        add(panel);
        setVisible(true);
    }
}