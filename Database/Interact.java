package Database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Interact {

	public static void execute(String sql) throws SQLException {
		
		Connection con = null;
		Statement st = null;
		DatabaseConnect.dbConnect();
		con = DatabaseConnect.getConnection();
		
		try {
			st = con.createStatement();
			st.execute(sql);
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
