package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

import com.sun.org.apache.xerces.internal.impl.dv.xs.DayDV;

import model.Appointment;
import model.Day;
import model.MeetTime;
import model.User;

public class DBAppointment{


	public static int addAppointment(Appointment app){
		return addAppointment(app.getDescription(), app.getLeader(), app.getMeetingTime());
	}
	public static int addAppointment(String description, User leader, MeetTime time){

		String query = "INSERT INTO hendelse " +
		"(brukerNavn, beskrivelse, startTime, sluttTime, startMinutt, sluttMinutt, dag, uke, måned, år) VALUES ('"
		+ leader.getUserName() + "','" + description + "','" + time.getStart().getHour() + "','" + time.getEnd().getHour() + "','" + 
		time.getStart().getMinute() + "','" + time.getEnd().getMinute() + "','" + time.getDay().getValue() + "','" + time.getWeek() + "','" + 
		time.getMonth() + "','" + time.getYear() + "')";
		Interact.executeUpdate(query);


		ResultSet rs = Interact.execute("SELECT * FROM hendelse ORDER BY hendelseId DESC LIMIT 1");

		try {
			if(rs.next())
				return rs.getInt("hendelseId");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;

	}
	public static void editAppointment(Appointment app){
		StringBuilder query = new StringBuilder();

		query.append("UPDATE hendelse SET brukerNavn = '" + app.getLeader() + "',");
		query.append("startTime = '" + app.getMeetingTime().getStart().getHour() + "',");
		query.append("startMinutt = '" + app.getMeetingTime().getStart().getMinute() + "'");
		query.append("sluttTime = '" + app.getMeetingTime().getEnd().getHour() + "',");
		query.append("sluttMinutt = '" + app.getMeetingTime().getEnd().getMinute() + "',");
		query.append("dag = '" + app.getMeetingTime().getDay().getValue() + "',");
		query.append("uke = '" + app.getMeetingTime().getWeek() + "',");
		query.append("måned = '" + app.getMeetingTime().getMonth() + "',");
		query.append("år = '" + app.getMeetingTime().getYear() + "',");
		query.append("beskrivelse = '" + app.getDescription() + "'");
		query.append("WHERE brukerNavn = '" + app.getId() + "'");

		Interact.executeUpdate(query.toString());	
	}
	public static Appointment getAppointmentbyID(int id){
		Appointment app = null;
		ResultSet rs = Interact.execute("SELECT * FROM hendelse WHERE hendelseId = '" + id + "'");

		try {
			if(rs.next())
				app = makeAppointmentObject(rs);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return app;
	}
	public static ArrayList<Appointment> getUsersAppointments(User user){
		ResultSet rs = Interact.execute("SELECT * FROM hendelse WHERE brukerNavn = '" + user.getUserName() + "'");
		ArrayList<Appointment> result = new ArrayList<Appointment>();
		Appointment app;

		try {
			if(rs.next()){
				app = makeAppointmentObject(rs);

				result.add(app);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return result;

	}
	public static int getNewestID(Appointment app){

		ResultSet rs = Interact.execute("SELECT * FROM hendelse ORDER BY hendelseId DESC LIMIT 1");

		try {
			if(rs.next())
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
			String description = rs.getString("beskrivelse");
			User leader = (User) rs.getObject("brukerNavn");

			//creates MeetTime object
			model.Time start = new model.Time(rs.getInt("startTime"), rs.getInt("startMinutt"));
			model.Time end = new model.Time(rs.getInt("sluttTime"), rs.getInt("sluttMinutt"));
			Day day = Day.getDay(rs.getInt("dag"));
			int week = rs.getInt("uke");
			int year = rs.getInt("år");
			int id = rs.getInt("hendelseId");
			MeetTime time = new MeetTime(start, end, day, week, year);

			app = new Appointment(description, leader, time, id);

		} catch (Exception e) {
			// TODO: handle exception
		}

		return app;
	}
	//	private final static String HENDELSE = "hendelse";
	//	private final static String HENDELSE_ID = "hendelseId";
	//	private final static String HENDELSE_START = "start";
	//	private final static String HENDELSE_SLUTT = "slutt";
	//	private final static String HENDELSE_DATO = "dato";
	//	
	//
	//
	//	public static ArrayList<Time> getTime(int ID){
	//		ArrayList<Time> tid = new ArrayList<Time>();
	//		java.sql.Time tid1, tid2;
	//		try {
	//			ResultSet rs = Interact.execute("SELECT * FROM "+HENDELSE+" WHERE "+HENDELSE_ID +" = "+ID);
	//			while (rs.next()){
	//			tid1 = rs.getTime(HENDELSE_START);
	//			tid.add(tid1);
	//			tid2 = rs.getTime(HENDELSE_SLUTT);
	//			tid.add(tid2);
	//			}
	//		} catch (SQLException e) {
	//			
	//			e.printStackTrace();
	//		}
	//		return tid;
	//	}

	//	public static void main (String[]args){
	//	
	//			System.out.println(getTime(1));
	//		}
}
