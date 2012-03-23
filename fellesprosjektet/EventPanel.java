package fellesprosjektet;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import model.User;

public class EventPanel extends JPanel {
	
	DefaultListModel listModel;
	JList eventList;
	JScrollPane scrollList;
	JLabel headLabel;
	
	public EventPanel(int xpos, int ypos){
		GridBagConstraints cs = new GridBagConstraints();
		this.setLayout(new GridBagLayout());
		
		listModel = new DefaultListModel();
		eventList = new JList();
		headLabel = new JLabel("Oppkommende Møter");
		scrollList = new JScrollPane(eventList);
		
		eventList.setModel(listModel);
		
		fillList(); 
		
		
		cs.gridx = 0;
		cs.gridy = 0;
		
		cs.anchor = GridBagConstraints.NORTH;
		add(headLabel, cs);
		
		
		
		cs.gridy = 1;
		cs.fill = GridBagConstraints.BOTH;
		//cs.ipadx = 100;
		cs.ipady = 360;
		add(scrollList, cs);
		
		
		this.setBounds(xpos, ypos, 130, 400);
		
		
	}
	
	public void fillList(){
		if(!listModel.isEmpty()){
			listModel.clear();
		}
		// Legg til Users
		ArrayList<User> allUsers = new ArrayList<User>();
		allUsers.add(new User("Navn1"));
		allUsers.add(new User("Navn2"));
		
		for(int i = 3; i < 30;i++){
			allUsers.add(new User("Navn" + i));
		}
		//ArrayList<User> allUsers = DBUser.getAllUsers();
		
		for(User e : allUsers){
			listModel.addElement(e);
		}
	}

}
