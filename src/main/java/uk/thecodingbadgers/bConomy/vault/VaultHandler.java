package uk.thecodingbadgers.bConomy.vault;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import net.milkbowl.vault.economy.EconomyResponse.ResponseType;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.plugin.Plugin;
import uk.thecodingbadgers.bConomy.Global;
import uk.thecodingbadgers.bConomy.account.Account;
import uk.thecodingbadgers.bConomy.bConomy;
import uk.thecodingbadgers.bConomy.config.Config;
import uk.thecodingbadgers.bConomy.config.DatabaseManager;

import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

public class VaultHandler implements Economy {

    private static final Logger log = Logger.getLogger("Minecraft");
    private final String name = "bConomy";
    private Plugin plugin = null;
    protected bConomy economy = null;

    public class EconomyServerListener implements Listener {
        VaultHandler economy = null;

        public EconomyServerListener(VaultHandler economy) {
            this.economy = economy;
        }

        @EventHandler(priority = EventPriority.MONITOR)
        public void onPluginEnable(PluginEnableEvent event) {
            if (economy == null) {
                Plugin ec = plugin.getServer().getPluginManager().getPlugin("bConomy");
                if (ec != null && ec.isEnabled() && ec.getClass().getName().equals("uk.badger.bConomy.bConomy")) {
                    economy.economy = (bConomy) ec;
                    log.info(String.format("[%s][Economy] %s hooked.", plugin.getDescription().getName(), name));
                }
            }
        }

        @EventHandler(priority = EventPriority.MONITOR)
        public void onPluginDisable(PluginDisableEvent event) {
            if (economy == null) {
                Plugin ec = plugin.getServer().getPluginManager().getPlugin("bConomy");
                if (ec != null && ec.isEnabled() && ec.getClass().getName().equals("uk.badger.bConomy.bConomy")) {
                    economy.economy = (bConomy) ec;
                    log.info(String.format("[%s][Economy] %s hooked.", plugin.getDescription().getName(), name));
                }
            }
        }
    }

