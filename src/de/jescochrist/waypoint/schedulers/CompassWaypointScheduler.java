package de.jescochrist.waypoint.schedulers;

// Import packages
import java.util.UUID;
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
				
				// If none of the needed configuration values is a null value
				if (
					cfg.get("Waypoints." + p.getUniqueId() + "." + cfg.get("ActiveWaypoints." + p.getUniqueId() + "." + p.getLocation().getWorld().getUID().toString()) + "." + "WorldUID") != null &&
					cfg.get("Waypoints." + p.getUniqueId() + "." + cfg.get("ActiveWaypoints." + p.getUniqueId() + "." + p.getLocation().getWorld().getUID().toString()) + "." + "X") != null &&
					cfg.get("Waypoints." + p.getUniqueId() + "." + cfg.get("ActiveWaypoints." + p.getUniqueId() + "." + p.getLocation().getWorld().getUID().toString()) + "." + "Y") != null &&
					cfg.get("Waypoints." + p.getUniqueId() + "." + cfg.get("ActiveWaypoints." + p.getUniqueId() + "." + p.getLocation().getWorld().getUID().toString()) + "." + "Z") != null &&
					cfg.get("Waypoints." + p.getUniqueId() + "." + cfg.get("ActiveWaypoints." + p.getUniqueId() + "." + p.getLocation().getWorld().getUID().toString()) + "." + "Yaw") != null &&
					cfg.get("Waypoints." + p.getUniqueId() + "." + cfg.get("ActiveWaypoints." + p.getUniqueId() + "." + p.getLocation().getWorld().getUID().toString()) + "." + "Pitch") != null
				) {
					
					// Set players compass location to the waypoint coordinates
					p.setCompassTarget(new Location(
						Bukkit.getWorld(UUID.fromString((String) cfg.get("Waypoints." + p.getUniqueId() + "." + cfg.get("ActiveWaypoints." + p.getUniqueId() + "." + p.getLocation().getWorld().getUID().toString()) + "." + "WorldUID"))),
						((Double) cfg.get("Waypoints." + p.getUniqueId() + "." + cfg.get("ActiveWaypoints." + p.getUniqueId() + "." + p.getLocation().getWorld().getUID().toString()) + "." + "X")).intValue(),
						((Double) cfg.get("Waypoints." + p.getUniqueId() + "." + cfg.get("ActiveWaypoints." + p.getUniqueId() + "." + p.getLocation().getWorld().getUID().toString()) + "." + "Y")).intValue(),
						((Double) cfg.get("Waypoints." + p.getUniqueId() + "." + cfg.get("ActiveWaypoints." + p.getUniqueId() + "." + p.getLocation().getWorld().getUID().toString()) + "." + "Z")).intValue()
					));
					
				}
				
			}
			
		}
		
	}
	
}
