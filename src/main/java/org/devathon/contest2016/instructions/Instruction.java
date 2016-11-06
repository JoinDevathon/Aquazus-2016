package org.devathon.contest2016.instructions;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.devathon.contest2016.bots.ScriptEditor;

public abstract class Instruction {

	protected ItemStack icon;
	protected Entity entity;
	protected List<Parameter> parameters;
	
	public abstract void execute();
	
	public void setEntity(Entity entity) {
		this.entity = entity;
	}
	
	public abstract ItemStack getIcon();
	
	public void edit(Player player) {
		if (parameters.isEmpty()) {
			ScriptEditor.instances.get(player.getUniqueId()).addEntry(player, this);
			return;
		}
		
	}
	
	public abstract Instruction clone();
	
	public ItemStack generateIron(Material material, int amount, short damage, String name, List<String> lore) {
		ItemStack is = new ItemStack(material, amount, damage);
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(name);
		if (lore != null) {
			im.setLore(lore);
		}
		is.setItemMeta(im);
		return is;
	}
}
