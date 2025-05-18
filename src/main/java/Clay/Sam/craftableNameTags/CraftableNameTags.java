package Clay.Sam.craftableNameTags;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class CraftableNameTags extends JavaPlugin {

    @Override
    public void onEnable() {

        Plugin plugin = Bukkit.getPluginManager().getPlugin("CraftableNameTags");
        assert plugin != null;
        plugin.saveDefaultConfig();

        try {

            String line1 = plugin.getConfig().getString("recipe-line-1");
            String line2 = plugin.getConfig().getString("recipe-line-2");
            String line3 = plugin.getConfig().getString("recipe-line-3");

            ItemStack item1 = new ItemStack(Objects.requireNonNull(Material.getMaterial(plugin.getConfig().getString("item-1"))));
            ItemStack item2 = new ItemStack(Objects.requireNonNull(Material.getMaterial(plugin.getConfig().getString("item-2"))));
            ItemStack item3 = new ItemStack(Objects.requireNonNull(Material.getMaterial(plugin.getConfig().getString("item-3"))));
            ItemStack item4 = new ItemStack(Objects.requireNonNull(Material.getMaterial(plugin.getConfig().getString("item-4"))));
            ItemStack item5 = new ItemStack(Objects.requireNonNull(Material.getMaterial(plugin.getConfig().getString("item-5"))));
            ItemStack item6 = new ItemStack(Material.getMaterial(plugin.getConfig().getString("item-6")));
            ItemStack item7 = new ItemStack(Material.getMaterial(plugin.getConfig().getString("item-7")));
            ItemStack item8 = new ItemStack(Material.getMaterial(plugin.getConfig().getString("item-8")));
            ItemStack item9 = new ItemStack(Material.getMaterial(plugin.getConfig().getString("item-9")));

            if(line1 == null || line2 == null || line3 == null) {
                throw new NullPointerException();
            }

            ItemStack nameTag = new ItemStack(Material.NAME_TAG);

            try {
                ShapedRecipe recipe = new ShapedRecipe(nameTag);
                recipe.shape(line1, line2, line3);
                item1.getType();
                recipe.setIngredient('1', item1.getType());
                recipe.setIngredient('2', item2.getType());
                recipe.setIngredient('3', item3.getType());
                recipe.setIngredient('4', item4.getType());
                recipe.setIngredient('5', item5.getType());
                recipe.setIngredient('6', item6.getType());
                recipe.setIngredient('7', item7.getType());
                recipe.setIngredient('8', item8.getType());
                recipe.setIngredient('9', item9.getType());
                getServer().addRecipe(recipe);

            } catch (NullPointerException e) {
                getLogger().warning("One or more of the recipe lines in the config.yml is invalid. Please check your config.yml file.");
                return;
            }

        } catch (IllegalArgumentException e) {
            getLogger().warning("One or more of the items in the config.yml is invalid. Please check your config.yml file.");
            return;

            // Plugin startup logic
        } catch (NullPointerException e) {
            getLogger().warning("One or more of the recipe lines in the config.yml is invalid. Please check your config.yml file.");
            return;
        } catch (Exception e) {
            getLogger().warning("An unknown error occurred. Please check your config.yml file.");
            return;
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
