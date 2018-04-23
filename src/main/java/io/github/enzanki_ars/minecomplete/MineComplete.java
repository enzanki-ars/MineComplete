package io.github.enzanki_ars.minecomplete;

import io.github.enzanki_ars.minecomplete.events.advancement.MineCompleteAdvancementEvent;
import io.github.enzanki_ars.minecomplete.events.item.MineCompleteCraftItemEvent;
import io.github.enzanki_ars.minecomplete.events.item.MineCompleteItemGetEvent;
import io.github.enzanki_ars.minecomplete.events.other.MineCompleteJoinEvent;
import io.github.enzanki_ars.minecomplete.events.player.MineCompletePlayerDeathEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import static io.github.enzanki_ars.minecomplete.utils.MineCompleteScore.printPossiblePoints;

public class MineComplete extends JavaPlugin {

    @Override
    public void onEnable() {
        FileConfiguration config = this.getConfig();
        config.options().copyDefaults(true);
        saveConfig();

        getServer().getPluginManager().registerEvents(new MineCompleteJoinEvent(), this);
        getServer().getPluginManager().registerEvents(new MineCompleteAdvancementEvent(), this);
        getServer().getPluginManager().registerEvents(new MineCompleteItemGetEvent(), this);
        getServer().getPluginManager().registerEvents(new MineCompletePlayerDeathEvent(), this);

        getServer().getPluginManager().registerEvents(new MineCompleteCraftItemEvent(), this);

        Scoreboard board = this.getServer().getScoreboardManager().getMainScoreboard();

        Objective obj = board.getObjective("MineComplete");

        if (obj == null) {
            obj = board.registerNewObjective("MineComplete", "dummy");
            obj.setDisplaySlot(DisplaySlot.SIDEBAR);
            obj.setDisplayName("MineComplete");
        }

        printPossiblePoints();
    }
}
