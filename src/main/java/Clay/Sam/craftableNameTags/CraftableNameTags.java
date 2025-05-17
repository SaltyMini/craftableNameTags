package Clay.Sam.craftableNameTags;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class CraftableNameTags extends JavaPlugin {

    @Override
    public void onEnable() {

        Plugin plugin = Bukkit.getPluginManager().getPlugin("CraftableNameTags");
        plugin.saveDefaultConfig();

        try {

            String line1 = plugin.getConfig().getString("recipe-line-1");
            String line2 = plugin.getConfig().getString("recipe-line-2");
            String line3 = plugin.getConfig().getString("recipe-line-3");

            ItemStack item1 = new ItemStack(Material.getMaterial(plugin.getConfig().getString("item-1")));
            ItemStack item2 = new ItemStack(Material.getMaterial(plugin.getConfig().getString("item-2")));
            ItemStack item3 = new ItemStack(Material.getMaterial(plugin.getConfig().getString("item-3")));
            ItemStack item4 = new ItemStack(Material.getMaterial(plugin.getConfig().getString("item-4")));
            ItemStack item5 = new ItemStack(Material.getMaterial(plugin.getConfig().getString("item-5")));

            ItemStack nameTag = new ItemStack(Material.NAME_TAG);

            try {
                ShapedRecipe recipe = new ShapedRecipe(nameTag);
                recipe.shape(line1, line2, line3);
                recipe.setIngredient('1', item1.getType() != null ? item1.getType() : Material.BEDROCK);
                recipe.setIngredient('2', item2.getType() != null ? item2.getType() : Material.BEDROCK);
                recipe.setIngredient('3', item3.getType() != null ? item3.getType() : Material.BEDROCK);
                recipe.setIngredient('4', item4.getType() != null ? item4.getType() : Material.BEDROCK);
                recipe.setIngredient('5', item5.getType() != null ? item5.getType() : Material.BEDROCK);
                getServer().addRecipe(recipe);

            } catch (NullPointerException e) {
                getLogger().warning("One or more of the recipe lines in the config.yml is invalid. Please check your config.yml file.");
                return;
            }

        } catch (IllegalArgumentException e) {
            getLogger().warning("One or more of the items in the config.yml is invalid. Please check your config.yml file.");
            return;

            // Plugin startup logic


        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
