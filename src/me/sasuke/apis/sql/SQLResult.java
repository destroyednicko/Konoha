package me.sasuke.apis.sql;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLResult {

	private ResultSet set;
	public SQLResult(ResultSet set) {this.set=set;}
	
	public ResultSet set() {return set;}
	public String get(String str) {
		try {
			if(set.getString(str)==null) return null;
			return set.getString(str);
		} catch (SQLException e) {
			return null;
		}
	}
	
	public String get(String str, boolean rNull) {
		try {
			if(set.getString(str)==null) return null;
			return set.getString(str);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}
