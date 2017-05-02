package GUI;

import Appointment.Appointments;
import Appointment.DateUtil;
import Panel.MonthPanel;
import SaveOpen.SaveAppointments;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.Serializable;

import static Appointment.DateUtil.*;

/*
  Created by Mr.Pitty on 28.10.2016.
 */

public class CalenderWindow extends JFrame implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final short MIN_WIDTH = 350, MIN_HEIGHT = 400, DEFAULT_WIDTH = 450, DEFAULT_HEIGTH = 600;
    public static byte defaultMonth = 11;
    public static short defaultYear = 2016;
    private static JLabel lblMonthAndYear;
    private static MonthPanel panel;
    public static Dimension minSize;

    @SuppressWarnings("deprecation")
    public CalenderWindow(Appointments aps) {
        super("CalendarApp");
        minSize = new Dimension(MIN_WIDTH, MIN_HEIGHT);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                SaveAppointments.writeFile(defaultMonth, defaultYear, aps);
            }
        });
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGTH);
        setMinimumSize(minSize);
        setResizable(true);
        setLocationRelativeTo(null);
        getContentPane().setLayout(new BorderLayout(0, 0));

        panel = new MonthPanel(this, aps);
        getContentPane().add(panel, BorderLayout.CENTER);

        JPanel panelTop = new JPanel();
        getContentPane().add(panelTop, BorderLayout.NORTH);
        GridBagLayout gbl_panelTop = new GridBagLayout();
        gbl_panelTop.columnWidths = new int[]{0, 116, 116, 116, 0, 0};
        gbl_panelTop.rowHeights = new int[]{23, 23, 0, 0, 0};
        gbl_panelTop.columnWeights = new double[]{1.0, 1.0, 0.0, 1.0, 1.0, Double.MIN_VALUE};
        gbl_panelTop.rowWeights = new double[]{0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
        panelTop.setLayout(gbl_panelTop);

        JButton btnPrev = new JButton("<");
        GridBagConstraints gbc_btnPrev = new GridBagConstraints();
        gbc_btnPrev.anchor = GridBagConstraints.EAST;
        gbc_btnPrev.insets = new Insets(0, 0, 5, 5);
        gbc_btnPrev.gridx = 1;
        gbc_btnPrev.gridy = 1;
        panelTop.add(btnPrev, gbc_btnPrev);
        btnPrev.setSize(getPreferredSize());
        btnPrev.addActionListener(e -> {
            if ((defaultYear == getMinYearValue()) && (defaultMonth == getMinValue())) {
                btnPrev.disable();
            } else {
                defaultYear = defaultMonth == getMinValue() ? --defaultYear : defaultYear;
                defaultMonth = defaultMonth == getMinValue() ? getMonthMaxValue() : --defaultMonth;
                lblMonthAndYear.setText(getMonthOfYear(defaultMonth) + " " + defaultYear);
                panel.setDate(defaultMonth, defaultYear);
            }

        });
        lblMonthAndYear = new JLabel(getMonthOfYear(defaultMonth) + " " + defaultYear);
        GridBagConstraints gbc_lblMonthAndYear = new GridBagConstraints();
        gbc_lblMonthAndYear.fill = GridBagConstraints.VERTICAL;
        gbc_lblMonthAndYear.insets = new Insets(0, 0, 5, 5);
        gbc_lblMonthAndYear.gridx = 2;
        gbc_lblMonthAndYear.gridy = 1;
        panelTop.add(lblMonthAndYear, gbc_lblMonthAndYear);

        JButton btnNext = new JButton(">");
        GridBagConstraints gbc_btnNext = new GridBagConstraints();
        gbc_btnNext.anchor = GridBagConstraints.WEST;
        gbc_btnNext.fill = GridBagConstraints.VERTICAL;
        gbc_btnNext.insets = new Insets(0, 0, 5, 5);
        gbc_btnNext.gridx = 3;
        gbc_btnNext.gridy = 1;
        panelTop.add(btnNext, gbc_btnNext);
        btnNext.setSize(getPreferredSize());
        btnNext.addActionListener(e -> {
            if (defaultYear == getMaxYearValue() && defaultMonth == getMonthMaxValue()) {
                btnNext.disable();
            } else {
                defaultYear = defaultMonth == getMonthMaxValue() ? ++defaultYear : defaultYear;
                defaultMonth = defaultMonth == getMonthMaxValue() ? getMinValue() : ++defaultMonth;
                lblMonthAndYear.setText(getMonthOfYear(defaultMonth) + " " + defaultYear);
                panel.setDate(defaultMonth, defaultYear);
            }

        });
        JLabel madeBy = new JLabel("Made by \n@Mr.Pitty");
        GridBagConstraints gbc_lblMade = new GridBagConstraints();
        gbc_lblMade.anchor = GridBagConstraints.WEST;
        gbc_lblMade.fill = GridBagConstraints.VERTICAL;
        gbc_lblMade.insets = new Insets(0, 0, 5, 5);
        gbc_lblMade.gridx = 2;
        gbc_lblMade.gridy = 0;
        panelTop.add(madeBy, gbc_lblMade);

        JButton btnChoice = new JButton("Choice");
        btnChoice.setSize(getPreferredSize());
        btnChoice.addActionListener(e -> {
            new ChoiceDateWindow(this);
            panel.adjustLayout();
        });

        JButton btnAdd = new JButton("Add");
        btnAdd.addActionListener(e -> {
            new AppointmentWindow(this, aps);
            panel.adjustLayout();
        });
        GridBagConstraints gbc_btnAdd = new GridBagConstraints();
        gbc_btnAdd.insets = new Insets(0, 0, 5, 5);
        gbc_btnAdd.gridx = 1;
        gbc_btnAdd.gridy = 2;
        panelTop.add(btnAdd, gbc_btnAdd);
        GridBagConstraints gbc_btnChoice = new GridBagConstraints();
        gbc_btnChoice.insets = new Insets(0, 0, 5, 5);
        gbc_btnChoice.anchor = GridBagConstraints.SOUTH;
        gbc_btnChoice.gridx = 2;
        gbc_btnChoice.gridy = 2;
        panelTop.add(btnChoice, gbc_btnChoice);

        JButton btnClear = new JButton("Clear");
        btnClear.addActionListener(e -> {

            if (JOptionPane
                    .showConfirmDialog(null,
                            "Do you want to delete all appointments at " + DateUtil.getMonth(defaultMonth) + " "
                                    + defaultYear + " ?",
                            "Remove appointments", JOptionPane.YES_NO_OPTION) == getMinValue()) {

                for (byte day = 1; day <= daysOfMonth(defaultMonth, defaultYear); day++) {
                    if (aps.count(day, defaultMonth, defaultYear) > 0) {
                        aps.removeAll(day, defaultMonth, defaultYear);
                    }
                }
                panel.adjustLayout();
            }

        });
        GridBagConstraints gbc_btnClear = new GridBagConstraints();
        gbc_btnClear.insets = new Insets(0, 0, 5, 5);
        gbc_btnClear.gridx = 3;
        gbc_btnClear.gridy = 2;
        panelTop.add(btnClear, gbc_btnClear);

        setVisible(true);

    }

    public static Dimension getMinSize() {
        return minSize;
    }

    public static void setNewLabel(byte months, short years) {
        defaultMonth = months;
        defaultYear = years;
        lblMonthAndYear.setText(getMonthOfYear(defaultMonth) + " " + defaultYear);
        panel.setDate(defaultMonth, defaultYear);
    }

}
