package org.devathon.contest2016.instructions;

import java.util.Arrays;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftZombie;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class MoveForward extends Instruction {
	
	public MoveForward() {
		this.parameters = Arrays.asList(new Parameter(ParameterType.NUMBER, null));
	}
	
	@Override
	public void execute() {
		int blocks = Integer.parseInt(this.parameters.get(0).getValue());
		Location loc = this.entity.getLocation();
		float yaw = this.entity.getLocation().getYaw();
		if (yaw == 90.0) {
			((CraftZombie)this.entity).getHandle().getNavigation().a(loc.getX() - blocks, loc.getY(), loc.getZ(), 1);
		} else if (yaw == 0.0) {
			((CraftZombie)this.entity).getHandle().getNavigation().a(loc.getX(), loc.getY(), loc.getZ() + blocks, 1);
		} else if (yaw == -90.0) {
			((CraftZombie)this.entity).getHandle().getNavigation().a(loc.getX() + blocks, loc.getY(), loc.getZ(), 1);
		} else if (yaw == -180.0) {
			((CraftZombie)this.entity).getHandle().getNavigation().a(loc.getX(), loc.getY(), loc.getZ() - blocks, 1);
		}
	}
	
	@Override
	public MoveForward clone() {
		return new MoveForward();
	}
	
	@Override
	public ItemStack getIcon() {
		if (this.parameters.get(0).getValue() == null) {
			return this.generateIron(Material.IRON_BOOTS, 1, (short) 0, "§aMove Forward", Arrays.asList("§eParameters:", "§e - Amount of blocks"));
		}
		return this.generateIron(Material.IRON_BOOTS, 1, (short) 0, "§aMove Forward", Arrays.asList("§e" + Integer.parseInt(this.parameters.get(0).getValue()) + " blocks"));
	}
}
