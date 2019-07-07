package de.jescochrist.waypoint.commands;

// Import packages
import de.jescochrist.generalplugin.misc.*;
import de.jescochrist.waypoint.main.Initiate;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.Location;
import org.bukkit.World.Environment;

// Define class CompassWaypoint
public class CompassWaypoint implements CommandExecutor {
	
	// Define class variables
	FileConfiguration cfg;
	
	// Define the constructor
	public CompassWaypoint(FileConfiguration cfg) {
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
					
					// Check if player is in the correct world
					if (UUID.fromString((String) cfg.get("Waypoints." + p.getUniqueId() + "." + args[0] + "." + "WorldUID")).equals(p.getLocation().getWorld().getUID())) {
					
						// If world type is NORMAL
						if (Bukkit.getWorld(UUID.fromString((String) cfg.get("Waypoints." + p.getUniqueId() + "." + args[0] + "." + "WorldUID"))).getEnvironment() == Environment.NORMAL) {
							
							// Set players compass location to the waypoint coordinates
							p.setCompassTarget(new Location(
								Bukkit.getWorld(UUID.fromString((String) cfg.get("Waypoints." + p.getUniqueId() + "." + args[0] + "." + "WorldUID"))),
								((Double) cfg.get("Waypoints." + p.getUniqueId() + "." + args[0] + "." + "X")).intValue(),
								((Double) cfg.get("Waypoints." + p.getUniqueId() + "." + args[0] + "." + "Y")).intValue(),
								((Double) cfg.get("Waypoints." + p.getUniqueId() + "." + args[0] + "." + "Z")).intValue()
							));
							
							// Set waypoint as active in the configuration
							cfg.set("ActiveWaypoints." + p.getUniqueId() + "." + p.getLocation().getWorld().getUID().toString(), args[0]);
							
							// Save configuration file
							Initiate.getInstance().saveConfig();
							
							// Write information to player
							Write.writeToPlayer(p, messagePrefix + "Your compass target was set to waypoint §6" + args[0] + "§f.");
							
						}
						
						// If world type is not NORMAL
						else Write.writeToPlayer(p, messagePrefix + "§cYou can not set your compass location to worlds with an type other than overworld.");
						
					}
					
					else Write.writeToPlayer(p, messagePrefix + "§cYou can not set your compass location to a world you are not located in.");
					
				}
				
				// If any of the needed configuration values is a null value
				else Write.writeToPlayer(p, messagePrefix + "§cThe waypoint §6" + args[0] + " §cdoes not exist.");
				
			}
			
			// Else send short message to inform player about the usage
			else Write.writeToPlayer(p, messagePrefix + "Usage: /cwp <waypoint name>");
			
		}
		
		// Return false
		return false;
		
	}
	
}
