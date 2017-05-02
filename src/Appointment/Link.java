package Appointment;

import java.io.Serializable;

/*
  Created by Mr.Pitty on 18.10.2016.
 */

public class Link<T extends Appointment> implements Serializable{

	private static final long serialVersionUID = 8973778377433613771L;
	private final T data;
    public Link<T> next;

    Link(T data, Link<T> next) {
        this.data = data;
        this.next = next;
    }

    public T getData() {
        return data;
    }

}
