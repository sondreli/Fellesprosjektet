package Database;

import java.sql.*;

public class DBTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Connection con = null;
		Statement st = null;
		DatabaseConnect.dbConnect();
		con = DatabaseConnect.getConnection();
		
		try {
			st = con.createStatement();
			st.execute("INSERT INTO bruker(brukerNavn, navn, mail, passord) VALUES('test', 'testies', 'adrianskogvold@tull.no', '123456')");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			try {
				con.close();
				st.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
	}

}
