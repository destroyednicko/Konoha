package me.sasuke.apis.sql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import me.sasuke.Utils;

public class Table {

	private SQL sql;
	private String table;
	private HashMap<String, String> toInsert = new HashMap<>();
	private HashMap<String, String> onDuplicate = new HashMap<>();
	public Table(SQL sql, String table) {
		this.sql=sql;
		this.table=table;
	}

	public boolean update() {
		try {
			Statement statement = this.sql.getConnection().createStatement();
			statement.executeUpdate("INSERT INTO `"+table+"` " + getQuery());
			return true;
		} catch (Exception e) {
			Utils.error("Error while updating table("+table+") on " + this.sql.getHost());
			Utils.error("Error: " + e.getMessage());
			return false;
		}
	}
	
	public boolean update(String sql) {
		try {
			Statement statement = this.sql.getConnection().createStatement();
			statement.executeUpdate(sql);
			return true;
		} catch (Exception e) {
			Utils.error("Error while updating table("+table+") on " + this.sql.getHost());
			Utils.error("Error: " + e.getMessage());
			return false;
		}
	}
	public Table delete(String key, String value) {
		update("DELETE FROM `"+table+"` WHERE `"+key+"`='"+value+"'");
		return this;
	}
	
	public List<String> itensInTable(String key) {
		List<String> list = new ArrayList<>();
		ResultSet set = sql.getFromTable(table);
		try {
			while (set.next()) {
				list.add(set.getString(key));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	public SQLResult where(String key, String value) {
		return new SQLResult(sql.getFromTable(table, "`"+key+"`='"+value+"'"));
	}
	
	public Table set(String key, String value) {
		toInsert.put(key, value);
		return this;
	}
	public Table duplicate(String key, String value) {
		onDuplicate.put(key, value);
		return this;
	}
	public String getQuery() {
		String sq = "(";
		for (String k : toInsert.keySet()) sq+="`"+k+"`, ";
		sq=sq.substring(0, sq.length()-2)+") VALUES (";
		for (String v : toInsert.values()) sq+="'"+v+"', ";
		sq=sq.substring(0, sq.length()-2)+")";
		if(!onDuplicate.isEmpty()) {
			sq+=" ON DUPLICATE KEY UPDATE ";
			for (String k : onDuplicate.keySet()) sq+="`"+k+"`='"+onDuplicate.get(k)+"', ";
			sq=sq.substring(0, sq.length()-2);
		}
		toInsert = new HashMap<>();
		onDuplicate = new HashMap<>();
		return sq;
	}
}
