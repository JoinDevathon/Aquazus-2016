package org.devathon.contest2016.instructions;

import java.util.Arrays;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;

public class BreakBlock extends Instruction {
	
	public BreakBlock() {}
	
	@Override
	public void execute(Entity entity) {
		Location loc = entity.getLocation();
		int yaw = (int) entity.getLocation().getYaw();
		if (yaw == 90) {
			new Location(loc.getWorld(), loc.getX() - 1, loc.getY(), loc.getZ()).getBlock().breakNaturally(new ItemStack(Material.DIAMOND_PICKAXE));
		} else if (yaw == 0) {
			new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ() + 1).getBlock().breakNaturally(new ItemStack(Material.DIAMOND_PICKAXE));
		} else if (yaw == -90) {
			new Location(loc.getWorld(), loc.getX() + 1, loc.getY(), loc.getZ()).getBlock().breakNaturally(new ItemStack(Material.DIAMOND_PICKAXE));
		} else if (yaw == -180) {
			new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ() - 1).getBlock().breakNaturally(new ItemStack(Material.DIAMOND_PICKAXE));
		}
	}
	
	@Override
	public BreakBlock clone() {
		return new BreakBlock();
	}
	
	@Override
	public ItemStack getIcon() {
		return this.generateIron(Material.IRON_PICKAXE, 1, (short) 0, "§aBreak Block", Arrays.asList("§e- Break the block infront", "§eof the bot"));
	}
}
