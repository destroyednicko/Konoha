package saiint.api.apis.mongo;

import java.util.ArrayList;
import java.util.HashMap;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.MongoClient;

@SuppressWarnings({"deprecation"})
public class MongoDB {

	public static MongoClient connection = new MongoClient("localhost", 27017);
	public static ArrayList<MongoDB> mongos = new ArrayList<>();
	
	private String databaseName;
	private HashMap<String, Collection> collections = new HashMap<>();
	private DB database;
	public MongoDB(String databaseName) {
		this.databaseName=databaseName;
		this.database = connection.getDB(databaseName);
		if(!mongos.contains(this)) mongos.add(this);
	}
	public String getDatabaseName() {return databaseName;}
	public Collection getCollection(String name) {
		Collection col;
		if(!database.collectionExists(name)) col = new Collection(database.createCollection(name, new BasicDBObject()));
		col = new Collection(database.getCollection(name));
		if(collections.containsKey(name)) collections.remove(name);
		collections.put(name, col);
		return col;
	}
}
