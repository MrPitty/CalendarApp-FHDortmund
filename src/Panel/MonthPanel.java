package Panel;

/*
    Created by Mr.Pitty on 09.12.2016.
*/

import Appointment.Appointments;
import Appointment.Day;
import GUI.CalenderWindow;
import GUI.DayWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static Appointment.DateUtil.*;

public class MonthPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private static final double WIDHT_IN_PERCENT = 0.90;
    private static final double HEIGHT_IN_PERCENT = 0.8;
    private static final short MIN_WIDTH = 350;
    private static final short MIN_HEIGHT = 400;
    private static final byte DAYS_IN_WEEK = 7;
    private static final byte CLICK_COUNT = 2;
    private byte month = CalenderWindow.defaultMonth;
    private short year = CalenderWindow.defaultYear;
    private double width, height;
    private Appointments aps;
    private Day[] days;
    Color red = new Color(255, 0, 0);
    Color purple = new Color(255, 0, 255);
    Color blue = new Color(0, 0, 255);
    Color defaultColor = new Color(0, 0, 0);
    Dimension minSize = new Dimension(MIN_WIDTH, MIN_HEIGHT);

    public MonthPanel(JFrame owner, Appointments aps) {
        super();

        this.aps = aps;
        days = new Day[daysOfMonth(month, year)];
        adjustLayout();
        fillArray();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == CLICK_COUNT) {
                    byte day = hitDay(e.getX(), e.getY());

                    if (day == -1) {
                        return;
                    }

                    new DayWindow(owner, aps, day);
                    adjustLayout();
                }
            }
        });

        addComponentListener(new ComponentListener() {

            @Override
            public void componentShown(ComponentEvent e) {

            }

            @Override
            public void componentResized(ComponentEvent e) {
                adjustLayout();
            }

            @Override
            public void componentMoved(ComponentEvent e) {

            }

            @Override
            public void componentHidden(ComponentEvent e) {

            }
        });

    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int i = 1; i <= DAYS_IN_WEEK; i++) {
            g.drawString(getDaysName(i - 1), (int) (i * width - width / 3), (int) height / 2);
        }

        for (int day = 0; day < daysOfMonth(month, year); day++) {
            Day temp = days[day];
            g.setColor(temp.getColor());
            g.drawString((day + 1) + ".", (int) (temp.getXCoord() + width / 2), (int) (temp.getYCoord() + height / 2));
            g.setColor(Color.LIGHT_GRAY);
            g.drawRect((int) temp.getXCoord(), (int) temp.getYCoord(), (int) width, (int) height);
        }

    }

    public void setDate(byte month, short year) {
        this.month = month;
        this.year = year;
        adjustLayout();
    }

    public void adjustLayout() {
        this.width = (getSize().getWidth()) * WIDHT_IN_PERCENT / DAYS_IN_WEEK;
        this.height = ((getSize().getHeight() / weeksInMonth(CalenderWindow.defaultMonth, CalenderWindow.defaultYear))
                * HEIGHT_IN_PERCENT);
        fillArray();
        repaint();
    }

    public void fillArray() {
        byte day = 0;
        days = new Day[daysOfMonth(month, year)];
        for (byte i = 1; i <= weeksInMonth(month, year); i++) {
            for (byte j = 1; j <= DAYS_IN_WEEK; j++) {

                if (day >= daysOfMonth(month, year)) {
                    return;
                } else if (i == 1) {
                    for (; j <= firstDayOfMonth(month, year); j++) {

                    }
                }

                byte pixel = (byte) (CalenderWindow.getMinSize() == minSize ? 0 : 6);
                int count = aps.count((byte) (day + 1), month, year);

                days[day] = new Day(j * width - width * HEIGHT_IN_PERCENT, (int) (i * height + pixel), count,
                        (j % 6 == 0 || j % 7 == 0) && (count != 0) ? purple
                                : count != 0 ? blue : (j % 6 == 0 || j % 7 == 0) ? red : defaultColor);

                day++;
            }
        }

    }

    public byte hitDay(int x, int y) {

        for (byte i = 0; i < daysOfMonth(month, year); i++) {
            int XCoord = (int) days[i].getXCoord();
            int YCoord = (int) days[i].getYCoord();

            if (XCoord <= x && XCoord + width >= x && YCoord <= y && YCoord + height >= y) {
                return i;
            }

        }
        return -1;
    }

}