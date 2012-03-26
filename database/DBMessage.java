package database;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.Date;

import model.Message;
import model.User;

public class DBMessage {

	
	public static void addMessage(Message message){
		addMessage(message.getDateSendt(), message.getTopic(), message.getContent(),message.getRecepient(),message.getSender());
	}
	
	public static void addMessage(Date dateSent, String topic, String content,User recepient,User sender){
		Interact.executeUpdate("INSERT INTO inbox " +
		"(datoSendt, tittel, innhold, til, fra) VALUES ('"
		+ dateSent + "','" + topic + "','" + content + "','" + recepient.getUserName() + "','" + sender.getUserName() + "')");
	}
	public static ArrayList<Message> getInbox(User user){
		ArrayList<Message> messages = new ArrayList<Message>(); 
		Message message = null;
		try {
			ResultSet rs = Interact.execute("SELECT * FROM inbox WHERE til = '" + user.getUserName() + "'");

			while(rs.next()){
				//Construct user object ResultSet
				message = makeMessageObject(rs);

				//Add user to users list
				messages.add(message);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return messages;
	}
	
	public static Message makeMessageObject(ResultSet rs){
		Message message = null;
		
		try {
			String content = rs.getString("innhold");
			String topic = rs.getString("tittel");
			Date sent = rs.getDate("datoSendt");
			User sender = DBUser.getUser(rs.getString("fra")) ;
			User recepient =  DBUser.getUser(rs.getString("til"));
			boolean read = rs.getBoolean("lest");
			
			message = new Message(sent, topic, content, recepient, sender, read);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return message;
		
	}
}
