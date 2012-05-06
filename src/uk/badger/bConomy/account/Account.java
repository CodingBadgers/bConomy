package uk.badger.bConomy.account;

import org.bukkit.entity.Player;

public class Account {
	
	private Player m_player = null;

	public Player getPlayer() {
		return m_player;
	}

	public void setPlayer(Player player) {
		m_player = player;
	}

}
