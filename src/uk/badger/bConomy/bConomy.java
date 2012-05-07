package uk.badger.bConomy;

import org.bukkit.plugin.java.JavaPlugin;

import uk.badger.bConomy.config.Config;
import uk.badger.bConomy.config.DatabaseManager;

public class bConomy extends JavaPlugin {
	
	public void onEnable() {
		
		Global.outputToConsole("Initialising bConomy");
		Global.setPlugin(this);

		// setup config and database
		DatabaseManager.setupDatabase(this);
		Config.setupConfig();
	}
	
}
