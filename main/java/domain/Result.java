package domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Result {

	private String name;
	private String climate;
	private String terrain;
	private List<String> films = null;

	public Result() {
	}

	public Result(String name, String climate, String terrain, List<String> films) {
		this.name = name;
		this.climate = climate;
		this.terrain = terrain;
		this.films = films;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Result withName(String name) {
		this.name = name;
		return this;
	}


	public String getClimate() {
		return climate;
	}

	public void setClimate(String climate) {
		this.climate = climate;
	}

	public Result withClimate(String climate) {
		this.climate = climate;
		return this;
	}

	public String getTerrain() {
		return terrain;
	}

	public void setTerrain(String terrain) {
		this.terrain = terrain;
	}

	public Result withTerrain(String terrain) {
		this.terrain = terrain;
		return this;
	}
	
	public List<String> getFilms() {
		return films;
	}

	public void setFilms(List<String> films) {
		this.films = films;
	}

	public Result withFilms(List<String> films) {
		this.films = films;
		return this;
	}
}