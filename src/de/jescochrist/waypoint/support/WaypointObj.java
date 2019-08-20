package de.jescochrist.waypoint.support;

//Import packages
import de.jescochrist.waypoint.main.Initiate;
import java.util.UUID;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

//Define class WaypointObj
public class WaypointObj {
	
	// Define class variables
	private Player player;
	private String waypointName;
	private UUID worldUID;
	private double x;
	private double y;
	private double z;
	
	// Define the constructor (Player, String)
	public WaypointObj(Player player, String waypointName) {
		
		// Store the given values in the local objects variables
		this.player = player;
		this.waypointName = waypointName;
		
	}
	
	// Define the constructor (Player, String, UUID, 3x double)
	public WaypointObj(Player player, String waypointName, UUID worldUID, double x, double y, double z) {
		
		// Store the given values in the local objects variables
		this.player = player;
		this.waypointName = waypointName;
		this.worldUID = worldUID;
		this.x = x;
		this.y = y;
		this.z = z;
		
	}
	
	// Define method getWorldUUID
	public UUID getWorldUID() {
		return this.worldUID;
	}
	
	// Define method getX
	public double getX() {
		return this.x;
	}
	
	// Define method getY
	public double getY() {
		return this.y;
	}
	
	// Define method getZ
	public double getZ() {
		return this.z;
	}
	
	// Define method loadFromConfig
	public boolean loadFromConfig(FileConfiguration cfg) {
		
		// If none of the needed configuration values is a null value
		if (
			cfg.get("Waypoints." + player.getUniqueId() + "." + waypointName + ".WorldUID") != null &&
			cfg.get("Waypoints." + player.getUniqueId() + "." + waypointName + ".X") != null &&
			cfg.get("Waypoints." + player.getUniqueId() + "." + waypointName + ".Y") != null &&
			cfg.get("Waypoints." + player.getUniqueId() + "." + waypointName + ".Z") != null
		) {
			
			// Store the values from the configuration file in the local object variables
			this.worldUID = UUID.fromString((String) cfg.get("Waypoints." + player.getUniqueId() + "." + waypointName + ".WorldUID"));
			this.x = (Double) cfg.get("Waypoints." + player.getUniqueId() + "." + waypointName + ".X");
			this.y = (Double) cfg.get("Waypoints." + player.getUniqueId() + "." + waypointName + ".Y");
			this.z = (Double) cfg.get("Waypoints." + player.getUniqueId() + "." + waypointName + ".Z");
			
			// Return true (success)
			return true;
			
		}
		
		// If any of the needed configuration values is a null value return false (fail)
		else return false;
		
	}
	
	// Define method loadFromPlayerLocation
	public void loadFromPlayerLocation() {
		
		// Store the values from the player object in the local object variables
		this.worldUID = player.getLocation().getWorld().getUID();
		this.x = player.getLocation().getX();
		this.y = player.getLocation().getY();
		this.z = player.getLocation().getZ();
		
	}
	
	// Define method removeFromConfig
	public boolean removeFromConfig(FileConfiguration cfg) {
		
		// If none of the needed configuration values is a null value
		if (
			cfg.get("Waypoints." + player.getUniqueId() + "." + waypointName + ".WorldUID") != null &&
			cfg.get("Waypoints." + player.getUniqueId() + "." + waypointName + ".X") != null &&
			cfg.get("Waypoints." + player.getUniqueId() + "." + waypointName + ".Y") != null &&
			cfg.get("Waypoints." + player.getUniqueId() + "." + waypointName + ".Z") != null
		) {
			
			// Remove the waypoint from the configuration file
			cfg.set("Waypoints." + player.getUniqueId() + "." + waypointName, null);
			
			// Save configuration file
			Initiate.getInstance().saveConfig();
			
			// Return true (success)
			return true;
			
		}
		
		// If any of the needed configuration values is a null value return false (fail)
		else return false;
		
	}
	
	// Define method writeConfig
	public boolean writeToConfig(FileConfiguration cfg) {
		
		// If all needed configuration values are null values
		if (
			cfg.get("Waypoints." + player.getUniqueId() + "." + waypointName + ".WorldUID") == null &&
			cfg.get("Waypoints." + player.getUniqueId() + "." + waypointName + ".X") == null &&
			cfg.get("Waypoints." + player.getUniqueId() + "." + waypointName + ".Y") == null &&
			cfg.get("Waypoints." + player.getUniqueId() + "." + waypointName + ".Z") == null
		) {
			
			// Store the local object variables in the configuration file
			cfg.set("Waypoints." + player.getUniqueId() + "." + waypointName + ".WorldUID", this.worldUID.toString());
			cfg.set("Waypoints." + player.getUniqueId() + "." + waypointName + ".X", this.x);
			cfg.set("Waypoints." + player.getUniqueId() + "." + waypointName + ".Y", this.y);
			cfg.set("Waypoints." + player.getUniqueId() + "." + waypointName + ".Z", this.z);
			
			// Save configuration file
			Initiate.getInstance().saveConfig();
			
			// Return true (success)
			return true;
			
		}
		
		// If any of the needed configuration values is not a null value return false (fail)
		else return false;
		
	}
	
}
