package persistence;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

public class DatabaseHelper {
	
	private static DatabaseHelper instance;
	private static MongoClient mongodb;
	private static String dbConnectionString;
	private static String databaseName;
	private static DB database;
	
	private DatabaseHelper(String connectionString, String dbName)
	{
		dbConnectionString = connectionString;
		databaseName = dbName;
	}
	
	public static DatabaseHelper getInstance(String conString, String dbName)
	{
		if (instance == null)
		{
			instance = new DatabaseHelper(conString, dbName);
		}
		
		return instance;
	}
	
	public MongoClient getConnection()
	{
		if (mongodb == null)
		{
			mongodb = new MongoClient(new MongoClientURI(dbConnectionString));
		}
		
		return mongodb;
	}
	
	public DB getDabase()
	{
		if (database == null)
		{
			database = getConnection().getDB(databaseName);
		}
		
		return database;
	}

}
