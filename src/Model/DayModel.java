package Model;

import Appointment.Appointment;
import Appointment.Appointments;
import Appointment.Link;
import GUI.CalenderWindow;

import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;


/*
  Created by Mr.Pitty on 28.10.2016.
 */

public class DayModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	private static final byte COLUMN_COUNT = 3;
	private static final short DEFAULT_YEAR = 1900;
	private final byte id;
	private ArrayList<TableModelListener> listener = new ArrayList<>();
	private Appointments aps;

	public DayModel(Appointments aps, byte id) {
		this.aps = aps;
		this.id = id;
	}

	@Override
	public void addTableModelListener(TableModelListener l) {
		listener.add(l);
	}

	@Override
	public Class<?> getColumnClass(int arg0) {
		return String.class;
	}

	@Override
	public int getColumnCount() {
		return COLUMN_COUNT;
	}

	@Override
	public String getColumnName(int columnIndex) {
		switch (columnIndex) {
		case 0:
			return "Begin: ";
		case 1:
			return "End: ";
		case 2:
			return "Subject: ";
		default:
			return null;
		}
	}

	@Override
	public int getRowCount() {
		return aps.count(id, CalenderWindow.defaultMonth, CalenderWindow.defaultYear);
	}

	@Override
	public String getValueAt(int rowIndex, int columnIndex) {
		short year = (short) (CalenderWindow.defaultYear - DEFAULT_YEAR);
		short day = aps.calculateDay(id, CalenderWindow.defaultMonth, CalenderWindow.defaultYear);
		Link<Appointment> run = aps.buckets[year][day];

		for (int i = 0; i < rowIndex; i++) {
			run = run.next;
		}

		switch (columnIndex) {
		case 0:
			return run.getData().getStartToString();
		case 1:
			return run.getData().getLengthToString();
		case 2:
			return run.getData().getSubject();
		}

		return null;
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	@Override
	public void removeTableModelListener(TableModelListener l) {
		listener.remove(l);
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {

	}
}
