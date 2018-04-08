package presentation;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.delete;

import java.util.ArrayList;

import com.mongodb.DBObject;

import domain.Planet;
import domain.PlanetAdaptor;
import domain.PlanetFactory;
import persistence.DBConfig;
import persistence.DatabaseHelper;
import persistence.PlanetDOHelper;

// TODO Melhorar mensagens de resposta
// TODO Add http status correto
// TODO criar os testes para essa classe
// TODO criar a documentacao com os endpoints

public class PlanetApi {
	
	PlanetDOHelper planetDb;
	PlanetFactory factory;
	
	public PlanetApi()
	{
		initDatabase();
		initRoutes();
		factory = new PlanetFactory();
		
	}
	
	//Database
	private void initDatabase()
	{
		planetDb = new PlanetDOHelper(
				DatabaseHelper.getInstance(
						DBConfig.getConnectionUrl())
				.getDabase(),
				DBConfig.getPlanetsCollectionName());
	}
	
	//Routes
	private void initRoutes()
	{
		// List all planets
		get("/planets", (req, res) -> 
		{
			res.type("application/json");
			PlanetFactory factory = new PlanetFactory();
			Planet kamino = factory.createPlanet("Kamino", "temperate", "ocean");
			Planet jakku = factory.createPlanet("jakku", "unknown", "deserts");
			
			// valid insertion
			planetDb.insertPlanet(kamino);	
			planetDb.insertPlanet(jakku);
			ArrayList<DBObject> planets = planetDb.readAllPlanets();
			
			// Database empty
			if (planets.size() == 0)
			{
				return "Nenhum planeta a ser exibido.";
			}
			
			res.status(200);
			return planets;
		});
		
		// Search a planet by id
		get("/planets/id/:id", (req, res) ->
		{
			res.type("application/json");
			DBObject planet = planetDb.findPlanetById(req.params(":id"));
			if (planet == null)
			{
				return "Id invalido";
			}
			
			res.status(200);
			return planet;
		});
		
		// Search a planet by name
		get("/planets/name/:name", (req, res) ->
		{
			res.type("application/json");
			DBObject planet = planetDb.findPlanetByName(req.params(":name"));
			
			if (planet == null)
			{
				return "Nome invalido";
			}
			
			res.status(200);
			return planet;
		});	
		

		// Create a planet
		post("/create", (req, res) -> {
			
			if(planetDb.insertPlanet(factory.createPlanet(req.queryParams("name"), req.queryParams("climate"), req.queryParams("terrain"))))
			{
				res.status(200);
				return "So alegria";
			}
			else
			{
				res.status(500);
				return "Problema na insercao";
			}
		});
		
		// Create a planet
		delete("/:id", (req, res) -> {
			
			if (planetDb.deletePlanet(req.queryParams(":id")))
			{
				res.status(200);
				return "So alegria!";
			}
			else
			{
				res.status(500);
				return "Problema pra apagar";
			}
		});
	}
}
