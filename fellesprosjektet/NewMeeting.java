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

import model.User;

public class NewMeeting{
	
	DefaultListModel users, addedUsers;
	JList usersList, addedUsersList;
	JScrollPane usersScrollList, addedUsersScrollList, messageScroll;
	JTextField search;
	JTextPane message;
	JButton addButton, removeButton;
	JLabel usersLabel, addedLabel, dateLabel;
	JLabel clockLabel, clockFromLabel, clockToLabel;
	JLabel meetingLabel, messageLabel; 
	JComboBox numberDate, monthDate, fromHour, fromMinute;
	JComboBox toHour, toMinute, meetingRoom;
	
	JPanel pan1, pan2;
	
	
	public static void main(String[]args){
		
		NewMeeting gogo = new NewMeeting();
	
	}
	public NewMeeting(){
		JFrame frame = new JFrame();
		
		pan1 = new JPanel();
		pan2 = new JPanel();
		pan1.setLayout(new GridBagLayout());
		pan2.setLayout(new GridBagLayout());
		GridBagConstraints cs = new GridBagConstraints();
		
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
		users = new DefaultListModel();
		addedUsers = new DefaultListModel();
		
		usersList = new JList();
		addedUsersList = new JList();
		
		usersList.setModel(users);
		addedUsersList.setModel(addedUsers);
		
		usersScrollList = new JScrollPane(usersList);
		addedUsersScrollList = new JScrollPane(addedUsersList);
		
		message = new JTextPane();
		messageScroll = new JScrollPane(message);
		search = new JTextField();
		
		addButton = new JButton("Add User");
		removeButton = new JButton("Remove User");
		
		usersLabel = new JLabel("Brukere");
		addedLabel = new JLabel("Lagt til");
		dateLabel = new JLabel("Dato");
		clockLabel = new JLabel("Klokkeslett  ");
		clockFromLabel = new JLabel("Fra:");
		clockToLabel = new JLabel("Til:");
		meetingLabel = new JLabel("Møterom:");
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
		
		addButton.addActionListener(new AddButtonListener());
		removeButton.addActionListener(new RemoveButtonListener());
		search.addKeyListener(new SearchFieldListener());
		monthDate.addActionListener(new MonthChangeListener());
		
		
		
		// add testelements
		
		fillList(users);
		
		
		
		
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
		
		
		
		// add to pane2
		cs.gridx = 0;
		cs.gridy = 0;
		cs.ipadx = 0;
		cs.ipady = 0;
		cs.gridwidth = 1;
		cs.fill = GridBagConstraints.HORIZONTAL;
		cs.anchor = GridBagConstraints.NORTH;
		pan2.add(usersLabel, cs);
		
		
		
		cs.gridy = 1;
		cs.gridx = 0;
		cs.ipadx = 50;
		pan2.add(search, cs);
	
		
		cs.gridy = 2;
		cs.gridx = 1;
		cs.ipadx = 0;
		cs.ipady = 0;
		pan2.add(addButton, cs);
		
		cs.gridy = 3;
		cs.gridx = 1;
		
		pan2.add(removeButton, cs);
		
		cs.gridy = 0;
		cs.gridx = 2;
		cs.anchor = GridBagConstraints.CENTER;
		pan2.add(addedLabel, cs);
		
		
		cs.gridy = 2;
		cs.gridx = 0;
		cs.ipadx = 50;
		cs.ipady = 100;
		cs.gridheight = 5;
		cs.fill = GridBagConstraints.BOTH;
		pan2.add(usersScrollList, cs);
		
		cs.gridy = 1;
		cs.gridx = 2;
		cs.gridheight = 4;
		cs.ipady = 120;
		cs.ipadx = 70;
		
		pan2.add(addedUsersScrollList, cs);
		
		
		
		pan1.setBounds(0, 0, 400, 500);
		pan2.setBounds(0, 0, 300, 500);
		
		
		frame.setLayout(new GridBagLayout());
		GridBagConstraints con = new GridBagConstraints();
		con.gridx = 0;
		con.gridy = 0;
		con.anchor = GridBagConstraints.NORTH;
		
		frame.add(pan1, con);
		con.gridx = 1;
		frame.add(pan2, con);
		frame.setVisible(true);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void fillList(DefaultListModel listModel){
		if(!listModel.isEmpty()){
			listModel.clear();
		}
		
		// legg til denne user
		
		User disUser = new User("Navn1", "Random1", "pass");
		// Legg til Users
		ArrayList<User> allUsers = new ArrayList<User>();
		addedUsers.addElement(disUser);
		allUsers.add(disUser);
		for(int i = 2; i < 30;i++){
			allUsers.add(new User("Navn" + i, "Random" + i, "pass"));
		}
		//ArrayList<User> allUsers = DBUser.getAllUsers();
		
		for(User e : allUsers){
			listModel.addElement(e);
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
		ArrayList<User> allUsers = new ArrayList<User>();
		for(int i = 1; i < 30;i++){
			allUsers.add(new User("Navn" + i));
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
	class AddButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			if(usersList.getSelectedValues().length != 0){
				for(Object user : usersList.getSelectedValues()){
					if(!addedUsers.contains((User)user)){
						addedUsers.addElement((User)user);
					}
				}
			}
			
		}
	}
	class RemoveButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			if(addedUsersList.getSelectedValues().length != 0){
				for(Object user : addedUsersList.getSelectedValues()){
					addedUsers.removeElement((User)user);
				}
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
	class SearchFieldListener implements KeyListener{

		@Override
		public void keyPressed(KeyEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyReleased(KeyEvent arg0) {
			if(search.getText() != null){
				fillList(search.getText(), users);
			}
			
		}

		@Override
		public void keyTyped(KeyEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}
}

