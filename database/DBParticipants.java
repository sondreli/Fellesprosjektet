package database;

import java.sql.ResultSet;
import java.util.ArrayList;

import model.Appointment;
import model.Meeting;
import model.User;

public class DBParticipants {

	public static void addParticipants(ArrayList<User> parts, int meetingID){

		if(meetingID != 0){
			for (User user : parts) {
				String query = "INSERT INTO deltaker " +
				"(brukerNavn, møteId, svar) VALUES ('"
				+ user.getUserName() + "', " + meetingID + ",'" + "usvart" + "')";
				Interact.executeUpdate(query);
			}
		}
		else
			System.out.println("feil i møteID");
	}
	
	public static ArrayList<Meeting> getUsersMeetings(User user){
		ArrayList<Meeting> meetings = new ArrayList<Meeting>();
		Meeting meeting = null;
		ResultSet rs = Interact.execute("SELECT * FROM deltaker WHERE brukerNavn = '" + user.getUserName() + "'");
		
		try {
			while(rs.next()){
				meeting = DBMeeting.getMeeting(rs.getInt("møteId"));
				meetings.add(meeting);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return meetings;
	}
	/**
	 * 
	 * @param evens
	 * @param answer: returns participants based on "yes", "no", "unanswered" or "all"
	 * @return ArrayList<User> with participants
	 */
	public static ArrayList<User> getParticipants(int meetingID, String answer){
		ArrayList<User> users = new ArrayList<User>();
		User user = null;
		String query = null;



		switch (answer.charAt(0)) {
		case 'y': 
			query = "SELECT * FROM deltaker WHERE møteId = '" + meetingID + "' AND svar = 'ja'";
			break;
		case 'n':
			query = "SELECT * FROM deltaker WHERE møteId = '" + meetingID + "' AND svar = 'nei'";
			break;
		case 'u':
			query = "SELECT * FROM deltaker WHERE møteId = '" + meetingID + "' AND svar = 'usvart'";
			break;
		case 'a':
			query = "SELECT * FROM deltaker WHERE møteId = '" + meetingID + "'";
			break;
		default: System.out.println("intet deltakersvar valgt");
		break;
		}
		ResultSet rs = Interact.execute(query);

		try {
			while(rs.next()){
				
				user = DBUser.getUser(rs.getString("brukerNavn"));
				users.add(user);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return users;
	}
}
