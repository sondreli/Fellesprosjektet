package fellesprosjektet;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.User;

import com.sun.media.sound.Toolkit;

import database.DBUser;

public class Innlogging  extends JPanel{

		private JFrame frame;
		private JPanel pane;
		private JTextField userName, passWord;
		private JButton login, cancel;
		private JLabel userNameLabel, passwordLabel;
		
		public Innlogging() {
			frame = new JFrame("Login");
			frame.setLayout(new BorderLayout());
			pane = new JPanel();
			pane.setLayout(new GridBagLayout());
			
			login = new JButton("Login");
			cancel = new JButton("Cancel");
			
			userNameLabel = new JLabel("Brukernavn: ");
			passwordLabel = new JLabel("Passord: ");
			
			userName = new JTextField(30);
			passWord = new JTextField(30);
			
			login.addActionListener(new loginButtonListener());
			cancel.addActionListener(new cancelButtonListener());
			userName.addFocusListener(new FieldFocusListener());
			passWord.addFocusListener(new FieldFocusListener());
			
			GridBagConstraints c = new GridBagConstraints();
			
			
			c.fill = GridBagConstraints.HORIZONTAL;
			c.weightx = 1;
			c.gridx = 0;
			c.gridy = 0;
			pane.add(userNameLabel, c);
			
			c.gridx = 1;
			c.gridy = 0;
			pane.add(userName, c);
			
			c.gridx = 0;
			c.gridy = 1;
			pane.add(passwordLabel, c);
			
			c.gridx = 1;
			c.gridy = 1;
			pane.add(passWord, c);
			
			c.anchor = GridBagConstraints.EAST;
			c.gridx = 0;
			c.gridy = 2;
			pane.add(login, c);
			
			
			c.anchor = GridBagConstraints.WEST;
			c.gridx = 1;
			pane.add(cancel, c);
			
			
			
			frame.setSize(300, 100);
			frame.setLocation(400, 225);
			frame.getContentPane().add(pane);
			frame.setVisible(true);
			
		}

		public static void main(String[] args) {

			new Innlogging();
			
		}
		public void emptyEVERYTHING(){
			userName.setText("");
			passWord.setText("");
		}
		class loginButtonListener implements ActionListener{

			@Override
			public void actionPerformed(ActionEvent e) {
				if(!userName.getText().equals("") || !passWord.getText().equals("")){
					User user = DBUser.getUser(userName.getText());
					if(user == null){
						userName.setText("Wrong Username");
						passWord.setText("Or Password");
					}else if(!user.getPassword().equals(passWord.getText())){
						userName.setText("Wrong Username");
						passWord.setText("Or Password");
					}else{
						new CalenderView(user);
						frame.dispose();
					}
					
				}else{
					
					userName.setText("Remember to Fill");
					passWord.setText("All the Fields");
					
				}
					
				
				
			}
			
		}
		class FieldFocusListener implements FocusListener{

			@Override
			public void focusGained(FocusEvent arg0) {
				if(userName.getText() != null){
					if(userName.getText().equals("Wrong Username") ||
							userName.getText().equals("Remember to Fill")){
						emptyEVERYTHING();
					}
				}
				
			}

			@Override
			public void focusLost(FocusEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		}
		class cancelButtonListener implements ActionListener{

			@Override
			public void actionPerformed(ActionEvent e) {
				
				frame.dispose();
			}
			
		}


		
	
}
