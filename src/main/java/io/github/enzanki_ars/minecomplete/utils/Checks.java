package io.github.enzanki_ars.minecomplete.utils;

import io.github.enzanki_ars.minecomplete.MineComplete;
import io.github.enzanki_ars.minecomplete.events.item.MineCompleteItemGetEvent;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

import static io.github.enzanki_ars.minecomplete.utils.MineCompleteScore.addScoreToPlayer;

public class Checks {
    public static void checkInventoryForNewItems(Player player) {
        FileConfiguration config = MineComplete.getPlugin(MineComplete.class).getConfig();

        String playerUUID = player.getUniqueId().toString();

        ConfigurationSection playerSection = config.getConfigurationSection(playerUUID);

        for (ItemStack item : player.getInventory()) {
            if (item != null) {
                String itemName = item.getType().name();

                List<String> itemList = playerSection.getStringList(MineCompleteItemGetEvent.EVENT_TYPE);

                if (!itemList.contains(itemName)) {
                    itemList.add(itemName);
                    itemList.sort(String.CASE_INSENSITIVE_ORDER);
                    playerSection.set(MineCompleteItemGetEvent.EVENT_TYPE, itemList);

                    addScoreToPlayer(player, MineCompleteItemGetEvent.EVENT_POINTS, "picking up a new item", itemName);
                }
            }
        }
    }
}
