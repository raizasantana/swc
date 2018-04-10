package presentation;

import java.io.FileInputStream;
import java.util.Properties;

import persistence.DatabaseHelper;

public class Application {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Properties properties = new Properties();
		try
		{
			properties.load(new FileInputStream("config.properties"));
			
			String conUrl = properties.getProperty("connectionUrl");
			String dbName = properties.getProperty("databaseName");
			String colName = properties.getProperty("collectionName");
			
			if (conUrl != null && dbName != null && colName != null)
			{
				PlanetApi api = new PlanetApi(DatabaseHelper.getInstance(conUrl, dbName).getDabase(), colName);
			}
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

}
