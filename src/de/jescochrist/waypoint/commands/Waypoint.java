package de.jescochrist.waypoint.commands;

// Import packages
import de.jescochrist.generalplugin.misc.*;
import de.jescochrist.waypoint.support.WaypointObj;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

// Define class Waypoint
public class Waypoint implements CommandExecutor {
	
	// Define class variables
	FileConfiguration cfg;
	
	// Define the constructor
	public Waypoint(FileConfiguration cfg) {
		// Store the cfg object in the local classes cfg object
		this.cfg = cfg;
	}
	
	// Define class variable
	String messagePrefix = "§f[ §bWAYPOINT §f] ";
	
	// Define method onCommand
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		// If sender is a player and not the console or a command block
		if (CheckSender.isPlayer(sender)) {
			
			// Define new player object from sender
			Player p = (Player) sender;
			
			// If one argument was passed to the command
			if (args.length == 1) {
				
				// Define new waypoint object from player object and waypoint name
				WaypointObj wp = new WaypointObj(p, args[0]);
				
				// Load the waypoint from the configuration file; If loading the waypoint was successful
				if (wp.loadFromConfig(cfg)) {
					
					// Write world name and coordinates to player
					Write.writeToPlayer(p, messagePrefix + "Coordinates for waypoint §6" + args[0] + " §f- " +
						"World: " + Bukkit.getWorld(wp.getWorldUID()).getName() + ", " +
						"X: " + ((Double) wp.getX()).intValue() + ", " +
						"Y: " + ((Double) wp.getY()).intValue() + ", " +
						"Z: " + ((Double) wp.getZ()).intValue()
					);
					
				}
				
				// If loading the waypoint was not successful
				else Write.writeToPlayer(p, messagePrefix + "§cThe waypoint §6" + args[0] + " §cdoes not exist.");
				
			}
			
			// Else send short message to inform player about the usage
			else Write.writeToPlayer(p, messagePrefix + "Usage: /wp <waypoint name>");
			
		}
		
		// Return false
		return false;
		
	}
	
}
