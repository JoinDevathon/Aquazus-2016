package org.devathon.contest2016.bots;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.devathon.contest2016.instructions.Instruction;
import org.devathon.contest2016.instructions.Instructions;

public class ScriptEditor implements Listener {
	
	public static HashMap<UUID, ScriptEditor> instances = new HashMap<>();
	private UUID uuid;
	private Bot bot;
	private Inventory menu;
	private ItemStack[] backup;
	private HashMap<ItemStack, Instruction> itemToInstruction = new HashMap<>();
	private HashMap<ItemStack, Instruction> itemToRawInstruction = new HashMap<>();

	public ScriptEditor(Player player, Bot bot) {
		this.uuid = player.getUniqueId();
		if (instances.get(this.uuid) != null) {
			instances.get(this.uuid).close();
		}
		this.backup = player.getInventory().getContents();
		player.getInventory().clear();
		this.bot = bot;
		menu = Bukkit.createInventory(player, 54, "Script Editor");
		for (Entry<Integer, Instruction> entry : bot.instructions.entrySet()) {
			menu.setItem(entry.getKey(), entry.getValue().getIcon());
			itemToInstruction.put(entry.getValue().getIcon(), entry.getValue());
		}
		ItemStack none = new ItemStack(Material.STAINED_GLASS_PANE, 64, (short) 8);
		ItemMeta noneIm = none.getItemMeta();
		noneIm.setDisplayName("§7Empty");
		none.setItemMeta(noneIm);
		for (int i = 0; i < 9; i++) {
			player.getInventory().setItem(i, none);
		}
		for (Instructions instructions : Instructions.values()) {
			player.getInventory().addItem(instructions.getInstruction().getIcon());
			itemToRawInstruction.put(instructions.getInstruction().getIcon(), instructions.getInstruction());
		}
		for (int i = 0; i < 28; i++) {
			player.getInventory().addItem(none);
		}
		for (ItemStack item : player.getInventory().getContents()) {
			if (item == null) {
				continue;
			}
			if (item.getType() == Material.STAINED_GLASS_PANE) {
				item.setAmount(1);
			}
		}
		ItemStack help = new ItemStack(Material.BOOK, 1);
		ItemMeta helpIm = help.getItemMeta();
		helpIm.setDisplayName("§9Protip");
		helpIm.setLore(Arrays.asList("§aRight-click = remove"));
		help.setItemMeta(helpIm);
		menu.setItem(53, help);
		player.openInventory(menu);
		instances.put(this.uuid, this);
		Bukkit.getPluginManager().registerEvents(this, bot.plugin);
	}
	
	public void addEntry(Player player, Instruction instruction) {
		menu.addItem(instruction.getIcon());
		itemToInstruction.put(instruction.getIcon(), instruction);
		player.openInventory(menu);
	}
	
	public void close() {
		Player player = Bukkit.getPlayer(uuid);
		player.getInventory().clear();
		player.getInventory().setContents(backup);
		HashMap<Integer, Instruction> instructions = new HashMap<>();
		int count = 0;
		for (ItemStack items : menu.getContents()) {
			if (itemToInstruction.get(items) != null) {
				instructions.put(count, itemToInstruction.get(items));
				count++;
			}
		}
		bot.instructions = instructions;
		instances.remove(uuid);
		itemToInstruction = null;
		itemToRawInstruction = null;
		uuid = null;
		bot = null;
		HandlerList.unregisterAll(this);
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if (event.getWhoClicked().getUniqueId().equals(uuid)) {
			if (!event.getClickedInventory().getName().equalsIgnoreCase("Script Editor")) {
				event.setCancelled(true);
				ItemStack item = event.getCurrentItem();
				if (itemToRawInstruction.containsKey(item)) {
					Instruction instruction = itemToRawInstruction.get(item).clone();
					menu.addItem(instruction.getIcon());
					itemToInstruction.put(instruction.getIcon(), instruction);
				}
			} else if (event.getClickedInventory().getName().equalsIgnoreCase("Script Editor")) {
				ItemStack item = event.getCurrentItem();
				if (item.getType() == Material.BOOK) {
					event.setCancelled(true);
				}
				if (event.getAction() == InventoryAction.PICKUP_HALF) {
					event.setCancelled(true);
					menu.remove(item);
				}
			}
		}
	}
	
	@EventHandler
	public void onInventoryClose(InventoryCloseEvent event) {
		if (event.getPlayer().getUniqueId().equals(uuid)) {
			close();
		}
	}
}
