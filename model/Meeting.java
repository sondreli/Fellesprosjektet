package model;

import java.util.ArrayList;

import database.DBMeeting;


public class Meeting extends Appointment{

	private ArrayList<User> participants;
	private User leader;
	private Answer answer;
	private MeetingRoom room;
	private String description;
	private int meetingID;


	/**
	 * Creates new meeting
	 * 
	 * @param participants
	 * @param room
	 * @param dateOfMeeting
	 * @param description
	 * @param leader
	 */

	public Meeting(ArrayList<User> participants, MeetingRoom room, String description, User leader, MeetTime meetingTime){
		super(description, leader, meetingTime);
		this.participants = participants;
		this.room = room;
		
		this.meetingID = DBMeeting.addMeeting(this);
		
	}

	public Meeting(ArrayList<User> participants, MeetingRoom room, Appointment app, int meetingID){
		super(app.getDescription(), app.getLeader(), app.getMeetingTime());
		this.participants = participants;
		this.room = room;
		this.meetingID = meetingID;
	}

	public Meeting(ArrayList<User> participants, MeetingRoom room, String description, User leader, MeetTime meetingTime, int meetingID){
		super(description, leader, meetingTime);
		this.participants = participants;
		this.room = room;
		this.meetingID = meetingID;
		
	}
	public ArrayList<User> getParticipants() {
		return participants;
	}


	public void setParticipants(ArrayList<User> participants) {
		this.participants = participants;
	}


	public User getLeader() {
		return leader;
	}


	public void setLeader(User leader) {
		this.leader = leader;
	}


	public Answer getAnswer() {
		return answer;
	}


	public void setAnswer(Answer answer) {
		this.answer = answer;
	}


	public MeetingRoom getRoom() {
		return room;
	}


	public void setRoom(MeetingRoom room) {
		this.room = room;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public int getMeetingID() {
		return meetingID;
	}


	public void setMeetingID(int meetingID) {
		this.meetingID = meetingID;
	}

}
