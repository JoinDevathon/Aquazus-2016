package org.devathon.contest2016.bots;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.devathon.contest2016.instructions.Instruction;
import org.devathon.contest2016.instructions.Instructions;

public class ScriptEditor implements Listener {
	
	public static HashMap<UUID, ScriptEditor> instances = new HashMap<>();
	private UUID uuid;
	private Bot bot;
	private Inventory menu;
	private ItemStack[] backup;

	public ScriptEditor(Player player, Bot bot) {
		this.uuid = player.getUniqueId();
		this.backup = player.getInventory().getContents();
		player.getInventory().clear();
		this.bot = bot;
		menu = Bukkit.createInventory(player, 54, "Script Editor");
		for (Entry<Integer, Instruction> entry : bot.instructions.entrySet()) {
			menu.setItem(entry.getKey(), entry.getValue().getIcon());
		}
		for (Instructions instructions : Instructions.values()) {
			player.getInventory().addItem(instructions.getInstruction().getIcon());
		}
		player.openInventory(menu);
		instances.put(this.uuid, this);
		Bukkit.getPluginManager().registerEvents(this, bot.plugin);
	}
	
	public void close() {
		Player player = Bukkit.getPlayer(uuid);
		player.getInventory().clear();
		player.getInventory().setContents(backup);
		instances.remove(uuid);
		uuid = null;
		bot = null;
		HandlerList.unregisterAll(this);
	}
}
