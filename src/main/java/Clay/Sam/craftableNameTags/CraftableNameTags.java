package Clay.Sam.craftableNameTags;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
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

            Material[] items = new Material[9];
            for (int i = 0; i < 9; i++) {
                String key = "item-" + (i + 1);
                String matName = plugin.getConfig().getString(key);
                assert matName != null;
                Material mat = Material.getMaterial(matName);
                Objects.requireNonNull(mat, "Invalid material for " + key + " in config.yml");
                items[i] = mat;
            }

            if(line1 == null || line2 == null || line3 == null) {
                throw new NullPointerException();
            }

            ItemStack nameTag = new ItemStack(Material.NAME_TAG);

            try {
                NamespacedKey key = new NamespacedKey(this, "craftableNameTag");
                ShapedRecipe recipe = new ShapedRecipe(key, nameTag);
                recipe.shape(line1, line2, line3);
                recipe.setIngredient('1', items[0]);
                recipe.setIngredient('2', items[1]);
                recipe.setIngredient('3', items[2]);
                recipe.setIngredient('4', items[3]);
                recipe.setIngredient('5', items[4]);
                recipe.setIngredient('6', items[5]);
                recipe.setIngredient('7', items[6]);
                recipe.setIngredient('8', items[7]);
                recipe.setIngredient('9', items[8]);
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
