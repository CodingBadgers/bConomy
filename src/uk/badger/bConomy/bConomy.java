package uk.badger.bConomy;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import uk.badger.bConomy.account.Account;
import uk.badger.bConomy.config.Config;
import uk.badger.bConomy.config.DatabaseManager;

public class bConomy extends JavaPlugin {
	
	private PlayerListener m_playerListener = new PlayerListener();
	
	public void onEnable() {
		
		Global.setPlugin(this);
		Global.outputToConsole("Initialising bConomy");
		Global.getServer().getPluginManager().registerEvents(m_playerListener, this);

		// setup config and database
		Config.setupConfig();
		DatabaseManager.setupDatabase(this);
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		
		if (commandLabel.equalsIgnoreCase("money")) {
						
			// show the help
			if (args.length != 0 && (args[0].equalsIgnoreCase("?") || args[0].equalsIgnoreCase("help"))) {
				showHelp(sender);
				return true;
			}
			
			
			// handle /money last
			if (args.length <= 1) {
				handleMoney(sender, args);
				return true;
			}
			
			return true;
		}
		
		return false;
	}

	private void handleMoney(CommandSender sender, String[] args) {
		
		if (!Global.hasPermission(sender, "bconomy.admin.grant", true)) {
			return;
		}
			
		Account playerAccount = null;
		
		if (args.length == 1) {
			playerAccount = Global.getAccounts().get(args[0]);
		}
		
		// not looking for an account and command is from console get out of here
		if (playerAccount == null && !(sender instanceof Player)) {
			Global.output(sender, "The console does not have an account.");
			Global.output(sender, "Use /money <name>.");
			return;
		} else if (playerAccount == null) {
			Player player = (Player)sender;
			playerAccount = Global.getAccounts().get(player.getName());
		}
		
		// could not find an account
		if (playerAccount == null) {
			Global.output(sender, "Could not find an account for that player.");
			return;
		}
		
		Global.output(sender, playerAccount.getPlayer().getName() + " has " + Global.format(playerAccount.getBalance()) );
	}

	private void showHelp(CommandSender sender) {
		sender.sendMessage(ChatColor.GOLD + "-- bConomy --");
		
		sender.sendMessage("/money [name] - Displays the amount of money in an account");
		sender.sendMessage("/money pay (name) (amount) - Pays a player");
		sender.sendMessage("/money top [amount] - Shows the top player balances");
		
		if (Global.hasPermission(sender, "bconomy.admin.grant", false))
			sender.sendMessage("/money grant (name) (amount) - Grants a player money");
		
		if (Global.hasPermission(sender, "bconomy.admin.withdraw", false))
			sender.sendMessage("/money withdraw (name) (amount) - Withdraws from an account");
		
		if (Global.hasPermission(sender, "bconomy.admin.set", false))
			sender.sendMessage("/money set (name) (amount) - Sets a players balance");
		
		if (Global.hasPermission(sender, "bconomy.admin.reset", false))
			sender.sendMessage("/money reset (name) - Resets a players balance");
	}
	
}
