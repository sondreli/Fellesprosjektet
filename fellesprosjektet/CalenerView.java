package fellesprosjektet;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.LayoutManager;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import model.Calendar;

import com.sun.xml.internal.ws.api.server.Container;

public class CalenerView {
	public JFrame myFrame = new JFrame("Calener");
	public JLayeredPane lpane = new JLayeredPane();
	public JPanel myPanel;
	public JPanel calpanel;
	public JPanel cal2panel;
	public JPanel mes;
	public java.awt.Container pane;
	public GridBagConstraints layout;
	public model.Calendar caldata;
	public MonthCalender cal;
	public WeekCalendar wcal;
	public JButton butt;
	
	public CalenerView() {
		//Look and feel
		try {UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());}
		catch (ClassNotFoundException e) {}
		catch (InstantiationException e) {}
		catch (IllegalAccessException e) {}
		catch (UnsupportedLookAndFeelException e) {}
		
//		myFrame = new JFrame();
		myPanel = new JPanel();
		
		//Prepare frame
		Dimension mysize = new Dimension(1000, 600);
		myFrame.setSize(mysize); //Set size to 400x400 pixels
		myFrame.setLayout(new BorderLayout());
//		lpane.setLayout(new BorderLayout());
		myFrame.setContentPane(lpane);
//		myFrame.add(lpane, BorderLayout.CENTER);
//		pane = myFrame.getContentPane(); //Get content pane
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Close when X is clicked
		myFrame.setVisible(true);

		//Create controls
		butt = new JButton("hei");
		myPanel = new JPanel();
		calpanel = new JPanel();
		cal2panel = new JPanel();
		layout = new GridBagConstraints();
		myPanel.setLayout(new GridBagLayout());
		caldata = new model.Calendar();
		cal = new MonthCalender(lpane, caldata, 0, 30, 1, BorderLayout.WEST);
		wcal = new WeekCalendar(caldata, 270, 30);
		MessageBar mbar = new MessageBar(0, 0);
		mes = new JPanel();
		
		
		mes.setBounds(150, 150, 50, 50);
		mes.setBackground(new Color(200, 50, 50, 150));
		mes.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		//Add controls to pane
		myPanel.add(butt);
		layout.gridx = 0;
		layout.gridy = 0;
		lpane.add(mes, new Integer(2));
		lpane.add(wcal, new Integer(2));
		lpane.add(mbar, new Integer(2));
//		lpane.add(myPanel);
//		lpane.add(cal);//, BorderLayout.WEST);
//		lpane.add(cal2, BorderLayout.EAST);
		lpane.repaint();

		myPanel.setBounds(0, 0, 800, 600);
		
		
//		myPanel.add(cal);
	}
}
