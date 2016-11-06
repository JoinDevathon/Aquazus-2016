package org.devathon.contest2016.instructions;

import java.util.Arrays;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.ItemStack;

public class MoveForward extends Instruction {
	
	public MoveForward() {
		this.parameter = 0;
	}
	
	@Override
	public void execute(Entity entity) {
		Location loc = entity.getLocation();
		int yaw = (int) entity.getLocation().getYaw();
		if (yaw == 90) {
			entity.teleport(new Location(loc.getWorld(), loc.getX() - parameter, loc.getY(), loc.getZ()));
		} else if (yaw == 0) {
			entity.teleport(new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ() + parameter));
		} else if (yaw == -90) {
			entity.teleport(new Location(loc.getWorld(), loc.getX() + parameter, loc.getY(), loc.getZ()));
		} else if (yaw == -180) {
			entity.teleport(new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ() - parameter));
		}
	}
	
	@Override
	public MoveForward clone() {
		return new MoveForward();
	}
	
	@Override
	public void handleClick(InventoryAction action) {
		if (action.equals(InventoryAction.PICKUP_ALL)) {
			parameter = 1;
		} else if (action.equals(InventoryAction.PICKUP_HALF)) {
			parameter = 5;
		} else if (action.equals(InventoryAction.MOVE_TO_OTHER_INVENTORY)) {
			parameter = 10;
		}
	}
	
	@Override
	public ItemStack getIcon() {
		if (parameter == 0) {
			return this.generateIron(Material.IRON_BOOTS, 1, (short) 0, "§aMove Forward", Arrays.asList("§e- Left click: 1 block", "§e- Right click: 5 blocks", "§e- Shift click: 10 blocks"));
		}
		return this.generateIron(Material.IRON_BOOTS, 1, (short) 0, "§aMove Forward", Arrays.asList("§e" + parameter + " blocks"));
	}
}
