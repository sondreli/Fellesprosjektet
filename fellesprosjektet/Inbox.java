package fellesprosjektet;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import sun.java2d.loops.GeneralRenderer;

import database.DBMessage;
import database.DBUser;

import model.Message;
import model.User;

public class Inbox {
	
	JFrame frame;
	JPanel pan1, pan3;
	JPanel pan2, pan4;
	JLabel emneLabel, fraLabel, datoLabel;
	JLabel messageEmneLabel, messageDateLabel, messageFromLabel;
	JTextArea textArea;
	DefaultListModel listModel;
	JList list;
	JScrollPane scrollList, scrollMessage;
	ArrayList<Message> messages;
	
	JButton acceptButton, declineButton;
	JButton switchButton;
	
	User thisUser;
	
	public static void main(String[]args){
		User user = DBUser.getAllUsers().get(0);
		new Inbox(user);
		
	}
	
	public Inbox(User user){
		
		thisUser = user;
		// init everything
		frame = new JFrame("Inbox");
		
		pan1 = new JPanel();
		pan2 = new JPanel();
		pan3 = new JPanel();
		pan4 = new JPanel();
		
		
		
		frame.setLayout(new BorderLayout());
		pan1.setLayout(new GridBagLayout());
		pan2.setLayout(new GridBagLayout());
		pan3.setLayout(new GridBagLayout());
		pan4.setLayout(new GridBagLayout());
		emneLabel = new JLabel("emne");
		fraLabel = new JLabel("fra");
		datoLabel = new JLabel("dato");
		
		
		messageEmneLabel = new JLabel("Topic:");
		messageFromLabel = new JLabel("From :");
		messageDateLabel = new JLabel("Date  :");
		
		
		acceptButton = new JButton("Accept");
		declineButton = new JButton("Decline");
		switchButton = new JButton("Old Messages");
		
		acceptButton.setEnabled(false);
		declineButton.setEnabled(false);
		
		textArea = new JTextArea();
		textArea.setEditable(false);
		
		GridBagConstraints cs = new GridBagConstraints();
		
		listModel = new DefaultListModel();
		
		list = new JList();
		
		scrollList = new JScrollPane(list);
		scrollMessage = new JScrollPane(textArea);
		scrollList.setSize(150, 150);
		scrollList.setIgnoreRepaint(true);
		
		list.setModel(listModel);
		
		
		
		// add listeners
		
		acceptButton.addActionListener(new AcceptButtonListener());
		declineButton.addActionListener(new DeclineButtonListener());
		switchButton.addActionListener(new SwitchButtonListener());
		list.addListSelectionListener(new SelectedMessageChanged());
		
		// legg til i panes
		cs.fill = GridBagConstraints.NONE;
		cs.gridx = 0;
		cs.gridy = 0;
		cs.ipadx = 50;
		cs.anchor = GridBagConstraints.WEST;
		pan1.add(emneLabel, cs);
		
		cs.gridx = 1;
		cs.ipadx = 5;
		cs.anchor = GridBagConstraints.EAST;
		pan1.add(fraLabel, cs);
		
		cs.gridx = 2;
		cs.anchor = GridBagConstraints.EAST;
		pan1.add(datoLabel, cs);
		
		cs.gridx = 0;
		cs.gridy = 1;
		cs.gridwidth = 3;
		cs.ipadx = 50;
		cs.ipady = 35;
		cs.fill = GridBagConstraints.BOTH;
		cs.anchor = GridBagConstraints.CENTER;
		
	    
	    ((GridBagLayout)pan1.getLayout()).setConstraints(scrollList, cs);
		pan1.add(scrollList);
		
		// for pan2
		cs = new GridBagConstraints();
		
		cs.anchor = GridBagConstraints.WEST;
		cs.gridx = 0;
		cs.gridy = 0;
		cs.gridwidth = 2;
		pan2.add(messageEmneLabel, cs);
		
		cs.gridy = 1;
		pan2.add(messageFromLabel, cs);
		
		cs.gridy = 2;
		pan2.add(messageDateLabel, cs);
		
		cs.gridy = 3;
		cs.ipadx = 200;
		cs.ipady = 100;
		pan2.add(scrollMessage, cs);
		
		cs.gridwidth = 1;
		cs.gridy = 4;
		cs.ipadx = 0;
		cs.ipady = 0;
		pan2.add(acceptButton, cs);
		
		cs.gridx = 1;
		pan2.add(declineButton, cs);
	
		
		// pan3
		
		cs = new GridBagConstraints();
		cs.ipadx = 5;
		cs.fill = GridBagConstraints.HORIZONTAL;
		pan3.add(switchButton, cs);
		
		GridBagConstraints con = new GridBagConstraints();
		pan1.setBounds(0, 0, 200, 300);
		pan2.setBounds(200, 0, 200, 300);
		pan3.setBounds(0,300,40,40);
		con.anchor = GridBagConstraints.NORTHWEST;
		con.gridx = 0;
		con.gridy = 0;
		con.insets = new Insets(1, 1, 1, 1);
		pan4.add(pan1, con);
		
		
		con.gridx = 1;
		pan4.add(pan2, con);
		con.gridy = 1;
		con.gridx = 0;
		con.fill = GridBagConstraints.HORIZONTAL;
		pan4.add(pan3, con);
		
		

		frame.getContentPane().add(pan4, BorderLayout.NORTH);
		frame.setResizable(false);
		frame.pack();
		frame.setVisible(true);
		
		
		messages = makeMessages();
		

		
	}
	
