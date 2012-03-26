package Database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Interact {


	public static ResultSet execute(String sql) {

		Connection con = null;
		Statement st = null;
		DatabaseConnect.dbConnect();
		con = DatabaseConnect.getConnection();
		ResultSet rs = null;

		try {
			st = con.createStatement();
			rs = st.executeQuery(sql);


		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
	/**
	 * only for sql statements UPDATE, INSERT, DELETE.
	 * @param sql
	 * @throws SQLException
	 */
	public static void executeUpdate(String sql){

		Connection con = null;
		Statement st = null;
		DatabaseConnect.dbConnect();
		con = DatabaseConnect.getConnection();

		try {
			st = con.createStatement();
			st.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
