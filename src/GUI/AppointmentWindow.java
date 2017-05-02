package GUI;

import Appointment.Appointment;
import Appointment.Appointments;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static Appointment.DateUtil.getMonthOfYear;
import static Appointment.DateUtil.parseMinutes;
import static javax.swing.JOptionPane.showMessageDialog;

/*
  Created by Mr.Pitty on 28.10.2016.
 */

class AppointmentWindow extends JDialog {

	private static final long serialVersionUID = 1L;
	private static final short WEIGHT = 260, HEIGHT = 270;
	private static final short MINUTES_MAX_VALUE = 1439;
	private static final byte MIN_VALUE = 0;
	private final JTextField textSubject;
	private final JTextField textDay;
	private final JTextField textYear;
	private final JComboBox<String> comboMonth;
	private final JCheckBox allDay;
	private final Appointments appointments;
	private JTextField textBegin;
	private JTextField textEnd;

	/*
	 * WIchtig! Hier und in AddButtonWindow hab ich MigLayout benutzt, die
	 * Bibliothek ist in Project drin. Wenn damit irgendwelche problewwe werder,
	 * bitte kontaktieren Sie mich. Bibliother ist unte diese adresse
	 * ....\KalenderApp_7092688\miglayout-src.zip
	 * ....\KalenderApp_7092688\miglayout15-swing.jar
	 */

	public AppointmentWindow(JFrame owner, Appointments aps) {
		super(owner, "Neuer Termin", true);
		setResizable(false);
		setSize(WEIGHT, HEIGHT);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(new MigLayout("", "[grow][grow][grow][grow][grow][grow]", "[][][][][][][][][][][]"));
		appointments = aps;

		JLabel labelSubject = new JLabel("Subject");
		getContentPane().add(labelSubject, "cell 2 1,alignx left");

		textSubject = new JTextField("PK Bonusaufgabe");
		getContentPane().add(textSubject, "cell 3 1,growx");
		textSubject.setColumns(10);
		textSubject.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 1) {
					textSubject.setText("");
				}
			}
		});

		JLabel labelDay = new JLabel("Day");
		getContentPane().add(labelDay, "cell 2 2,alignx left");

		textDay = new JTextField("1");
		textDay.setColumns(10);
		getContentPane().add(textDay, "cell 3 2,growx");
		textDay.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 1) {
					textDay.setText("");
				}
			}
		});

		JLabel labelMonth = new JLabel("Month");
		getContentPane().add(labelMonth, "cell 2 3,alignx left");

		comboMonth = new JComboBox<>(getMonthOfYear());
		getContentPane().add(comboMonth, "cell 3 3,growx");

		JLabel labelYear = new JLabel("Year");
		getContentPane().add(labelYear, "cell 2 4,alignx left");

		textYear = new JTextField("2016");
		textYear.setColumns(10);
		getContentPane().add(textYear, "cell 3 4,growx");
		textYear.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 1) {
					textYear.setText("");
				}
			}
		});

		JLabel lblAllDay = new JLabel("All day");
		getContentPane().add(lblAllDay, "cell 2 5");

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
		getContentPane().add(allDay, "cell 3 5,growx");

		JLabel labelBeginn = new JLabel("Begin");
		getContentPane().add(labelBeginn, "cell 2 6,alignx left");

		textBegin = new JTextField("10:00");
		textBegin.setColumns(10);
		getContentPane().add(textBegin, "cell 3 6,growx");

		JLabel labelEnd = new JLabel("End");
		getContentPane().add(labelEnd, "cell 2 7,alignx left");

		textEnd = new JTextField("12:00");
		textEnd.setColumns(10);
		getContentPane().add(textEnd, "cell 3 7,growx");

		JButton btnOk = new JButton("Ok");
		btnOk.addActionListener(e -> {

			Appointment ap = new Appointment(null);
			try {
				ap.setYear(Short.parseShort(textYear.getText()));
				ap.setMonth((byte) comboMonth.getSelectedIndex());
				ap.setDay(Byte.parseByte(textDay.getText()));
			} catch (IllegalArgumentException argumentException) {
				showMessageDialog(null, "ooops, invalid date");
				return;
			}

			ap.setSubject(textSubject.getText());
			ap.setAllDay(allDay.isSelected());

			if (allDay.isSelected()) {
				ap.setStart(MIN_VALUE);
				ap.setLength(MINUTES_MAX_VALUE);
			} else {
				try {
					ap.setStart(parseMinutes(textBegin.getText()));
					ap.setLength((short) (parseMinutes(textEnd.getText()) - parseMinutes(textBegin.getText())));

					if (ap.isValid()) {
						throw new IllegalArgumentException();
					}

				} catch (IllegalArgumentException | IllegalStateException | StringIndexOutOfBoundsException exception) {
					showMessageDialog(null, "ooops, invalid time");
					return;
				}
			}

			appointments.add(ap);
			
			dispose();

		});
		getContentPane().add(btnOk, "cell 2 10,growx");

		JButton btnCalcel = new JButton("Cancel");
		btnCalcel.addActionListener(e -> this.dispose());
		getContentPane().add(btnCalcel, "cell 3 10,growx");
		setVisible(true);
	}

}
