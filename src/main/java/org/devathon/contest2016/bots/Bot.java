package org.devathon.contest2016.bots;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.devathon.contest2016.PuzzleBotsPlugin;
import org.devathon.contest2016.instructions.Instruction;

import net.minecraft.server.v1_10_R1.NBTTagCompound;

public class Bot implements Listener {

	private String owner;
	private Entity entity;
	private BotsManager botsManager;
	public PuzzleBotsPlugin plugin;
	private BotState state = BotState.INACTIVE;
	private int currentInstruction = 0;
	private int timerId;
	private int tickDelay = 20;
	private boolean rename = false;
	public HashMap<Integer, Instruction> instructions = new HashMap<>();
	
	public Bot(Player owner, Location loc, BotsManager botsManager, PuzzleBotsPlugin plugin) {
		this.owner = owner.getUniqueId().toString();
		this.botsManager = botsManager;
		this.entity = loc.getWorld().spawnEntity(loc, EntityType.ZOMBIE);
		this.plugin = plugin;
		Zombie entityZombie = (Zombie) entity;
		entityZombie.setCollidable(false);
		owner.setCollidable(false);
		entityZombie.setBaby(true);
		entityZombie.setCanPickupItems(false);
		entityZombie.setCustomName("§a" + owner.getName() + "'s §ePuzzleBot");
		entityZombie.setCustomNameVisible(true);
		entityZombie.getEquipment().setHelmet(new ItemStack(Material.IRON_HELMET));
		entityZombie.getEquipment().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
		entityZombie.getEquipment().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
		entityZombie.getEquipment().setBoots(new ItemStack(Material.IRON_BOOTS));
		net.minecraft.server.v1_10_R1.Entity nmsEntity = ((CraftEntity) this.entity).getHandle();
		NBTTagCompound compound = new NBTTagCompound();
		nmsEntity.c(compound);
        compound.setByte("Silent", (byte) 1);
        compound.setByte("NoAI", (byte) 1);
        nmsEntity.f(compound);
        Bukkit.getPluginManager().registerEvents(this, plugin);
        tick();
	}
	
	public void remove() {
		Bukkit.getScheduler().cancelTask(timerId);
		entity.getLocation().getWorld().playSound(entity.getLocation(), Sound.ENTITY_WITHER_BREAK_BLOCK, 1f, 1f);
		entity.getLocation().getWorld().spawnParticle(Particle.CLOUD, entity.getLocation(), 10);
		entity.remove();
		botsManager.removeBot(this);
	}
	
	public void tick() {
		timerId = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
			@Override
			public void run() {
				if (state == BotState.ACTIVE) {
					if (instructions.get(currentInstruction) == null) {
						currentInstruction = 0;
					}
					Instruction instruction = instructions.get(currentInstruction);
					instruction.execute(entity);
					currentInstruction++;
				}
			}
		}, 0L, tickDelay);
	}
	
	@EventHandler
	public void onEntityDamage(EntityDamageEvent event) {
		if (event.getEntity().equals(this.entity) && event.getCause() != DamageCause.VOID) {
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onEntityInteract(PlayerInteractEntityEvent event) {
		if (event.getHand() == EquipmentSlot.OFF_HAND) {
			event.setCancelled(true);
			return;
		}
		if (!event.getRightClicked().equals(this.entity)) {
			return;
		}
		Player player = event.getPlayer();
		if (!player.getUniqueId().toString().equals(this.owner)) {
			player.sendMessage("§cThis is not your PuzzleBot");
			return;
		}
		openGui(player);
	}
	
	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		if (!owner.equals(player.getUniqueId().toString())) {
			return;
		}
		if (rename) {
			rename = false;
			entity.setCustomName(event.getMessage());
			player.sendMessage("§aYou have renamed your bot !");
		}
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		if (!owner.equals(player.getUniqueId().toString())) {
			return;
		}
		Inventory inv = event.getInventory();
		ItemStack clicked = event.getCurrentItem();
		if (clicked == null) {
			return;
		}
		if (inv.getName().startsWith("State: ")) {
			event.setCancelled(true);
			if (clicked.getType() == Material.REDSTONE) {
				new ScriptEditor(player, this);
			} else if (clicked.getType() == Material.NAME_TAG) {
				player.closeInventory();
				rename = true;
				player.sendMessage("§ePlease now type the name desired for your bot :");
			} else if (clicked.getType() == Material.TNT) {
				remove();
			} else if (clicked.getType() == Material.WOOL && clicked.getDurability() != (short) 15) {
				if (clicked.getDurability() == (short) 5) {
					this.state = BotState.ACTIVE;
				} else if (clicked.getDurability() == (short) 4) {
					this.state = BotState.ACTIVE;
				} else if (clicked.getDurability() == (short) 1) {
					this.state = BotState.PAUSED;
				} else if (clicked.getDurability() == (short) 14) {
					this.state = BotState.INACTIVE;
					this.currentInstruction = 0;
				}
				player.closeInventory();
				openGui(player);
			}
		}
	}
	
	public void openGui(Player player) {
		Inventory mainMenu = Bukkit.createInventory(Bukkit.getPlayer(UUID.fromString(owner)), 9, "State: " + state.getText());
		mainMenu.setItem(0, generateButton(Material.REDSTONE, 1, (short) 0, "§aScript Editor"));
		mainMenu.setItem(1, generateButton(Material.NAME_TAG, 1, (short) 0, "§aRename"));
		mainMenu.setItem(2, generateButton(Material.TNT, 1, (short) 0, "§c§lDestroy"));
		if (state.equals(BotState.INACTIVE)) {
			mainMenu.setItem(6, generateButton(Material.WOOL, 1, (short) 5, "§aStart"));
			mainMenu.setItem(7, generateButton(Material.WOOL, 1, (short) 15, "§ePause §7(Not available)"));
			mainMenu.setItem(8, generateButton(Material.WOOL, 1, (short) 15, "§cStop §7(Not available)"));
		} else if (state.equals(BotState.PAUSED)) {
			mainMenu.setItem(6, generateButton(Material.WOOL, 1, (short) 15, "§aStart §7(Not available)"));
			mainMenu.setItem(7, generateButton(Material.WOOL, 1, (short) 4, "§eResume"));
			mainMenu.setItem(8, generateButton(Material.WOOL, 1, (short) 14, "§cStop"));
		} else if (state.equals(BotState.ACTIVE)) {
			mainMenu.setItem(6, generateButton(Material.WOOL, 1, (short) 15, "§aStart §7(Not available)"));
			mainMenu.setItem(7, generateButton(Material.WOOL, 1, (short) 1, "§6Pause"));
			mainMenu.setItem(8, generateButton(Material.WOOL, 1, (short) 14, "§cStop"));
		}
		player.openInventory(mainMenu);
	}
	
	private ItemStack generateButton(Material material, int amount, short damage, String name) {
		ItemStack is = new ItemStack(material, amount, damage);
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(name);
		is.setItemMeta(im);
		return is;
	}
}
