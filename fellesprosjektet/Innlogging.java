package fellesprosjektet;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Innlogging  extends JPanel{

		private JTextField felt= new JTextField(30), felt2= new JTextField(30);
		private JLabel navn = new JLabel("Brukernavn: "), navn2 = new JLabel("Passord: ");
		int noe;
		
		public Innlogging() {
			JPanel pane = new JPanel();
			pane.setLayout(new GridBagLayout());
			GridBagConstraints c = new GridBagConstraints();
			c.fill = GridBagConstraints.HORIZONTAL;
			c.weightx = 1;
			c.gridx = 0;
			c.gridy = 0;
			pane.add(navn, c);
			c.gridx = 1;
			c.gridy = 0;
			pane.add(felt, c);
			c.gridx = 0;
			c.gridy = 1;
			pane.add(navn2, c);
			c.gridx = 1;
			c.gridy = 1;
			pane.add(felt2, c);
			Object[] options = { "Logg inn", "Cancel" };
			noe = JOptionPane.showOptionDialog(null,
					pane, "Innlogging",
					JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
			if(noe == 0){
				System.out.println(felt.getText()+ "\n"+felt2.getText());
			}
		}

		public static void main(String[] args) {

			JPanel panel = new Innlogging();
			JFrame frame = new JFrame("Øving 1");
			frame.getContentPane().add(panel);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.pack();
			frame.setVisible(true);
			
		}


		
	
}
