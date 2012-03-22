package model;

public class Time {
	private int hour;
	private int minute;
	
	public Time(int hour, int minute) {
		if(hour >= 0 && hour <= 24) {
			this.hour = hour;
		}
		else {
			System.err.println("Hour must be a positve int less than or equal to 24");
			System.exit(1);
		}
		if(hour >= 0 && hour <= 60) {
			this.minute = minute;
		}
		else {
			System.err.println("Minute must be a positve int less than or equal to 60");
			System.exit(1);
		}
		
	}

	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public int getMinute() {
		return minute;
	}

	public void setMinute(int minute) {
		this.minute = minute;
	}
	
}
