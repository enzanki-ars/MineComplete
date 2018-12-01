package io.github.enzanki_ars.minecomplete.events.item;

import io.github.enzanki_ars.minecomplete.MineComplete;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.logging.Logger;

import static io.github.enzanki_ars.minecomplete.utils.MineCompleteScore.addScoreToPlayer;

public class MineCompleteItemGetEvent implements Listener {
    public static int EVENT_POINTS = 1;
    public static String EVENT_TYPE = "items";

    @EventHandler
    public void onPlayerItemGetEvent(EntityPickupItemEvent event) {
        if (event.getEntityType().equals(EntityType.PLAYER)) {
            Player player = (Player) event.getEntity();

            JavaPlugin plugin = MineComplete.getPlugin(MineComplete.class);

            FileConfiguration config = plugin.getConfig();

            String playerUUID = player.getUniqueId().toString();
            String itemName = event.getItem().getItemStack().getType().name();

            ConfigurationSection playerSection = config.getConfigurationSection(playerUUID);

            List<String> itemList = playerSection.getStringList(EVENT_TYPE);

            if (!itemList.contains(itemName)) {
                itemList.add(itemName);
                itemList.sort(String.CASE_INSENSITIVE_ORDER);
                playerSection.set(EVENT_TYPE, itemList);

                addScoreToPlayer(player, EVENT_POINTS, "picking up a new item", itemName);
            }
        }
    }
}
