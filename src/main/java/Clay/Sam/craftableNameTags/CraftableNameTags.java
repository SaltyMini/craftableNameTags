package Clay.Sam.craftableNameTags;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public final class CraftableNameTags extends JavaPlugin implements CommandExecutor {

    NamespacedKey key = new NamespacedKey(this, "craftableNameTag");

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (command.getLabel().equalsIgnoreCase("cnt") || command.getLabel().equalsIgnoreCase("craftablenametags")) {

            if(commandSender.hasPermission("craftablenametags.reload")) {
                commandSender.sendMessage("You do not have permission to use this command.");
                return true;
            }

            if (args[0].equalsIgnoreCase("reload")) {

                try {
                    reloadConfig();
                    Bukkit.removeRecipe(key);
                    onEnable();
                } catch (Exception e) {
                    commandSender.sendMessage("An error occurred while reloading the plugin. Please check the console for more details.");
                    getLogger().warning("Error reloading plugin: " + e.getMessage());
                } finally {
                    commandSender.sendMessage("Plugin reloaded");
                }
                return true;
            }

            if (args.length == 1) {
                commandSender.sendMessage("Use '/cnt help' to see available commands");
                return true;
            }

            return true;

        }
        return false;
    }



        @Override
    public void onEnable() {

        checkForUpdates();

        Plugin plugin = Bukkit.getPluginManager().getPlugin(this.getName());
        assert plugin != null;
        plugin.saveDefaultConfig();

        try {

            String[] lines = new String[3];
            lines[0] = plugin.getConfig().getString("recipe-line-1");
            lines[1] = plugin.getConfig().getString("recipe-line-2");
            lines[2] = plugin.getConfig().getString("recipe-line-3");

            Material[] items = new Material[9];
            for (int i = 0; i < 9; i++) {
                String key = "item-" + (i + 1);
                String matName = plugin.getConfig().getString(key);
                assert matName != null;
                Material mat = Material.getMaterial(matName);
                Objects.requireNonNull(mat, "Invalid material for " + key + " in config.yml");
                items[i] = mat;
            }

            ItemStack nameTag = new ItemStack(Material.NAME_TAG);

            try {

                Set<Character> usedSymbols = new HashSet<>();
                for (String line : lines) {
                    for (char c : line.toCharArray()) {
                        if (c != '#') usedSymbols.add(c);
                    }
                }

                NamespacedKey key = new NamespacedKey(this, "craftableNameTag");
                ShapedRecipe recipe = new ShapedRecipe(key, nameTag);
                recipe.shape(lines[0], lines[1], lines[2]);

                for (char symbol : usedSymbols) {
                    if (Character.isDigit(symbol)) {
                        int idx = Character.getNumericValue(symbol) - 1;
                        if (idx >= 0 && idx < items.length) {
                            recipe.setIngredient(symbol, items[idx]);
                        }
                    }
                }
                getServer().addRecipe(recipe);

            } catch (NullPointerException e) {
                getLogger().warning("Unable to create recipe");
                getLogger().warning("Warning: " + e.getMessage());
                getLogger().warning("One or more of the recipe lines in the config.yml is invalid. Please check your config.yml file.");
                return;
            }

        } catch (IllegalArgumentException e) {
            getLogger().warning("Warning: " + e.getMessage());
            getLogger().warning("One or more of the items in the config.yml is invalid. Please check your config.yml file.");
        } catch (NullPointerException e) {
            getLogger().warning("Warning: " + e.getMessage());
            getLogger().warning("One or more of the recipe lines in the config.yml is invalid. Please check your config.yml file.");
        } catch (Exception e) {
            getLogger().warning("Warning: " + e.getMessage());
            getLogger().warning("An unknown error occurred. Please check your config.yml file.");
        }
    }

    private void checkForUpdates() {
        new UpdateCheck(this, 125107).getVersion(version -> {
            String currentVersion = this.getDescription().getVersion();
            if (!currentVersion.equals(version)) {
                getLogger().info("There is a new update available!");
                getLogger().info("Your version: " + currentVersion);
                getLogger().info("Latest version: " + version);
                getLogger().info("Download the latest version from: https://www.spigotmc.org/resources/" + 125107);
            }
        });
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
