package model;

import Database.DBAppointment;


public class Appointment{

	private MeetTime meetingTime;
	private String description;
	private int id;
	private User leader;

	
	public Appointment(String description, User leader, MeetTime time) {
		this.description = description;
		this.meetingTime = time;
		this.leader = leader;	
		
		this.id = DBAppointment.addAppointment(this);
		
	}
	public Appointment(String description, User leader, MeetTime time, int id) {
		this.description = description;
		this.meetingTime = time;
		this.leader = leader;	
		this.id = id;
		
	}	/**
	 * @param Appointment compareTo: the meeting to compare to
	 * @return true of no overlap, false if they overlap
	 */
	
//	public boolean timeCheck(Appointment compareTo){
//		if(this.getDateOfMeeting().equals(compareTo.getDateOfMeeting())){
//			if(this.getStart() >= compareTo.getEnd() || this.getEnd() <= compareTo.getStart()){
//				return true;
//			}
//			else 
//				return false;
//		}
//		else
//			return true;
//	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public User getLeader() {
		return leader;
	}
	public void setLeader(User leader) {
		this.leader = leader;
	}
	public MeetTime getMeetingTime() {
		return meetingTime;
	}
	public void setMeetingTime(MeetTime meetingTime) {
		this.meetingTime = meetingTime;
	}
	
	
	
}
