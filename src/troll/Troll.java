package troll;

import java.util.Random;

import org.bukkit.plugin.java.JavaPlugin;

public class Troll extends JavaPlugin {
	final static String[] stupidPlayers = {"zorlocinatorjaid"};
	static Troll troll;
	@Override
	public void onEnable() {
		troll = this;
		getLogger().info("Happy Trolling!");
	}

	@Override
	public void onDisable() {
		Random r = new Random();
		int i = r.nextInt(2);
		if (i == 0) {
			getLogger().info("Aw come on man, unfair!");
		} else if (i == 2) {
			getLogger().info("One more exploded player, please?");
		} else if (i == 3) {
			getLogger().info("I hope this is a reload. Never mind about that, reloads are dangerous!");
		}
	}
}
