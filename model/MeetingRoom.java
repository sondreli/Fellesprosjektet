package model;

public class MeetingRoom {

	private String name, location, description;
	
	public MeetingRoom(String location, String name, String description){
		this.name = name;
		this.location = location;
		this.description = description;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
