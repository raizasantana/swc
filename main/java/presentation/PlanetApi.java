package presentation;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.delete;

import java.util.ArrayList;

import com.mongodb.DB;
import domain.Planet;
import domain.PlanetAdaptor;
import domain.PlanetFactory;
import domain.ReturnMessages;
import persistence.PlanetDOHelper;

public class PlanetApi {
	
	PlanetDOHelper planetDb;
	PlanetFactory factory;
	
	public PlanetApi(DB database, String collectionName)
	{
		initRoutes();
		initDatabase(database, collectionName);
				
		// Create two planets
		factory = new PlanetFactory();
		PlanetFactory factory = new PlanetFactory();
		planetDb.insertPlanet(factory.createPlanet("1", "Kamino", "temperate", "ocean"));	
		planetDb.insertPlanet(factory.createPlanet("2", "jakku", "unknown", "deserts"));
	}
	
	private void initDatabase(DB database, String collectionName)
	{
		planetDb = new PlanetDOHelper(database, collectionName);
	}
	
	private void initRoutes()
	{
		// List all planets
		get("/planets", (req, res) -> 
		{
			res.type("application/json");
			ArrayList<Planet> planets = planetDb.getAllPlanets();
			
			// Database empty
			if (planets.size() == 0)
			{
				return ReturnMessages.NO_ELEMENTS;
			}
			
			return PlanetAdaptor.toObjectList(planets);
		});
		
		// Search a planet by id
		get("/planets/id/:id", (req, res) ->
		{
			res.type("application/json");
			String id = req.params(":id");
			
			if (id == null)
			{
				return ReturnMessages.INVALID_ID;
			}
			
			Planet planet = planetDb.findPlanetById(id);
			
			if (planet == null)
			{
				return ReturnMessages.NOT_FOUND;
			}
			
			return PlanetAdaptor.toObject(planet);
		});
		
		// Search a planet by name
		get("/planets/name/:name", (req, res) ->
		{
			res.type("application/json");
			String name = req.params(":name");
			
			if (name == null)
			{
				return ReturnMessages.INVALID_NAME;
			}
			
			Planet planet = planetDb.findPlanetByName(name);
			
			if (planet == null)
			{
				return ReturnMessages.NOT_FOUND;
			}
			
			return PlanetAdaptor.toObject(planet);
		});	
		

		// Create a planet
		post("planets/create", (req, res) -> {
			String name = req.queryParams("name");
			String climate = req.queryParams("climate");
			String terrain = req.queryParams("terrain");
			
			if (name == null || climate == null || terrain == null)
			{
				return ReturnMessages.NULL_PARAM;
			}			
			
			if(planetDb.insertPlanet(factory.createPlanet(null, name, climate, terrain)))
			{
				return PlanetAdaptor.toObject(planetDb.findPlanetByName(name));
			}
			else
			{
				return ReturnMessages.ERROR_CREATE;
			}
		});
		
		// Delete a planet
		delete("planets/delete", (req, res) -> {
			String id = req.queryParams("id");
			
			if (id == null)
			{
				return ReturnMessages.INVALID_ID;
			}
			
			if (planetDb.deletePlanet(id))
			{
				return ReturnMessages.SUCCESS_DELETE;
			}
			else
			{
				return ReturnMessages.ERROR_DELETE;
			}
		});
	}
}
