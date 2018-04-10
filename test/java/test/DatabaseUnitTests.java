package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.FileInputStream;
import java.util.Properties;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.mongodb.DBObject;
import com.mongodb.MongoClient;

import domain.Planet;
import domain.PlanetAdaptor;
import domain.PlanetFactory;
import persistence.DatabaseHelper;
import persistence.PlanetDOHelper;

public class DatabaseUnitTests {
	
	private static MongoClient mongoClient;
	private PlanetFactory factory;
	private PlanetDOHelper planetDbTest;
	private Properties properties;
	private String connectionUrl;
	private String dbName;
	private String collectionName;
	
	private Boolean loadTestProperties()
	{
		properties = new Properties();
		try {
			properties.load(new FileInputStream("config.properties"));
			
			connectionUrl = properties.getProperty("connectionUrl");
			dbName = properties.getProperty("testDatabaseName");
			collectionName = properties.getProperty("testCollectionName");
			
			return true;
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	@Before
	public void setUp()
	{
		if(loadTestProperties())
		{
			mongoClient = DatabaseHelper.getInstance(connectionUrl, dbName).getConnection();
			planetDbTest = new PlanetDOHelper(mongoClient.getDB(dbName),  collectionName);
			factory = new PlanetFactory();
		}		
	}
	
	@After
	public void close()
	{
		planetDbTest.getCollection().drop();
		planetDbTest.getDatabase().dropDatabase();
	}	
	
	@Test
	public void connectionUrlTest()
	{
		assertNotNull(DatabaseHelper.getInstance(connectionUrl, dbName).getConnection());	
	}
	
	@Test
	public void convertPlanetToDBObjectTest()
	{
		Planet jakku = factory.createPlanet("10", "jakku", "unknown", "deserts");
		
		DBObject jakkuAsDBObject = PlanetAdaptor.toObject(jakku);
		
		String expectedDBObject = "{ \"_id\" : \"10\", " +
									"\"name\" : \"Jakku\", " +
									"\"lowerCaseName\" : \"jakku\", " +
									"\"climate\" : \"unknown\", " +
									"\"terrain\" : \"deserts\", " +
									"\"appearance\" : 1 }";
		
		assertEquals(expectedDBObject, jakkuAsDBObject.toString());
	}
	
	@Test
	public void insertPlanetTest()
	{		
		Planet kamino = factory.createPlanet("20", "Kamino", "temperate", "ocean");
		// valid insertion
		planetDbTest.insertPlanet(kamino);		
		assertEquals(1, planetDbTest.getCollection().count());
		assertEquals(1,  kamino.getAppearance());
		
		// duplicate insertion
		planetDbTest.insertPlanet(kamino);		
		assertEquals(1, planetDbTest.getCollection().count());
		
		// Invalid insertion
		planetDbTest.insertPlanet(factory.createPlanet("30", "Dummy planet", "dummy climate", "dummy terrain"));		
		assertEquals(1, planetDbTest.getCollection().count());
	}
	
	@Test
	public void findAllPlanetsTest()
	{
		
		Planet jakku = factory.createPlanet("10", "Jakku", "unknown", "deserts");
		Planet kamino = factory.createPlanet("20", "Kamino", "temperate", "ocean");
		
		planetDbTest.insertPlanet(jakku);
		planetDbTest.insertPlanet(kamino);
		
		assertEquals(2, planetDbTest.getCollection().count());
		
		assertEquals("Jakku", planetDbTest.getAllPlanets().get(0).getName());
	}
	
	@Test
	public void findByIdTest()
	{
		Planet jakku = factory.createPlanet("10", "Jakku", "unknown", "deserts");
		
		planetDbTest.insertPlanet(jakku);
		
		// Test insertion
		assertEquals(1, planetDbTest.getCollection().count());
		
		Planet retrievedPlanet = planetDbTest.findPlanetById("10");
		
		// Test the search by a valid id
		assertNotNull(retrievedPlanet);
		assertEquals("Jakku", retrievedPlanet.getName());
		
		// Test a search by a invalid id
		assertNull(planetDbTest.findPlanetById("25"));
	}
	
	@Test
	public void findByNameTest()
	{
		// Insert a planet
		Planet kamino = factory.createPlanet("20", "Kamino", "temperate", "ocean");		
		planetDbTest.insertPlanet(kamino);
		
		assertEquals(1, planetDbTest.getCollection().count());
		
		// Find a planet by the correct name
		Planet retrievedPlanet = planetDbTest.findPlanetByName("Kamino");		
		assertNotNull(retrievedPlanet);
		assertEquals("Kamino", retrievedPlanet.getName());
		
		// Try find a planet with wrong name
		assertNull(planetDbTest.findPlanetByName("dummy planet"));
		
		// Find a planet by the lower case version of correct name
		retrievedPlanet = planetDbTest.findPlanetByName("kamino");
		assertNotNull(retrievedPlanet);
		assertEquals("Kamino", retrievedPlanet.getName());
		
		// Find a planet by the upper case version of correct name
		retrievedPlanet = planetDbTest.findPlanetByName("KAMINo");
		assertNotNull(retrievedPlanet);
		assertEquals("Kamino", retrievedPlanet.getName());
		
	}
	
	@Test
	public void removePlanerTest()
	{
		Planet kamino = factory.createPlanet("15", "Kamino", "temperate", "ocean");
		planetDbTest.insertPlanet(kamino);
		
		Planet jakku = factory.createPlanet("20", "Jakku", "unknown", "deserts");
		planetDbTest.insertPlanet(jakku);
				
		assertEquals(2, planetDbTest.getCollection().count());
		
		planetDbTest.deletePlanet("15");
		
		assertEquals(1, planetDbTest.getCollection().count());
		
		planetDbTest.deletePlanet("150");
		
		assertEquals(1, planetDbTest.getCollection().count());
	}
}
