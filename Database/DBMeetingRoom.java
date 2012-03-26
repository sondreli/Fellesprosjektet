package database;

import java.sql.ResultSet;
import java.sql.SQLException;

import model.MeetingRoom;

public class DBMeetingRoom {


	public static void addMeetingRoom(MeetingRoom room){
		addMeetingRoom(room.getName(), room.getDescription(), room.getLocation());
	}

	private static void addMeetingRoom(String name, String description, String location) {

		String query = "INSERT INTO m¿teRom " +
		"(navn, sted, beskrivelse) VALUES ('"
		+ name + "','" + description + "','" + location + "')";
		Interact.executeUpdate(query);
	}

	public static void removeMeetingRoom(String name){
		String query = "DELETE FROM m¿teRom WHERE navn = '" + name + "'";
		Interact.executeUpdate(query);
	}

	public MeetingRoom makeMeetingRoomObject(ResultSet rs){
		MeetingRoom room = null;

		try {
			String name = rs.getString("navn");
			String description = rs.getString("beskrivelse");
			String location = rs.getString("sted");

			room = new MeetingRoom(name, description, location);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return room;
	}
}
