package GUI;

import Appointment.Appointment;
import Appointment.Appointments;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static Appointment.DateUtil.parseMinutes;
import static GUI.CalenderWindow.defaultMonth;
import static GUI.CalenderWindow.defaultYear;
import static javax.swing.JOptionPane.showMessageDialog;

/*
  Created by Mr.Pitty on 28.10.2016.
 */

class AddButtonWindow extends JDialog {

	private static final short WEIGHT = 260, HEIGHT = 200;
	private static final short MINUTES_MAX_VALUE = 1439;
	private static final byte MIN_VALUE = 0;
	private static final long serialVersionUID = 1L;
	private final JTextField textSubject;
	private final JCheckBox allDay;
	private final Appointments appointments;
	private JTextField textBegin;
	private JTextField textEnd;

	AddButtonWindow(JDialog owner, Appointments aps, byte day) {
		super(owner, "Neuer Termin", true);
		setResizable(false);
		setSize(WEIGHT, HEIGHT);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new MigLayout("", "[grow][grow][grow][grow][grow][grow]",
				"[21.00,grow,fill][grow][grow][grow][grow][][][grow][grow,fill]"));
		appointments = aps;

		JLabel labelSubject = new JLabel("Subject");
		getContentPane().add(labelSubject, "cell 2 1,alignx left");

		textSubject = new JTextField("PK Bonusaufgabe");
		textSubject.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 1) {
					textSubject.setText("");
				}
			}
		});
		getContentPane().add(textSubject, "cell 3 1,growx");
		textSubject.setColumns(10);

		JLabel lblAllDay = new JLabel("All day");
		getContentPane().add(lblAllDay, "cell 2 2");

		allDay = new JCheckBox();
		allDay.addActionListener(e -> {

			if (allDay.isSelected()) {
				textBegin.setText("");
				textBegin.setEditable(false);
				textEnd.setText("");
				textEnd.setEditable(false);
			} else {
				textBegin.setText("10:00");
				textBegin.setEditable(true);
				textEnd.setText("12:00");
				textEnd.setEditable(true);
			}

		});
		getContentPane().add(allDay, "cell 3 2,growx");

		JLabel labelBeginn = new JLabel("Begin");
		getContentPane().add(labelBeginn, "cell 2 3,alignx left");

		textBegin = new JTextField("10:00");
		textBegin.setColumns(10);
		getContentPane().add(textBegin, "cell 3 3,growx");

		JLabel labelEnd = new JLabel("End");
		getContentPane().add(labelEnd, "cell 2 4,alignx left");

		textEnd = new JTextField("12:00");
		textEnd.setColumns(10);
		getContentPane().add(textEnd, "cell 3 4,growx");

		JButton btnOk = new JButton("Ok");
		btnOk.addActionListener(e -> {
			Appointment ap = new Appointment(null);

			ap.setYear(defaultYear);
			ap.setMonth(defaultMonth);
			ap.setDay((byte) (day + 1));

			ap.setSubject(textSubject.getText());
			ap.setAllDay(allDay.isSelected());

			if (allDay.isSelected()) {
				ap.setStart(MIN_VALUE);
				ap.setLength(MINUTES_MAX_VALUE);
			} else {
				try {
					ap.setStart(parseMinutes(textBegin.getText()));
					ap.setLength((short) (parseMinutes(textEnd.getText()) - parseMinutes(textBegin.getText())));
				} catch (IllegalArgumentException | IllegalStateException | StringIndexOutOfBoundsException exception) {
					showMessageDialog(null, "Invalid time");
					return;
				}
			}

			appointments.add(ap);

			DayWindow.updateTable();

			dispose();

		});
		getContentPane().add(btnOk, "cell 2 7,alignx right");

		JButton banCancel = new JButton("Cancel");
		banCancel.addActionListener(e -> this.dispose());
		getContentPane().add(banCancel, "cell 3 7,alignx left");

		getContentPane().addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					dispose();
				}
			}
		});

		setVisible(true);
	}

}
