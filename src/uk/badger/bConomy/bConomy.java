package uk.badger.bConomy;

import org.bukkit.plugin.java.JavaPlugin;

public class bConomy extends JavaPlugin {

	public void onEnable() {
		
		Global.outputToConsole("Initialising bConomy");
		Global.setPlugin(this);
		Global.loadAccounts(this);
		
		
	}
	
}
