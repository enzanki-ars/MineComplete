package io.github.enzanki_ars.minecomplete.events.item;

import io.github.enzanki_ars.minecomplete.MineComplete;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.logging.Logger;

import static io.github.enzanki_ars.minecomplete.utils.MineCompleteScore.addScoreToPlayer;

public class MineCompleteCraftItemEvent implements Listener {
    public static int EVENT_POINTS = 1;

    @EventHandler
    public void onPlayerCraftEvent(CraftItemEvent event) {
        Player player = (Player) event.getViewers().get(0);

        JavaPlugin plugin = MineComplete.getPlugin(MineComplete.class);

        FileConfiguration config = plugin.getConfig();

        String playerUUID = player.getUniqueId().toString();
        String itemName = event.getInventory().getResult().getType().name();

        ConfigurationSection playerSection = config.getConfigurationSection(playerUUID);

        List<String> itemList = playerSection.getStringList("items");

        if (!itemList.contains(itemName)) {
            itemList.add(itemName);
            playerSection.set("items", itemList);

            addScoreToPlayer(player, EVENT_POINTS, "crafting a new item", itemName);
        }
    }
}
