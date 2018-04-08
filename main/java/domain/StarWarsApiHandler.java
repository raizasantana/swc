package domain;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

public class StarWarsApiHandler 
{	
	private static final String SEARCH_PLANET_URL = "https://swapi.co/api/planets/";
	private Client client;
	private WebTarget target;
	
	private void initTarget(String resourceName, String searchURL)
	{
		client = ClientBuilder.newBuilder().build();
		//https://swapi.co/api/people/?search=r2
		target =  client.target(searchURL).queryParam("search", resourceName);
	}
	
	private Planet getPlanetFromApi()
	{
		PlanetResponse response =  target
				.request(MediaType.APPLICATION_JSON)
				.get(PlanetResponse.class);
		
		return response.getPlanet();
	}
	
	public Planet searchPlanet(String planetName)
	{
		initTarget(planetName, SEARCH_PLANET_URL);
		return getPlanetFromApi();
	}
}
