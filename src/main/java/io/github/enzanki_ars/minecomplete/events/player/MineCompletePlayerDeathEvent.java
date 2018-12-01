package io.github.enzanki_ars.minecomplete.events.player;

import io.github.enzanki_ars.minecomplete.MineComplete;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

import static io.github.enzanki_ars.minecomplete.utils.MineCompleteScore.addScoreToPlayer;

public class MineCompletePlayerDeathEvent implements Listener {
    public static int EVENT_POINTS = 1;
    public static String EVENT_TYPE = "deaths";

    @EventHandler
    public void onPlayerDeathEvent(PlayerDeathEvent event) {
        JavaPlugin plugin = MineComplete.getPlugin(MineComplete.class);

        FileConfiguration config = plugin.getConfig();

        Player player = event.getEntity();
        ;
        EntityDamageEvent lastDamageCause = player.getLastDamageCause();
        EntityDamageEvent.DamageCause damageCause = lastDamageCause.getCause();

        String playerUUID = player.getUniqueId().toString();

        ConfigurationSection playerSection = config.getConfigurationSection(playerUUID);

        List<String> deathList = playerSection.getStringList(EVENT_TYPE);

        if (!deathList.contains(damageCause.toString())) {
            deathList.add(damageCause.toString());
            deathList.sort(String.CASE_INSENSITIVE_ORDER);
            playerSection.set(EVENT_TYPE, deathList);

            addScoreToPlayer(player, EVENT_POINTS, "new death type", damageCause.toString());
        }
    }
}
