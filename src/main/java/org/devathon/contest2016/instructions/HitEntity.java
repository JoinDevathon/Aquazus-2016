package org.devathon.contest2016.instructions;

import java.util.Arrays;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

public class HitEntity extends Instruction {
	
	public HitEntity() {}
	
	@Override
	public void execute(Entity entity) {
		Location loc = entity.getLocation();
		int yaw = (int) entity.getLocation().getYaw();
		if (yaw == 90) {
			for (Entity entities : loc.getWorld().getNearbyEntities(new Location(loc.getWorld(), loc.getX() - 1, loc.getY(), loc.getZ()), 0.2, 1, 0.2)) {
				LivingEntity ent = (LivingEntity) entities;
				ent.damage(4);
				break;
			}
		} else if (yaw == 0) {
			for (Entity entities : loc.getWorld().getNearbyEntities(new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ() + 1), 0.2, 1, 0.2)) {
				LivingEntity ent = (LivingEntity) entities;
				ent.damage(4);
				break;
			}
		} else if (yaw == -90) {
			for (Entity entities : loc.getWorld().getNearbyEntities(new Location(loc.getWorld(), loc.getX() + 1, loc.getY(), loc.getZ()), 0.2, 1, 0.2)) {
				LivingEntity ent = (LivingEntity) entities;
				ent.damage(4);
				break;
			}
		} else if (yaw == -180) {
			for (Entity entities : loc.getWorld().getNearbyEntities(new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ() - 1), 0.2, 1, 0.2)) {
				LivingEntity ent = (LivingEntity) entities;
				ent.damage(4);
				break;
			}
		}
	}
	
	@Override
	public HitEntity clone() {
		return new HitEntity();
	}
	
	@Override
	public ItemStack getIcon() {
		return this.generateIron(Material.IRON_SWORD, 1, (short) 0, "§aHit Entity", Arrays.asList("§e- Hit the entity infront", "§eof the bot"));
	}
}
