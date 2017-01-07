package troll;

import static org.bukkit.ChatColor.AQUA;
import static org.bukkit.ChatColor.DARK_RED;
import static org.bukkit.ChatColor.RED;
import static org.bukkit.ChatColor.STRIKETHROUGH;
import static troll.Trolls.*;
import static troll.Util.*;

import java.util.Arrays;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public class Commands {
	private static HashMap<CommandSender, Integer> strikeFailure = new HashMap<CommandSender, Integer>();
	final static String TAG = DARK_RED + "[" + RED + "Trololol" + DARK_RED + "] " + AQUA;

	static boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(TAG + RED + "This command can only be used ingame!");
			return true;
		}
		Player s = (Player) sender;
		String sName = s.getName();
		// Disallow stupid players to run the command
		if (Arrays.asList(Troll.stupidPlayers).contains(sName.toLowerCase())) {
			sender.sendMessage(RED + "An internal error has occurred while attempting to perform this command");
			return true;
		}
		if (cmd.getName().equalsIgnoreCase("troll")) {

			// Down here, the command is targeting players
			if (args.length >= 1) {
				if (args[0].equals("tnt")) {
					if (args.length == 1 || args[1] == null) {
						sender.sendMessage(TAG + "Usage: /troll tnt <player>");
						return true;
					}
					if (args[1].equals("cancel")) {
						if (args.length == 2 || args[2] == null) {
							sender.sendMessage(TAG + "Usage: /troll tnt cancel <player>");
							return true;
						}
						if (isOnline(args[2])) {
							Player target = Bukkit.getPlayer(args[2]);
							cancelTnt(target);
							sender.sendMessage(TAG + target.getName() + "'s TNT troll has been canceled.");
							return true;
						} else {
							pnf(sender);
							return true;
						}
					}
					if (isOnline(args[1])) {
						Player target = Bukkit.getPlayer(args[1]);
						Trolls.tnt(target);
						sender.sendMessage(TAG + "Entities that are near " + target.getName() + " have been visually turned into primed TNT");
						return true;
					} else {
						pnf(sender);
					}
					return true;
				}

				if (args[0].equals("target")) {
					if (args.length == 1 || args[1] == null) {
						sender.sendMessage(TAG + "Usage: /troll target <player>");
						return true;
					}
					if (isOnline(args[1])) {
						Player target = Bukkit.getPlayer(args[1]);
						Trolls.target(target);
						sender.sendMessage(TAG + "Mobs near " + target.getName() + " have been forced to attack!");
						return true;
					} else {
						pnf(sender);
					}
					return true;
				}

				if (args[0].equals("launch")) {
					if (args.length == 1 || args[1] == null) {
						sender.sendMessage(TAG + "Usage: /troll launch <player>");
						return true;
					}
					if (isOnline(args[1])) {
						Player target = Bukkit.getPlayer(args[1]);
						Trolls.launch(target);
						sender.sendMessage(TAG + target.getName() + " has been launched!");
						return true;
					} else {
						pnf(sender);
					}
					return true;
				}

				if (args[0].equals("strike")) {
					if (args.length == 1 || args[1] == null) {
						sender.sendMessage(TAG + "Usage: /troll strike <player>");
						return true;
					}
					if (isOnline(args[1])) {
						Player target = Bukkit.getPlayer(args[1]);
						if (target.getGameMode() != GameMode.CREATIVE) {
							if (strikeFailure.get(sender) != null && strikeFailure.get(sender) >= 9) {
								sender.sendMessage(TAG + "You finally stopped trying to annoy me. Now get back to your trolling, dumbo. Do this command again to activate the troll.");
								strikeFailure.remove(sender);
								return true;
							}
							Trolls.strike(target);
							sender.sendMessage(TAG + target.getName() + " has been struck by lightning!");
							return true;
						} else {
							// How many times the troll has tried to strike a player
							// in creative mode
							int i = 0;
							if (strikeFailure.get(sender) != null) {
								i = strikeFailure.get(sender);
							}
							if (i == 0) {
								sender.sendMessage(TAG + "Hey " + sender.getName() + ", I think you are trying to strike a player in creative mode. Just saying.");
							} else if (i == 1) {
								sender.sendMessage(TAG + "If you need a reminder about not striking players in creative mode, here it is!");
							} else if (i == 2) {
								sender.sendMessage(TAG + "DUDE WHAT THE HECK.... Sorry about the caps, I usually get annoyed when people don't listen to me over and over again.");
							} else if (i == 3) {
								sender.sendMessage(TAG + "I give up at this... It's like trying to teach a 3 year old with an IQ of 10 about how to do calculus.");
							} else if (i == 4) {
								sender.sendMessage(TAG + "You're just doing this to watch me suffer, aren't you?");
							} else if (i == 5) {
								sender.sendMessage(TAG + "If you keep doing this I will just give you a counter of how many times you have tried to strike a player in creative mode.");
							} else if (i == 6) {
								sender.sendMessage(TAG + "Loading counter...");
							} else if (i == 7) {
								sender.sendMessage(TAG + "Counter is almost ready...");
							} else if (i == 8) {
								sender.sendMessage(TAG + "Here it is! After here the counter will be at number 9! Or I think so at least.");
							} else if (i == 100) {
								sender.sendMessage(TAG + "Wow, you are really good at this! Have a gold star! " + ChatColor.GOLD + "★");
							} else {
								sender.sendMessage(TAG + "The amount of times you have tried to annoy the plugin developer is " + i + ".");
							}
							strikeFailure.remove(sender);
							strikeFailure.put(sender, i + 1);
							return true;
						}
					} else {
						pnf(sender);
					}
				}
			}
			
			if (args.length == 0) {
				sender.sendMessage(AQUA + "" + STRIKETHROUGH + "----------" + AQUA + TAG + "" + STRIKETHROUGH + "----------");
				sender.sendMessage(AQUA + "tnt - Visually turns mobs into TNT around a player");
				sender.sendMessage(AQUA + "tnt cancel - Cancels the TNT troll");
				sender.sendMessage(AQUA + "target - Makes mobs target the player");
				sender.sendMessage(AQUA + "launch - Launches a player");
				sender.sendMessage(AQUA + "strike - Strikes a player until they die");
				return true;
			}
		}
		return true;
	}

	protected static void pnf(CommandSender p) {
		p.sendMessage(TAG + "Error: Player is not online");
	}
	
	
}
