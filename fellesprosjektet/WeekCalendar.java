package fellesprosjektet;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
//import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import org.omg.CORBA.Bounds;

import com.sun.org.apache.xerces.internal.impl.dv.xs.DayDV;

import model.Appointment;
import model.Day;
import model.EventList;
import model.MeetTime;
import model.Meeting;
import model.Time;


public class WeekCalendar extends JPanel implements PropertyChangeListener {
	private JLabel lblWeek;
	private JButton btnPrev, btnNext;
	private GridBagConstraints constrnts;
	private JTable tblCalendar;
	private DefaultTableModel mtblCalendar; //Table model
	private JScrollPane stblCalendar; //The scrollpane
	private JPanel pnlCalendar;
	private JLayeredPane lpane;
	private model.MyDate data;
	private Rectangle bounds;
	private EventList evlist;
	private ArrayList<Sticker> myStickers;
	
	public WeekCalendar(JLayeredPane lpane, model.MyDate data, EventList evlist, int xpos, int ypos) {
		
		//Create controls
		lblWeek= new JLabel ("Uke");
		btnPrev = new JButton ("<<");
		btnNext = new JButton (">>");
		constrnts = new GridBagConstraints();
		mtblCalendar = new DefaultTableModel(){public boolean isCellEditable(int rowIndex, int mColIndex){return true;}};
		tblCalendar = new JTable(mtblCalendar);
		stblCalendar = new JScrollPane(tblCalendar);
		pnlCalendar = new JPanel(null);
		pnlCalendar.setOpaque(true);
		this.data = data;
		bounds = new Rectangle(xpos, ypos, 600, 600);
		this.lpane = lpane; 
		this.evlist = evlist;
		myStickers = new ArrayList<Sticker>();
		
		this.evlist.addPropertyChangeListener(this);
		this.data.addPropertyChangeListener(this);
		
		//Register action listeners
		btnPrev.addActionListener(new btnPrev_Action());
		btnNext.addActionListener(new btnNext_Action());
		
		//Set border
		this.setBorder(BorderFactory.createTitledBorder("Calendar"));
		setLayout(new GridBagLayout());
		this.setBounds(bounds);
		
		
		constrnts.weightx = 0;
		constrnts.anchor = GridBagConstraints.NORTHWEST;
		constrnts.gridx = 0;
		constrnts.gridy = 0;
		add(btnPrev, constrnts);
		
		constrnts.weightx = 5;
		constrnts.anchor = GridBagConstraints.CENTER;
		constrnts.gridx = 1;
		constrnts.gridy = 0;
		add(lblWeek, constrnts);
		
		constrnts.weightx = 0;
		constrnts.anchor = GridBagConstraints.NORTHEAST;
		constrnts.gridx = 2;
		constrnts.gridy = 0;
		add(btnNext, constrnts);
		
		constrnts.weightx = 50;
		constrnts.weighty = 5;
		constrnts.fill = GridBagConstraints.BOTH;
		constrnts.gridwidth = 3;
		constrnts.gridx = 0;
		constrnts.gridy = 2;
		add(stblCalendar, constrnts);
		
//		this.setSize(1000, 400);
		initCalendar();

	}
	
	private void initCalendar() {
		//Add headers
		String[] headers = {"   ", "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"}; //All headers
		for (int i=0; i<8; i++){
			mtblCalendar.addColumn(headers[i]);
		}
		
		tblCalendar.getParent().setBackground(tblCalendar.getBackground()); //Set background

		//No resize/reorder
		tblCalendar.getTableHeader().setResizingAllowed(false);
		tblCalendar.getTableHeader().setReorderingAllowed(false);

		//Single cell selection
		tblCalendar.setColumnSelectionAllowed(true);
		tblCalendar.setRowSelectionAllowed(true);
		tblCalendar.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		//Set row/column count
		tblCalendar.setRowHeight(50);
		mtblCalendar.setColumnCount(8);
		mtblCalendar.setRowCount(9);
		
		//Refresh calendar
		refreshCalendar(data.getRealWeek(), data.getRealYear()); //Refresh calendar
	}
	
	private void refreshCalendar(int week, int year){
		//Variables
		String[] weeks = new String[54];
		for(int i=1; i<=54; i++) {
			Integer str = new Integer(i);
			weeks[i-1] = "Uke " + str.toString();
		}
		int nod, som; //Number Of Days, Start Of Month
			
		//Allow/disallow buttons
		btnPrev.setEnabled(true);
		btnNext.setEnabled(true);
//		if (week == 0 && year <= data.getRealYearlistener()-10){btnPrev.setEnabled(false);} //Too early
//		if (week == 11 && year >= data.getRealYear()+100){btnNext.setEnabled(false);} //Too late
		lblWeek.setText(weeks[week-1]); //Refresh the month label (at the top)
		
		//Clear table
		for (int i=0; i<9; i++){
			for (int j=0; j<8; j++){
				if(j == 0) {
					mtblCalendar.setValueAt(6+2*i + " - " + (8+2*i), i, j);
				}
				else
					mtblCalendar.setValueAt(null, i, j);
			}
		}
		removeAllSticers();
		drawEvents(lpane, evlist);
		addNowTime(lpane);
//		addStickers();
		
//		//Get first day of month and number of days
//		GregorianCalendar cal = new GregorianCalendar(year, month, 1);
//		nod = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
//		som = cal.get(GregorianCalendar.DAY_OF_WEEK);
//		
//		//Draw calendar
//		for (int i=1; i<=nod; i++){
//			int row = new Integer((i+som-2)/7);
//			int column  =  (i+som-2)%7;
//			mtblCalendar.setValueAt(i, row, column);
//		}

		//Apply renderers
		tblCalendar.setDefaultRenderer(tblCalendar.getColumnClass(0), new tblCalendarRenderer());
	}

