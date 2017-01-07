package troll;

import java.util.HashMap;
import java.util.HashSet;

import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import me.libraryaddict.disguise.DisguiseAPI;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import me.libraryaddict.disguise.disguisetypes.MiscDisguise;

public class Trolls {
	private static HashMap<Player, HashSet<Entity>> disguisedMobs = new HashMap<Player, HashSet<Entity>>();

	protected static void target(Player p) {
		for (Entity e : p.getNearbyEntities(50, 50, 50)) {
			if (e instanceof Creature) {
				Creature c = (Creature) e;
				c.setTarget(p);
			}
		}
	}

	protected static void tnt(Player p) {
		HashSet<Entity> hs = new HashSet<Entity>();
		for (Entity e : p.getNearbyEntities(50, 50, 50)) {
			MiscDisguise tnt = new MiscDisguise(DisguiseType.PRIMED_TNT);
			DisguiseAPI.disguiseToPlayers(e, tnt, p);
			hs.add(e);
		}
		disguisedMobs.put(p, hs);
	}

	protected static void cancelTnt(Player p) {
		for (Entity s : disguisedMobs.get(p)) {
			DisguiseAPI.undisguiseToAll(s);
			disguisedMobs.remove(s);
		}
	}

	protected static void launch(Player p) {
		// CoreProtectAPI api = Util.getCoreProtect();
		// Location loc = p.getEyeLocation();
		// while(loc.getBlockY() <= 256){
		// if (loc.getBlock().getType() != Material.AIR) {
		// api.logRemoval("#troll", loc, loc.getBlock().getType(),
		// loc.getBlock().getData());
		// loc.getBlock().setType(Material.AIR);
		// }
		// loc.add(0, 1, 0);
		// }
		p.setVelocity(new Vector(0.0, 5.0, 0.0));
	}

	protected static void strike(Player p) {
		new BukkitRunnable() {
			boolean counting = false;
			int counter = 0;
			@Override
			public void run() {
				p.getWorld().strikeLightning(p.getLocation());
				if (p.getHealth() < 1) {
					counting = true;
				}
				if (counting) {
					counter++;
				}
				if (counter == 10) {
					this.cancel();
				}
			}

		}.runTaskTimer(Troll.troll, 2, 0);
	}
}
