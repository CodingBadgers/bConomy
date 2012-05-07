package uk.badger.bConomy;

import org.bukkit.plugin.java.JavaPlugin;

import uk.badger.bConomy.config.Config;
import uk.badger.bConomy.config.DatabaseManager;

public class bConomy extends JavaPlugin {
	
	public void onEnable() {
		
		Global.setPlugin(this);
		Global.outputToConsole("Initialising bConomy");

		// setup config and database
		Config.setupConfig();
		DatabaseManager.setupDatabase(this);
	}
	
}
