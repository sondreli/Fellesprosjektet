package Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import model.Appointment;
import model.User;

public class DBAppointment{

	
	public static int addAppointment(Appointment app){
		return addAppointment(app.getDateOfMeeting(), app.getDescription(), app.getStart(), app.getEnd(), app.getLeader());
	}
	public static int addAppointment(Date dateOfMeeting, String description, int start, int end, User leader){
		ResultSet rs = null;
		String query = "INSERT INTO hendelse " +
		"(brukerNavn, beskrivelse, dato, start, slutt) VALUES ('"
		+ leader.getUserName() + "','" + description + "','" + dateOfMeeting + "','" + start + "','" + end + "')";
		Interact.executeUpdate(query);
		
		//Sender tilbake ID på appointment
		rs = Interact.execute("SELECT * FROM hendelse ORDER BY hendelseId DESC LIMIT 1");
		try {
			
			return rs.getInt("hendelseId");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	public static Appointment makeAppointmentObject(ResultSet rs){
		Appointment app = null;
		
		try {
			Date dateOfMeeting = rs.getDate("dato");
			String description = rs.getString("beskrivelse");
			User leader = (User) rs.getObject("brukerNavn");
			int start = rs.getInt("start");
			int end = rs.getInt("slutt");
			
			app = new Appointment(dateOfMeeting, description, start, end, leader);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return app;
	}
}
