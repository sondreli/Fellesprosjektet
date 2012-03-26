<<<<<<< HEAD

=======
>>>>>>> 25f8ea6e157b663c4661024f06dbcfa79e876fdd
package database;

import java.sql.SQLException;
import java.sql.Time;

import java.sql.ResultSet;
import java.util.ArrayList;


public class DBEvent {
	private final static String HENDELSE = "hendelse";
	private final static String HENDELSE_ID = "hendelseId";
	private final static String HENDELSE_START = "start";
	private final static String HENDELSE_SLUTT = "slutt";
	private final static String HENDELSE_DATO = "dato";
	


	public static ArrayList<Time> getTime(int ID){
		ArrayList<Time> tid = new ArrayList<Time>();
		java.sql.Time tid1, tid2;
		try {
			ResultSet rs = Interact.execute("SELECT * FROM "+HENDELSE+" WHERE "+HENDELSE_ID +" = "+ID);
			while (rs.next()){
			tid1 = rs.getTime(HENDELSE_START);
			tid.add(tid1);
			tid2 = rs.getTime(HENDELSE_SLUTT);
			tid.add(tid2);
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return tid;
	}

	public static void main (String[]args){
	
			System.out.println(getTime(1));
		}
	}
	


