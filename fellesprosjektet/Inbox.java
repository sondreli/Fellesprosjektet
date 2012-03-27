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
	
	
	
	JButton acceptButton, declineButton;
	JButton switchButton;
	
	
	public static void main(String[]args){
		new Inbox();
	}
	public Inbox(){
		
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
		
		messageEmneLabel = new JLabel("emnetest");
		messageFromLabel = new JLabel("fratest");
		messageDateLabel = new JLabel("datetest");
		
		
		acceptButton = new JButton("Accept");
		declineButton = new JButton("Decline");
		switchButton = new JButton("Old Messages");
		
		textArea = new JTextArea();
		
		GridBagConstraints cs = new GridBagConstraints();
		
		listModel = new DefaultListModel();
		
		list = new JList();
		
		scrollList = new JScrollPane(list);
		scrollMessage = new JScrollPane(textArea);
		
		
		
		list.setModel(listModel);
		
		
		
		// add listeners
		
		acceptButton.addActionListener(new AcceptButtonListener());
		declineButton.addActionListener(new DeclineButtonListener());
		switchButton.addActionListener(new SwitchButtonListener());
		
		// legg til i panes
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
		cs.ipadx = 0;
		cs.anchor = GridBagConstraints.CENTER;
		
		
		pan1.add(scrollList, cs);
		
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
		cs.ipadx = 200 - scrollMessage.getWidth();
		cs.ipady = 100 - scrollMessage.getHeight();
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
		pan3.add(switchButton, cs);
		
		GridBagConstraints con = new GridBagConstraints();
		pan1.setBounds(0, 0, 200, 300);
		pan2.setBounds(200, 0, 200, 300);
		pan3.setBounds(0,300,40,40);
		con.gridx = 0;
		con.gridy = 0;
		con.insets = new Insets(1, 1, 1, 1);
		pan4.add(pan1, con);
		
		
		con.gridx = 1;
		pan4.add(pan2, con);
		con.gridy = 1;
		con.gridx = 0;
		pan4.add(pan3, con);
		
		

		frame.setContentPane(pan4);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
		
		
		ArrayList<Message> messages = makeMessages();
		for(Message m: messages){
			listModel.addElement(m);
		}




		
		
		
		
		
	}
	
	public ArrayList<Message> makeMessages(){
		ArrayList<Message> messages = new ArrayList<Message>();
		
		for(int i = 0; i < 10; i++){
			messages.add(new Message(new Date(5, 5, 2012), "Emne" + i, "Dette er meldingen", new User("Navn"), new User("navn"), false));
		}
		
		return messages;
	}

	class AcceptButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			listModel.clear();

			
		}
		
	}
	class DeclineButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}
	class SwitchButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			if(switchButton.getText() == "New Messages"){
				switchButton.setText("Old Messages");
			}
			else{
				switchButton.setText("New Messages");
				switchButton.setSize(100, 200);
			}
		}
		
	}
	
}
