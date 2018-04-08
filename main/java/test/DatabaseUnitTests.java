package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

import domain.Planet;
import domain.PlanetAdaptor;
import domain.PlanetFactory;
import persistence.DBConfig;
import persistence.DatabaseHelper;
import persistence.PlanetDOHelper;

public class DatabaseUnitTests {
	
	private static MongoClient mongoClient;
	private PlanetFactory factory;
	private PlanetDOHelper planetDbTest;
	
	@Before
	public void setUp()
	{
		mongoClient = DatabaseHelper.getInstance(DBConfig.getConnectionUrl()).getConnection();
		planetDbTest = new PlanetDOHelper(mongoClient.getDB("TestDatabase"),  "planetsTestColletion");
		factory = new PlanetFactory();
	}
	
	@After
	public void close()
	{
		planetDbTest.getCollection().drop();
		planetDbTest.getDatabase().dropDatabase();
	}
	
	@AfterClass
	public static void closeConnection()
	{
		mongoClient.close();
	}
	
	@Test
	public void connectionUrlTest()
	{
		assertNotNull(DatabaseHelper.getInstance("mongodb://localhost:27017").getConnection());	
	}
	
	@Test
	public void convertPlanetToDBObjectTest()
	{
		Planet jakku = factory.createPlanet("jakku", "unknown", "deserts");
		
		DBObject jakkuAsDBObject = PlanetAdaptor.toObject(jakku);
		
		String expectedDBObject = "{ " +
									"\"name\" : \"jakku\" , " +
									"\"climate\" : \"unknown\" , " +
									"\"terrain\" : \"deserts\" , " +
									"\"appearances\" : 1}";
		
		assertEquals(expectedDBObject, jakkuAsDBObject.toString());
	}
	
	@Test
	public void insertPlanetTest()
	{		
		Planet kamino = factory.createPlanet("Kamino", "temperate", "ocean");
		
		// valid insertion
		planetDbTest.insertPlanet(kamino);		
		assertEquals(1, planetDbTest.getCollection().count());
		
		// duplicate insertion
		planetDbTest.insertPlanet(kamino);		
		assertEquals(1, planetDbTest.getCollection().count());
		
		// Invalid insertion
		planetDbTest.insertPlanet(factory.createPlanet("Dummy planet", "dummy climate", "dummy terrain"));		
		assertEquals(1, planetDbTest.getCollection().count());
	}
	
	@Test
	public void findAllPlanetsTest()
	{
		
		Planet jakku = factory.createPlanet("Jakku", "unknown", "deserts");
		Planet kamino = factory.createPlanet("Kamino", "temperate", "ocean");
		
		planetDbTest.insertPlanet(jakku);
		planetDbTest.insertPlanet(kamino);
		
		assertEquals(2, planetDbTest.readAllPlanets().size());
	}
	
	@Test
	public void findByIdTest()
	{
		Planet jakku = factory.createPlanet("Jakku", "unknown", "deserts");
		jakku.setId(1);
		
		planetDbTest.insertPlanet(jakku);
		
		// Test insertion
		assertEquals(1, planetDbTest.readAllPlanets().size());
		
		DBObject retrievedPlanet = planetDbTest.findPlanetById("1");
		
		// Test the search by a valid id
		assertNotNull(retrievedPlanet);
		assertEquals("Jakku", PlanetAdaptor.toPlanet(retrievedPlanet).getName());
		
		// Test a search by a invalid id
		assertNull(planetDbTest.findPlanetById("25"));
	}
	
	@Test
	public void findByNameTest()
	{
		Planet kamino = factory.createPlanet("Kamino", "temperate", "ocean");		
		planetDbTest.insertPlanet(kamino);
		
		assertEquals(1, planetDbTest.readAllPlanets().size());
		
		DBObject retrievedPlanet = planetDbTest.findPlanetByName("Kamino");
		
		assertNotNull(retrievedPlanet);
		assertEquals("Kamino", PlanetAdaptor.toPlanet(retrievedPlanet).getName());
		
		assertNull(planetDbTest.findPlanetByName("dummy planet"));
	}
	
	@Test
	public void removePlanerTest()
	{
		Planet kamino = factory.createPlanet("Kamino", "temperate", "ocean");
		kamino.setId(15);
		planetDbTest.insertPlanet(kamino);
		
		Planet jakku = factory.createPlanet("Jakku", "unknown", "deserts");
		jakku.setId(20);		
		planetDbTest.insertPlanet(jakku);
				
		assertEquals(2, planetDbTest.readAllPlanets().size());
		
		planetDbTest.deletePlanet("15");
		
		assertEquals(1, planetDbTest.readAllPlanets().size());
		
		planetDbTest.deletePlanet("150");
		
		assertEquals(1, planetDbTest.readAllPlanets().size());
	}
}
