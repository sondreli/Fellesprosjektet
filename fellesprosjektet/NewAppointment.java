package fellesprosjektet;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import model.Appointment;
import model.Meeting;
import model.MeetingRoom;
import database.DBMeetingRoom;
import database.DBUser;
import model.User;

public class NewAppointment{
	
	JScrollPane messageScroll;
	JTextPane message;
	JButton deleteButton, okButton, abortButton;
	JLabel addedLabel, dateLabel;
	JLabel clockLabel, clockFromLabel, clockToLabel;
	JLabel meetingLabel, messageLabel; 
	JComboBox numberDate, monthDate, fromHour, fromMinute;
	JComboBox toHour, toMinute, meetingRoom;
	ArrayList<Meeting> meetings;
	Meeting meeting;
	JFrame frame;
	ArrayList<User> allUsers;
	ArrayList<MeetingRoom> rooms;
	
	JPanel pan1, underPanel;
	public NewAppointment(ArrayList<Appointment> apps){
		JFrame frame = new JFrame();
	}
	public static void main(String[]args){
		
		NewAppointment gogo = new NewAppointment();
	
	}
	public NewAppointment(){
		frame = new JFrame();
		
		pan1 = new JPanel();
		underPanel = new JPanel();
		pan1.setLayout(new GridBagLayout());
		underPanel.setLayout(new GridBagLayout());
		GridBagConstraints cs = new GridBagConstraints();
		this.meetings = meetings;
//		meeting = new Meeting(participants, room, description, leader, meetingTime)
		
		String[] months =  {"January", "February", "March", "April", "May", "June", "July",
				"August", "September", "October", "November", "December"};
		
		
		
		String[] hours =  new String[24];
		String[] minutes = new String[60];
		
		
		for(int i = 0; i< 24; i++){
			if(i < 10){
				hours[i] = "0" + (i);
				continue;
			}
			hours[i] = "" + (i);
		}
		for(int i = 0; i< 60; i++){
			if(i < 10){
				minutes[i] = "0" + (i);
				continue;
			}
			minutes[i] = "" + (i);
		}
		//init everything
		
		
		message = new JTextPane();
		messageScroll = new JScrollPane(message);
		
		deleteButton = new JButton("Slett");
		okButton = new JButton("OK");
		abortButton = new JButton("Avbryt");
		
		
		
		
		dateLabel = new JLabel("Dato");
		clockLabel = new JLabel("Klokkeslett  ");
		clockFromLabel = new JLabel("Fra:");
		clockToLabel = new JLabel("Til:");
		meetingLabel = new JLabel("M¿terom:");
		messageLabel = new JLabel("Beskjed:");	
		


		numberDate = new JComboBox();
		fillDays(31);
		monthDate = new JComboBox(months);
		fromHour = new JComboBox(hours);
		fromMinute = new JComboBox(minutes);
		toHour = new JComboBox(hours);
		toMinute = new JComboBox(minutes);
		meetingRoom = new JComboBox();
		
		
		// add listeners
		monthDate.addActionListener(new MonthChangeListener());
		deleteButton.addActionListener(new DeleteButtonListener());
		abortButton.addActionListener(new AbortButtonListener());
		okButton.addActionListener(new OKButtonListener());
		
		
		// add testelements
		fillMeetingRoomList(meetingRoom);		
		
		
		
		// add to pane
		cs.gridx = 0;
		cs.gridy = 1;
		cs.anchor = GridBagConstraints.WEST;
		cs.fill = GridBagConstraints.HORIZONTAL;
		pan1.add(dateLabel, cs);
		
		cs.gridx = 1;
		cs.gridy = 1;
		pan1.add(numberDate, cs);
		
		cs.gridx = 2;
		cs.gridy = 1;
		cs.gridwidth = 2;
		pan1.add(monthDate, cs);
		
		cs.gridy = 2;
		cs.gridx = 0;
		cs.gridwidth = 1;
		pan1.add(clockLabel, cs);
		
		cs.gridx = 1;
		cs.gridy = 2;
		pan1.add(clockFromLabel, cs);
		
		cs.gridx = 3;
		cs.gridy = 2;
		pan1.add(fromMinute, cs);
		
		cs.gridx = 2;
		cs.gridy = 2;
		pan1.add(fromHour, cs);
		
		cs.gridx = 1;
		cs.gridy = 3;
		pan1.add(clockToLabel, cs);
		
		cs.gridx = 3;
		cs.gridy = 3;
		pan1.add(toMinute, cs);
		
		cs.gridx = 2;
		cs.gridy = 3;
		pan1.add(toHour, cs);
		
		cs.gridx = 0;
		cs.gridy = 4;
		pan1.add(meetingLabel, cs);
		
		cs.gridx = 1;
		cs.gridy = 4;
		cs.weightx = 3;
		cs.fill = GridBagConstraints.HORIZONTAL;
		pan1.add(meetingRoom, cs);
		
		cs.gridx = 0;
		cs.gridy = 5;
		cs.weightx = 1;
		cs.fill = GridBagConstraints.HORIZONTAL;
		pan1.add(messageLabel, cs);
		
		cs.gridx = 0;
		cs.gridy = 6;
		cs.ipadx = 100;
		cs.ipady = 142;
		cs.gridwidth = 4;
		cs.fill = GridBagConstraints.BOTH;
		pan1.add(messageScroll, cs);
	
		//UnderPanel
		cs.gridx = 0;
		cs.gridy = 0;
		cs.gridheight = 1;
		cs.gridwidth = 1;
		cs.ipadx = 0;
		cs.ipady = 0;
		cs.fill = GridBagConstraints.NONE;
		cs.anchor = GridBagConstraints.WEST;
		underPanel.add(deleteButton, cs);
		
		cs.gridx = 1;
		cs.anchor = GridBagConstraints.EAST;
		underPanel.add(abortButton, cs);
		
		cs.gridx = 2;
		underPanel.add(okButton, cs);
		
		pan1.setBounds(0, 0, 400, 500);		
		
		frame.setLayout(new GridBagLayout());
		GridBagConstraints con = new GridBagConstraints();
		con.gridx = 0;
		con.gridy = 0;
		con.anchor = GridBagConstraints.NORTH;
		
		frame.add(pan1, con);
		con.gridx = 0;
		con.gridy = 1;
		con.gridwidth = 2;
		frame.add(underPanel, con);
		frame.setVisible(true);
		frame.setResizable(false);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	public void fillMeetingRoomList(JComboBox comboBox){
		if(comboBox.getItemCount() != 0){
			comboBox.removeAllItems();
		}
		rooms = new ArrayList<MeetingRoom>();
		
		rooms = DBMeetingRoom.getAllMeetingRooms();
		
		for(MeetingRoom e : rooms){
			comboBox.addItem(e);
		}
	}
	
	public void fillDays(int days){
		
		//String[] daylist = new String[days];
		numberDate.removeAllItems();
		for(int i = 0; i< days; i++){
			//daylist[i] = "" + (i+1);
			numberDate.addItem((i+1));
		}
		
		
		
	}
	public void fillList(String contains, DefaultListModel listModel){
		if(!listModel.isEmpty()){
			listModel.clear();
		}
		
		for (User e : allUsers){
			if(e.getUserName().toLowerCase().contains(contains)){
				listModel.addElement(e);
				continue;
			}
			if(e.getName().toLowerCase().contains(contains)){
				listModel.addElement(e);
				continue;
			}	
		}
	}
	
	class MonthChangeListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			if(monthDate.getSelectedItem() == "February"){
				fillDays(28);
			}
			else if(monthDate.getSelectedItem() == "April" ||
					monthDate.getSelectedItem() == "June" ||
					monthDate.getSelectedItem() == "September" ||
					monthDate.getSelectedItem() == "November"){
				fillDays(30);
			}
			else {
				fillDays(31);
			}	
		}	
	}
	class DeleteButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}
	class OKButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}
	class AbortButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			frame.dispose();
			
		}
		
	}
	
}

