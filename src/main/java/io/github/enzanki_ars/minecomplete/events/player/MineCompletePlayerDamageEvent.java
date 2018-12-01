package io.github.enzanki_ars.minecomplete.events.player;

import io.github.enzanki_ars.minecomplete.MineComplete;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

import static io.github.enzanki_ars.minecomplete.utils.MineCompleteScore.addScoreToPlayer;

public class MineCompletePlayerDamageEvent implements Listener {
    public static int EVENT_POINTS = 1;
    public static String EVENT_TYPE = "damages";

    @EventHandler
    public void onEntityDamageEvent(EntityDamageEvent event) {
        JavaPlugin plugin = MineComplete.getPlugin(MineComplete.class);

        FileConfiguration config = plugin.getConfig();

        if (event.getEntityType().equals(EntityType.PLAYER)) {
            Player player = (Player) event.getEntity();

            EntityDamageEvent lastDamageCause = player.getLastDamageCause();
            EntityDamageEvent.DamageCause damageCause = lastDamageCause.getCause();
            String playerUUID = player.getUniqueId().toString();

            ConfigurationSection playerSection = config.getConfigurationSection(playerUUID);

            List<String> damageList = playerSection.getStringList(EVENT_TYPE);

            if (!damageList.contains(damageCause.toString())) {
                damageList.add(damageCause.toString());
                damageList.sort(String.CASE_INSENSITIVE_ORDER);
                playerSection.set(EVENT_TYPE, damageList);

                addScoreToPlayer(player, EVENT_POINTS, "new damage type", damageCause.toString());
            }
        }
    }
}
