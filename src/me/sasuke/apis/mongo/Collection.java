package me.sasuke.apis.mongo;

import java.util.ArrayList;
import java.util.List;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;

public class Collection {

	private DBCollection collection;
	
	private BasicDBObject dbObject;
	private BasicDBObject dbObjectU;
	public Collection(DBCollection collection) {
		this.collection=collection;
		this.dbObject=new BasicDBObject();
		this.dbObjectU=new BasicDBObject();
	}
	
	public Result get(String key, Object val) {
		return new Result(collection.find(new BasicDBObject().append(key, val)).next());
	}
	public List<Result> getAll(String key, Object val) {
		DBCursor obj = collection.find(new BasicDBObject().append(key, val));
		List<Result> r = new ArrayList<>();
		while(obj.hasNext()) r.add(new Result(obj.next())); 
		return r;
	}
	public List<Object> getAllItensFromTable(String key) {
		DBCursor obj = collection.find();
		List<Object> r = new ArrayList<>();
		while(obj.hasNext()) r.add(obj.next().get(key)); 
		return r;
	}
	public List<Object> getAllItensFromTable() {
		DBCursor obj = collection.find();
		List<Object> r = new ArrayList<>();
		while(obj.hasNext()) r.add(obj.next().get("_id")); 
		return r;
	}
	
	public Collection delete(String val) {
		collection.remove(new BasicDBObject("_id", val));
		return this;
	}
	public Collection delete(String key, String val) {
		collection.remove(new BasicDBObject(key, val));
		return this;
	}
	
	public Collection set(String key, Object val) {
		dbObject.append(key, val);
		return this;
	}
	public Collection duplicate(String key, Object val) {
		dbObjectU.append(key, val);
		return this;
	}
	public boolean contains(String val) {
		DBCursor iterable = collection.find(new BasicDBObject("_id", val));
		return iterable.hasNext();
	}
	public boolean contains(String key, Object val) {
		DBCursor iterable = collection.find(new BasicDBObject(key, val));
		return iterable.hasNext();
	}
	@SuppressWarnings("deprecation")
	public Collection save() {
		if(contains(dbObject.getString("_id"))) {
			for (String str: dbObject.keySet()) if(!dbObjectU.containsKey(str)) dbObjectU.append(str, get("_id", dbObject.get("_id")).string(str).toString());
			BasicDBObject searchQuery = new BasicDBObject().append("_id", dbObject.get("_id"));
			collection.update(searchQuery, dbObjectU);
		}
		else {
			collection.insert(dbObject);
		}
		this.dbObject=new BasicDBObject();
		this.dbObjectU=new BasicDBObject();
		return this;
	}
}
