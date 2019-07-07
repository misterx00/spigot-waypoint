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
public class MoveWaypoint implements CommandExecutor {
	
	// Define class variables
	FileConfiguration cfg;
	
	// Define the constructor
	public MoveWaypoint(FileConfiguration cfg) {
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
			if (args.length == 2) {
				
				// If none of the needed source configuration values is a null value
				if (
					cfg.get("Waypoints." + p.getUniqueId() + "." + args[0] + "." + "WorldUID") != null &&
					cfg.get("Waypoints." + p.getUniqueId() + "." + args[0] + "." + "X") != null &&
					cfg.get("Waypoints." + p.getUniqueId() + "." + args[0] + "." + "Y") != null &&
					cfg.get("Waypoints." + p.getUniqueId() + "." + args[0] + "." + "Z") != null &&
					cfg.get("Waypoints." + p.getUniqueId() + "." + args[0] + "." + "Yaw") != null &&
					cfg.get("Waypoints." + p.getUniqueId() + "." + args[0] + "." + "Pitch") != null
				) {
					
					// If all needed destination configuration values are null values
					if (
						cfg.get("Waypoints." + p.getUniqueId() + "." + args[1] + "." + "WorldUID") == null &&
						cfg.get("Waypoints." + p.getUniqueId() + "." + args[1] + "." + "X") == null &&
						cfg.get("Waypoints." + p.getUniqueId() + "." + args[1] + "." + "Y") == null &&
						cfg.get("Waypoints." + p.getUniqueId() + "." + args[1] + "." + "Z") == null &&
						cfg.get("Waypoints." + p.getUniqueId() + "." + args[1] + "." + "Yaw") == null &&
						cfg.get("Waypoints." + p.getUniqueId() + "." + args[1] + "." + "Pitch") == null
					) {
						
						// Copy data from source waypoint to destination waypoint
						cfg.set("Waypoints." + p.getUniqueId() + "." + args[1] + "." + "WorldUID", cfg.get("Waypoints." + p.getUniqueId() + "." + args[0] + "." + "WorldUID"));
						cfg.set("Waypoints." + p.getUniqueId() + "." + args[1] + "." + "X", cfg.get("Waypoints." + p.getUniqueId() + "." + args[0] + "." + "X"));
						cfg.set("Waypoints." + p.getUniqueId() + "." + args[1] + "." + "Y", cfg.get("Waypoints." + p.getUniqueId() + "." + args[0] + "." + "Y"));
						cfg.set("Waypoints." + p.getUniqueId() + "." + args[1] + "." + "Z", cfg.get("Waypoints." + p.getUniqueId() + "." + args[0] + "." + "Z"));
						cfg.set("Waypoints." + p.getUniqueId() + "." + args[1] + "." + "Yaw", cfg.get("Waypoints." + p.getUniqueId() + "." + args[0] + "." + "Yaw"));
						cfg.set("Waypoints." + p.getUniqueId() + "." + args[1] + "." + "Pitch", cfg.get("Waypoints." + p.getUniqueId() + "." + args[0] + "." + "Pitch"));
						
						// Set source waypoint data to null
						cfg.set("Waypoints." + p.getUniqueId() + "." + args[0], null);
						
						// Save configuration file
						Initiate.getInstance().saveConfig();
						
						// Write information to player
						Write.writeToPlayer(p, messagePrefix + "§aThe waypoint §6" + args[0] + " §awas successfully moved to §6" + args[1] + " §a.");
						
					}
					
					// If any of the needed destination configuration values is not a null value
					else Write.writeToPlayer(p, messagePrefix + "§cThe destination waypoint §6" + args[1] + " §calready exists.");
					
				}
				
				// If any of the needed source configuration values is a null value
				else Write.writeToPlayer(p, messagePrefix + "§cThe source waypoint §6" + args[0] + " §cdoes not exist.");
				
			}
			
			// Else send short message to inform player about the usage
			else Write.writeToPlayer(p, messagePrefix + "Usage: /mvwp <waypoint name>");
			
		}
		
		// Return false
		return false;
		
	}
	
}
