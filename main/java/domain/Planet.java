package domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Planet {
	
	private String id;
	private String name;
	private String lowerCaseName;
	private String climate;
	private String terrain;
	private int appearance;
	
	protected Planet(String name, String weather, String ground, int appearance)
	{
		this.name = name;
		this.lowerCaseName = name.toLowerCase();
		this.climate = weather;
		this.terrain = ground;
		this.appearance = appearance;
	}
	
	protected Planet(String id, String name, String weather, String ground, int appearance)
	{
		this.id = id;
		this.name = name;
		this.lowerCaseName = name.toLowerCase();
		this.climate = weather;
		this.terrain = ground;
		this.appearance = appearance;
	}
	
	// Dummy constructor to JSON 
	public Planet()
	{
		
	}
	
	// Getters and Setters
	public String getId() {
		return id;
	}	
	
	public String getName() {
		return name;
	}
	
	public String getTerrain() {
		return terrain;
	}	

	public String getClimate() {
		return climate;
	}

	public int getAppearance() {
		return appearance;
	}
	
	public String getLowerCaseName() {
		return lowerCaseName;
	}
}
