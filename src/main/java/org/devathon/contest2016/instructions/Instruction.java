package org.devathon.contest2016.instructions;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public abstract class Instruction {

	protected ItemStack icon;
	
	public abstract void execute(Entity entity);
	
	public abstract ItemStack getIcon();
	
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
