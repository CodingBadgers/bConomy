package uk.badger.bConomy;

import org.bukkit.plugin.java.JavaPlugin;

import uk.badger.bConomy.config.DatabaseManager;

public class bConomy extends JavaPlugin {

	DatabaseManager m_dbmanager;
	
	public void onEnable() {
		
		Global.outputToConsole("Initialising bConomy");
		Global.setPlugin(this);

		m_dbmanager = new DatabaseManager(this);
		
	}
	
}
