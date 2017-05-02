
/*
  Created by Mr.Pitty on 18.10.2016.
 */

import GUI.CalenderWindow;
import SaveOpen.SaveAppointments;

public class AppointmentsTest {

    public static void main(String[] args) {

        new CalenderWindow(SaveAppointments.readFile());
    }

}
