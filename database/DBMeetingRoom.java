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

		String query = "INSERT INTO m\uc3b8teRom " +
		"(navn, sted, beskrivelse) VALUES ('"
		+ name + "','" + description + "','" + location + "')";
		Interact.executeUpdate(query);
	}

	public static void removeMeetingRoom(String name){
		String query = "DELETE FROM m\uc3b8teRom WHERE navn = '" + name + "'";
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
		return meetings;
	}
	public static ArrayList<MeetingRoom> getAllMeetingRooms(){
		ResultSet rs = Interact.execute("SELECT * FROM m\uc3b8teRom");
		ArrayList<MeetingRoom> rooms = new ArrayList<MeetingRoom>();
		
		try {
			while(rs.next()){
				rooms.add(makeMeetingRoomObject(rs));
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return rooms;
	}
	public static void editMeetingRoom(MeetingRoom room){
		Interact.executeUpdate("UPDATE m\uc3b8teRom SET navn = '" + room.getName() + "'," + 
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

			room = new MeetingRoom(location, name, description);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return room;
	}

	public static MeetingRoom getMeetingRoom(String roomName) {
		ResultSet rs = Interact.execute("SELECT * FROM m\uc3b8teRom WHERE romNavn = '" + roomName + "'");
		
		return makeMeetingRoomObject(rs);
	}
}