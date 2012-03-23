package fellesprosjektet;

import java.awt.Color;
import java.awt.Rectangle;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Sticker extends JPanel {
	JLabel lblmessage;
	
	public Sticker(Rectangle pos, String message) {
		lblmessage = new JLabel(message);
		
		setBounds(pos);
		setBackground(new Color(200, 50, 50, 150));
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		add(lblmessage);
	}
}
