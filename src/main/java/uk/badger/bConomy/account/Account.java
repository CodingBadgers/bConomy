package uk.badger.bConomy.account;

import org.bukkit.entity.Player;

import uk.badger.bConomy.Global;
import uk.badger.bConomy.config.Config;
import uk.badger.bConomy.config.DatabaseManager;

public class Account {
	
	private int m_id = 0;
	private final String m_playerName;
	private double m_balance = 0;

	/**
	 * Account construct for new accounts
	 * 
	 * @param id - the account id
	 * @param player - the offline player to use
	 */
	public Account(int id, String playerName) {
		m_id = id;
		m_playerName = playerName;
		m_balance = Config.m_startingBalance;
	}
	
	/**
	 * Account construct for existing accounts
	 * 
	 * @param id - the account id
	 * @param player - the offlineplayer instance
	 * @param balance - their current balance
	 */
	public Account(int id, String playerName, double balance) {
		m_id = id;
		m_playerName = playerName;
		m_balance = balance;
	}
	
	/**
	 * get the players id
	 * 
	 * @return the player's id
	 */
	public int getId() {
		return m_id;
	}
	
	/**
	 * Get the player name
	 * 
	 * @return the player name of this account
	 */
	public String getPlayerName() {
		return m_playerName;
	}
	
	/**
	 * Get the player object
	 * 
	 * @return the player instance
	 */
	public Player getPlayer() {
		return Global.getServer().getPlayer(m_playerName);
	}
	
	/** 
	 * check if the account owner is online
	 * 
	 * @return if the account owner is online
	 */
	public boolean isOnline() {
		return Global.getServer().getPlayer(m_playerName) != null;
	}

	/**
	 * Get the accounts balance
	 * 
	 * @return the balance
	 */
	public double getBalance() {
		return m_balance;
	}

	/**
	 * Set the accounts balance
	 * 
	 * @param balance to set it to
	 */
	public void setBalance(double balance) {
		m_balance = balance;
		DatabaseManager.updateAccount(this);
	}
	
	/**
	 * Give the player an amount of money
	 * 
	 * @param amount to give them
	 */
	public void deposit(double amount) {
		m_balance += amount;
		DatabaseManager.updateAccount(this);
	}
	
	/**
	 * take money off the player
	 * 
	 * @param amount to take off them
	 */
	public void withdraw(double amount) {
		m_balance -= amount;
		DatabaseManager.updateAccount(this);
	}
	
	/** 
	 * check if the player has a amount of money
	 * 
	 * @param amount to check
	 * @return whether they have that amount
	 */
	public boolean has(double amount) {
		return amount <= m_balance;
	}

}
