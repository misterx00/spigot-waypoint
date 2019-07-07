package de.jescochrist.waypoint.commands;

// Import packages
import de.jescochrist.generalplugin.misc.*;
import de.jescochrist.waypoint.main.Initiate;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.Location;

// Define class SetWaypoint
public class SetWaypoint implements CommandExecutor {
	
	// Define class variables
	FileConfiguration cfg;
	
	// Define the constructor
	public SetWaypoint(FileConfiguration cfg) {
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
				
				// If all needed configuration values are null values
				if (
					cfg.get("Waypoints." + p.getUniqueId() + "." + args[0] + "." + "WorldUID") == null &&
					cfg.get("Waypoints." + p.getUniqueId() + "." + args[0] + "." + "X") == null &&
					cfg.get("Waypoints." + p.getUniqueId() + "." + args[0] + "." + "Y") == null &&
					cfg.get("Waypoints." + p.getUniqueId() + "." + args[0] + "." + "Z") == null &&
					cfg.get("Waypoints." + p.getUniqueId() + "." + args[0] + "." + "Yaw") == null &&
					cfg.get("Waypoints." + p.getUniqueId() + "." + args[0] + "." + "Pitch") == null
				) {
					
					// Get players location
					Location l = p.getLocation();
					
					// Set configuration parameters
					cfg.set("Waypoints." + p.getUniqueId() + "." + args[0] + "." + "WorldUID", l.getWorld().getUID().toString());
					cfg.set("Waypoints." + p.getUniqueId() + "." + args[0] + "." + "X", l.getX());
					cfg.set("Waypoints." + p.getUniqueId() + "." + args[0] + "." + "Y", l.getY());
					cfg.set("Waypoints." + p.getUniqueId() + "." + args[0] + "." + "Z", l.getZ());
					cfg.set("Waypoints." + p.getUniqueId() + "." + args[0] + "." + "Yaw", l.getYaw());
					cfg.set("Waypoints." + p.getUniqueId() + "." + args[0] + "." + "Pitch", l.getPitch());
					
					// Save configuration file
					Initiate.getInstance().saveConfig();
					
					// Write information to player
					Write.writeToPlayer(p, messagePrefix + "§aThe waypoint §6" + args[0] + " §awas set successfully.");
				
				}
				
				// If any of the needed configuration values is not a null value
				else Write.writeToPlayer(p, messagePrefix + "§cThe waypoint §6" + args[0] + " §already exists.");
				
			}
			
			// Else send short message to inform player about the usage
			else Write.writeToPlayer(p, messagePrefix + "Usage: /setwp <waypoint name>");
			
		}
		
		// Return false
		return false;
		
	}
	
}
