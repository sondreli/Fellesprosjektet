package model;

import java.util.Date;

public class Appointment{

	private Date dateOfMeeting;
	private String description;
	
	public Appointment(Date dateOfMeeting, String description) {
		this.dateOfMeeting = dateOfMeeting;
		this.description = description;
	}

	public Date getDateOfMeeting() {
		return dateOfMeeting;
	}

	public void setDateOfMeeting(Date dateOfMeeting) {
		this.dateOfMeeting = dateOfMeeting;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	
	
}