    public VaultHandler(Plugin plugin) {
        this.plugin = plugin;
        Bukkit.getServer().getPluginManager().registerEvents(new EconomyServerListener(this), plugin);
        // Load Plugin in case it was loaded before
        try {
            if (economy == null) {
                Plugin ec = plugin.getServer().getPluginManager().getPlugin("bConomy");
                if (ec != null && ec.isEnabled() && ec.getClass().getName().equals("uk.badger.bConomy.bConomy")) {
                    economy = (bConomy) ec;
                    log.info(String.format("[%s][Economy] %s hooked.", plugin.getDescription().getName(), name));
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public int fractionalDigits() {
        return 2;
    }

    @Override
    public boolean hasAccount(String playerName, String worldName) {
        return hasAccount(playerName);
    }

    @Override
    public boolean hasAccount(OfflinePlayer offlinePlayer, String s) {
        return hasAccount(offlinePlayer.getName());
    }

    @Override
    public double getBalance(String playerName, String world) {
        return getBalance(playerName);
    }

    @Override
    public double getBalance(OfflinePlayer offlinePlayer, String s) {
        return getBalance(offlinePlayer.getName());
    }

    @Override
    public boolean has(String playerName, String worldName, double amount) {
        return has(playerName, amount);
    }

    @Override
    public boolean has(OfflinePlayer offlinePlayer, String s, double v) {
        return has(offlinePlayer.getName(), v);
    }

    @Override
    public EconomyResponse withdrawPlayer(String playerName, String worldName, double amount) {
        return withdrawPlayer(playerName, amount);
    }

    @Override
    public EconomyResponse withdrawPlayer(OfflinePlayer offlinePlayer, String s, double v) {
        return withdrawPlayer(offlinePlayer.getName(), v);
    }

    @Override
    public EconomyResponse depositPlayer(String playerName, String worldName, double amount) {
        return depositPlayer(playerName, amount);
    }

    @Override
    public EconomyResponse depositPlayer(OfflinePlayer offlinePlayer, String s, double v) {
        return depositPlayer(offlinePlayer.getName(), v);
    }

    @Override
    public boolean createPlayerAccount(String playerName, String worldName) {
        return createPlayerAccount(playerName);
    }

    @Override
    public boolean createPlayerAccount(OfflinePlayer offlinePlayer, String s) {
        return createPlayerAccount(offlinePlayer.getName());
    }

    @Override
    public boolean isEnabled() {
        if (economy == null) {
            return false;
        } else {
            return economy.isEnabled();
        }
    }
    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean hasBankSupport() {
        return false;
    }

    @Override
    public String format(double amount) {
        return Global.format(amount);
    }

    @Override
    public String currencyNamePlural() {
        return Config.m_currency.name;
    }

    @Override
    public String currencyNameSingular() {
        return Config.m_currency.name;
    }

    @Override
    public boolean hasAccount(String playerName) {
        return Global.getAccounts().get(playerName) != null;
    }

    @Override
    public boolean hasAccount(OfflinePlayer offlinePlayer) {
        return hasAccount(offlinePlayer.getName());
    }

    @Override
    public double getBalance(String playerName) {
        Account account = Global.getAccounts().get(playerName);
        if (account == null) {
            return 0;
        }
        return account.getBalance();
    }

    @Override
    public double getBalance(OfflinePlayer offlinePlayer) {
        return getBalance(offlinePlayer.getName());
    }

    @Override
    public boolean has(String playerName, double amount) {
        Account account = Global.getAccounts().get(playerName);
        if (account == null) {
            return false;
        }
        return account.has(amount);
    }

    @Override
    public boolean has(OfflinePlayer offlinePlayer, double v) {
        return has(offlinePlayer.getName(), v);
    }

    @Override
    public EconomyResponse withdrawPlayer(String playerName, double amount) {
        Account account = Global.getAccounts().get(playerName);
        if (account == null) {
            return new EconomyResponse(0, 0, ResponseType.FAILURE, "Account doesn't exist");
        }
        if (account.has(amount)) {
            account.withdraw(amount);
            return new EconomyResponse(amount, account.getBalance(), ResponseType.SUCCESS, null);
        } else {
            return new EconomyResponse(0, account.getBalance(), ResponseType.FAILURE, "Insufficient funds");
        }
    }

    @Override
    public EconomyResponse withdrawPlayer(OfflinePlayer offlinePlayer, double v) {
        return withdrawPlayer(offlinePlayer.getName(), v);
    }

    @Override
    public EconomyResponse depositPlayer(String playerName, double amount) {
        Account account = Global.getAccounts().get(playerName);
        if (account == null) {
            return new EconomyResponse(0, 0, ResponseType.FAILURE, "Account doesn't exist");
        }
        account.deposit(amount);
        return new EconomyResponse(amount, account.getBalance(), ResponseType.SUCCESS, null);
    }

    @Override
    public EconomyResponse depositPlayer(OfflinePlayer offlinePlayer, double v) {
        return depositPlayer(offlinePlayer.getName(), v);
    }

    @Override
    public EconomyResponse createBank(String name, String player) {
        return new EconomyResponse(0, 0, ResponseType.NOT_IMPLEMENTED, getName() + " does not support Banks.");
    }

    @Override
    public EconomyResponse createBank(String s, OfflinePlayer offlinePlayer) {
        return new EconomyResponse(0, 0, ResponseType.NOT_IMPLEMENTED, getName() + " does not support Banks");
    }

    @Override
    public EconomyResponse deleteBank(String name) {
        return new EconomyResponse(0, 0, ResponseType.NOT_IMPLEMENTED, getName() + " does not support Banks.");
    }

    @Override
    public EconomyResponse bankBalance(String name) {
        return new EconomyResponse(0, 0, ResponseType.NOT_IMPLEMENTED, getName() + " does not support Banks.");
    }

    @Override
    public EconomyResponse bankHas(String name, double amount) {
        return new EconomyResponse(0, 0, ResponseType.NOT_IMPLEMENTED, getName() + " does not support Banks.");
    }

    @Override
    public EconomyResponse bankWithdraw(String name, double amount) {
        return new EconomyResponse(0, 0, ResponseType.NOT_IMPLEMENTED, getName() + " does not support Banks.");
    }

    @Override
    public EconomyResponse bankDeposit(String name, double amount) {
        return new EconomyResponse(0, 0, ResponseType.NOT_IMPLEMENTED, getName() + " does not support Banks.");
    }

    @Override
    public EconomyResponse isBankOwner(String name, String playerName) {
        return new EconomyResponse(0, 0, ResponseType.NOT_IMPLEMENTED, getName() + " does not support Banks.");
    }

    @Override
    public EconomyResponse isBankOwner(String s, OfflinePlayer offlinePlayer) {
        return new EconomyResponse(0, 0, ResponseType.NOT_IMPLEMENTED, getName() + " does not support Banks.");
    }

    @Override
    public EconomyResponse isBankMember(String name, String playerName) {
        return new EconomyResponse(0, 0, ResponseType.NOT_IMPLEMENTED, getName() + " does not support Banks.");
    }

    @Override
    public EconomyResponse isBankMember(String s, OfflinePlayer offlinePlayer) {
        return new EconomyResponse(0, 0, ResponseType.NOT_IMPLEMENTED, getName() + " does not support Banks.");
    }

    @Override
    public List<String> getBanks() {
        throw new UnsupportedOperationException(getName() + " does not support listing of Banks");
    }

    @Override
    public boolean createPlayerAccount(String playerName) {
        if (hasAccount(playerName)) {
            return false;
        }
        Account account = new Account(Global.getAccounts().size(), playerName, UUID.randomUUID());
        Global.addAccout(account);
        DatabaseManager.addAccount(account);
        return true;
    }

    @Override
    public boolean createPlayerAccount(OfflinePlayer offlinePlayer) {
        if (hasAccount(offlinePlayer.getName())) {
            return false;
        }
        Account account = new Account(Global.getAccounts().size(), offlinePlayer.getName(), offlinePlayer.getUniqueId());
        Global.addAccout(account);
        DatabaseManager.addAccount(account);
        return true;
    }
}
