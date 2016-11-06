package org.devathon.contest2016.instructions;

import java.util.List;

import org.bukkit.entity.Entity;

public abstract class Instruction {

	protected Entity entity;
	protected List<Parameter> parameters;
	
	public abstract void execute();
	
	public void setEntity(Entity entity) {
		this.entity = entity;
	}
}
