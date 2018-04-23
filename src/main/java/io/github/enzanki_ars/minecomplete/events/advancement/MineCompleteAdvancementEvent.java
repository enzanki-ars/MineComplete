package io.github.enzanki_ars.minecomplete.events.advancement;

import io.github.enzanki_ars.minecomplete.MineComplete;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.logging.Logger;

import static io.github.enzanki_ars.minecomplete.utils.MineCompleteScore.addScoreToPlayer;

public class MineCompleteAdvancementEvent implements Listener {
    public static int EVENT_POINTS = 1;

    @EventHandler
    public void onPlayerAdvancementEvent(PlayerAdvancementDoneEvent event) {
        JavaPlugin plugin = MineComplete.getPlugin(MineComplete.class);

        FileConfiguration config = plugin.getConfig();

        Logger log = plugin.getLogger();

        String playerName = event.getPlayer().getName();
        String playerUUID = event.getPlayer().getUniqueId().toString();
        String advancementName = event.getAdvancement().getKey().getKey();

        ConfigurationSection playerSection = config.getConfigurationSection(playerUUID);

        List<String> advancementList = playerSection.getStringList("advancements");

        if (!advancementList.contains(advancementName)) {
            advancementList.add(advancementName);

            playerSection.set("advancements", advancementList);

            addScoreToPlayer(event.getPlayer(), EVENT_POINTS, "earning a new advancement", advancementName);
        } else {
            log.severe("Something weird happened with the advancements...");
            log.severe("Advancement " + advancementName + " already was completed...");
        }
    }
}
