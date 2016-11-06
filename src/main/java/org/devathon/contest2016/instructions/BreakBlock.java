package org.devathon.contest2016.instructions;

import java.util.Arrays;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.ItemStack;

public class BreakBlock extends Instruction {
	
	public BreakBlock() {
		this.parameter = 0;
	}
	
	@Override
	public void execute(Entity entity) {
		Location loc = entity.getLocation();
		int yaw = (int) entity.getLocation().getYaw();
		if (yaw == 90) {
			new Location(loc.getWorld(), loc.getX() - parameter, loc.getY(), loc.getZ()).getBlock().breakNaturally(new ItemStack(Material.DIAMOND_PICKAXE));
		} else if (yaw == 0) {
			new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ() + parameter).getBlock().breakNaturally(new ItemStack(Material.DIAMOND_PICKAXE));
		} else if (yaw == -90) {
			new Location(loc.getWorld(), loc.getX() + parameter, loc.getY(), loc.getZ()).getBlock().breakNaturally(new ItemStack(Material.DIAMOND_PICKAXE));
		} else if (yaw == -180) {
			new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ() - parameter).getBlock().breakNaturally(new ItemStack(Material.DIAMOND_PICKAXE));
		}
	}
	
	@Override
	public BreakBlock clone() {
		return new BreakBlock();
	}
	
	@Override
	public void handleClick(InventoryAction action) {
		if (action.equals(InventoryAction.PICKUP_ALL)) {
			parameter = 1;
		} else if (action.equals(InventoryAction.PICKUP_HALF)) {
			parameter = 1;
		} else if (action.equals(InventoryAction.MOVE_TO_OTHER_INVENTORY)) {
			parameter = 1;
		}
	}
	
	@Override
	public ItemStack getIcon() {
		return this.generateIron(Material.IRON_PICKAXE, 1, (short) 0, "§aBreak Block", Arrays.asList("§e- Break the block infront", "§eof the zombie"));
	}
}
