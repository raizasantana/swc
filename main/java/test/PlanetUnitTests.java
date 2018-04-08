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
		
		assertNotNull(factory.createPlanet("jakku", "unknown", "deserts"));
		
		Planet naboo = factory.createPlanet("Naboo", "temperate", "grassy hills, swamps, forests, mountains");
		assertEquals("temperate", naboo.getClimate());
		assertEquals(4, naboo.getAppearances());
	}
	
	@Test
	public void testInvalidPlanets()
	{
		factory = new PlanetFactory();
		
		assertNull(factory.createPlanet("Dummy planet", "dummy climate", "dummy terrain"));
	}
}
