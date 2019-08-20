package de.jescochrist.waypoint.commands;

// Import packages
import de.jescochrist.generalplugin.misc.*;
import de.jescochrist.waypoint.support.WaypointObj;
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
				/*if (
					cfg.get("Waypoints." + p.getUniqueId() + "." + args[0] + ".WorldUID") != null &&
					cfg.get("Waypoints." + p.getUniqueId() + "." + args[0] + ".X") != null &&
					cfg.get("Waypoints." + p.getUniqueId() + "." + args[0] + ".Y") != null &&
					cfg.get("Waypoints." + p.getUniqueId() + "." + args[0] + ".Z") != null
				) {
					
					// If all needed destination configuration values are null values
					if (
						cfg.get("Waypoints." + p.getUniqueId() + "." + args[1] + ".WorldUID") == null &&
						cfg.get("Waypoints." + p.getUniqueId() + "." + args[1] + ".X") == null &&
						cfg.get("Waypoints." + p.getUniqueId() + "." + args[1] + ".Y") == null &&
						cfg.get("Waypoints." + p.getUniqueId() + "." + args[1] + ".Z") == null
					) {
						
						// Copy data from source waypoint to destination waypoint
						cfg.set("Waypoints." + p.getUniqueId() + "." + args[1] + ".WorldUID", cfg.get("Waypoints." + p.getUniqueId() + "." + args[0] + ".WorldUID"));
						cfg.set("Waypoints." + p.getUniqueId() + "." + args[1] + ".X", cfg.get("Waypoints." + p.getUniqueId() + "." + args[0] + ".X"));
						cfg.set("Waypoints." + p.getUniqueId() + "." + args[1] + ".Y", cfg.get("Waypoints." + p.getUniqueId() + "." + args[0] + ".Y"));
						cfg.set("Waypoints." + p.getUniqueId() + "." + args[1] + ".Z", cfg.get("Waypoints." + p.getUniqueId() + "." + args[0] + ".Z"));
						
						// Set source waypoint data to null
						cfg.set("Waypoints." + p.getUniqueId() + "." + args[0], null);
						
						// Save configuration file
						Initiate.getInstance().saveConfig();
						
						// Write information to player
						Write.writeToPlayer(p, messagePrefix + "§aThe waypoint §6" + args[0] + " §awas successfully moved to §6" + args[1] + "§a.");
						
					}
					
					// If any of the needed destination configuration values is not a null value
					else Write.writeToPlayer(p, messagePrefix + "§cThe destination waypoint §6" + args[1] + " §calready exists.");
					
				}
				
				// If any of the needed source configuration values is a null value
				else Write.writeToPlayer(p, messagePrefix + "§cThe source waypoint §6" + args[0] + " §cdoes not exist.");*/
				
				// Define new source waypoint object from player object and waypoint name
				WaypointObj srcwp = new WaypointObj(p, args[0]);
				
				// Load the source waypoint from the configuration file; If loading the source waypoint was successful
				if (srcwp.loadFromConfig(cfg)) {
					
					// Define new destination waypoint object from player object, waypoint name and variables from source waypoint object
					WaypointObj dstwp = new WaypointObj(p, args[1], srcwp.getWorldUID(), srcwp.getX(), srcwp.getY(), srcwp.getZ());
					
					// Save destination waypoint to config and catch result; If setting the destination waypoint was successful
					if (dstwp.writeToConfig(cfg)) {
						
						// Remove the source waypoint from the configuration file; If removing the source waypoint was successful
						if (srcwp.removeFromConfig(cfg)) {
							
							// Write information to player
							Write.writeToPlayer(p, messagePrefix + "§aThe waypoint §6" + args[0] + " §awas successfully moved to §6" + args[1] + "§a.");
							
						}
						
						// If removing the source waypoint was not successful
						Write.writeToPlayer(p, messagePrefix + "§cThe destination waypoint §6" + args[1] + " §cwas created, but the source waypoint §6" + args[0] + " §ccould not be deleted.");
						
					}
					
					// If setting the destination waypoint was not successful
					else Write.writeToPlayer(p, messagePrefix + "§cThe destination waypoint §6" + args[1] + " §calready exists.");
					
				}
				
				// If loading the source waypoint was not successful
				else Write.writeToPlayer(p, messagePrefix + "§cThe source waypoint §6" + args[0] + " §cdoes not exist.");
				
			}
			
			// Else send short message to inform player about the usage
			else Write.writeToPlayer(p, messagePrefix + "Usage: /mvwp <waypoint name>");
			
		}
		
		// Return false
		return false;
		
	}
	
}
