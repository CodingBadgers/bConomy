package uk.badger.bConomy.account;

import org.bukkit.entity.Player;

public class Account {
	
	private Player m_player = null;
	private double m_balance = 0;

	public Player getPlayer() {
		return m_player;
	}

	public void setPlayer(Player player) {
		m_player = player;
	}

	public double getBalance() {
		return m_balance;
	}

	public void setBalance(double balance) {
		m_balance = balance;
	}
	
	public void deposit(double amount) {
		m_balance += amount;
	}
	
	public void withdraw(double amount) {
		m_balance -= amount;
	}
	
	public boolean has(double amount) {
		return amount <= m_balance;
	}

}
