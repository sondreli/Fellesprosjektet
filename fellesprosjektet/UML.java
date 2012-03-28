
/*
@startuml
title Klassediagram
package fellesprosjektet
EventPanel --|> JPanel
MessageBar --|> JPanel
MessageButtonListener <|- ActionListener
NewEventListener <|. ActionListener
SettingsListener <|. ActionListener
LogOffButtonListener <|. ActionListener
MonthCalender -|> JComponent
MonthCalender <|. PropertyChangeListener
tblCalendarRenderer --|> DefaultTableCellRenderer
btnPrev_Action <|. ActionListener
btnNext_Action <|. ActionListener
cmbYear_Action <|. ActionListener
tbl_Action <|. MouseListener
AddButtonListener <|. ActionListener
RemoveButtonListener <|. ActionListener
MonthChangeListener <|. ActionListener
AbortButtonListener <|. ActionListener
OKButtonListener <|. ActionListener
DeleteButtonListener <|. ActionListener
SearchFieldListener <|. KeyListener







class CalenderView {
	+ JFrame myFrame = new JFrame("Calender");
	+ JLayeredPane lpane = new JLayeredPane();
	+ JPanel myPanel;
	+ JPanel calpanel;
	+ JPanel cal2panel;
	+ UserView uview;
	+ java.awt.Container pane;
	+ GridBagConstraints layout;
	+ model.MyDate caldata;
	+ MonthCalender cal;
	+ WeekCalendar wcal;
	+ JButton butt;
	+ ArrayList<Meeting> meetings;

	+CalenderView() 
}
class Client {

	#CalenderView cal;
	+main(String[] args) {

	}
class EventPanel {

	+DefaultListModel listModel;
	+JList eventList;
	+JScrollPane scrollList;
	+JLabel headLabel;

	+EventPanel(int xpos, int ypos)

	+  fillList()

}

class MessageBar{

	+JButton newEventButton, settingsButton, logOffButton, messagesButton;
	+JLabel fillLabel;
	+ArrayList<Meeting> meetings;


	+MessageBar(int xpos, int ypos, ArrayList<Meeting> meetings)
}
class MessageButtonListener{
	+actionPerformed(ActionEvent arg0) 
}
class NewEventListener {
	+actionPerformed(ActionEvent e) 
}
class SettingsListener {
	+actionPerformed(ActionEvent e)
}
class LogOffButtonListener{
	+actionPerformed(ActionEvent e)
}
class MonthCalender{
	- JLabel lblMonth, lblYear;
	- JButton btnPrev, btnNext;
	- JTable tblCalendar;
	- JComboBox cmbYear;
	- Container pane;
	- DefaultTableModel mtblCalendar; //Table model
	- JScrollPane stblCalendar; //The scrollpane
	- JPanel pnlCalendar;
	- int realYear, realMonth, realWeek, realDay, currentYear, currentMonth, currentWeek;
	- model.MyDate data;
	- GridBagConstraints myCon;
	+ MonthCalender(JLayeredPane pane, model.MyDate data, int xpos, int ypos, int nr, String blayout) 
	- refreshCalendar(int month, int year)
	+ propertyChange(PropertyChangeEvent evt) 
class tblCalendarRenderer{
	+ Component getTableCellRendererComponent (JTable table, Object value, boolean selected, boolean focused, int row, int column)
}
class btnPrev_Action{
	+ actionPerformed (ActionEvent e)
}
class btnNext_Action{
	+ actionPerformed (ActionEvent e)
}
class cmbYear_Action {
	+ actionPerformed (ActionEvent e)
}
class tbl_Action{
	+ mouseClicked(MouseEvent e) 

	+ mouseEntered(MouseEvent e) 
	+ mouseExited(MouseEvent e) 
	+ mousePressed(MouseEvent e) 
	+ mouseReleased(MouseEvent e) 
}



class NewMeeting{
	
	+DefaultListModel users, addedUsers;
	+JList usersList, addedUsersList;
	+JScrollPane usersScrollList, addedUsersScrollList, messageScroll;
	+JTextField search;
	+JTextPane message;
	+JButton addButton, removeButton;
	+JButton deleteButton, okButton, abortButton;
	+JLabel usersLabel, addedLabel, dateLabel;
	+JLabel clockLabel, clockFromLabel, clockToLabel;
	+JLabel meetingLabel, messageLabel; 
	+JComboBox numberDate, monthDate, fromHour, fromMinute;
	+JComboBox toHour, toMinute, meetingRoom;
	+ArrayList<Meeting> meetings;
	+Meeting meeting;
	+JFrame frame;
	+ArrayList<User> allUsers;
	+JPanel pan1, pan2, underPanel;
	+ NewMeeting(ArrayList<Meeting> meetings)
	+ static void main(String[]args)
	+ NewMeeting()
	+ fillList(DefaultListModel listModel)
	+ fillDays(int days)
	+ fillList(String contains, DefaultListModel listModel)
	}

class AddButtonListener{
		+ actionPerformed(ActionEvent arg0) 
}

class RemoveButtonListener{
		+ actionPerformed(ActionEvent arg0) 	
}

class MonthChangeListener{
		+ actionPerformed(ActionEvent arg0) 
}

class DeleteButtonListener{
		+ actionPerformed(ActionEvent arg0) 
}

class OKButtonListener{
		+ actionPerformed(ActionEvent arg0) 	
}

class AbortButtonListener{
		+ actionPerformed(ActionEvent arg0) 
}

class SearchFieldListener{
		+ keyPressed(KeyEvent arg0) 
		+ keyReleased(KeyEvent arg0) 
		+ keyTyped(KeyEvent arg0) 
}







@enduml
 */

