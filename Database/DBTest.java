package database;

import java.awt.image.DataBufferInt;
import java.util.ArrayList;
import java.util.Date;

import model.Appointment;
import model.Day;
import model.MeetTime;
import model.Meeting;
import model.MeetingRoom;
import model.Message;
import model.Time;
import model.User;

public class DBTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		ArrayList<User> participants = new ArrayList<User>();
		participants.add(new User("Adrian"));
		participants.add(new User("Du", "meg", "123456"));
		MeetingRoom room = new MeetingRoom("p15", "424", "et rom i fjerde etasje");
		String description = "dette er en test av databasen, håper alt funker";
		User leader = new User("SuperLeader", "Wilhelm", "hemmelig");
		MeetTime meetingTime = new MeetTime(new Time(12, 15), new Time(16, 00), Day.Friday, 52, 2012);
		
		Meeting meeting = new Meeting(participants, room, description, leader, meetingTime);
		

	}
}
