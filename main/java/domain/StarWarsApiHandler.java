package domain;

import java.io.FileInputStream;
import java.util.Properties;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

public class StarWarsApiHandler 
{	
	private Client client;
	private WebTarget target;
	private Properties properties;
	
	private Boolean initProperties()
	{		
		try 
		{
			properties = new Properties();
			properties.load(new FileInputStream("config.properties"));
			return true;
		} 
		catch (Exception e) {
			return false;
		}
		
	}
	
	private void initTarget(String resourceName, String searchURL)
	{
		client = ClientBuilder.newBuilder().build();
		//https://swapi.co/api/people/?search=r2
		target =  client.target(searchURL).queryParam("search", resourceName);
	}
	
	private Planet getPlanetFromApi()
	{
		try
		{
			PlanetResponse response =  target
					.request(MediaType.APPLICATION_JSON)
					.get(PlanetResponse.class);
			
			if (response.getResults().size() > 0)
			{
				return PlanetAdaptor.toPlanet(response.getResults().get(0));
			}
			
			return null;
		}
		catch (Exception ex)
		{
			return null;
		}		
	}
	
	public Planet searchPlanet(String planetName)
	{
		if (initProperties())
		{
			initTarget(planetName, properties.getProperty("SWSearchPlanetUrl"));
			return getPlanetFromApi();
		}
		
		return null;
		
	}
}
