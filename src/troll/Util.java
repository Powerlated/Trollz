package troll;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import net.coreprotect.CoreProtect;
import net.coreprotect.CoreProtectAPI;

public class Util {
	
	
	protected static boolean isOnline(String s) {
		if (Bukkit.getPlayer(s) != null) {
			return true;
		}
		return false;
	}
	
	protected static CoreProtectAPI getCoreProtect() {
		Plugin plugin = Troll.troll.getServer().getPluginManager().getPlugin("CoreProtect");

		// Check that CoreProtect is loaded
		if (plugin == null || !(plugin instanceof CoreProtect)) {
			return null;
		}

		// Check that the API is enabled
		CoreProtectAPI coreProtect = ((CoreProtect) plugin).getAPI();
		if (coreProtect.isEnabled() == false) {
			return null;
		}

		// Check that a compatible version of the API is loaded
		if (coreProtect.APIVersion() < 4) {
			return null;
		}

		return coreProtect;
	}
}
