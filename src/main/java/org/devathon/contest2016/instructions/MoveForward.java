package org.devathon.contest2016.instructions;

import java.util.Arrays;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftZombie;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.ItemStack;

public class MoveForward extends Instruction {
	
	public MoveForward() {
		this.parameter = 0;
	}
	
	@Override
	public void execute() {
		Location loc = this.entity.getLocation();
		float yaw = this.entity.getLocation().getYaw();
		if (yaw == 90.0) {
			((CraftZombie)this.entity).getHandle().getNavigation().a(loc.getX() - parameter, loc.getY(), loc.getZ(), 1);
		} else if (yaw == 0.0) {
			((CraftZombie)this.entity).getHandle().getNavigation().a(loc.getX(), loc.getY(), loc.getZ() + parameter, 1);
		} else if (yaw == -90.0) {
			((CraftZombie)this.entity).getHandle().getNavigation().a(loc.getX() + parameter, loc.getY(), loc.getZ(), 1);
		} else if (yaw == -180.0) {
			((CraftZombie)this.entity).getHandle().getNavigation().a(loc.getX(), loc.getY(), loc.getZ() - parameter, 1);
		}
	}
	
	@Override
	public MoveForward clone() {
		return new MoveForward();
	}
	
	@Override
	public void handleClick(InventoryAction action) {
		
	}
	
	@Override
	public ItemStack getIcon() {
		if (parameter == 0) {
			return this.generateIron(Material.IRON_BOOTS, 1, (short) 0, "§aMove Forward", Arrays.asList("§e- Left click: 1 block", "§e- Right click: 5 blocks", "§e- Middle click: 10 blocks"));
		}
		return this.generateIron(Material.IRON_BOOTS, 1, (short) 0, "§aMove Forward", Arrays.asList("§e" + parameter + " blocks"));
	}
}
