package model;

public class MeetTime {
	private Time start;
	private Time end;
	private Day day;
	private int week;  // mulige verdier: 1 - 53
	private int month; // mulige verdier: 0 - 11
	private int year;  // mulige verdier: 2012 +- 100
	
	public MeetTime(Time start, Time end, Day day, int week, int month, int year) {
		this.start = start;
		this.end = end;
		this.day = day;
		this.week = week;
		this.month = month;
		this.year = year;
	}

	public Time getStart() {
		return start;
	}

	public void setStart(Time start) {
		this.start = start;
	}

	public Time getEnd() {
		return end;
	}

	public void setEnd(Time end) {
		this.end = end;
	}

	public Day getDay() {
		return day;
	}

	public void setDay(Day day) {
		this.day = day;
	}

	public int getWeek() {
		return week;
	}

	public int getMonth() {
		return month;
	}

	public int getYear() {
		return year;
	}
	
}
