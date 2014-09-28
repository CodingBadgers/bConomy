package uk.thecodingbadgers.bConomy.config;

import org.bukkit.configuration.file.FileConfiguration;
import uk.thecodingbadgers.bConomy.Global;
import uk.thecodingbadgers.bDatabaseManager.bDatabaseManager.DatabaseType;

public class Config {

	public static class DatabaseInfo {
		public DatabaseType driver;
		public String host;
		public String dbname;
		public String tablename;
		public String user;
		public String password;
		public int port = 3306;
		public int update = 20;
	}
	
	public static class Currency {
		public String format;
	}
	
	public static DatabaseInfo m_dbInfo = null;
	public static Currency m_currency = null;
	public static int m_startingBalance;
	
	public static boolean setupConfig() {
		
		FileConfiguration config = Global.getPlugin().getConfig();
		
		try {
			// database config
			config.addDefault("database.driver", "SQL");
			config.addDefault("database.host", "localhost");
			config.addDefault("database.dbname", "bConomy");
			config.addDefault("database.tablename", "bConomy");
			config.addDefault("database.user", "root");
			config.addDefault("database.password", "");
			config.addDefault("database.port", 3306);
			config.addDefault("database.updateTime", 2);
			
			// currency info config
			config.addDefault("currency.format", "@##0.00");
			
			// economy config
			config.addDefault("economy.startingBalance", 30);
			
			config.options().copyDefaults(true);
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
		
		m_dbInfo = new DatabaseInfo();
		m_dbInfo.driver = DatabaseType.valueOf(config.getString("database.driver", "SQL"));
		m_dbInfo.host = config.getString("database.host", "localhost");
		m_dbInfo.dbname = config.getString("database.dbname", "bConomy");
		m_dbInfo.tablename = config.getString("database.tablename", "bConomy");
		m_dbInfo.user = config.getString("database.user", "root");
		m_dbInfo.password = config.getString("database.password", "");
		m_dbInfo.port = config.getInt("database.port", 3306);
		m_dbInfo.update = config.getInt("database.updateTime", 2);
	
		m_currency = new Currency();
		m_currency.format = "0 Crumbs"; // config.getString("currency.fomat", "£#,##0.00");
		
		m_startingBalance = config.getInt("economy.startingBalance", 30);
		
		Global.getPlugin().saveConfig();
		
		return true;
	}
}
