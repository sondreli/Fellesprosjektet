package model;

public enum Day {
	Monday(0), Tuesday (1), Wednsday(2), Thursday(3), Friday(4), Saturday(5), Sunday(6);
	
	private int nr;

	Day(int nr) {
		this.nr = nr;
	}
	
	public int getValue() {
		return nr;
	}

	public static Day getDay(int nr){
		for (Day day : values()) {
			if(day.nr == nr)
				return day;
		}
		throw new IllegalArgumentException();
	}
}


