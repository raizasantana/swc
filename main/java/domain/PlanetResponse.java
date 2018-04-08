package domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PlanetResponse {
	
	private List<Result> results = null;

	public Planet getPlanet()
	{
		if (results.size() > 0)
		{
			return new Planet(results.get(0).getName(), results.get(0).getClimate(), results.get(0).getTerrain(), results.get(0).getFilms().size());
		}
		
		return null;
	}
	
	public PlanetResponse() 
	{
	}

	public PlanetResponse(List<Result> results) 
	{
		this.results = results;
	}

	public List<Result> getResults() {
		return results;
	}

	public void setResults(List<Result> results) {
		this.results = results;
	}
	
	// Inner class 
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@JsonIgnoreProperties(ignoreUnknown = true)
	
	private static class Result {

		private String name;
		private String climate;
		private String terrain;
		private List<String> films = null;
		
		private Result() {
		}

		private Result(String name, String climate, String terrain, List<String> films) 
		{
			this.name = name;
			this.climate = climate;
			this.terrain = terrain;
			this.films = films;
		}

		private String getName() {
			return name;
		}		

		private String getClimate() {
			return climate;
		}

		private void setClimate(String climate) {
			this.climate = climate;
		}

		private String getTerrain() {
			return terrain;
		}

		private void setTerrain(String terrain) {
			this.terrain = terrain;
		}
		
		private List<String> getFilms() {
			return films;
		}

		private void setFilms(List<String> films) {
			this.films = films;
		}
	}
}
