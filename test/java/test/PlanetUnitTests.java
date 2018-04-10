package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import domain.Planet;
import domain.PlanetFactory;

public class PlanetUnitTests {

	PlanetFactory factory;
	
	@Test
	public void testValidPlanets()
	{
		factory = new PlanetFactory();
		
		assertNotNull(factory.createPlanet("1", "Jakku", "unknown", "deserts"));
		
		Planet naboo = factory.createPlanet("2", "Naboo", "temperate", "grassy hills, swamps, forests, mountains");
		assertEquals("temperate", naboo.getClimate());
		assertEquals("naboo", naboo.getLowerCaseName());
		assertEquals(4, naboo.getAppearance());
	}
	
	@Test
	public void testInvalidPlanets()
	{
		factory = new PlanetFactory();
		
		assertNull(factory.createPlanet("3", "Dummy planet", "dummy climate", "dummy terrain"));
	}
}
