package uk.badger.bConomy;

import java.text.DecimalFormat;

import n3wton.me.BukkitDatabaseManager.Database.BukkitDatabase;

import org.bukkit.plugin.java.JavaPlugin;

import uk.badger.bConomy.account.Account;
import uk.badger.bConomy.account.PlayerAccounts;
import uk.badger.bConomy.config.Config;

public class Global {
	
	private static JavaPlugin m_plugin = null;
	private static PlayerAccounts m_accounts = null;
	public static BukkitDatabase m_database = null;

	/**
	 * get the JavaPlugin instance
	 * 
	 * @return the plugin instance
	 */
	public static JavaPlugin getPlugin() {
		return m_plugin;
	}
	
	/**
	 * Set the plugin instance
	 * 
	 * @param plugin - the plugin instance to use
	 */
	public static void setPlugin(JavaPlugin plugin) {
		m_plugin = plugin;
	}
	
	/**
	 * Output a message to console
	 * 
	 * @param message to output
	 */
	public static void outputToConsole(String message) {
		
		m_plugin.getLogger().info(message);		
	}

	/**
	 * Get the accounts array
	 * 
	 * @return the accounts array
	 */
	public static PlayerAccounts getAccounts() {
		return m_accounts;
	}

	/**
	 * Set the accounts array 
	 * 
	 * @param m_accounts - the array to set it to
	 */
	public static void setAccounts(PlayerAccounts m_accounts) {
		Global.m_accounts = m_accounts;
	}

	/**
	 * Add a account to the array
	 * 
	 * @param account to add
	 */
	public static void addAccout(Account account) {
		m_accounts.add(account);	
	}
	
	public static String format(double amount) {
		DecimalFormat format = new DecimalFormat("#,##0.00");
		String formatted = format.format(amount);
		
		if (formatted.endsWith("."))
			formatted = formatted.substring(0, formatted.length() - 1);
		
		return Config.currencySymbol + formatted;
	}

}
