package uk.thecodingbadgers.bConomy.account;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.UUID;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

@SuppressWarnings("serial")
public class PlayerAccounts extends ArrayList<Account> {
	
	/**
	 * Removes the account for a given player.
	 *
	 * @param player the player who's account to remove
	 * @return true, if successfully removed
	 */
	public boolean remove(Player player) {

		Account account = get(player);
		if (account != null) {
			remove(account);
			return true;
		}

		return false;
	}
	
	/**
	 * Removes the account for a given player name.
	 *
	 * @param player the players name who's account should be removed
	 * @return true, if successfully removed
	 */
	public boolean remove(String player) {

		Account account = get(player);
		if (account != null) {
			remove(account);
			return true;
		}

		return false;
	}

	/**
	 * Removes the account for a given player name.
	 *
	 * @param player the players name who's account should be removed
	 * @return true, if successfully removed
	 */
	public boolean remove(OfflinePlayer player) {

		Account account = get(player);
		if (account != null) {
			remove(account);
			return true;
		}

		return false;
	}
	
	/**
	 * Gets an account from a given player.
	 *
	 * @param player the player
	 * @return the account of the given player
	 */
	public Account get(OfflinePlayer player) {

		Iterator<Account> itr = iterator();
		while (itr.hasNext()) {
			Account currentAccount = itr.next();
			if (currentAccount.getUniqueId().equals(player.getUniqueId())) {
				return currentAccount;
			}
		}

		return null;
	}

	/**
	 * Gets an account from a player name.
	 *
	 * @param player the player name
	 * @return the account of the given player name
	 */
	public Account get(String player) {

		Iterator<Account> itr = iterator();
		while (itr.hasNext()) {
			Account currentAccount = itr.next();
			if (currentAccount.getPlayerName().equalsIgnoreCase(player)) {
				return currentAccount;
			}
		}

		return null;
	}
	
	/***
	 * Gets an account from its underlying mojang uuid.
	 * 
	 * @param id - the players uuid
	 * @return the account for the given uuid
	 */
	public Account get(UUID id) {

		Iterator<Account> itr = iterator();
		while (itr.hasNext()) {
			Account currentAccount = itr.next();
			if (currentAccount.getUniqueId().equals(id)) {
				return currentAccount;
			}
		}

		return null;
	}
	
	public ArrayList<Account> getTop(int amount) {
		
		ArrayList<Account> sortedAccounts = this;
		ArrayList<Account> topAccounts = new ArrayList<Account>();
		
		Collections.sort(sortedAccounts);
		
		int start = sortedAccounts.size() - amount;
		if (start < 0)
			start = 0;
		
		for (int i = start; i < sortedAccounts.size(); ++i) {
			topAccounts.add(sortedAccounts.get(i));
		}
		
		return topAccounts;
		
	}
	
}
