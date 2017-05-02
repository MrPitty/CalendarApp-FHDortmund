package SaveOpen;

import Appointment.Appointments;
import GUI.CalenderWindow;

import java.io.*;

/*
Created by Mr.Pitty on 11.12.2016.
*/

public class SaveAppointments {

	private static final String FILE_NAME = "Appointments.bin";

	public static void writeFile(byte month, short year, Appointments db) {
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
			oos.writeByte(month);
			oos.writeShort(year);
			oos.writeObject(db);

		} catch (IOException ioException) {
			ioException.printStackTrace();
		}
	}

	public static Appointments readFile() {
		Appointments aps = new Appointments();

		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
			CalenderWindow.defaultMonth = ois.readByte();
			CalenderWindow.defaultYear = ois.readShort();
			aps = (Appointments) ois.readObject();
		} catch (FileNotFoundException fileNotFoundException) {
			writeFile(CalenderWindow.defaultMonth, CalenderWindow.defaultYear, new Appointments());
			readFile();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}

		return aps;
	}

}
