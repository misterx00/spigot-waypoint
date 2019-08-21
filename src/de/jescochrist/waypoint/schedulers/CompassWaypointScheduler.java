package de.jescochrist.waypoint.schedulers;

// Import packages
import de.jescochrist.waypoint.support.WaypointObj;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.Location;

// Define class CompassWaypointScheduler
public class CompassWaypointScheduler {
	
	// Define method updateCompassLocation
	public static void updateCompassLocation(FileConfiguration cfg) {
		
		// Loop through all online players
		for (Player p : Bukkit.getOnlinePlayers()) {
			
			// If none of the needed configuration values is a null value
			if (cfg.get("ActiveWaypoints." + p.getUniqueId() + "." + p.getLocation().getWorld().getUID().toString()) != null) {
				
				// Define new waypoint object from player object and waypoint name
				WaypointObj wp = new WaypointObj(p, (String) cfg.get("ActiveWaypoints." + p.getUniqueId() + "." + p.getLocation().getWorld().getUID().toString()));
				
				// Load the waypoint from the configuration file; If loading the waypoint was successful
				if (wp.loadFromConfig(cfg)) {
					
					// Set players compass location to the waypoint coordinates
					p.setCompassTarget(new Location(
						Bukkit.getWorld(wp.getWorldUID()),
						((Double) wp.getX()).intValue(),
						((Double) wp.getY()).intValue(),
						((Double) wp.getZ()).intValue()
					));
					
				}
				
			}
			
		}
		
	}
	
}
