package model;

import java.util.ArrayList;
import java.util.Date;

import sun.tools.tree.CaseStatement;

public class Meeting {

	private ArrayList<User> participants;
	private Date dateOfMeeting;
	private User leader;
	private Answer answer;
	private MeetingRoom room;
	private String description;

	public Meeting(ArrayList<User> participants, MeetingRoom room, Date dateOfMeeting){
		this.participants = participants;
		this.leader = getCurrentUser();
		this.description = " ";
		this.room = room;
		this.dateOfMeeting = dateOfMeeting;
	}


	public ArrayList<User> getParticipants() {
		return participants;
	}


	public void setParticipants(ArrayList<User> participants) {
		this.participants = participants;
	}


	public Date getDateOfMeeting() {
		return dateOfMeeting;
	}


	public void setDateOfMeeting(Date dateOfMeeting) {
		this.dateOfMeeting = dateOfMeeting;
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


	public void deleteMeeting(){

	}

	public void addParticipant(User participant){
		//changeListener med ADD_PARTICIPANT

	}
	public void removeParticipant(User participant){
		//ChangeListener med REMOVE_PARTICIPANT
	}
	public void editMeeting(Change change){
		switch (change) {
		case REMOVE_PARTICIPANT: //TODO: noe
		case ADD_PARTICIPANT: //TODO: noe
		case ROOM: //TODO: noe
		case TIME: //TODO: noe
		case CANCELLED: //TODO: noe
			break;

		default:System.out.println("Noe ble endret, men vet ikke hva!");
		break;
		}
	}

}
