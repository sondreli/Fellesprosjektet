package model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class Calendar {
	private int realYear, realMonth, realWeek, realDay, currentYear, currentMonth, currentWeek;
	private PropertyChangeSupport change;
	
	public Calendar() {
		change = new PropertyChangeSupport(this);
	}

	public int getRealYear() {
		return realYear;
	}

	public void setRealYear(int realYear) {
		int tempOld = this.realYear;
		this.realYear = realYear;
		change.firePropertyChange("NamePropertyComponent", tempOld, realYear);
	}

	public int getRealMonth() {
		return realMonth;
	}

	public void setRealMonth(int realMonth) {
		int tempOld = this.realMonth;
		this.realMonth = realMonth;
		change.firePropertyChange("NamePropertyComponent", tempOld, realMonth);
	}

	public int getRealWeek() {
		return realWeek;
	}

	public void setRealWeek(int realWeek) {
		int tempOld = this.realWeek;
		this.realWeek = realWeek;
		change.firePropertyChange("NamePropertyComponent", tempOld, realWeek);
	}

	public int getRealDay() {
		return realDay;
	}

	public void setRealDay(int realDay) {
		int tempOld = this.realDay;
		this.realDay = realDay;
		change.firePropertyChange("NamePropertyComponent", tempOld, realDay);
	}

	public int getCurrentYear() {
		return currentYear;
	}

	public void setCurrentYear(int currentYear) {
		int tempOld = this.currentYear;
		this.currentYear = currentYear;
		change.firePropertyChange("NamePropertyComponent", tempOld, currentYear);
	}

	public int getCurrentMonth() {
		return currentMonth;
	}

	public void setCurrentMonth(int currentMonth) {
		int tempOld = this.currentMonth;
		this.currentMonth = currentMonth;
		change.firePropertyChange("NamePropertyComponent", tempOld, currentMonth);
	}

	public int getCurrentWeek() {
		return currentWeek;
	}

	public void setCurrentWeek(int currentWeek) {
		int tempOld = this.currentWeek;
		this.currentWeek = currentWeek;
		change.firePropertyChange("NamePropertyComponent", tempOld, currentWeek);
	}
	
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		change.addPropertyChangeListener(listener);
	}
	
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		change.removePropertyChangeListener(listener);
	}
	
}
