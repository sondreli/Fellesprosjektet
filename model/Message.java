package model;

import java.util.Calendar;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Message {

	private String content, topic;
	private String dateSendt;
	private User sender;

	public Message(String topic, String content){
		this.topic = topic;
		this.content = content;
		this.dateSendt = getDateTime();
		
		
		//kaller på newMessageListener eller noe slik at alt løser seg
	}
	//Setter datoen som en String
	private String getDateTime() {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yy HH:mm");
		Date date = new Date();
		return dateFormat.format(date);
	}
}
