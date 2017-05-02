package Appointment;

import java.io.Serializable;

/*
  Created by Mr.Pitty on 18.10.2016.
 */

public class Appointment implements Serializable {
  
	private static final long serialVersionUID = 6843393131060733552L;
	private final static byte DAY_MIN_VALUE = 1;
    private final static byte MIN_VALUE = 0;
    private final static byte MINUTES_IN_HOUR = 60;
    private final static short MINUTES_MAX_VALUE = 1439;
    private final static short DEFAULT_YEAR = 1900;
    private final static short YEAR_MAX_VALUE = 2099;
    private final static byte MONTH_MAX_VALUE = 11;
    private final static byte MINUTES_DEZIMAL_MAX_VALUE = 10;
    private boolean allDay;
    private byte day, month;
    private short year, start, length;
    private String subject;

    public Appointment(Appointment ap) {
        if (ap != null) {
            this.subject = ap.getSubject();
            this.day = ap.getDay();
            this.month = ap.getMonth();
            this.year = ap.getYear();
            this.allDay = ap.getAllDay();
            this.start = (short) (allDay ? MIN_VALUE : ap.getStart());
            this.length = (short) (allDay ? MINUTES_MAX_VALUE : ap.getLength());
        }
    }

    @Override
    public String toString() {
        return DateUtil.getDayOfWeek(this.day, this.month, this.year) + ". " + this.day + " "
                + DateUtil.getMonth(this.month) + " " + this.year + "\n" + "Anfang: "
                + (getAllDay() ? MIN_VALUE : getStartToString()) + "\n" + "....." + "\t" + getSubject() + "\n"
                + "Ende: \t" + (getAllDay() ? MINUTES_MAX_VALUE : getLengthToString());
    }

    public String getStartToString() {
        if (getAllDay()) {
            return getHour(MIN_VALUE) + ":" + getMinutes(MIN_VALUE);
        }

        return getHour(getStart()) + ":" + getMinutes(getStart());
    }

    public String getLengthToString() {

        if (getAllDay()) {
            return getHour(MINUTES_MAX_VALUE) + ":" + getMinutes(MINUTES_MAX_VALUE);
        }

        return getHour(getStart() + getLength()) + ":" + getMinutes(getStart() + getLength());
    }

    private String getHour(int hour) {
        return hour / MINUTES_IN_HOUR < MINUTES_DEZIMAL_MAX_VALUE ? "0" + hour / MINUTES_IN_HOUR
                : "" + hour / MINUTES_IN_HOUR;
    }

    private String getMinutes(int minutes) {
        return minutes % MINUTES_IN_HOUR < MINUTES_DEZIMAL_MAX_VALUE ? "0" + minutes % MINUTES_IN_HOUR
                : "" + minutes % MINUTES_IN_HOUR;
    }

    public boolean isValid() {
        return !DateUtil.checkDate(this.day, this.month, this.year);
    }

    int getStart() {
        return this.start;
    }

    public void setStart(short start) {

        if (start < MIN_VALUE || start > MINUTES_MAX_VALUE) {
            throw new IllegalStateException();
        }

        this.start = start;

    }

    private int getLength() {
        return length;
    }

    public void setLength(short length) {

        if (length < MIN_VALUE || length > MINUTES_MAX_VALUE) {
            throw new IllegalStateException();
        }

        this.length = length;

    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public byte getDay() {
        return day;
    }

    public void setDay(byte day) {

        if (day < DAY_MIN_VALUE || day > DateUtil.daysOfMonth(this.getMonth(), this.getYear())) {
            throw new IllegalArgumentException();
        }

        this.day = day;
    }

    public byte getMonth() {
        return month;
    }

    public void setMonth(byte month) {

        if (month < MIN_VALUE || month > MONTH_MAX_VALUE) {
            throw new IllegalArgumentException();
        }

        this.month = month;
    }

    public short getYear() {
        return year;
    }

    public void setYear(short year) {
        if (year < DEFAULT_YEAR || year > YEAR_MAX_VALUE) {
            throw new IllegalArgumentException();
        }
        this.year = year;
    }

    public boolean getAllDay() {
        return allDay;
    }

    public void setAllDay(boolean allDay) {
        this.allDay = allDay;
    }

    public short getEnd() {
        return (short) (this.length + this.start);
    }
}
