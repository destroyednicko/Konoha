package me.sasuke.apis.sql;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import me.sasuke.Utils;

public class SQL {

	public static HashMap<String/*database*/, SQL> sqlConnections = new HashMap<>();
	public static Connection con;
	public static SQL getSQL(String database) {
		return sqlConnections.get(database);
	}
	
	private String host, user, pass, database;

	public SQL(String host, String user, String pass, String database) {
		this.host=host;
		this.user=user;
		this.pass=pass;
		this.database=database;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			if(con==null) con = DriverManager.getConnection("jdbc:mysql://"+host+"/"+database, user, pass);
			if(sqlConnections.containsKey(database)) sqlConnections.remove(database);
			sqlConnections.put(database, this);
			Utils.log("SQL connection with " + host + " established");
		} catch (SQLException | ClassNotFoundException e) {
			Utils.error("Error while establishing SQL connection with " + host);
			Utils.error("Error: " + e.getMessage());
		}
	}
	
	public Connection getConnection() {return con;}
	public String getHost() {return host;}
	public String getUser() {return user;}
	public String getPass() {return pass;}
	public String getDatabase() {return database;}
	
	public List<Table> tables() {
		try {
			DatabaseMetaData md = con.getMetaData();
			ResultSet rs = md.getTables(null, null, "%", null);
			List<Table> pre = new ArrayList<>();
			while (rs.next()) pre.add(new Table(this, rs.getString(3)));
			return pre;
		} catch (SQLException e) {
			Utils.error("Error while listing tables in " + host);
			Utils.error("Error: " + e.getMessage());
			return null;
		}
	}
	public Table getTable(String str) {
		return new Table(this, str);
	}
	
	public boolean createTable(String str, String... values) {
		try {
			Statement statement = con.createStatement();
			String value = "(";
			for (String s : values)	value+=s+", ";
			value = value.substring(0, value.length()-2)+")";
			statement.executeUpdate("CREATE TABLE IF NOT EXISTS `"+str+"`"+value);
			Utils.log("Table("+str+") loaded on " + host);
			return true;
		} catch (Exception e) {
			Utils.error("Error while creating table("+str+") on " + host);
			Utils.error("Error: " + e.getMessage());
			return false;
		}
	}
	public ResultSet getFromTable(String table) {
		try {
			Statement statement = con.createStatement();
			ResultSet set = statement.executeQuery("SELECT * FROM `"+table+"`");
			return set;
		} catch (Exception e) {
			Utils.error("Error while retrieving information from table("+table+") on " + host);
			Utils.error("Error: " + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
	public ResultSet getFromTable(String table, String... str) {
		try {
			Statement statement = con.createStatement();
			String value = "";
			for (String s : str) value+=s+" AND ";
			value = value.substring(0, value.length()-5)+"";
			ResultSet set = statement.executeQuery("SELECT * FROM `"+table+"` WHERE "+value);
			set.next();
			return set;
		} catch (Exception e) {
			Utils.error("Error while retrieving information from table("+table+") on " + host);
			Utils.error("Error: " + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
	public ResultSet getFromTable(int i, String table, String... str) {
		try {
			Statement statement = con.createStatement();
			String value = "(";
			for (String s : str) value+=s+" AND ";
			value = value.substring(0, value.length()-5)+")";
			ResultSet set = statement.executeQuery("SELECT "+i+" FROM `"+table+"` WHERE "+value);
			set.next();
			return set;
		} catch (Exception e) {
			Utils.error("Error while retrieving information from table("+table+") on " + host);
			Utils.error("Error: " + e.getMessage());
			return null;
		}
	}
	
}
