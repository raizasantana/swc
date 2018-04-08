package persistence;

import java.net.UnknownHostException;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

public class DatabaseHelper {
	
	private static DatabaseHelper instance;
	private static MongoClient mongodb;
	private static String dbConnectionString;
	private static DB database;
	
	private DatabaseHelper(String dbString)
	{
		dbConnectionString = dbString;
	}
	
	public static DatabaseHelper getInstance(String dbString)
	{
		if (instance == null)
		{
			instance = new DatabaseHelper(dbString);
		}
		
		return instance;
	}
	
	public MongoClient getConnection()
	{
		try 
		{
			if (mongodb == null)
			{
				mongodb = new MongoClient(new MongoClientURI(dbConnectionString));
			}
			
			return mongodb;
		} 
		catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public DB getDabase()
	{
		if (database == null)
		{
			database = getConnection().getDB(DBConfig.getDatabaseName());
		}
		
		return database;
	}

}
