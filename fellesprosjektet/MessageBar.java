package fellesprosjektet;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.EventList;
import model.Meeting;
import model.User;

public class MessageBar extends JPanel{
	
	JButton newAppointmentButton, newMeetingButton, settingsButton, logOffButton, messagesButton;
	JLabel fillLabel;
	EventList meetings;
	User user;
	
	
	public MessageBar(int xpos, int ypos, EventList meetings, User user){
		//init
		this.setLayout(new GridBagLayout());
		GridBagConstraints cs = new GridBagConstraints();
		
		newAppointmentButton = new JButton("New Appointment");
		newMeetingButton = new JButton("New Meeting");
		settingsButton = new JButton("Settings");
		logOffButton = new JButton("Log Off");
		messagesButton = new JButton("Messages");
		fillLabel = new JLabel();
		this.meetings = meetings;
		this.user = user;
		
		
		
		//add listeners
		messagesButton.addActionListener(new MessageButtonListener());
		newAppointmentButton.addActionListener(new NewAppointmentListener());
		newMeetingButton.addActionListener(new NewMeetingListener());
		settingsButton.addActionListener(new SettingsListener());
		logOffButton.addActionListener(new LogOffButtonListener());
		
		
		// add to panel
		
		cs.anchor = GridBagConstraints.WEST;
		cs.gridx = 0;
		cs.gridy = 0;
		cs.ipadx = 200;
		
		add(messagesButton, cs);
		
		cs.gridx = 1;
		cs.ipadx = 320;
		add(fillLabel, cs);
		
		
		cs.anchor = GridBagConstraints.NORTHWEST;
		cs.gridx = 2;
		cs.ipadx = 0;
		add(newAppointmentButton, cs);
		
		cs.anchor = GridBagConstraints.NORTHWEST;
		cs.gridx = 3;
		cs.ipadx = 0;
		add(newMeetingButton, cs);
		
		cs.gridx = 4;
		add(settingsButton, cs);
		
		cs.anchor = GridBagConstraints.EAST;
		cs.gridx = 5;
		
		add(logOffButton, cs);
		
		
		this.setBounds(xpos, ypos, 1100, 30);

		
	}
	class MessageButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
		
	}
	class NewMeetingListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			new NewMeeting(meetings, user);
			
		}
		
	}
	class NewAppointmentListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			new NewAppointment(meetings, user);
			
		}
	}
	class SettingsListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		
	}
	class LogOffButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			
			
		}
		
	}
	
	
	
	

}
