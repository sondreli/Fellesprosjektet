package database;
<<<<<<< HEAD

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.DBAppointment;

import model.Appointment;
import model.MeetTime;
import model.Meeting;
import model.MeetingRoom;
import model.User;
=======
>>>>>>> 25f8ea6e157b663c4661024f06dbcfa79e876fdd

public class DBMeeting {


	public static int addMeeting(Meeting meeting){
		return addMeeting(meeting.getParticipants(), meeting.getRoom(), meeting.getId());
	}
	public static int addMeeting(ArrayList<User> participants, MeetingRoom room, int ID){
		int meetingID = 0;

		String query = "INSERT INTO m�te " +
		"(romNavn, hendelseId) VALUES ('"
		+ room.getName() + "','" + ID + "')";
		Interact.executeUpdate(query);

		ResultSet rs = Interact.execute("SELECT * FROM m�te ORDER BY m�teId DESC LIMIT 1");

		try {
			if(rs.next())
				meetingID =  rs.getInt("m�teId");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		DBParticipants.addParticipants(participants, meetingID);
		Interact.executeUpdate("INSERT INTO kobling " + 
		"(m�teId, romNavn) VALUES ('" + meetingID + "','" + room.getName() + "')");
		
		return meetingID;		
	}
	/**
	 * ONLY use if changing meetingID or roomName.
	 * If editing other parts, use editParticipants or editAppointment
	 * @param meeting
	 */
	public static void editMeeting(Meeting meeting){
		//M� sende melding til deltakere
		StringBuilder query = new StringBuilder();
		
		query.append("UPDATE m�te SET m�teId = '" + meeting.getMeetingID() + "',");
		query.append("romNavn = '" + meeting.getRoom() + "',");
		query.append("WHERE m�teId = '" + meeting.getMeetingID() + "'");
		
		Interact.executeUpdate(query.toString());	
		}
	public static void cancelMeeting(Meeting meeting){
		//M� sende melding til deltakere
		Interact.executeUpdate("DELETE FROM m�te WHERE m�teId = '" + meeting.getMeetingID() + "'");

	}
	public static Meeting makeMeetingObject(ResultSet rs){
		Meeting meeting = null;
		

		try {
			int meetingID = rs.getInt("m�teId");
			ArrayList<User> participants = DBParticipants.getParticipants(meetingID, "all");
			MeetingRoom room = DBMeetingRoom.getMeetingRoom(rs.getString("romNavn"));
			Appointment app = DBAppointment.getAppointmentbyID(rs.getInt("hendelseId"));
			
			meeting = new Meeting(participants, room, app, meetingID);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return meeting;
	}
}
