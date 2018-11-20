package me.sasuke.apis.mongo;

import com.mongodb.DBObject;

public class Result {

	private DBObject dbObject;
	public Result(DBObject dbObject) {
		this.dbObject=dbObject;
	}
	
	public String string(String key) {return dbObject.get(key).toString();}
	public Object get(String key) {return dbObject.get(key);}
	
}
