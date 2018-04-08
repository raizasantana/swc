package persistence;

import java.util.ArrayList;

import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.Cursor;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;

import domain.Planet;
import domain.PlanetAdaptor;
import domain.PlanetFactory;

public class PlanetDOHelper {

	private DB database;
	private DBCollection planetCollection;
	private Cursor cursor;
	private DBObject queryObj;
	private String collectionName;
	
	public PlanetDOHelper(DB database, String collectionName)
	{
		this.collectionName = collectionName;		
		this.database = database;
		this.planetCollection = this.database.getCollection(this.collectionName);
	}
	
	public Boolean insertPlanet(Planet planet)
	{
		try
		{
			queryObj = PlanetAdaptor.toObject(planet);
			WriteResult insertedObj = this.planetCollection.update(queryObj, queryObj, true, false);
			return insertedObj == null ? false : true;
		}
		catch (IllegalArgumentException ex)
		{
			System.err.println("ERROR | Error while insert planet, invalid name.");
			return false;
		}				
	}
	
	public ArrayList<DBObject> readAllPlanets()
	{
		ArrayList<DBObject> result = new ArrayList<DBObject>();		
		cursor = planetCollection.find();

		while (cursor.hasNext())
		{
			result.add(cursor.next());
		}
		
		return result;
	}
	
	public DBObject findPlanetByName(String name)
	{
		queryObj = new BasicDBObject("name", name);
		cursor = planetCollection.find(queryObj);
		
		return cursor.hasNext() ? cursor.next() : null;
	}
	
	public DBObject findPlanetById(String id)
	{
		queryObj = new BasicDBObject("_id", new ObjectId(id));
		cursor = planetCollection.find(queryObj);
		
		return cursor.hasNext() ? cursor.next() : null;
	}
	
	public Boolean deletePlanet(String id)
	{
		try
		{
			queryObj = new BasicDBObject("_id", new ObjectId(id));
			Planet deletedPlanet = PlanetAdaptor.toPlanet(planetCollection.findAndRemove(queryObj));
			
			return deletedPlanet == null ? false : true;
		}
		catch (NullPointerException ex)
		{
			System.err.println("ERROR | Error while deleting planet, invalid id.");
			return false;
		}
		
	}
	
	public DBCollection getCollection()
	{
		return this.planetCollection;
	}
	
	public DB getDatabase()
	{
		return this.database;
	}
}
