package de.jescochrist.waypoint.commands;

// Import packages
import de.jescochrist.generalplugin.misc.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

// Define class ListWaypoints
public class ListWaypoints implements CommandExecutor {
	
	// Define class variables
	FileConfiguration cfg;
	
	// Define the constructor
	public ListWaypoints(FileConfiguration cfg) {
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
				
			// If no arguments were passed to the command
			if (args.length == 0) {
				
				// Get the configuration section
				ConfigurationSection cfgSection = cfg.getConfigurationSection("Waypoints." + p.getUniqueId());
				
				// If cfgSection is empty, it will yield a NullPointerException
				try {
					
					// Define string keys
					String keys = "";
					
					// Run over all keys in the configuration section
					for (String key : cfgSection.getKeys(false)) {
						
						// Append keys string by the current key
						keys += "§6" + key + "§f, ";
						
					}
					
					// If keys string is not empty
					if (keys != "") {
						
						// Remove the last two chars at the end of the string
						keys = keys.substring(0, keys.length() - 2);
						
						// Write information to player
						Write.writeToPlayer(p, messagePrefix + "Your set waypoints: " + keys);
						
					}
					
					// If the keys string is empty the player has no set waypoints
					else { Write.writeToPlayer(p, messagePrefix + "§cYou have no set waypoints."); }
					
				}
				
				// Catch NullPointerException
				catch (NullPointerException e) { Write.writeToPlayer(p, messagePrefix + "§cYou have no set waypoints."); }
				
			}
			
			// Else send short message to inform player about the usage
			else Write.writeToPlayer(p, messagePrefix + "Usage: /lswp");
			
		}
		
		// Return false
		return false;
		
	}
	
}