	public ArrayList<Message> makeMessages(){
		listModel.clear();
		ArrayList<Message> messages = DBMessage.getInbox(thisUser);
		for(Message m: messages){
			if(!m.getRead()){
				listModel.addElement(m);
			}
		}
		return messages;
	}
	public void fillList(boolean status){
		listModel.clear();
		if(status){
			for(Message m: messages){
				if(m.getRead()){
					listModel.addElement(m);
				}
			}
		}
		else{
			for(Message m: messages){
				if(!m.getRead()){
					listModel.addElement(m);
				}
			}
		}
		
		
	}
	public void emptyMessageBox(){
		messageEmneLabel.setText("Topic:");
		messageFromLabel.setText("From :");
		messageDateLabel.setText("Date  :");
		textArea.setText("");
		
	}
	class SelectedMessageChanged implements ListSelectionListener{

		@Override
		public void valueChanged(ListSelectionEvent arg0) {
			if(list.getSelectedValue() != null){
				Message message = (Message)list.getSelectedValue();
				textArea.setText(message.getContent());
				messageEmneLabel.setText("Topic: " + message.getTopic());
				messageFromLabel.setText("From : " + message.getSender().getName());
				messageDateLabel.setText("Date  : " + message.getDateSendt().toString());
				message.setRead(true);
				DBMessage.editMessage(message);
				if(!message.getStatus().equals("Waiting")){
					acceptButton.setEnabled(false);
					declineButton.setEnabled(false);
				} else {
					acceptButton.setEnabled(true);
					declineButton.setEnabled(true);
				}
			}
			
			
		}
		
	}
	class AcceptButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			if(list.getSelectedValue() != null){
				Message message = (Message)list.getSelectedValue();
				message.setStatus("Accepted");
				acceptButton.setEnabled(false);
				declineButton.setEnabled(false);
				DBMessage.editMessage(message);
			}

		}
		
	}
	class DeclineButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			if(list.getSelectedValue() != null){
				Message message = (Message)(list.getSelectedValue());
				message.setStatus("Declined");
				acceptButton.setEnabled(false);
				declineButton.setEnabled(false);
				DBMessage.editMessage(message);
			}
			
		}
		
	}
	class SwitchButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			emptyMessageBox();
			if(switchButton.getText() == "New Messages"){
				switchButton.setText("Old Messages");
				fillList(false);
			}
			else{
				switchButton.setText("New Messages");
				fillList(true);
			}
		}
		
	}
	
}
