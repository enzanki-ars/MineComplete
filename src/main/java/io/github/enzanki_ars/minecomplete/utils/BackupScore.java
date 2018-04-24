package io.github.enzanki_ars.minecomplete.utils;

import io.github.enzanki_ars.minecomplete.MineComplete;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

public class BackupScore {
    public static void backupScore() {
        JavaPlugin plugin = MineComplete.getPlugin(MineComplete.class);

        FileConfiguration config = plugin.getConfig();

        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd-HHmmss");
        Date date = new Date();

        String saveName = plugin.getDataFolder().getPath() + File.separator + "config-backup-" + dateFormat.format(date) + ".yml";

        Logger log = plugin.getLogger();

        log.info("Saving backup of score to: " + saveName);

        try {
            config.save(saveName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
