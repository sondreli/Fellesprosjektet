package Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


import model.User;

public class DBUser {

	public static void addUser(User user){
		addUser(user.getUserName(), user.getName(), user.getPassword());
	}
	public static void addUser(String userName, String name, String password){
		
		String query = "INSERT INTO bruker " +
		"(brukerNavn, navn, passord) VALUES ('"
		+ userName + "','" + name + "','" + password + "')";
		Interact.executeUpdate(query);
		
	}
	
	public static void removeUser(String brukerNavn){
		String query = "DELETE FROM bruker WHERE brukerNavn = '" + brukerNavn + "'";
		Interact.executeUpdate(query);
	}
	
	public static void editUser(User update){
		
		StringBuilder query = new StringBuilder();
		
		query.append("UPDATE bruker SET brukerNavn = '" + update.getUserName() + "',");
		query.append("navn = '" + update.getName() + "',");
		query.append("passord = '" + update.getPassword() + "'");
		query.append("WHERE brukerNavn = '" + update.getUserName() + "'");
		
		Interact.executeUpdate(query.toString());
		
	}
	public static User getUser(String userName){
		String query = "SELECT * FROM bruker WHERE brukerNavn = '" + userName + "'";
		ResultSet rs = Interact.execute(query);
		User user = null;
		
		try {
			if(rs.next())
				user = makeUserObject(rs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

		return user;

	}

	public static ArrayList<User> getAllUsers() {
		ArrayList<User> users = new ArrayList<User>();
		User user;

		String query = "SELECT * FROM bruker";
		try {
			ResultSet rs = Interact.execute(query);

			while(rs.next()){
				//Construct user object ResultSet
				user = makeUserObject(rs);

				//Add user to users list
				users.add(user);
			}
		} catch (Exception e) {
			// TODO: handle exception
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


