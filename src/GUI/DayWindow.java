package GUI;

import Appointment.Appointment;
import Appointment.Appointments;
import Appointment.DateUtil;
import Appointment.Link;
import Model.DayModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/*
  Created by Mr.Pitty on 28.10.2016.
 */

public class DayWindow extends JDialog {

    private static final long serialVersionUID = 1L;
    private static final int WEIGHT = 400;
    private static final int HEIGHT = 500;
    private static final short DEFAULT_YEAR = 1900;
    private static JTable table;

    public DayWindow(JFrame owner, Appointments aps, byte id) {
        super(owner, "Ihre Termine am " + (id + 1) + ". " + DateUtil.getMonth(CalenderWindow.defaultMonth) + " "
                + CalenderWindow.defaultYear, true);
        setResizable(true);
        setSize(WEIGHT, HEIGHT);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        DayModel dayModel = new DayModel(aps, (byte) (id + 1));
        JPanel panel = new JPanel();
        getContentPane().add(panel, BorderLayout.SOUTH);
        GridBagLayout gbl_panel = new GridBagLayout();
        gbl_panel.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
        gbl_panel.rowHeights = new int[]{0, 0};
        gbl_panel.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        gbl_panel.rowWeights = new double[]{1.0, Double.MIN_VALUE};
        panel.setLayout(gbl_panel);

        table = new JTable(dayModel);
        getContentPane().add(table, BorderLayout.CENTER);

        JLabel poworedBy = new JLabel("@MrPitty");

        JScrollPane scrollPane = new JScrollPane(table);
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        table.getColumnModel().getColumn(2).setPreferredWidth(300);

        JButton btnAdd = new JButton("Add");
        btnAdd.addActionListener(e -> {

            new AddButtonWindow(this, aps, id);

            updateTable();
        });
        GridBagConstraints gbc_btnAdd = new GridBagConstraints();
        gbc_btnAdd.insets = new Insets(0, 0, 0, 5);
        gbc_btnAdd.gridx = 2;
        gbc_btnAdd.gridy = 0;
        panel.add(btnAdd, gbc_btnAdd);


        table.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_DELETE) {
                    calculateSelectedItem(aps, id);
                    updateTable();
                }
            }
        });
        table.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    dispose();
                }
            }
        });
        JButton btnRemove = new JButton("Remove");
        btnRemove.addActionListener(e -> {
            calculateSelectedItem(aps, id);

            updateTable();

        });
        GridBagConstraints gbc_btnRemove = new GridBagConstraints();
        gbc_btnRemove.insets = new Insets(0, 0, 0, 5);
        gbc_btnRemove.gridx = 5;
        gbc_btnRemove.gridy = 0;
        panel.add(btnRemove, gbc_btnRemove);

        updateTable();

        setVisible(true);
    }

    static void updateTable() {
        table.updateUI();
    }

    private void calculateSelectedItem(Appointments aps, int id) {
        Link<Appointment> run = aps.buckets[CalenderWindow.defaultYear - DEFAULT_YEAR][aps.calculateDay((byte) (id + 1),
                CalenderWindow.defaultMonth, CalenderWindow.defaultYear)];

        if (table.getSelectedRow() == -1) {
            return;
        }

        for (int i = 0; i < table.getSelectedRow(); i++) {
            run = run.next;
        }

		/*
         * NPE bei nicht-vorhandener Selektion (behält alte Auswahl unsichtbar
		 * bei).
		 * 
		 * Ist okay so.
		 * 
		 * ~Sebastian
		 */
        try {
            if (!aps.remove(run.getData())) {
                JOptionPane.showMessageDialog(null, "Appointment can be deleted.");
            }
        } catch (NullPointerException ignored) {
        }
    }

}
