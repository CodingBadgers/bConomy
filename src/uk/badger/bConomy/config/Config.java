package uk.badger.bConomy.config;

import n3wton.me.BukkitDatabaseManager.BukkitDatabaseManager.DatabaseType;

import org.bukkit.configuration.file.FileConfiguration;

import uk.badger.bConomy.Global;

public class Config {

	public static class DatabaseInfo {
		public DatabaseType driver;
		public String host;
		public String dbname;
		public String tablename;
		public String user;
		public String password;
		public int port = 3306;
	}
	
	public static DatabaseInfo m_dbInfo = null;
	public static String m_currency;
	public static int m_startingBalance;
	public static String m_currencySymbol;
	
	public static boolean setupConfig() {
		
		FileConfiguration config = Global.getPlugin().getConfig();
		
		try {
			config.addDefault("database.driver", "SQL");
			config.addDefault("database.host", "localhost");
			config.addDefault("database.dbname", "bConomy");
			config.addDefault("database.tablename", "bConomy");
			config.addDefault("database.user", "root");
			config.addDefault("database.password", "");
			config.addDefault("database.port", 3306);
			config.addDefault("economy.startingBalance", 30);
			config.addDefault("economy.currency", "pounds");
			config.addDefault("economy.currencySymbol", "�");
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
		
		m_currency = config.getString("econonmy.currency", "pounds");
		m_currencySymbol = config.getString("economy.currencySymbol", "�");
		m_startingBalance = config.getInt("economy.startingBalance", 30);
		
		Global.getPlugin().saveConfig();
		
		return true;
	}
}
