package uk.badger.bConomy;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
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
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		
		if (commandLabel.equalsIgnoreCase("money")) {
			
			// show the help
			if (args[0].equalsIgnoreCase("?") || args[0].equalsIgnoreCase("help")) {
				showHelp(sender);
				return true;
			}
			
			return true;
		}
		
		return false;
	}

	private void showHelp(CommandSender sender) {
		sender.sendMessage("-- bConomy --");
		
		sender.sendMessage("/money [name] - Displays the amount of money in an account");
		sender.sendMessage("/money pay (name) (amount) - Pays a player");
		sender.sendMessage("/money top [amount] - Shows the top player balances");
		
		sender.sendMessage("/money grant (name) (amount) - Grants a player money");
		sender.sendMessage("/money withdraw (name) (amount) - Withdraws money from a player");
		sender.sendMessage("/money set (name) (amount) - Sets a players balance");
		sender.sendMessage("/money reset (name) - Resets a players balance");
	}
	
}
