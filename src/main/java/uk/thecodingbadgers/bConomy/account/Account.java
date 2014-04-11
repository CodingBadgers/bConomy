package uk.thecodingbadgers.bConomy.account;

import java.util.UUID;

import org.bukkit.entity.Player;
import uk.thecodingbadgers.bConomy.Global;
import uk.thecodingbadgers.bConomy.config.Config;
import uk.thecodingbadgers.bConomy.config.DatabaseManager;

public class Account implements Comparable<Account> {
	
	private int m_id = 0;
	private String m_playerName;
	private final UUID m_uuid;
	private double m_balance = 0;

	/**
	 * Account construct for new accounts
	 * 
	 * @param id - the account id
	 * @param player - the offline player to use
	 * @param uuid - the player's uuid
	 */
	public Account(int id, String playerName, UUID uuid) {
		this(id, playerName, uuid, Config.m_startingBalance);
	}
	
	/**
	 * Account construct for existing accounts
	 * 
	 * @param id - the account id
	 * @param player - the offlineplayer instance
	 * @param uuid - the player's uuid
	 * @param balance - their current balance
	 */
	public Account(int id, String playerName, UUID uuid, double balance) {
		m_id = id;
		m_uuid = uuid;
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
	 * Update this accounts associated player name.
	 * 
	 * @param name the new player name
	 */
	public void setPlayerName(String name) {
		this.m_playerName = name;
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
	 * Gets the players mojang id.
	 * 
	 * @return the players mojang id.
	 */
	public UUID getUniqueId() {
		return m_uuid;
	}
	
	/**
	 * Get the player object
	 * 
	 * @return the player instance
	 */
	public Player getPlayer() {
		return Global.getServer().getPlayer(m_uuid);
	}
	
	/** 
	 * check if the account owner is online
	 * 
	 * @return if the account owner is online
	 */
	public boolean isOnline() {
		return getPlayer() != null;
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

	/**
	 * Compare this account to another account, will return which has the
	 * greator balance
	 * 
	 * @param o the other account
	 * @return 
	 */
	public int compareTo(Account o) {
		return (int) Math.signum(getBalance() - o.getBalance());
	}

}
