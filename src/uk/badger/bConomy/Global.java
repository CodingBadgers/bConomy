package uk.badger.bConomy;

import n3wton.me.BukkitDatabaseManager.BukkitDatabaseManager;
import n3wton.me.BukkitDatabaseManager.BukkitDatabaseManager.DatabaseType;
import n3wton.me.BukkitDatabaseManager.Database.BukkitDatabase;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import uk.badger.bConomy.account.PlayerAccounts;

public class Global {
	
	private static Plugin m_plugin = null;
	private static PlayerAccounts m_accounts = null;
	public static BukkitDatabase m_database = null;

	public static Plugin getPlugin() {
		return m_plugin;
	}

	public static void setPlugin(Plugin plugin) {
		m_plugin = plugin;
	}
	
	public static void outputToConsole(String message) {
		
		System.out.println("[bConomy] " + message);
		
	}
	
	public static void loadAccounts(JavaPlugin plugin) {
		
		m_database = BukkitDatabaseManager.CreateDatabase("bConomy", plugin, DatabaseType.SQL);
		
		
		
	}

	public static PlayerAccounts getAccounts() {
		return m_accounts;
	}

	public static void setAccounts(PlayerAccounts m_accounts) {
		Global.m_accounts = m_accounts;
	}

}
