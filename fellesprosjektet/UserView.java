package fellesprosjektet;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import database.DBUser;

import model.User;

public class UserView extends JPanel{
	
	DefaultListModel listModel;
	JList userList;
	JTextField searchField;
	JScrollPane scrollList;
	User thisUser;
	CalenderView view;
	
	
	public UserView(int xpos, int ypos, User user, CalenderView view){
		
		//init
		this.setLayout(new GridBagLayout());
		searchField = new JTextField();		
		userList = new JList();
		scrollList = new JScrollPane(userList);
		this.view = view;
		
		GridBagConstraints cs = new GridBagConstraints();
		listModel = new DefaultListModel();
		userList.setModel(listModel);
		

		fillList();
	
		
		// add to panel
		cs.anchor = GridBagConstraints.NORTHWEST;
		cs.gridwidth = 1;
		cs.gridx = 0;
		cs.gridy = 0;
		cs.ipady = 3;
		cs.ipadx = 135;
		cs.fill = GridBagConstraints.HORIZONTAL;
		cs.weightx = 0;
		cs.weighty = 0;
		add(searchField, cs);
		
	

		
		cs.anchor = GridBagConstraints.NORTH;
		cs.gridx = 0;
		cs.gridy = 1;
		cs.weightx = 10;
		cs.weighty = 10;
		cs.ipadx = 50;
		cs.ipady = 195;
		cs.fill = GridBagConstraints.HORIZONTAL;
		cs.gridheight = 350;
		cs.gridwidth = 130;
		add(scrollList, cs);
		
		
		searchField.setText("Search for users");
		searchField.addFocusListener(new SearchFieldListener());
		searchField.addKeyListener(new SearchListener());

		userList.addListSelectionListener(new UserListSelectionChangedListener());
		this.setBounds(xpos, ypos, 140, 400);
		
	}
	
	public void fillList(){
		if(!listModel.isEmpty()){
			listModel.clear();
		}
		// Legg til Users
		ArrayList<User> allUsers = DBUser.getAllUsers();
		listModel.addElement(thisUser);
		for(User e : allUsers){
			if(e.equals(thisUser)){
				continue;
			}
			listModel.addElement(e);
		}
	}
	public void fillList(String contains){
		if(!listModel.isEmpty()){
			listModel.clear();
		}
		ArrayList<User> allUsers = DBUser.getAllUsers();
		for (User e : allUsers){
			if(e.equals(thisUser)){
				continue;
			}
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
	
	class SearchListener implements KeyListener{

		@Override
		public void keyPressed(KeyEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyReleased(KeyEvent arg0) {
			if(searchField.getText() != null){
				if(!searchField.getText().equals("Search for users")){
					fillList(searchField.getText().toLowerCase());
				}
				
			}
			
		}

		@Override
		public void keyTyped(KeyEvent arg0) {
			
		}
		
	}
	
	class UserListSelectionChangedListener implements ListSelectionListener{

		@Override
		public void valueChanged(ListSelectionEvent arg0) {
			view.clearEvents();
			if(!listModel.isEmpty()){
				
				for( User u : (User[])userList.getSelectedValues()){
					view.addEvents(u);
				}
			}else {
				view.addEvents(thisUser);
			}
			
		}
		
	}
	class SearchFieldListener implements FocusListener{
		

		@Override
		public void focusGained(FocusEvent arg0) {
			if(searchField.getText() != null){
				if(searchField.getText().equals("Search for users")){
					searchField.setText("");
				}
			}
			
		}

		@Override
		public void focusLost(FocusEvent arg0) {
			if(searchField.getText() != null){
				if(searchField.getText().equals("")){
					searchField.setText("Search for users");
					fillList();
				}
				
			}
			
		}
	}

}
