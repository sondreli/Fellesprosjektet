package Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
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
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
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
	private final static String HENDELSE = "hendelse";
	private final static String HENDELSE_ID = "hendelseId";
	private final static String HENDELSE_START = "start";
	private final static String HENDELSE_SLUTT = "slutt";
	private final static String HENDELSE_DATO = "dato";
	


	public static ArrayList<Time> getTime(int ID){
		ArrayList<Time> tid = new ArrayList<Time>();
		java.sql.Time tid1, tid2;
		try {
			ResultSet rs = Interact.execute("SELECT * FROM "+HENDELSE+" WHERE "+HENDELSE_ID +" = "+ID);
			while (rs.next()){
			tid1 = rs.getTime(HENDELSE_START);
			tid.add(tid1);
			tid2 = rs.getTime(HENDELSE_SLUTT);
			tid.add(tid2);
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return tid;
	}

//	public static void main (String[]args){
//	
//			System.out.println(getTime(1));
//		}
}
