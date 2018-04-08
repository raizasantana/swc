package domain;

public class Planet {
	
	private int id;
	private String name;
	private String climate;
	private String terrain;
	private int appearances;
	
	protected Planet(String name, String weather, String ground, int appearances)
	{
		this.name = name;
		this.climate = weather;
		this.terrain = ground;
		this.appearances = appearances;
	}

	// Getters and Setters
	public int getId() {
		return id;
	}	
	
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	
	public String getTerrain() {
		return terrain;
	}

	protected void setTerrain(String terrain) {
		this.terrain = terrain;
	}	

	protected void setClimate(String climate) {
		this.climate = climate;
	}

	public String getClimate() {
		return climate;
	}
	
	protected void setAppearances(int appearances)
	{
		this.appearances = appearances;
	}
	
	public int getAppearances() {
		return appearances;
	}	
}
