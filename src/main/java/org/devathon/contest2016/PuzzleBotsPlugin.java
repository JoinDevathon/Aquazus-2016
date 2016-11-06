package org.devathon.contest2016;

import java.util.Arrays;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.devathon.contest2016.bots.BotsManager;
import org.devathon.contest2016.listeners.PlayerListener;

public class PuzzleBotsPlugin extends JavaPlugin {
	
	private ItemStack spawnItem;
	private BotsManager botsManager;
	private Logger logger;

    @Override
    public void onEnable() {
    	logger = this.getLogger();
        Bukkit.getPluginManager().registerEvents(new PlayerListener(this), this);
        registerSpawnItem();
        botsManager = new BotsManager(this);
        logger.info("Plugin enabled!");
    }

    @Override
    public void onDisable() {
    	logger.info("Plugin disabled!");
    }
    
    private void registerSpawnItem() {
    	spawnItem = new ItemStack(Material.IRON_BARDING);
    	ItemMeta spawnItemMeta = spawnItem.getItemMeta();
    	spawnItemMeta.setDisplayName("§aPuzzleBot");
    	spawnItemMeta.setLore(Arrays.asList("§7-> Right click to spawn"));
    	spawnItem.setItemMeta(spawnItemMeta);
    	ShapedRecipe recipe = new ShapedRecipe(spawnItem);
    	recipe.shape("EIE", "ICI", "III");
    	recipe.setIngredient('I', Material.IRON_BLOCK);
    	recipe.setIngredient('E', Material.EYE_OF_ENDER);
    	recipe.setIngredient('C', Material.END_CRYSTAL);
    	Bukkit.addRecipe(recipe);
    }
    
    public ItemStack getSpawnItem() {
    	return spawnItem;
    }
    
    public BotsManager getBotsManager() {
    	return botsManager;
    }
}

