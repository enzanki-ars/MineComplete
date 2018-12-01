package io.github.enzanki_ars.minecomplete.events.item;

import io.github.enzanki_ars.minecomplete.MineComplete;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.Map;

import static io.github.enzanki_ars.minecomplete.utils.MineCompleteScore.addScoreToPlayer;

public class MineCompleteEnchantmentEvent implements Listener {
    public static int EVENT_POINTS = 1;
    public static String EVENT_TYPE = "enchantments";

    @EventHandler
    public void onPlayerItemGetEvent(EnchantItemEvent event) {
        Player player = event.getEnchanter();

        JavaPlugin plugin = MineComplete.getPlugin(MineComplete.class);

        FileConfiguration config = plugin.getConfig();

        String playerUUID = player.getUniqueId().toString();
        Map<Enchantment, Integer> enchantments = event.getEnchantsToAdd();

        ConfigurationSection playerSection = config.getConfigurationSection(playerUUID);

        List<String> enchantmentList = playerSection.getStringList(EVENT_TYPE);

        for (Map.Entry<Enchantment, Integer> enchantmentEntry : enchantments.entrySet()) {
            String enchantmentName = enchantmentEntry.getKey().toString();

            if (!enchantmentList.contains(enchantmentName)) {
                enchantmentList.add(enchantmentName);
                enchantmentList.sort(String.CASE_INSENSITIVE_ORDER);
                playerSection.set(EVENT_TYPE, enchantmentList);

                addScoreToPlayer(player, EVENT_POINTS, "new enchantment", enchantmentName);
            }
        }
    }
}
