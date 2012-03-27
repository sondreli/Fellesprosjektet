package model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

public class EventList extends ArrayList<Object> {
	private PropertyChangeSupport change;
	
	public EventList() {
		super();
		change = new PropertyChangeSupport(this);
	}
	
	public void addMeeting(Meeting m) {
		ArrayList<Object> tempOld = this;
		add(m);
		change.firePropertyChange("NamePropertyComponent", tempOld, this);
	}
	
	public void addAppointment(Appointment a) {
		ArrayList<Object> tempOld = this;
		this.add(a);
		change.firePropertyChange("NamePropertyComponent", tempOld, this);
	}
	
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		change.addPropertyChangeListener(listener);
	}
	
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		change.removePropertyChangeListener(listener);
	}
}
