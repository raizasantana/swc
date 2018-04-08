package persistence;

public abstract class DBConfig {
	
	private static final String connectionUrl = "mongodb://localhost:27017";
	private static final String databaseName = "starwarsdb";
	private static final String planetCollectionName = "planets";
	
	public static String getConnectionUrl()
	{
		return connectionUrl;
	}
	
	public static String getDatabaseName()
	{
		return databaseName;
	}
	
	public static String getPlanetsCollectionName()
	{
		return planetCollectionName;
	}

}
