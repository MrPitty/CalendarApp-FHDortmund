package Appointment;

/*
  Created by Mr.Pitty on 18.10.2016.
 */

public class DateUtil {
	private final static byte DAYS_IN_WEEK = 7;
	private final static byte MONTH_MAX_VALUE = 11;
	private final static byte MIN_VALUE = 0;
	private static final byte MAX_VALUE_FOR_PARSE_LENGTH = 2;
	private static final byte MINUTES_MAX_VALUE = 60;
	private final static short MIN_YEAR_VALUE = 1900;
	private final static short MAX_YEAR_VALUE = 2099;
	private final static String[] DAY_OF_WEEK = new String[] { "Mo", "Tu", "We", "Th", "Fr", "Sa", "Su" };
	private final static String[] MONTH_OF_YEAR = new String[] { "January", "February", "March", "April", "Mai", "June",
			"July", "August", "September", "October", "November", "December" };
	private static final byte HOURS_MAX_VALUE = 23;

	public static boolean isLeapYear(short year) {
		return (year % 4 == 0 && year % 100 != 0 || year % 400 == 0);
	}

	public static byte daysOfMonth(byte month, short year) {
		assert (month >= MIN_VALUE);

		switch (month) {
		case 1:
			return (byte) (isLeapYear(year) ? 29 : 28);
		case 3:
		case 5:
		case 8:
		case 10:
			return 30;
		default:
			return 31;
		}
	}

	public static byte firstDayOfYear(short year) {
		return (byte) ((5 * ((year - 1) % 4) + 4 * ((year - 1) % 100) + 6 * ((year - 1) % 400)) % 7);
	}

	public static byte firstDayOfMonth(byte month, short year) {
		short firstDayOfMonth = firstDayOfYear(year);

		for (byte i = 0; i < month; i++) {
			firstDayOfMonth += daysOfMonth(i, year);
		}

		return (byte) (firstDayOfMonth % DAYS_IN_WEEK);
	}

	public static boolean checkDate(byte day, byte month, short year) {

		return (day > MIN_VALUE && day <= daysOfMonth(month, year)) && (month >= MIN_VALUE && month <= MONTH_MAX_VALUE)
				&& (year >= MIN_YEAR_VALUE && year <= MAX_YEAR_VALUE);
	}

	public static byte weeksInMonth(byte month, short year) {
		return (byte) (Math.ceil((double) (firstDayOfMonth(month, year) + daysOfMonth(month, year)) / DAYS_IN_WEEK));
	}
	
	public static short parseMinutes(String hour) {
		String[] min = hour.split(":");
		short hours = Short.parseShort(min[0]);
		short minutes = Short.parseShort(min[1]);

		if (hours > HOURS_MAX_VALUE || hours < MIN_VALUE || min[0].length() > MAX_VALUE_FOR_PARSE_LENGTH) {
			throw new IllegalArgumentException();
		} else if (minutes >= MINUTES_MAX_VALUE || minutes < MIN_VALUE
				|| min[1].length() > MAX_VALUE_FOR_PARSE_LENGTH) {
			throw new IllegalArgumentException();
		}

		return (short) ((hours * MINUTES_MAX_VALUE) + minutes);
	}

	public static String getMonth(byte month) {
		return MONTH_OF_YEAR[month];
	}

	public static String getDayOfWeek(byte day, byte month, short year) {
		return DAY_OF_WEEK[(firstDayOfMonth(month, year) + --day) % DAYS_IN_WEEK];
	}

	public static byte getMonthMaxValue() {
		return MONTH_MAX_VALUE;
	}

	public static byte getMinValue() {
		return MIN_VALUE;
	}

	public static short getMinYearValue() {
		return MIN_YEAR_VALUE;
	}

	public static short getMaxYearValue() {
		return MAX_YEAR_VALUE;
	}

	public static String[] getMonthOfYear() {
		return MONTH_OF_YEAR;
	}

	public static String getMonthOfYear(int month) {
		return MONTH_OF_YEAR[month];
	}

	public static String getDaysName(int i) {
		return DAY_OF_WEEK[i];
	}

}
