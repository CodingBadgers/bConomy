package uk.badger.bConomy;

import org.bukkit.plugin.Plugin;

import uk.badger.bConomy.account.PlayerAccounts;

public class Global {
	
	private static Plugin m_plugin = null;
	private static PlayerAccounts m_accounts = null;

	public static Plugin getPlugin() {
		return m_plugin;
	}

	public static void setPlugin(Plugin plugin) {
		m_plugin = plugin;
	}
	
	public static void outputToConsole(String message) {
		
		System.out.println("[bConomy] " + message);
		
	}
	
	public static void createAccounts() {
		
	}

	public static PlayerAccounts getAccounts() {
		return m_accounts;
	}

	public static void setAccounts(PlayerAccounts m_accounts) {
		Global.m_accounts = m_accounts;
	}

}
