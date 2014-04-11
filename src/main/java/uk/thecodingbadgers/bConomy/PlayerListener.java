package uk.thecodingbadgers.bConomy;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import uk.thecodingbadgers.bConomy.account.Account;
import uk.thecodingbadgers.bConomy.config.Config;
import uk.thecodingbadgers.bConomy.config.DatabaseManager;

public class PlayerListener implements Listener {

	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerJoin(PlayerJoinEvent event) {
		
		Player player = event.getPlayer();
		Account account = Global.getAccounts().get(player);
		if (account == null) {
			// new player, create an account
			account = new Account(Global.getNextId(), player.getName(), player.getUniqueId());
			Global.getAccounts().add(account);
			DatabaseManager.addAccount(account);
		}		
		else {
			DatabaseManager.getAccount(account);
		}
		
		if (!account.getPlayerName().equals(player.getName())) {
			account.setPlayerName(player.getName());
			DatabaseManager.updateUsername(account);
		}
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerQuit(PlayerQuitEvent event) {
		
		Player player = event.getPlayer();
		Account account = Global.getAccounts().get(player);
		if (account == null)
			return;
		
		if (account.getBalance() == Config.m_startingBalance) {
			// default balance so delete them from the database
			DatabaseManager.removeAccount(account);	
			return;
		}
		
		DatabaseManager.updateAccount(account);
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerKick(PlayerKickEvent event) {
		
		Player player = event.getPlayer();
		Account account = Global.getAccounts().get(player);
		if (account == null)
			return;
		
		if (account.getBalance() == Config.m_startingBalance) {
			// default balance so delete them from the database
			DatabaseManager.removeAccount(account);	
			return;
		}
		
		DatabaseManager.updateAccount(account);
	}
	
}