	private class tblCalendarRenderer extends DefaultTableCellRenderer{
		public Component getTableCellRendererComponent (JTable table, Object value, boolean selected, boolean focused, int row, int column){
			super.getTableCellRendererComponent(table, value, selected, focused, row, column);
			if (column == 1 || column == 7){ //Week-end
				setBackground(new Color(255, 220, 220));
			}
			else if(column == 0) {
				setBackground(new Color(235, 235, 235));
			}
			else{ //Week
				setBackground(new Color(255, 255, 255));
			}
			if (value != null){
//				if (Integer.parseInt(value.toString()) == data.getRealDay() &&
//					data.getCurrentMonth() == data.getRealMonth() &&
//					data.getCurrentYear() == data.getRealYear()) { //Today
//					setBackground(new Color(220, 220, 255));
//				}
			}
			setBorder(null);
			setForeground(Color.black);
			return this;  
		}
	}
	
	private class btnPrev_Action implements ActionListener{
		public void actionPerformed (ActionEvent e){
			
			controller.AlterDate.decreaseWeek(data);
		}
	}
	private class btnNext_Action implements ActionListener{
		public void actionPerformed (ActionEvent e){
			
			controller.AlterDate.increaseWeek(data);
		}
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		refreshCalendar(data.getCurrentWeek(), data.getCurrentYear());
		pnlCalendar.repaint();
	}
	
	public Rectangle getEventBounds(MeetTime time) {
		Rectangle sbounds = new Rectangle();
//		MeetTime time = new MeetTime(new Time(10, 0), new Time(11, 30), Day.Tuesday, 12, 2012);
		
//		if(time.getWeek() == data.getCurrentWeek()) {
		int xpos, ypos, xsize, ysize;
		int colwidth = bounds.width/8 -1;
		
		xsize = colwidth +1;
		ysize = (int)((time.getEnd().getHour() - time.getStart().getHour())/2.0*tblCalendar.getRowHeight()) + 
				(int)((time.getEnd().getMinute() - time.getStart().getMinute())/60.0/2.0*tblCalendar.getRowHeight()) + 1;
		
		xpos = (int)bounds.getX() + (time.getDay().getValue()+1)*colwidth + stblCalendar.getX() - time.getDay().getValue();
		ypos = stblCalendar.getY() + btnPrev.getHeight() + 
				tblCalendar.getTableHeader().getHeight() +
				(int)((time.getStart().getHour()-6)/2.0*tblCalendar.getRowHeight()) + // Legger til pos for timene
				(int)(time.getStart().getMinute()/60.0/2.0*tblCalendar.getRowHeight()) + // Legger til pos for minuttene
				/*ysize - 2*/5;

//		System.out.println("posx: " + xpos + " ypos: " + ypos + " colwidth: " + colwidth);
//		System.out.println("stbl:"+stblCalendar.getY()+
//							" btn:"+btnPrev.getHeight()+
//							" tblHeader:"+tblCalendar.getTableHeader().getHeight()+
//							" time:"+(int)((time.getStart().getHour()-6)/2.0*tblCalendar.getRowHeight())+
//							" minutes:"+(int)(time.getStart().getMinute()/60.0/2.0*tblCalendar.getRowHeight()));
		System.out.println("stblX:"+stblCalendar.getX()+" sboundsX:"+bounds.getX());
		sbounds.setBounds(xpos, ypos, xsize, ysize);
		
		return sbounds;
	
	}
	
	private void addNowTime(JLayeredPane lpane) {
		Calendar cal = Calendar.getInstance();
		Date time = cal.getTime();
		Time start = new Time(time.getHours(), time.getMinutes());
		Time end = new Time(start.getHour(), start.getMinute()+10);
		MeetTime mtime = new MeetTime(start, end, Day.getDay(time.getDay()), data.getCurrentMonth(), data.getCurrentYear());
		
//		System.out.println("day:"+time.getDay()+" hour:"+time.getHours()+" min:"+time.getMinutes());
		
		Sticker temp = new Sticker(getEventBounds(mtime), "   ");
		myStickers.add(temp);
		lpane.add(temp, new Integer(3));
	}
	
	public void drawEvents(JLayeredPane lpane, EventList meetings) {
		for (Object meeting : meetings) {
			if(((Appointment)meeting).getMeetingTime().getWeek() == data.getCurrentWeek()) {
				Rectangle sbounds = getEventBounds(((Appointment)meeting).getMeetingTime());
				Sticker temp = new Sticker(sbounds, ((Appointment)meeting).getDescription());
				myStickers.add(temp);
				lpane.add(temp, new Integer(3));
				System.out.println("xpos:"+sbounds.getX()+" ypos:"+sbounds.getY()+
									" width:"+sbounds.getWidth()+" heigth:"+sbounds.getHeight());
			}
		}
	}
	
	public void addAppointment(JLayeredPane lpane, ArrayList<Appointment> appointments) {
		for (Appointment appointment : appointments) {
			if(appointment.getMeetingTime().getWeek() == data.getCurrentWeek()) {
				Rectangle sbounds = getEventBounds(appointment.getMeetingTime());
				lpane.add(new Sticker(sbounds, appointment.getDescription()));
			}
		}
	}
	
	private void removeAllSticers() {
		for (Sticker sticker : myStickers) {
			lpane.remove(sticker);
		}
	}

}
