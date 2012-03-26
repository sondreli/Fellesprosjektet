package database;
/**
 * @version 1.0
 * Dette programmet tar seg av tilkoblingen til databaseserveren.
 * Det er mulig å benytte flere drivere.
 * 
 * Kall dbConnect() fra DatabaseInteract-klassen for å opprette en tilkobling. 0 returneres hvis ok.
 * getConnection() for å få tilkoblingen.
 * @author HulkingUnicorn
 */


import java.sql.*;

public class DatabaseConnect {
	private static String driver = "org.gjt.mm.mysql.Driver";
	private static String url = "jdbc:mysql://mysql.stud.ntnu.no/datdanny_SU47";
	private static String user = "datdanny_sql47";
	private static String password = "gruppe47";
	private static Connection connection;
	
	/**
	 * Loads driver and connects to database.
	 * 
	 * @return Returns a finish code as Integer.
	 */
	protected static int dbConnect() {
		if (!loadDriver()) {
			System.out.println("Can't load driver " + driver);
			return 1;
		}
		if (!initConnection()) {
			System.out.println("Can't connect to database " + url);
			return 2;
		}
		return 0;
	}
	
	/**
	 * Loads the database driver.
	 * 
	 * @return Returns a boolean for whether the driver is loaded.
	 */
	private static boolean loadDriver() {
		try {
			Class.forName("org.gjt.mm.mysql.Driver").newInstance();
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	/**
	 * Connects to the database.
	 * 
	 * @return Returns boolean for whether the connection could be initialized.
	 */
	private static boolean initConnection() {
		try {
			connection = java.sql.DriverManager.getConnection(url,user,password);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * @return Returns a String with the current driver name.
	 */
	public static String getDriver() {
		return driver;
	}
	
	/**
	 * @param driver a driver for the connection to the database.
	 */
	public static void setDriver(String driver) {
		DatabaseConnect.driver = driver;
	}
	
	/**
	 * @return Returns the database URL as a String.
	 */
	public static String getUrl() {
		return url;
	}

	/**
	 * @param url a database url for the connection.
	 */
	public static void setUrl(String url) {
		DatabaseConnect.url = url;
	}
	
	/**
	 * @return Returns username as a String.
	 */
	public static String getUser() {
		return user;
	}

	/**
	 * @param user a username for the user of the database.
	 */
	public static void setUser(String user) {
		DatabaseConnect.user = user;
	}
	
	/**
	 * Changes the stored password to a new one.
	 * 
	 * @return Returns boolean whether the password was successfully changed.
	 * @param oldPassword a old password. 
	 * @param newPassword1 a new password for the database.
	 * @param newPassword2 a comfirmation of the new password for the database.
	 */
	public static boolean changePassword(String oldPassword, String newPassword1, String newPassword2 ) {
		if (DatabaseConnect.password.equals(oldPassword) && newPassword1.equals(newPassword2)){
			DatabaseConnect.password = newPassword1;
			return true;
		}
		return false;
	}
	
	/**
	 * @return Returns the current connection to the database.
	 */
	protected static Connection getConnection() {
		return connection;
	}
}
