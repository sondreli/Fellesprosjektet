package Database;

import java.awt.image.DataBufferInt;
import java.util.ArrayList;
import java.util.Date;

import model.Appointment;
import model.Day;
import model.MeetTime;
import model.Meeting;
import model.Message;
import model.Time;
import model.User;

public class DBTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub	
		User test = new User("Autogenerert");
		
		Message mote = new Message("testee2", "zoo do?", test, test);
		//DBAppointment.addAppointment(mote);
		
		
//		m¿te.setId(DBAppointment.getNewestID(m¿te));
		ArrayList<Message> m  = DBMessage.getInbox(test);
		for (Message message : m) {
			System.out.println(message.getContent());
		}
		

	}
}
