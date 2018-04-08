package domain;

public class PlanetFactory {
	
	private StarWarsApiHandler swapi;
	
	public PlanetFactory()
	{
		this.swapi = new StarWarsApiHandler();
	}
	
	/*
	 * This method validates the parameters, using the StarWars API, before create a planet.
	 * The terrain and/or climate are corrected before the planet instantiation.
	 */
	public Planet createPlanet(String name, String climate, String terrain)
	{
		Planet validPlanet = swapi.searchPlanet(name);
		
		if (validPlanet != null)
		{
			String validTerrain = getCorrectPlanetTerrain(validPlanet, terrain);
			String validClimate = getCorrectPlanetClimate(validPlanet, climate);
			
			return new Planet(name, validClimate, validTerrain, validPlanet.getAppearances());
		}
		
		return null;
	}
	
	public String getCorrectPlanetTerrain(Planet validPlanet, String terrain)
	{
		return validPlanet.getTerrain().equals(terrain) ? terrain : validPlanet.getTerrain();
	}
	
	public String getCorrectPlanetClimate(Planet validPlanet, String climate)
	{
		return validPlanet.getClimate().equals(climate) ? climate : validPlanet.getClimate();
	}
}
