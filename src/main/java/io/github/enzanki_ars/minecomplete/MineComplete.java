package io.github.enzanki_ars.minecomplete;

import io.github.enzanki_ars.minecomplete.events.advancement.MineCompleteAdvancementEvent;
import io.github.enzanki_ars.minecomplete.events.item.*;
import io.github.enzanki_ars.minecomplete.events.other.MineCompleteJoinEvent;
import io.github.enzanki_ars.minecomplete.events.player.MineCompletePlayerDamageEvent;
import io.github.enzanki_ars.minecomplete.events.player.MineCompletePlayerDeathEvent;
import io.github.enzanki_ars.minecomplete.tasks.AutoSaveScore;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import static io.github.enzanki_ars.minecomplete.utils.BackupScore.backupScore;
import static io.github.enzanki_ars.minecomplete.utils.MineCompleteScore.printPossiblePoints;

public class MineComplete extends JavaPlugin {

    @Override
    public void onEnable() {
        FileConfiguration config = this.getConfig();
        config.options().copyDefaults(true);
        saveConfig();
        backupScore();

        getServer().getPluginManager().registerEvents(new MineCompleteAdvancementEvent(), this);

        getServer().getPluginManager().registerEvents(new MineCompleteCraftItemEvent(), this);
        getServer().getPluginManager().registerEvents(new MineCompleteEnchantmentEvent(), this);
        getServer().getPluginManager().registerEvents(new MineCompleteFishEvent(), this);
        getServer().getPluginManager().registerEvents(new MineCompleteFurnaceExtractEvent(), this);
        getServer().getPluginManager().registerEvents(new MineCompleteInventoryExtractEvent(), this);
        getServer().getPluginManager().registerEvents(new MineCompleteItemGetEvent(), this);

        getServer().getPluginManager().registerEvents(new MineCompleteJoinEvent(), this);

        getServer().getPluginManager().registerEvents(new MineCompletePlayerDamageEvent(), this);
        getServer().getPluginManager().registerEvents(new MineCompletePlayerDeathEvent(), this);

        BukkitScheduler scheduler = getServer().getScheduler();
        scheduler.scheduleSyncRepeatingTask(this, new AutoSaveScore(), 6000L, 6000L);

        Scoreboard board = this.getServer().getScoreboardManager().getMainScoreboard();

        Objective obj = board.getObjective("MineComplete");

        if (obj == null) {
            obj = board.registerNewObjective("MineComplete", "dummy", "MineComplete");
            obj.setDisplaySlot(DisplaySlot.SIDEBAR);
            obj.setDisplayName("MineComplete");
        }

        printPossiblePoints();
    }

    @Override
    public void onDisable() {
        backupScore();
    }
}
