package uk.badger.bConomy.config;

import org.bukkit.configuration.file.FileConfiguration;

import uk.badger.bConomy.Global;

public class Config {

	public class DatabaseInfo {
		public String host;
		public String dbname;
		public String user;
		public String password;
		public int port = 3306;
	}
	
	public static DatabaseInfo m_dbInfo = null;
	
	public boolean setupConfig() {
		
		FileConfiguration config = Global.getPlugin().getConfig();
		
		try {
			config.addDefault("database.host", "localhost");
			config.addDefault("database.dbname", "bConomy");
			config.addDefault("database.user", "root");
			config.addDefault("database.password", "");
			config.addDefault("database.port", 3306);
			config.options().copyDefaults(true);
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
		
		m_dbInfo.host = config.getString("database.host", "localhost");
		m_dbInfo.dbname = config.getString("database.dbname", "bConomy");
		m_dbInfo.user = config.getString("database.user", "root");
		m_dbInfo.password = config.getString("database.password", "");
		m_dbInfo.port = config.getInt("database.port", 3306);
		
		return true;
	}
}
