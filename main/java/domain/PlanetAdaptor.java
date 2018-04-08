package domain;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public abstract class PlanetAdaptor {

	public static DBObject toObject(Planet planet)
	{
		if (planet == null)
		{
			return null;
		}
		
		DBObject planetObj = new BasicDBObject("name", planet.getName())
				.append("climate", planet.getClimate())
				.append("terrain", planet.getTerrain())
				.append("appearances", planet.getAppearances());
		
		if (planet.getId() > 0)
		{
			((BasicDBObject) planetObj).append("_id", planet.getId());
		}
		
		return planetObj;
	}
	
	public static Planet toPlanet(DBObject planetObj)
	{
		String name = (String) planetObj.get("name");
		String climate = (String) planetObj.get("climate");
		String terrain = (String) planetObj.get("terrain");
		int appearances = (Integer) planetObj.get("appearances");
		
		return new Planet(name, climate, terrain, appearances);
	}
}
