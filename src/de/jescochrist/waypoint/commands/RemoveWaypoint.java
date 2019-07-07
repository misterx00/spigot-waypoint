package de.jescochrist.waypoint.commands;

// Import packages
import de.jescochrist.generalplugin.misc.*;
import de.jescochrist.waypoint.main.Initiate;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

// Define class RemoveWaypoint
public class RemoveWaypoint implements CommandExecutor {
	
	// Define class variables
	FileConfiguration cfg;
	
	// Define the constructor
	public RemoveWaypoint(FileConfiguration cfg) {
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
					
					// Set waypoint data to null
					cfg.set("Waypoints." + p.getUniqueId() + "." + args[0], null);
					
					// Save configuration file
					Initiate.getInstance().saveConfig();
					
					// Write information to player
					Write.writeToPlayer(p, messagePrefix + "§aThe waypoint §6" + args[0] + " §awas deleted successfully.");
					
				}
				
				// If any of the needed configuration values is a null value
				else Write.writeToPlayer(p, messagePrefix + "§cThe waypoint §6" + args[0] + " §cdoes not exist.");
				
			}
			
			// Else send short message to inform player about the usage
			else Write.writeToPlayer(p, messagePrefix + "Usage: /rmwp <waypoint name>");
			
		}
		
		// Return false
		return false;
		
	}
	
}
