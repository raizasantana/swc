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
			if (planet != null)
			{
				queryObj = PlanetAdaptor.toObject(planet);
				WriteResult insertedObj = this.planetCollection.update(queryObj, queryObj, true, false);
				return insertedObj == null ? false : true;
			}
			return false;
		}
		catch (IllegalArgumentException ex)
		{
			System.err.println("ERROR | Error while insert planet, invalid name.");
			return false;
		}				
	}
	
	public ArrayList<Planet> getAllPlanets()
	{
		try
		{
			ArrayList<Planet> result = new ArrayList<Planet>();		
			cursor = planetCollection.find();

			while (cursor.hasNext())
			{
				result.add(PlanetAdaptor.toPlanet(cursor.next()));
			}
			
			return result;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
		
	}
	
	public Planet findPlanetByName(String name)
	{
		try
		{
			queryObj = new BasicDBObject("lowerCaseName", name.toLowerCase());
			cursor = planetCollection.find(queryObj);
			
			return cursor.hasNext() ? PlanetAdaptor.toPlanet(cursor.next()) : null;
		}
		catch (NullPointerException ex)
		{
			return null;
		}
	}
	
	public Planet findPlanetById(String id)
	{
		try
		{
			queryObj = new BasicDBObject("_id", new ObjectId(id));
		}
		catch (IllegalArgumentException ex)
		{
			queryObj = new BasicDBObject("_id", id);
		}
				

		cursor = planetCollection.find(queryObj);
		return cursor.hasNext() ? PlanetAdaptor.toPlanet(cursor.next()) : null;
	}
	
	public Boolean deletePlanet(String id)
	{
		DBObject deletedPlanet;
		try
		{
			queryObj = new BasicDBObject("_id", new ObjectId(id));
		}
		catch (Exception ex)
		{
			if (ex instanceof IllegalArgumentException)
			{
				queryObj = new BasicDBObject("_id", id);				
			}
			else
			{
				return false;
			}						
		}
		
		deletedPlanet = planetCollection.findAndRemove(queryObj);
		return deletedPlanet == null ? false : true;
		
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
