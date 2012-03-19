package model;

import java.util.ArrayList;

public class User {
	
	private String userName;
	private String name;
	private String password;
	private ArrayList<Message> inbox;
	
	public User(String usrName){
		this.userName = usrName;
		this.name = "Autogenerert";
		this.password = "123456";
		this.inbox = new ArrayList<Message>();
		
	}
	public User(String usrName, String name, String password){
		this.userName = usrName;
		this.name = name;
		this.password = password;
		this.inbox = new ArrayList<Message>();
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public ArrayList<Message> getInbox() {
		return inbox;
	}
	public void setInbox(ArrayList<Message> inbox) {
		this.inbox = inbox;
	}
	
	
}
