package Appointment;

import javax.swing.*;
import java.io.Serializable;

public class Appointments implements Serializable {
	
	private static final long serialVersionUID = -4069540263013909826L;
	private final static short YEARS_MAX_VALUE = 200;
	private final static short DAY_MAX_VALUE = 366;
	private final static short DEFAULT_YEAR = 1900;
	@SuppressWarnings("unchecked")
	public final Link<Appointment>[][] buckets = new Link[YEARS_MAX_VALUE][DAY_MAX_VALUE];
	
	public boolean add(Appointment ap) {
		if (ap.isValid())
			return false;

		short year = (short) (ap.getYear() - DEFAULT_YEAR);
		short day = calculateDay(ap.getDay(), ap.getMonth(), ap.getYear());
		
		Link<Appointment> run = buckets[year][day];
		
		if (checkAppointmens(ap, run)) {
			buckets[year][day] = new Link<Appointment>(ap, buckets[year][day]);
		}
		
		return true;

	}

	public short calculateDay(byte day, byte month, short year) {
		short days = --day;

		for (byte i = 0; i < month; i++)
			days = (short) (days + DateUtil.daysOfMonth(i, year));

		return days;
	}

	public boolean remove(Appointment ap) {
		assert (ap != null);

		if (ap.isValid()) {
			return false;
		}

		short year = (short) (ap.getYear() - DEFAULT_YEAR);
		short day = calculateDay(ap.getDay(), ap.getMonth(), ap.getYear());
		Link<Appointment> run = buckets[year][day];
		Link<Appointment> prev = null;

		while (run != null) {

			if (run.getData() == ap) {
				if (prev == null) {
					buckets[year][day] = buckets[year][day].next;
				} else {
					prev.next = run.next;
				}
				return true;
			}

			prev = run;
			run = run.next;
		}

		return false;
	}

	public int count(byte day, byte month, short year) {
		int count = 0;
		short years = (short) (year - DEFAULT_YEAR);
		short days = calculateDay(day, month, year);

		Link<Appointment> run = buckets[years][days];

		while (run != null) {
			count++;
			run = run.next;
		}

		return count;
	}

	public void removeAll(byte day, byte month, short year) {
		buckets[year - DEFAULT_YEAR][calculateDay(day, month, year)] = null;
	}

	private boolean checkAppointmens(Appointment ap, Link<Appointment> run) {
		int confirm = 0;

		while (run != null) {
			if (ap.getAllDay() || run.getData().getStart() == ap.getStart() || run.getData().getEnd() > ap.getEnd()) {
				confirm = JOptionPane.showConfirmDialog(null,
						"Appointments are overlapping\n" + "do u want to add this?", "Appointments are overlapping",
						JOptionPane.YES_NO_OPTION);
				break;
			} else if (run.getData().getAllDay()) {
				confirm = JOptionPane.showConfirmDialog(null, "Appointment is allday\n" + "do u want to add one more?",
						"Appointment is allday", JOptionPane.YES_NO_OPTION);
				break;
			}
			run = run.next;
		}

        return confirm == 0;
    }
	
}
