


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
Sticker -|> JPanel
UserView -|> JPanel
SearchListener <|. KeyListener
UserListSelectionChangedListener <|. ListSelectionListener
SearchFieldListener <|. FocusListener
WeekCalendar -|> JPanel
WeekCalendar  <|. PropertyChangeListener
btnPrev_Action <|. ActionListener
btnNext_Action <|. ActionListener
tblCalendarRenderer -|> DefaultTableCellRenderer

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

class Sticker {
	+JLabel lblmessage;

	+Sticker(Rectangle pos, String message) 
}

class UserView{

	+DefaultListModel listModel;
	+JList userList;
	+JTextField searchField;
	+JScrollPane scrollList;
	+User thisUser;
	+CalenderView view;


	+UserView(int xpos, int ypos, User user, CalenderView view)

	+fillList()
	+fillList(String contains)

	class SearchListener{

		+ keyPressed(KeyEvent arg0) 
		+ keyReleased(KeyEvent arg0) 
		+ keyTyped(KeyEvent arg0) 
	}

	class UserListSelectionChangedListener {
		+ valueChanged(ListSelectionEvent arg0)

	}
	class SearchFieldListener {


		+ focusGained(FocusEvent arg0) 


		+ focusLost(FocusEvent arg0) 
	}

}

class WeekCalendar {
	- JLabel lblWeek;
	- JButton btnPrev, btnNext;
	- GridBagConstraints constrnts;
	- JTable tblCalendar;
	- DefaultTableModel mtblCalendar; //Table model
	- JScrollPane stblCalendar; //The scrollpane
	- JPanel pnlCalendar;
	- JLayeredPane lpane;
	- model.MyDate data;
	- Rectangle bounds;
	- EventList evlist;
	- ArrayList<Sticker> myStickers;
	
	- WeekCalendar(JLayeredPane lpane, model.MyDate data, EventList evlist, int xpos, int ypos)
	
	- initCalendar() 
	
	- refreshCalendar(int week, int year)



	+ void propertyChange(PropertyChangeEvent evt) 
	
	- Rectangle getEventBounds(MeetTime time) 
	
	- void addNowTime(JLayeredPane lpane) 
	
	+ drawEvents(JLayeredPane lpane, EventList meetings) 
	
	+ addAppointment(JLayeredPane lpane, ArrayList<Appointment> appointments) 	
	- void removeAllSticers() 
	

}
	class tblCalendarRenderer {
		- Component getTableCellRendererComponent (JTable table, Object value, boolean selected, boolean focused, int row, int column)
	}
	class btnPrev_Action {
		+ actionPerformed (ActionEvent e)
	}
	class btnNext_Action {
		+ actionPerformed (ActionEvent e)
	}
package database;

class DatabaseConnect {
	-String driver = "org.gjt.mm.mysql.Driver";
	-String url = "jdbc:mysql://mysql.stud.ntnu.no/datdanny_SU47";
	-String user = "datdanny_sql47";
	-String password = "gruppe47";
	-Connection connection;
	
	#int dbConnect() 

	-boolean loadDriver() 
	
	-boolean initConnection()
	+String getDriver() 
	
	+setDriver(String driver) 
	
	+getUrl() 

	+setUrl(String url) 
	
	+getUser() 

	+setUser(String user) 
	
	+changePassword(String oldPassword, String newPassword1, String newPassword2 ) 
	
	#Connection getConnection() 
}
class DBAppointment{
	+ int addAppointment(Appointment app)
	+ int addAppointment(String description, User leader, MeetTime time)
	+ void editAppointment(Appointment app)
	+ Appointment getAppointmentbyID(int id)
	+ ArrayList<Appointment> getUsersAppointments(User user)
	+ int getNewestID(Appointment app)
	+ Appointment makeAppointmentObject(ResultSet rs)

}
class DBEvent {
	- String HENDELSE = "hendelse";
	- String HENDELSE_ID = "hendelseId";
	- String HENDELSE_START = "start";
	- String HENDELSE_SLUTT = "slutt";
	- String HENDELSE_DATO = "dato";
	+ ArrayList<Time> getTime(int ID)

}

class DBMeeting {
	+ int addMeeting(Meeting meeting)
	+ int addMeeting(ArrayList<User> participants, MeetingRoom room, int ID)
	+ Meeting getMeeting(int meetingID)

	+ void editMeeting(Meeting meeting)
	+ void cancelMeeting(Meeting meeting)
	+ Meeting makeMeetingObject(ResultSet rs)
}
class DBMeetingRoom {
	+ void addMeetingRoom(MeetingRoom room)
	- void addMeetingRoom(String name, String description, String location) 
	+ void removeMeetingRoom(String name)
	+ ArrayList<Meeting> getMeetingsInRoom(MeetingRoom room)
	+ ArrayList<MeetingRoom> getAllMeetingRooms()
	+ void editMeetingRoom(MeetingRoom room)
	+ MeetingRoom makeMeetingRoomObject(ResultSet rs)
	+ MeetingRoom getMeetingRoom(String roomName) 
}
class DBMessage {
	+ void addMessage(Message message)
	+ void addMessage(Date dateSent, String topic, String content,User recepient,User sender)
	+ void editMessage(Message message)
	+ ArrayList<Message> getInbox(User user)
	+ Message makeMessageObject(ResultSet rs)
}
class DBParticipants {
	+ void addParticipants(ArrayList<User> parts, int meetingID)
	+ ArrayList<Meeting> getUsersMeetings(User user)
	+ ArrayList<User> getParticipants(int meetingID, String answer)
}
class DBUser {
	+ void addUser(User user)
	+ void addUser(String userName, String name, String password)
	+ void removeUser(String brukerNavn)
	+ void editUser(User update)
	+ User getUser(String userName)
	+ ArrayList<User> getAllUsers() 
	+ User makeUserObject(ResultSet rs)
}
class Interact {
	+ ResultSet execute(String sql) 
	+ void executeUpdate(String sql)
}

@enduml
 */

