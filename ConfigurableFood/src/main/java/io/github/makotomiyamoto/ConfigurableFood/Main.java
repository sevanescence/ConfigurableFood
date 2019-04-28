package io.github.makotomiyamoto.ConfigurableFood;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;

public class Main extends JavaPlugin {

    public HashMap<String, CustomFood> Foods;

    public boolean DebugMode;

    @Override
    public void onEnable() {

        this.getConfig().options().copyDefaults(true);

        this.saveConfig();

        if (this.getConfig().getBoolean("enabled")) {

            DebugMode = this.getConfig().getBoolean("debug-mode");

            Foods = setFoodsList();

            // register events when Events.java is complete

        }

    }

    public void debug(String string) {

        if (DebugMode) {

            System.out.println("[ConfigurableFood] " + string);

        }

    }

    public void error(String string) {

        System.out.println("[ConfigurableFood] ERROR: " + string);

    }

    private HashMap<String, CustomFood> setFoodsList() {

        HashMap<String, CustomFood> stringCustomFoodHashMap = new HashMap<String, CustomFood>();

        ConfigurationSection foodsSection = this.getConfig().getConfigurationSection("Foods");

        debug("Checking for Foods list...");
        if (foodsSection == null) {
            error("Foods is not set. List of foods will not be created, since there is no list to create.");
            return stringCustomFoodHashMap;
        }
        debug("Foods list exists! Now, I'm going to set all of the items...");

        for (String foodKey : foodsSection.getKeys(false)) {

            debug("Scanning item " + foodKey + "...");

            String itemType = foodsSection.getString(foodKey + ".item-type");

            if (itemType == null) {
                error("item-type is undefined in " + foodKey);
                continue;
            } else {
                itemType = itemType.toUpperCase();
            }

            debug("item-type of " + foodKey + " is " + itemType);

            int saturation = this.getConfig().getInt("default-saturation");

            if (foodsSection.getString(foodKey + ".saturation") != null) {
                debug("Setting saturation of " + foodKey + " to " + foodsSection.getString(foodKey + ".saturation"));
                saturation = foodsSection.getInt(foodKey + ".saturation");
            } else {
                debug("Saturation of " + foodKey + " is not set, and will thus keep the default value of " + saturation);
            }

            double health = this.getConfig().getDouble("default-health");
            if (foodsSection.getString(foodKey + ".health") != null) {
                debug("Setting health of " + foodKey + " to " + foodsSection.getString(foodKey + ".health"));
                health = foodsSection.getDouble(foodKey + ".health");
            } else {
                debug("Health of " + foodKey + " is not set, and will thus keep the default value of " + health);
            }

            ItemStack itemStack = new ItemStack(Material.valueOf(itemType));
            ItemMeta itemMeta = itemStack.getItemMeta();

            String itemName = foodsSection.getString(foodKey + ".item-name");
            if (itemName != null && itemMeta != null) {
                debug("Item name set to " + itemName + "!");
                itemMeta.setDisplayName(itemName);
            }

            ArrayList<String> itemLore = new ArrayList<String>(foodsSection.getStringList(foodKey + ".item-lore"));
            if (itemLore.size() > 0 && itemMeta != null) {
                for (int loopIndex = 0; loopIndex < itemLore.size(); loopIndex++) {
                    itemLore.set(loopIndex, itemLore.get(loopIndex).replaceAll("&", "§"));
                }
                debug("Item lore set!");
            }

            itemStack.setItemMeta(itemMeta);

            CustomFood customFood = new CustomFood(saturation, health, itemStack, itemLore);

            stringCustomFoodHashMap.put(foodKey, customFood);

            debug(foodKey + " added to the HashMap!");

        }

        debug("setFoodsList() complete!");

        return stringCustomFoodHashMap;

    }

}
