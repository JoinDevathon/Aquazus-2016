package org.devathon.contest2016.instructions;

import java.util.Arrays;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;

public class MoveForward extends Instruction {
	
	public MoveForward() {}
	
	@Override
	public void execute(Entity entity) {
		Location loc = entity.getLocation();
		int yaw = (int) entity.getLocation().getYaw();
		if (yaw == 90) {
			entity.teleport(new Location(loc.getWorld(), loc.getX() - 1, loc.getY(), loc.getZ()));
		} else if (yaw == 0) {
			entity.teleport(new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ() + 1));
		} else if (yaw == -90) {
			entity.teleport(new Location(loc.getWorld(), loc.getX() + 1, loc.getY(), loc.getZ()));
		} else if (yaw == -180) {
			entity.teleport(new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ() - 1));
		}
	}
	
	@Override
	public MoveForward clone() {
		return new MoveForward();
	}
	
	@Override
	public ItemStack getIcon() {
		return this.generateIron(Material.IRON_BOOTS, 1, (short) 0, "§aMove Forward", Arrays.asList("§eMakes the bot move 1 block", "§ein front of him"));
	}
}
