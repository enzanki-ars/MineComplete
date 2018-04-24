package io.github.enzanki_ars.minecomplete.events.other;

import io.github.enzanki_ars.minecomplete.MineComplete;
import io.github.enzanki_ars.minecomplete.utils.Checks;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
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

        Player player = event.getPlayer();

        String playerName = player.getName();
        String playerUUID = player.getUniqueId().toString();

        ConfigurationSection playerSection = config.getConfigurationSection(playerUUID);

        if (playerSection == null) {
            playerSection = config.createSection(playerUUID);
            event.setJoinMessage("Welcome to MineComplete " + playerName + "!\n" +
                    "Because you are joining for the first time, you have no progress yet...");
            log.info(playerName + " is new to the game.");

            getPlayerScore(player);
        } else {
            event.setJoinMessage("Welcome back to MineComplete " + playerName + "!\n" +
                    "You currently have a score of " + getPlayerScore(player));
            log.info(playerName + " has previous progress.");
        }

        List<String> playerNameList = playerSection.getStringList("known_names");

        if (!playerNameList.contains(playerName)) {
            playerNameList.add(playerName);
        }
        playerSection.set("known_names", playerNameList);

        playerSection.set("last_known_name", playerName);


        Checks.checkInventoryForNewItems(player);

        MineComplete.getPlugin(MineComplete.class).saveConfig();
    }

}
