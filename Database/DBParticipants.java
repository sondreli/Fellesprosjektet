package database;

import java.sql.ResultSet;
import java.util.ArrayList;

import model.Appointment;
import model.Meeting;
import model.User;

public class DBParticipants {

	public static void addParticipants(ArrayList<User> parts, int meetingID){

		if(meetingID == 0){
			for (User user : parts) {
				String query = "INSERT INTO deltaker " +
				"(brukerNavn, m퓍eId, svar) VALUES ('"
				+ user.getUserName() + "','" + meetingID + "','" + "usvart" + "')";
				Interact.executeUpdate(query);
			}
		}
		else
			System.out.println("feil i m퓍eID");
	}
	/**
	 * 
	 * @param meeting
	 * @param answer: returns participants based on "yes", "no", "unanswered" or "all"
	 * @return ArrayList<User> with participants
	 */
	public static ArrayList<User> getParticipants(int meetingID, String answer){
		ArrayList<User> users = new ArrayList<User>();
		User user = null;
		String query = null;



		switch (answer.charAt(0)) {
		case 'y': 
			query = "SELECT * FROM deltaker WHERE m퓍eId = '" + meetingID + "' AND svar = 'ja'";
			break;
		case 'n':
			query = "SELECT * FROM deltaker WHERE m퓍eId = '" + meetingID + "' AND svar = 'nei'";
			break;
		case 'u':
			query = "SELECT * FROM deltaker WHERE m퓍eId = '" + meetingID + "' AND svar = 'usvart'";
			break;
		case 'a':
			query = "SELECT * FROM deltaker WHERE m퓍eId = '" + meetingID + "'";
			break;
		default: System.out.println("intet deltakersvar valgt");
		break;
		}
		ResultSet rs = Interact.execute(query);

		try {
			while(rs.next()){
				user = DBUser.makeUserObject(rs);
				users.add(user);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return users;
	}
}
