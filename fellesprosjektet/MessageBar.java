package fellesprosjektet;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MessageBar extends JPanel{
	
	JButton newEventButton, settingsButton, logOffButton, messagesButton;
	JLabel fillLabel;
	
	
	public MessageBar(int xpos, int ypos){
		//init
		this.setLayout(new GridBagLayout());
		GridBagConstraints cs = new GridBagConstraints();
		
		newEventButton = new JButton("New Event");
		settingsButton = new JButton("Settings");
		logOffButton = new JButton("Log Off");
		messagesButton = new JButton("Messages");
		fillLabel = new JLabel();
		
		
		
		//add listeners
		messagesButton.addActionListener(new MessageButtonListener());
		newEventButton.addActionListener(new NewEventListener());
		settingsButton.addActionListener(new SettingsListener());
		logOffButton.addActionListener(new LogOffButtonListener());
		
		
		// add to panel
		
		cs.anchor = GridBagConstraints.WEST;
		cs.gridx = 0;
		cs.gridy = 0;
		cs.ipadx = 200;
		
		add(messagesButton, cs);
		
		cs.gridx = 1;
		cs.ipadx = 460;
		add(fillLabel, cs);
		
		
		cs.anchor = GridBagConstraints.NORTHWEST;
		cs.gridx = 2;
		cs.ipadx = 0;
		add(newEventButton, cs);
		
		
		cs.gridx = 3;
		add(settingsButton, cs);
		
		cs.anchor = GridBagConstraints.EAST;
		cs.gridx = 4;
		
		add(logOffButton, cs);
		
		
		this.setBounds(xpos, ypos, 1000, 30);

		
	}
	class MessageButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
		
	}
	class NewEventListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			new NewMeeting();
			
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
