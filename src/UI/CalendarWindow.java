package UI;

import Listener.DataChangedListener;
import Model.Event.Event;
import Model.Group.Group;
import Model.User.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;

/**
 * A window that displays events in a monthly calendar grid.
 */
public class CalendarWindow extends JFrame implements DataChangedListener {

    private Group group;
    private User user;
    private YearMonth currentMonth;
    private JPanel calendarGrid;
    private JLabel monthLabel;

    public CalendarWindow(Group group, User user) {
        this.group = group;
        this.user = user;
        this.currentMonth = YearMonth.now();

        // Register for updates
        group.addListener(this);

        setTitle("Calendar - " + group.getName());
        setSize(800, 700);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        // --- TOP NAVIGATION ---
        JPanel navPanel = new JPanel(new BorderLayout());
        navPanel.setBackground(new Color(44, 62, 80));
        navPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JButton prevBtn = new JButton("<");
        JButton nextBtn = new JButton(">");
        monthLabel = new JLabel("", SwingConstants.CENTER);
        monthLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        monthLabel.setForeground(Color.WHITE);

        prevBtn.addActionListener(e -> { currentMonth = currentMonth.minusMonths(1); updateCalendar(); });
        nextBtn.addActionListener(e -> { currentMonth = currentMonth.plusMonths(1); updateCalendar(); });

        navPanel.add(prevBtn, BorderLayout.WEST);
        navPanel.add(monthLabel, BorderLayout.CENTER);
        navPanel.add(nextBtn, BorderLayout.EAST);

        // --- CALENDAR GRID ---
        calendarGrid = new JPanel(new GridLayout(0, 7)); // 7 columns for days
        calendarGrid.setBackground(Color.WHITE);

        add(navPanel, BorderLayout.NORTH);
        add(new JScrollPane(calendarGrid), BorderLayout.CENTER);

        updateCalendar();
        setVisible(true);
    }

    private void updateCalendar() {
        calendarGrid.removeAll();
        monthLabel.setText(currentMonth.getMonth().name() + " " + currentMonth.getYear());

        // Header - Days of week
        String[] daysOfWeek = {"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};
        for (String day : daysOfWeek) {
            JLabel label = new JLabel(day, SwingConstants.CENTER);
            label.setFont(new Font("Segoe UI", Font.BOLD, 12));
            label.setBorder(new LineBorder(Color.LIGHT_GRAY));
            calendarGrid.add(label);
        }

        // Calculate start of month
        LocalDate firstOfMonth = currentMonth.atDay(1);
        int dayOfWeekValue = firstOfMonth.getDayOfWeek().getValue(); // 1 = Mon, 7 = Sun

        // Empty cells before the first day
        for (int i = 1; i < dayOfWeekValue; i++) {
            calendarGrid.add(new JLabel(""));
        }

        // Fill days of the month
        int daysInMonth = currentMonth.lengthOfMonth();
        for (int day = 1; day <= daysInMonth; day++) {
            LocalDate date = currentMonth.atDay(day);
            calendarGrid.add(createDayComponent(date));
        }

        calendarGrid.revalidate();
        calendarGrid.repaint();
    }

    private JPanel createDayComponent(LocalDate date) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new LineBorder(new Color(230, 230, 230)));
        panel.setBackground(Color.WHITE);

        JLabel dayLabel = new JLabel(String.valueOf(date.getDayOfMonth()));
        dayLabel.setBorder(new EmptyBorder(5, 5, 5, 5));
        panel.add(dayLabel, BorderLayout.NORTH);

        // Check if there are events on this day
        ArrayList<Model.Event.Event> dayEvents = new ArrayList<>();
        for (Event e : group.getEvents()) {
            if (e.getDateTime().toLocalDate().equals(date)) {
                dayEvents.add(e);
            }
        }

        if (!dayEvents.isEmpty()) {
            panel.setBackground(new Color(235, 245, 255));
            JPanel eventContainer = new JPanel(new GridLayout(0, 1, 2, 2));
            eventContainer.setOpaque(false);

            for (Model.Event.Event e : dayEvents) {
                JButton eventBtn = new JButton(e.getTitle());
                eventBtn.setFont(new Font("Segoe UI", Font.PLAIN, 10));
                eventBtn.setBackground(new Color(52, 152, 219));
                eventBtn.setForeground(Color.WHITE);
                eventBtn.addActionListener(ae -> new EventDetailWindow(e, user));
                eventContainer.add(eventBtn);
            }
            panel.add(eventContainer, BorderLayout.CENTER);
        }

        return panel;
    }

    @Override
    public void onDataChanged() {
        updateCalendar();
    }
}