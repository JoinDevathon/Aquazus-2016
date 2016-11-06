package org.devathon.contest2016.bots;

import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.devathon.contest2016.PuzzleBotsPlugin;

public class BotsManager {

	private PuzzleBotsPlugin plugin;
	private HashMap<Bot, String> bots = new HashMap<>();
	
	public BotsManager(PuzzleBotsPlugin plugin) {
		this.plugin = plugin;
	}
	
	public void createBot(Player owner, Location loc) {
		Bot bot = new Bot(owner, loc, this, plugin);
		bots.put(bot, owner.getUniqueId().toString());
	}
}
