package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Properties;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.mongodb.MongoClient;

import domain.Planet;
import domain.PlanetFactory;
import persistence.DatabaseHelper;
import persistence.PlanetDOHelper;
import presentation.PlanetApi;

public class PlanetApiTests {

	private static MongoClient mongoClient;
	private PlanetFactory factory;
	private PlanetDOHelper planetDbTest;
	private Client client;
	private WebTarget target;
	private String apiMainUrl;
	private PlanetApi api;
	private Properties properties;
	private String connectionUrl;
	private String dbName;
	private String collectionName;

	@Before
	public void setUp() {
		
		if (loadTestProperties())
		{
			// Init DB
			mongoClient = DatabaseHelper.getInstance(connectionUrl, dbName).getConnection();
			planetDbTest = new PlanetDOHelper(mongoClient.getDB(dbName), collectionName);
			
			// Init factory
			factory = new PlanetFactory();
			
			// Insert few planets 
			planetDbTest.getCollection().drop();
			planetDbTest.insertPlanet(factory.createPlanet("100", "Jakku", "unknown", "deserts"));
			planetDbTest.insertPlanet(factory.createPlanet("200", "Kamino", "temperate", "ocean"));
			
			// Init API
			api = new PlanetApi(mongoClient.getDB(dbName), collectionName);

			// Prepare client
			client = ClientBuilder.newBuilder().build();

		}		
	}
	
	private Boolean loadTestProperties()
	{
		properties = new Properties();
		try {
			properties.load(new FileInputStream("config.properties"));
			
			connectionUrl = properties.getProperty("connectionUrl");
			dbName = properties.getProperty("testDatabaseName");
			collectionName = properties.getProperty("testCollectionName");
			apiMainUrl = properties.getProperty("apiMainUrl");
			
			return true;
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	@After
	public void close() {
		planetDbTest.getCollection().drop();
		planetDbTest.getDatabase().dropDatabase();
	}

	@Test
	public void getAllPlanetsViaApiTest() 
	{		
		target = client.target(apiMainUrl);
		ArrayList<Planet> response = target.request(MediaType.APPLICATION_JSON)
				.get(new GenericType<ArrayList<Planet>>() {});

		assertEquals(4, response.size());
	}

	@Test
	public void getPlanetByNameViaApiTest()
	{
		 String getJakkuByNameUrl = apiMainUrl + "/name/Kamino";
		 target = client.target(getJakkuByNameUrl);
		 
		 Planet response = target.request(MediaType.APPLICATION_JSON).get(Planet.class);
		 
		 assertNotNull(response);
		 assertEquals("Kamino", response.getName());
		 
		 getJakkuByNameUrl = apiMainUrl + "/name/kamino"; 
		 target = client.target(getJakkuByNameUrl);
		 
		 response = target.request(MediaType.APPLICATION_JSON).get(Planet.class);
		 
		 assertNotNull(response);
		 assertEquals("Kamino", response.getName());
		 
		 // Invalid name
		 getJakkuByNameUrl = apiMainUrl + "/name/dummy"; 
		 target = client.target(getJakkuByNameUrl);

		 response = target.request(MediaType.APPLICATION_JSON).get(Planet.class);
		 
		 assertNull(response.getName());		 
	}
	
	@Test
	public void getPlanetByIdViaApiTest()
	{
		 String getJakkuByNameUrl = apiMainUrl + "/id/100";
		 target = client.target(getJakkuByNameUrl);
		 
		 Planet response = target.request(MediaType.APPLICATION_JSON).get(Planet.class);
		 
		 assertNotNull(response);
		 assertEquals("Jakku", response.getName());
		 
		 getJakkuByNameUrl = apiMainUrl + "/id/200"; 
		 target = client.target(getJakkuByNameUrl);
		 
		 response = target.request(MediaType.APPLICATION_JSON).get(Planet.class);
		 
		 assertNotNull(response);
		 assertEquals("Kamino", response.getName());
		 
		// Invalid name
		 getJakkuByNameUrl = apiMainUrl + "/id/dummy"; 
		 target = client.target(getJakkuByNameUrl);

		 response = target.request(MediaType.APPLICATION_JSON).get(Planet.class);
		 
		 assertNull(response.getId());	
	}
	
	@Test
	public void addPlanetViaApiTest()
	{		
		target = client.target(apiMainUrl + "/create");
	    MultivaluedMap<String, String> formData = new MultivaluedHashMap<String, String>();
	    formData.add("name", "Champala");
	    formData.add("climate", "temperate");
	    formData.add("terrain", "deseroceans,rainforests,plateauts");
		    
	    target.request().post(Entity.form(formData));		
		
	    // 5 because I add +2 planets when I start the Api
		assertEquals(5, planetDbTest.getCollection().count());
		
	}
	
	@Test
	public void deletePlanetViaApiTest()
	{		
		target = client.target(apiMainUrl + "/delete").queryParam("id", "100");
	    target.request().delete();
		
	    // 3 because I add +2 planets when I start the Api
		assertEquals(3, planetDbTest.getCollection().count());
		
	}

}
