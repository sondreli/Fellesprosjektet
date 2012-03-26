package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.Interact;


import model.Meeting;
import model.MeetingRoom;

public class DBMeetingRoom {


	public static void addMeetingRoom(MeetingRoom room){
		addMeetingRoom(room.getName(), room.getDescription(), room.getLocation());
	}

	private static void addMeetingRoom(String name, String description, String location) {

		String query = "INSERT INTO m퓍eRom " +
		"(navn, sted, beskrivelse) VALUES ('"
		+ name + "','" + description + "','" + location + "')";
		Interact.executeUpdate(query);
	}

	public static void removeMeetingRoom(String name){
		String query = "DELETE FROM m퓍eRom WHERE navn = '" + name + "'";
		Interact.executeUpdate(query);
	}
	/**
	 * Gets all the meetings in that room
	 * @param room
	 * @return ArrayList<Meeting>
	 */
	public ArrayList<Meeting> getMeetingsInRoom(MeetingRoom room){
		ArrayList<Meeting> meetings = new ArrayList<Meeting>();
		ResultSet rs = null;
		
		rs = Interact.execute("SELECT * FROM kobling WHERE navn = '" + room.getName() + "'");
		try {
			while(rs.next()){
				meetings.add(DBMeeting.makeMeetingObject(rs));				
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	public static void editMeetingRoom(MeetingRoom room){
		Interact.executeUpdate("UPDATE m퓍eRom SET navn = '" + room.getName() + "'," + 
				"sted = '" + room.getLocation() + "'," + 
				"beskrivelse = '" + room.getDescription() + "'" + 
				"WHERE navn = '" + room.getName() + "'"
		);
		
	}
	public static MeetingRoom makeMeetingRoomObject(ResultSet rs){
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

	public static MeetingRoom getMeetingRoom(String roomName) {
		ResultSet rs = Interact.execute("SELECT * FROM m퓍eRom WHERE romNavn = '" + roomName + "'");
		
		return makeMeetingRoomObject(rs);
	}
}
