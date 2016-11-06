package org.devathon.contest2016.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.devathon.contest2016.PuzzleBotsPlugin;

public class PlayerListener implements Listener {
	
	private PuzzleBotsPlugin plugin;
	
	public PlayerListener(PuzzleBotsPlugin plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onPlayerUseSpawnItem(PlayerInteractEvent event) {
		if (event.getHand() == EquipmentSlot.OFF_HAND) {
			event.setCancelled(true);
			return;
		}
		Player player = event.getPlayer();
		ItemStack item = event.getItem();
		if (event.getAction() == Action.RIGHT_CLICK_BLOCK && item != null && item.isSimilar(plugin.getSpawnItem())) {
			player.getInventory().remove(item);
			player.updateInventory();
			plugin.getBotsManager().createBot(player, event.getClickedBlock().getLocation().add(0, 1, 0));
		}
	}
}
