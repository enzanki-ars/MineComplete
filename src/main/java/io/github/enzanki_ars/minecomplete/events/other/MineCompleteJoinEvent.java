package io.github.enzanki_ars.minecomplete.events.other;

import io.github.enzanki_ars.minecomplete.MineComplete;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.List;
import java.util.logging.Logger;

import static io.github.enzanki_ars.minecomplete.utils.MineCompleteScore.getPlayerScore;

public class MineCompleteJoinEvent implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        FileConfiguration config = MineComplete.getPlugin(MineComplete.class).getConfig();

        Logger log = MineComplete.getPlugin(MineComplete.class).getLogger();

        String playerName = event.getPlayer().getName();
        String playerUUID = event.getPlayer().getUniqueId().toString();

        ConfigurationSection playerSection = config.getConfigurationSection(playerUUID);

        if (playerSection == null) {
            playerSection = config.createSection(event.getPlayer().getUniqueId().toString());
            event.setJoinMessage("Welcome to MineComplete " + playerName + "!\n" +
                    "Because you are joining for the first time, you have no progress yet...");
            log.info(playerName + " is new to the game.");

            getPlayerScore(event.getPlayer());
        } else {
            event.setJoinMessage("Welcome back to MineComplete " + playerName + "!\n" +
                    "You currently have a score of " + getPlayerScore(event.getPlayer()));
            log.info(playerName + " has previous progress.");
        }

        List<String> playerNameList = playerSection.getStringList("known_names");

        if (!playerNameList.contains(playerName)) {
            playerNameList.add(playerName);
        }
        playerSection.set("known_names", playerNameList);

        playerSection.set("last_known_name", playerName);

        MineComplete.getPlugin(MineComplete.class).saveConfig();
    }

}
