package fellesprosjektet;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Rectangle;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Sticker extends JPanel {
	JLabel lblmessage;
	private GridBagConstraints constrnts;
	
	public Sticker(Rectangle pos, String message) {
		lblmessage = new JLabel(message);
		constrnts = new GridBagConstraints();
		
		setLayout(new GridBagLayout());
		
		setBounds(pos);
		setBackground(new Color(200, 50, 50, 150));
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		constrnts.gridx = 0;
		constrnts.gridy = 0;
		add(lblmessage, constrnts);
	}
}
