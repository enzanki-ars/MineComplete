package io.github.enzanki_ars.minecomplete.events.item;

import io.github.enzanki_ars.minecomplete.MineComplete;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

import static io.github.enzanki_ars.minecomplete.utils.MineCompleteScore.addScoreToPlayer;

public class MineCompleteFishEvent implements Listener {
    public static int EVENT_POINTS = 1;
    public static String EVENT_TYPE = "fished";

    @EventHandler(ignoreCancelled = true)
    public void onPlayerFishEvent(PlayerFishEvent event) {
        Player player = event.getPlayer();

        JavaPlugin plugin = MineComplete.getPlugin(MineComplete.class);

        FileConfiguration config = plugin.getConfig();

        String playerUUID = player.getUniqueId().toString();

        if (event.getState().equals(PlayerFishEvent.State.CAUGHT_FISH)) {
            Item fished = (Item) event.getCaught();

            String itemName = fished.getItemStack().getType().name();
            System.out.println(fished.getItemStack());

            ConfigurationSection playerSection = config.getConfigurationSection(playerUUID);

            List<String> fishedList = playerSection.getStringList(EVENT_TYPE);

            if (!fishedList.contains(itemName)) {
                fishedList.add(itemName);
                fishedList.sort(String.CASE_INSENSITIVE_ORDER);
                playerSection.set(EVENT_TYPE, fishedList);

                addScoreToPlayer(player, EVENT_POINTS, "fishing", itemName);

            }
        }
    }
}
