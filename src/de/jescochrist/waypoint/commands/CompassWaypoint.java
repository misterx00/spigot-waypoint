package de.jescochrist.waypoint.commands;

// Import packages
import de.jescochrist.generalplugin.misc.*;
import de.jescochrist.waypoint.main.Initiate;
import de.jescochrist.waypoint.support.WaypointObj;
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
	String messagePrefix = "�f[ �bWAYPOINT �f] ";
	
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
					
					// Check if player is in the correct world
					if (wp.getWorldUID().equals(p.getLocation().getWorld().getUID())) {
						
						// If world type is NORMAL
						if (Bukkit.getWorld(wp.getWorldUID()).getEnvironment() == Environment.NORMAL) {
							
							// Set players compass location to the waypoint coordinates
							p.setCompassTarget(new Location(
								Bukkit.getWorld(wp.getWorldUID()),
								((Double) wp.getX()).intValue(),
								((Double) wp.getY()).intValue(),
								((Double) wp.getZ()).intValue()
							));
							
							// Set waypoint as active in the configuration
							cfg.set("ActiveWaypoints." + p.getUniqueId() + "." + wp.getWorldUID().toString(), args[0]);
							
							// Save configuration file
							Initiate.getInstance().saveConfig();
							
							// Write information to player
							Write.writeToPlayer(p, messagePrefix + "Your compass target was set to waypoint �6" + args[0] + "�f.");
							
						}
						
						// If world type is not NORMAL
						else Write.writeToPlayer(p, messagePrefix + "�cYou can not set your compass location to worlds with an type other than overworld.");
						
					}
					
					// If player is not located in the correct world
					else Write.writeToPlayer(p, messagePrefix + "�cYou can not set your compass location to a world you are not located in.");
					
				}
				
				// If loading the waypoint was not successful
				else Write.writeToPlayer(p, messagePrefix + "�cThe waypoint �6" + args[0] + " �cdoes not exist.");
				
			}
			
			// Else send short message to inform player about the usage
			else Write.writeToPlayer(p, messagePrefix + "Usage: /cwp <waypoint name>");
			
		}
		
		// Return false
		return false;
		
	}
	
}
