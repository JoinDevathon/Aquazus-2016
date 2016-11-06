package org.devathon.contest2016.instructions;

import java.util.Arrays;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftZombie;

public class MoveForward extends Instruction {
	
	public MoveForward(int blocks) {
		this.parameters = Arrays.asList(new Parameter(ParameterType.NUMBER, ""+blocks));
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
}
