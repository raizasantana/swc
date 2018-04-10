package domain;

import java.util.ArrayList;

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
				.append("lowerCaseName", planet.getLowerCaseName())
				.append("climate", planet.getClimate())
				.append("terrain", planet.getTerrain())
				.append("appearance", planet.getAppearance());
		
		if (planet.getId() != null)
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
		Integer appearance = ((Number) planetObj.get("appearance")).intValue();
		return new Planet(name, climate, terrain, appearance);
	}
	
	public static Planet toPlanet(Result planetRes)
	{
		String name = planetRes.getName();
		String climate = planetRes.getClimate();
		String terrain = planetRes.getTerrain();
		int appearance = planetRes.getFilms().size();
		
		return new Planet(name, climate, terrain, appearance);
	}
	
	public static ArrayList<DBObject> toObjectList(ArrayList<Planet> planets)
	{
		ArrayList<DBObject> result = new ArrayList<DBObject>();
		
		if (planets != null && planets.size() > 0)
		{
			for (Planet p : planets)
			{
				result.add(toObject(p));
			}
		}
		return result;
	}
}
