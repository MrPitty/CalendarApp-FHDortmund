package Model;

/*
  Created by Mr.Pitty on 28.10.2016.
 */

import Appointment.Appointments;
import GUI.CalenderWindow;

import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

import static Appointment.DateUtil.daysOfMonth;

public class AppointmentModel extends AbstractTableModel {
    private static final long serialVersionUID = 1L;
    private static final int COLUMN_COUNT = 2;
    private ArrayList<TableModelListener> listener = new ArrayList<>();
    private Appointments appointments;

    public AppointmentModel(Appointments aps) {
        appointments = aps;
    }

    @Override
    public void addTableModelListener(TableModelListener l) {
        listener.add(l);
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
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
                return "Day:";
            case 1:
                return "Appointment";
            default:
                return null;
        }

    }

    @Override
    public int getRowCount() {
        return daysOfMonth(CalenderWindow.defaultMonth, CalenderWindow.defaultYear);
    }

    @Override
    public String getValueAt(int rowIndex, int columnIndex) {

        switch (columnIndex) {
            case 0:
                return ++rowIndex + ".";
            case 1:
                int count = appointments.count((byte) (++rowIndex), CalenderWindow.defaultMonth, CalenderWindow.defaultYear);
                return count == 0 ? "" : count == 1 ? "" + count + " Termin" : "" + count + " Termine";
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
