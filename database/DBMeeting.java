package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.DBAppointment;

import model.Appointment;
import model.MeetTime;
import model.Meeting;
import model.MeetingRoom;
import model.User;


public class DBMeeting {


	public static int addMeeting(Meeting meeting){
		return addMeeting(meeting.getParticipants(), meeting.getRoom(), meeting.getId());
	}
	public static int addMeeting(ArrayList<User> participants, MeetingRoom room, int ID){
		int meetingID = 0;

		String query = "INSERT INTO møte " +
		"(romNavn, hendelseId) VALUES ('"
		+ room.getName() + "','" + ID + "')";
		Interact.executeUpdate(query);

		ResultSet rs = Interact.execute("SELECT * FROM møte ORDER BY møteId DESC LIMIT 1");

		try {
			if(rs.next())
				meetingID =  rs.getInt("møteId");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		DBParticipants.addParticipants(participants, meetingID);
		Interact.executeUpdate("INSERT INTO kobling " + 
		"(møteId, romNavn) VALUES ('" + meetingID + "','" + room.getName() + "')");
		
		return meetingID;		
	}
	// ø kan skrives som ø
	public static Meeting getMeeting(int meetingID){
		ResultSet rs = Interact.execute("SELECT * FROM møte WHERE møteId = '" + meetingID + "'");
		Meeting møte = null;
		
		try {
			if(rs.next())
				møte = makeMeetingObject(rs);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return møte;
	}
//	public static ArrayList<Meeting> getAllMeetings(User user){
//		ArrayList<Meeting> meetings = new ArrayList<Meeting>();
//		ResultSet rs = Interact.execute("SELECT * FROM møte WHERE bruker='"+user+"'");
//		
//		try {
//			if(rs.next()){
//				meetings.add(makeMeetingObject(rs));
//			}
//			
//		} catch (Exception e){
//			
//		}
//		
//		
//		return meetings;
//		
//	}
	/**
	 * ONLY use if changing meetingID or roomName.
	 * If editing other parts, use editParticipants or editAppointment
	 * @param meeting
	 */
	public static void editMeeting(Meeting meeting){
		//Mø sende melding til deltakere
		StringBuilder query = new StringBuilder();
		
		query.append("UPDATE møte SET møteId = '" + meeting.getMeetingID() + "',");
		query.append("romNavn = '" + meeting.getRoom() + "',");
		query.append("WHERE møteId = '" + meeting.getMeetingID() + "'");
		
		Interact.executeUpdate(query.toString());	
		}
	public static void cancelMeeting(Meeting meeting){
		//Mø sende melding til deltakere
		Interact.executeUpdate("DELETE FROM møte WHERE møteId = '" + meeting.getMeetingID() + "'");

	}
	public static Meeting makeMeetingObject(ResultSet rs){
		Meeting meeting = null;
		

		try {
			int meetingID = rs.getInt("møteId");
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
