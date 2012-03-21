package Database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


import model.User;

public class DBUser {

	public static ArrayList<User> getAllUsers(){
		ArrayList<User> users = new ArrayList<User>();
		User user;
		Connection con = DatabaseConnect.getConnection();

		String query = "SELECT * FROM bruker;";

		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(query);

			while(rs.next()){
				//Construct user object ResultSet
				user = makeUserObject(rs);

				//Add user to users list
				users.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return users;
	}
	public static User makeUserObject(ResultSet rs){
		User user = null;

		try {
			String userName = rs.getString("brukerNavn");
			String password = rs.getString("passord");
			String name = rs.getString("navn");

			user = new User(userName, name, password);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return user;
	}
}

