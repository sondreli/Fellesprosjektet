package fellesprosjektet;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import model.Appointment;
import model.Day;
import model.EventList;
import model.Meeting;
import model.MyDate;
import model.MeetTime;
import model.Time;
import model.User;

import com.sun.xml.internal.ws.api.server.Container;

import database.DBAppointment;
import database.DBParticipants;

public class CalenderView {
	public JFrame myFrame = new JFrame("Calender");
	public JLayeredPane lpane = new JLayeredPane();
	public JPanel calpanel;
	public JPanel cal2panel;
	public UserView uview;
	public java.awt.Container pane;
	public GridBagConstraints layout;
	public model.MyDate caldata;
	public MonthCalender cal;
	public WeekCalendar wcal;
	public JButton butt;
	public EventList events;
	private User user;
	
	public CalenderView(User user) {
		//Look and feel
		try {UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());}
		catch (ClassNotFoundException e) {}
		catch (InstantiationException e) {}
		catch (IllegalAccessException e) {}
		catch (UnsupportedLookAndFeelException e) {}
		
//		myFrame = new JFrame();
		
		//Prepare frame
		Dimension mysize = new Dimension(1200, 600);
		myFrame.setSize(mysize); //Set size to 400x400 pixels
		myFrame.setLayout(new BorderLayout());
//		lpane.setLayout(new BorderLayout());
		myFrame.setContentPane(lpane);
//		myFrame.add(lpane, BorderLayout.CENTER);
//		pane = myFrame.getContentPane(); //Get content pane
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Close when X is clicked
		myFrame.setVisible(true);

		//Create controls
		calpanel = new JPanel();
		cal2panel = new JPanel();
		layout = new GridBagConstraints();
		caldata = new model.MyDate();
		this.user = user;
		events = new EventList();
		addEvents(user);
		MessageBar mbar = new MessageBar(0, 0, events, user);
		cal = new MonthCalender(lpane, caldata, 0, 30, 1, BorderLayout.WEST);
		wcal = new WeekCalendar(lpane, caldata, events, 270, 30);
		EventPanel evpnl = new EventPanel(870, 30);

		uview = new UserView(0, 250, user, this);
		
		//Add controls to pane
		layout.gridx = 0;
		layout.gridy = 0;
		lpane.add(wcal, new Integer(2));
		lpane.add(mbar, new Integer(2));
		lpane.add(uview, new Integer(2));
//		lpane.add(wcal.addStickers(new MeetTime(new Time(10, 0), new Time(11, 0), Day.Sunday, 12, 2012)), new Integer(3));
//		lpane.add(wcal.addStickers(), new Integer(3));
//		lpane.add(stick, new Integer(3));




	//	lpane.add(wcal.addStickers(), new Integer(3));
		//lpane.add(stick, new Integer(3));
		lpane.add(evpnl, new Integer(2));
//		lpane.add(myPanel);
//		lpane.add(cal);//, BorderLayout.WEST);
//		lpane.add(cal2, BorderLayout.EAST);
		
		
//		myPanel.add(cal);
	}
	
	public void clearEvents() {
		events.clear();
	}
	
	public void addEvents(User user) {
		for (Appointment appointment : DBAppointment.getUsersAppointments(user)) {
			events.add(appointment);
			System.out.println("id:"+appointment.getId());
		}
		for (Meeting meeting : DBParticipants.getUsersMeetings(user)) {
			events.add(meeting);
			System.out.println("møte:"+meeting.getId());
		}
	}
}
