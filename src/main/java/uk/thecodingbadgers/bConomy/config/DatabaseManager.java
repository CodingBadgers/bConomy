package uk.thecodingbadgers.bConomy.config;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

import org.apache.commons.lang.Validate;
import org.bukkit.plugin.java.JavaPlugin;

import uk.thecodingbadgers.bConomy.Global;
import uk.thecodingbadgers.bConomy.account.Account;
import uk.thecodingbadgers.bDatabaseManager.bDatabaseManager;
import uk.thecodingbadgers.bDatabaseManager.bDatabaseManager.DatabaseType;

public class DatabaseManager {
	
	private static boolean working = true;
	private static final Pattern UUID_FORMAT = Pattern.compile("[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}");
	
	private static String transactions = "bConomy_Transactions";
	
	/** 
	 * Sets up the database and loads in all the infomation in the table
	 * 
	 * @param plugin - the java plugin used to setup the database, used to get the offline player
	 */
	public static void setupDatabase(JavaPlugin plugin) {
		
		// creates the database instance
		Global.m_database = bDatabaseManager.createDatabase(Config.m_dbInfo.dbname, Global.getPlugin(), Config.m_dbInfo.driver, Config.m_dbInfo.update);
		
		// login if sql 
		if (Config.m_dbInfo.driver == DatabaseType.SQL) {
			// logs in using values from the config
			if (!Global.m_database.login(Config.m_dbInfo.host, Config.m_dbInfo.user, Config.m_dbInfo.password, Config.m_dbInfo.port)) {
				working = false;
				return;
			}
		}
		
		if (!Global.m_database.tableExists(Config.m_dbInfo.tablename)) {
			
			Global.outputToConsole("Could not find 'accounts' table, creating default now.");
			
			// creates the accounts table
			String query = "CREATE TABLE " + Config.m_dbInfo.tablename + " (" +
							"id INT," +
							"username VARCHAR(64)," +
							"uuid VARCHAR(36)," +
							"balance DOUBLE" +
							");";
			
			Global.m_database.query(query, true);
		}
		
		// Create the tansactions table
		if (!Global.m_database.tableExists(transactions)) {
			
			Global.outputToConsole("Could not find 'transactions' table, creating default now.");
			
			// creates the accounts table
			String query = "CREATE TABLE `" + transactions + "` (" +
							"`from` VARCHAR(64)," + // stores uuid
							"`to` VARCHAR(64)," +   // stores uuid
							"`amount` DOUBLE," +
							"`when` DOUBLE" +
							");";
			
			Global.m_database.query(query, true);
		}
		
		String query = "SELECT * FROM " + Config.m_dbInfo.tablename;
		ResultSet result = Global.m_database.queryResult(query);
		
		if (result == null)
			return;
		
		// load in the accounts
		try {
			while(result.next()) {
				int id = result.getInt("id");
				UUID uuid = UUID.fromString(result.getString("uuid"));
				String name = result.getString("username");
				double balance = result.getDouble("balance");
				
				// create the account and then add it to the array
				Account account = new Account(id, name, uuid, balance);
				Global.addAccout(account);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Add a account to the database
	 * 
	 * @param account the account to add
	 */
	public static void addAccount(Account account) {
		Validate.notNull(account, "Cannot add a null account");
		
		if (!working)
			return;
		
		String query = 	"INSERT INTO " + Config.m_dbInfo.tablename + " " +
						"(`id`, `username`, `uuid`, `balance`) VALUES (" +
						"'" + account.getId() + "', " +
						"'" + account.getPlayerName() + "', " +
						"'" + account.getUniqueId() + "', " +
						"'" + account.getBalance() + "');";
				
		Global.m_database.query(query, true);
		
		Global.outputToConsole("Account " + account.getPlayerName() + " has been added to the database.");
	}
	
	/**
	 * Update a account on the database
	 * 
	 * @param account the account to update
	 */
	public static void updateAccount(Account account) {
		Validate.notNull(account, "Cannot update a null account");
		
		if (!working)
			return;
		
		String query = "UPDATE " + Config.m_dbInfo.tablename +
				 " SET balance='" + account.getBalance() + "' " +
				 "WHERE id='" + account.getId() + "';";
		
		Global.m_database.query(query);
 	}
	
	/**
	 * Update the username associated with the account
	 * 
	 * @param account to update
	 */
	public static void updateUsername(Account account) {
		Validate.notNull(account, "Cannot update a null account");
		
		if (!working)
			return;
		
		String query = "UPDATE " + Config.m_dbInfo.tablename +
				 " SET username='" + account.getPlayerName() + "' " +
				 "WHERE id='" + account.getId() + "';";
		
		Global.m_database.query(query);
	}
	
	/**
	 * Remove a account from the database
	 * 
	 * @param account to remove
	 */
	public static void removeAccount(Account account) {
		Validate.notNull(account, "Cannot remove a null account");
		
		if (!working)
			return;
		
		String query = "DELETE FROM `" + Config.m_dbInfo.tablename + "` WHERE `id` = " + account.getId() + ";";
		
		Global.m_database.query(query, true);
		Global.getAccounts().remove(account);		
		Global.outputToConsole("Removed the account " + account.getPlayerName() +" [" + account.getId() + "]");
	}
	
	/**
	 * Execute a one off query 
	 * 
	 * @param query to execute
	 * @return the results
	 */
	public static ResultSet executeQuery(String query) {
		
		if (!working)
			return null;
		
		return Global.m_database.queryResult(query);
	}

	/**
	 * Log a payment 
	 * 
	 * @param from Who is paying the money
	 * @param to Who is receiving the money
	 * @param amount The amount of money
	 */
	public static void logPayment(String from, String to, double amount) {
		Map<String, UUID> ids = getUUID(from, to);
		logPaymentUUID(ids.get(from) == null ? from : ids.get(from).toString(),
						ids.get(to) == null ? to : ids.get(to).toString(), 
						amount);
	}
	
	public static void logPaymentUUID(String from, String to, double amount) {

		if (!working)
			return;

		Long time = System.currentTimeMillis();
		
		String query = "INSERT INTO " + transactions + " " +
				"(`from`, `to`, `amount`, `when`) VALUES (" +
				"'" + from + "', " +
				"'" + to + "', " +
				"'" + amount + "', " +
				"'" + time + "');";
		
		Global.m_database.query(query);
	}

	public static ArrayList<String> getTransactions(String playerName) {
		ArrayList<String> playerTransactions = new ArrayList<String>();
		

		if (!working)
			return playerTransactions;
		
		String uuid = getUUID(playerName);
		
		String query = "SELECT * FROM " + transactions +
				" WHERE `from` = '" + uuid + "' OR `to` = '" + uuid + 
				"' ORDER BY `when` DESC LIMIT 10";
		
		ResultSet result = Global.m_database.queryResult(query);

		if (result != null) {
			try {
				while (result.next()) {
					String from = result.getString("from");
					String to = result.getString("to");
					double amount = result.getDouble("amount");
					double time = result.getDouble("when");

					SimpleDateFormat format = new SimpleDateFormat("dd-MM-yy HH:mm");
					String dateString = format.format(new Date((long) time));

					String fromName = amount > 0 ? from : to;
					String toName = amount > 0 ? to : from;

					if (UUID_FORMAT.matcher(fromName).matches()) {
						fromName = getUsername(UUID.fromString(fromName));
					}

					if (UUID_FORMAT.matcher(toName).matches()) {
						toName = getUsername(UUID.fromString(toName));
					}

					String action = String.format("[%1$s] %2$s paid %3$s %4$s", dateString, fromName, toName, Global.format(Math.abs(amount)));
					
					playerTransactions.add(action);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					result.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		return playerTransactions;
	}

	public static void getAccount(Account account) {

		if (!working)
			return;
		
		String query = "SELECT * FROM " + Config.m_dbInfo.tablename + " WHERE 'uuid'='" + account.getUniqueId() + "'";
		ResultSet result = Global.m_database.queryResult(query);
		
		if (result == null)
			return;
		
		// load in the accounts
		try {
			while(result.next()) {
				double balance = result.getDouble("balance");
				
				// create the account and then add it to the array
				account.setBalance(balance);	
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static String getUsername(UUID id) {
		if (!working)
			return id.toString();
		
		String query = "SELECT username FROM `" + Config.m_dbInfo.tablename + "` WHERE `uuid`='" + id.toString() + "';";
		ResultSet result = Global.m_database.queryResult(query);
		
		if (result == null) {
			return id.toString();
		}
		
		try {
			if (result.next()) {
				return result.getString("username");
			} else {
				return id.toString();
			}
		} catch (SQLException ex) {
			Global.getPlugin().getLogger().severe("Error executing query");
			Global.getPlugin().getLogger().severe("Query: " + query.toString());
			ex.printStackTrace();
			return id.toString();
		}
	}
	
	public static Map<UUID, String> getUsername(UUID... uuids) {
		Map<UUID, String> usernames = new HashMap<UUID, String>();

		if (!working)
			return usernames;
		
		StringBuilder query = new StringBuilder();
		query.append("SELECT `uuid`, `username` FROM `").append(Config.m_dbInfo.tablename).append("` WHERE");
		boolean first = true;
		
		for (UUID id : uuids) {
			if (!first) {
				query.append("OR"); 
			}

			query.append(" `uuid`='").append(id.toString()).append("' ");
			first = false;
		}
		
		query.append(";");
		
		ResultSet results = Global.m_database.queryResult(query.toString());
		
		try {
			while(results.next()) {
				usernames.put(UUID.fromString(results.getString("uuid")), results.getString("username"));
			}
		} catch (SQLException ex) {
			Global.getPlugin().getLogger().severe("Error executing query");
			Global.getPlugin().getLogger().severe("Query: " + query.toString());
			ex.printStackTrace();
		}
		
		return usernames;
	}
	
	public static String getUUID(String username) {
		if (!working)
			return username;
		
		String query = "SELECT `uuid` FROM `" + Config.m_dbInfo.tablename + "` WHERE `username`='" + username + "';";
		ResultSet result = Global.m_database.queryResult(query);
		
		if (result == null) {
			return username;
		}
		
		try {
			if (result.next()) {
				return result.getString("uuid");
			} else {
				return username;
			}
		} catch (SQLException ex) {
			Global.getPlugin().getLogger().severe("Error executing query");
			Global.getPlugin().getLogger().severe("Query: " + query.toString());
			ex.printStackTrace();
			return username;
		}
	}

	public static Map<String, UUID> getUUID(String... names) {
		Map<String, UUID> usernames = new HashMap<String, UUID>();

		if (!working)
			return usernames;
		
		StringBuilder query = new StringBuilder();
		query.append("SELECT `uuid`, `username` FROM `").append(Config.m_dbInfo.tablename).append("` WHERE");
		boolean first = true;
		
		for (String id : names) {
			if (!first) {
				query.append("OR"); 
			}
			
			query.append(" `username`='").append(id.toString()).append("' ");
			first = false;
		}
		
		query.append(";");
		
		System.out.println(query.toString());
		ResultSet results = Global.m_database.queryResult(query.toString());
		
		try {
			while(results.next()) {
				usernames.put(results.getString("username"),UUID.fromString(results.getString("uuid")));
			}
		} catch (SQLException ex) {
			Global.getPlugin().getLogger().severe("Error executing query");
			Global.getPlugin().getLogger().severe("Query: " + query.toString());
			ex.printStackTrace();
		}
		
		return usernames;
	}
	
}
