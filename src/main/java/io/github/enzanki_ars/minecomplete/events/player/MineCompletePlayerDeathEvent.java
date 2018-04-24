package io.github.enzanki_ars.minecomplete.events.player;

import io.github.enzanki_ars.minecomplete.MineComplete;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.logging.Logger;

import static io.github.enzanki_ars.minecomplete.utils.MineCompleteScore.addScoreToPlayer;

public class MineCompletePlayerDeathEvent implements Listener {
    public static int EVENT_POINTS = 1;
    public static String EVENT_TYPE = "deaths";

    @EventHandler
    public void onPlayerDeathEvent(PlayerDeathEvent event) {
        JavaPlugin plugin = MineComplete.getPlugin(MineComplete.class);

        FileConfiguration config = plugin.getConfig();

        Logger log = plugin.getLogger();

        Entity entity = event.getEntity();
        Player player = (Player) entity;
        EntityDamageEvent lastDamageCause = entity.getLastDamageCause();
        EntityDamageEvent.DamageCause damageCause = lastDamageCause.getCause();

        String playerName = player.getName();
        String playerUUID = player.getUniqueId().toString();

        ConfigurationSection playerSection = config.getConfigurationSection(playerUUID);

        List<String> deathList = playerSection.getStringList(EVENT_TYPE);

        if (!deathList.contains(damageCause.name())) {
            deathList.add(damageCause.name());
            deathList.sort(String.CASE_INSENSITIVE_ORDER);

            playerSection.set(EVENT_TYPE, deathList);

            addScoreToPlayer(player, EVENT_POINTS, "dying", damageCause.name());
        }
    }
}
