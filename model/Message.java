package model;

import java.util.Calendar;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import Database.DBMessage;

public class Message {

	private String content, topic;
	private Date dateSendt;
	private User sender,  recepient;

	public Message(String topic, String content,User recepient,User sender){
		
		this.topic = topic;
		this.content = content;
	    java.util.Date utilDate = new java.util.Date();
		this.dateSendt = new java.sql.Date(utilDate.getTime());
		this.sender = sender;
		this.recepient = recepient;
		
		
		DBMessage.addMessage(this);
		
	}
	public Message(Date dateSent,String topic, String content,User recepient,User sender ){
		this.topic = topic;
		this.content = content;
		this.dateSendt = dateSent;
		this.sender = sender;
		this.recepient = recepient;
		
		
	}
	//Setter datoen som en String
	private String dateToString() {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yy HH:mm");
		 java.util.Date utilDate = new java.util.Date();
		Date date = new java.sql.Date(utilDate.getTime());
		return dateFormat.format(date);
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
}
