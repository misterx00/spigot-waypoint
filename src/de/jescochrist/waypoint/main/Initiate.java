package de.jescochrist.waypoint.main;

// Import packages
import de.jescochrist.generalplugin.misc.*;
import de.jescochrist.waypoint.commands.*;
import de.jescochrist.waypoint.schedulers.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

// Extend JavaPlugin class by Main class
public class Initiate extends JavaPlugin {
	
	// Save singleton to static variable
	private static Initiate instance;
	
	// Create getter method to return the singleton
	public static Initiate getInstance() {
		// Return instance
		return instance;
	}
	
	// Define configuration file
	FileConfiguration cfg = getConfig();
	
	// Define method that will be run when the plugin gets enabled
	public void onEnable() {
		instance = this;
		// Register all commands
		registerCommands();
		// Register all event handlers
		registerEventHandlers();
		// Register all schedulers
		registerSchedulers();
		// When plugin gets enabled, write information to console
		Write.writeToConsole("[ Waypoint ] Enabled plugin successfully");
	}
	
	// Define method that will be run when the plugin gets disabled
	public void onDisable() {
		// When plugin gets disabled, write information to console
		Write.writeToConsole("[ Waypoint ] Disabled plugin successfully");
	}
	
	// Define method to register all commands
	private void registerCommands() {
		// Register commands
		getCommand("cwp").setExecutor(new CompassWaypoint(cfg));
		getCommand("lswp").setExecutor(new ListWaypoints(cfg));
		getCommand("mvwp").setExecutor(new MoveWaypoint(cfg));
		getCommand("rmwp").setExecutor(new RemoveWaypoint(cfg));
		getCommand("setwp").setExecutor(new SetWaypoint(cfg));
		getCommand("wp").setExecutor(new Waypoint(cfg));
	}
	
	// Define method to register all event handlers
	private void registerEventHandlers() {
		// Create new plugin manager
		//PluginManager pm = Bukkit.getPluginManager();
		// Register events
		//pm.registerEvents(new CLASS(cfg), this);
	}
	
	// Define method to register all schedulers
	private void registerSchedulers() {
		// Create new scheduler
		BukkitScheduler bs = Bukkit.getServer().getScheduler();
		// Register schedulers
		bs.scheduleSyncRepeatingTask(this, new Runnable() {
			@Override
			public void run() {
				CompassWaypointScheduler.updateCompassLocation(cfg);
			}
		}, 0L, 100L);
	}
	
}
