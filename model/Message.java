package model;

import java.util.Calendar;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import database.DBMessage;


public class Message {

	private String content, topic;
	private Date dateSendt;
	private User sender,  recepient;
	private boolean read;
	private String status;
	private int ID;

	public Message(String topic, String content,User recepient,User sender){
		
		this.topic = topic;
		this.content = content;
	    java.util.Date utilDate = new java.util.Date();
		this.dateSendt = new java.sql.Date(utilDate.getTime());
		this.sender = sender;
		this.recepient = recepient;
		
		
		DBMessage.addMessage(this);
		
	}
	public Message(int id, Date dateSent,String topic, String content,User recepient,User sender, boolean read, String status){
		this.topic = topic;
		this.content = content;
		this.dateSendt = dateSent;
		this.sender = sender;
		this.recepient = recepient;
		this.read = read;
		this.status = status;
		this.ID = id;
		
		
	}
	//Setter datoen som en String
	private String dateToString() {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yy HH:mm");
		 java.util.Date utilDate = new java.util.Date();
		Date date = new java.sql.Date(utilDate.getTime());
		return dateFormat.format(date);
	}
	public boolean getRead(){
		return read;
	}
	public void setRead(boolean t){
		this.read = t;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getTopic() {
		return topic;
	}
	public void setTopic(String topic) {
		this.topic = topic;
	}
	public Date getDateSendt() {
		return dateSendt;
	}
	public void setDateSendt(Date dateSendt) {
		this.dateSendt = dateSendt;
	}
	public User getSender() {
		return sender;
	}
	public void setSender(User sender) {
		this.sender = sender;
	}
	public User getRecepient() {
		return recepient;
	}
	public void setRecepient(User recepient) {
		this.recepient = recepient;
	}
	public String getStatus(){
		return status;
	}
	public void setStatus(String Status){
		this.status = Status;
	}
	public int getID(){
		return ID;
	}
	
	@Override
	public String toString(){
		return getTopic() + " " + getSender().toString() + " " + getDateSendt();
	}
}
