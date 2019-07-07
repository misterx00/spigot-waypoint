package de.jescochrist.waypoint.commands;

// Import packages
import de.jescochrist.generalplugin.misc.*;
import java.util.UUID;
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
				
				// If none of the needed configuration values is a null value
				if (
					cfg.get("Waypoints." + p.getUniqueId() + "." + args[0] + "." + "WorldUID") != null &&
					cfg.get("Waypoints." + p.getUniqueId() + "." + args[0] + "." + "X") != null &&
					cfg.get("Waypoints." + p.getUniqueId() + "." + args[0] + "." + "Y") != null &&
					cfg.get("Waypoints." + p.getUniqueId() + "." + args[0] + "." + "Z") != null &&
					cfg.get("Waypoints." + p.getUniqueId() + "." + args[0] + "." + "Yaw") != null &&
					cfg.get("Waypoints." + p.getUniqueId() + "." + args[0] + "." + "Pitch") != null
				) {
					
					// Write world name and coordinates to player
					Write.writeToPlayer(p, messagePrefix + "Coordinates for waypoint §6" + args[0] + " §f- " +
						"World: " + Bukkit.getWorld(UUID.fromString((String) cfg.get("Waypoints." + p.getUniqueId() + "." + args[0] + "." + "WorldUID"))).getName() + ", " +
						"X: " + ((Double) cfg.get("Waypoints." + p.getUniqueId() + "." + args[0] + "." + "X")).intValue() + ", " +
						"Y: " + ((Double) cfg.get("Waypoints." + p.getUniqueId() + "." + args[0] + "." + "Y")).intValue() + ", " +
						"Z: " + ((Double) cfg.get("Waypoints." + p.getUniqueId() + "." + args[0] + "." + "Z")).intValue()
					);
					
				}
				
				// If any of the needed configuration values is a null value
				else Write.writeToPlayer(p, messagePrefix + "§cThe waypoint §6" + args[0] + " §cdoes not exist.");
				
			}
			
			// Else send short message to inform player about the usage
			else Write.writeToPlayer(p, messagePrefix + "Usage: /wp <waypoint name>");
			
		}
		
		// Return false
		return false;
		
	}
	
}
