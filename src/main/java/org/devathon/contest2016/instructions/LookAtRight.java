package org.devathon.contest2016.instructions;

import java.util.Arrays;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;

public class LookAtRight extends Instruction {
	
	public LookAtRight() {}
	
	@Override
	public void execute(Entity entity) {
		Location loc = entity.getLocation();
		int yaw = (int) entity.getLocation().getYaw();
		if (yaw == 90) {
			loc.setYaw(-180f);
		} else if (yaw == 0) {
			loc.setYaw(90f);
		} else if (yaw == -90) {
			loc.setYaw(0f);
		} else if (yaw == -180) {
			loc.setYaw(-90f);
		}
		entity.teleport(loc);
	}
	
	@Override
	public LookAtRight clone() {
		return new LookAtRight();
	}
	
	@Override
	public ItemStack getIcon() {
		return this.generateIron(Material.IRON_HELMET, 1, (short) 0, "§aLook at right", Arrays.asList("§e- Ask the bot to look at", "§ehis right"));
	}
}
