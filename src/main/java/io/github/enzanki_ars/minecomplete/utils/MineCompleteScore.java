package io.github.enzanki_ars.minecomplete.utils;

import io.github.enzanki_ars.minecomplete.MineComplete;
import io.github.enzanki_ars.minecomplete.events.advancement.MineCompleteAdvancementEvent;
import io.github.enzanki_ars.minecomplete.events.item.MineCompleteItemGetEvent;
import io.github.enzanki_ars.minecomplete.events.player.MineCompletePlayerDeathEvent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.advancement.Advancement;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import java.io.*;
import java.util.Iterator;
import java.util.logging.Logger;

public class MineCompleteScore {
    public static void addScoreToPlayer(Player player, int pointsEarned, String type, String how) {
        String playerName = player.getName();

        JavaPlugin plugin = MineComplete.getPlugin(MineComplete.class);

        Logger log = plugin.getLogger();

        getPlayerScore(player);

        String message = playerName + " has earned " + pointsEarned + " points for " + type + " (" + how + ")" + ".";

        log.info(message);

        plugin.saveConfig();
    }

    public static int getPlayerScore(Player player) {
        String playerUUID = player.getUniqueId().toString();

        JavaPlugin plugin = MineComplete.getPlugin(MineComplete.class);

        FileConfiguration config = plugin.getConfig();

        ConfigurationSection playerSection = config.getConfigurationSection(playerUUID);

        int playerScore = 0;

        playerScore += playerSection.getStringList("items").size() * MineCompleteItemGetEvent.EVENT_POINTS;
        playerScore += playerSection.getStringList("advancements").size() * MineCompleteAdvancementEvent.EVENT_POINTS;
        playerScore += playerSection.getStringList("deaths").size() * MineCompletePlayerDeathEvent.EVENT_POINTS;

        Scoreboard board = Bukkit.getScoreboardManager().getMainScoreboard();
        Objective obj = board.getObjective("MineComplete");
        Score score = obj.getScore(player.getName());
        score.setScore(playerScore);

        return playerScore;
    }

    public static void printPossiblePoints() {
        File folder = MineComplete.getPlugin(MineComplete.class).getDataFolder();
        File outputFile = new File(folder, "all_possible_points.txt");

        if (folder.mkdirs()) {
            Bukkit.getLogger().fine("Created directories for " + folder.getName());
        }

        int damageCauses = EntityDamageEvent.DamageCause.values().length;
        int materials = Material.values().length;
        int advancements = 0;

        try {
            FileOutputStream fos = new FileOutputStream(outputFile, false);
            Writer writer = new OutputStreamWriter(fos, "UTF-8");


            writer.write("Damage causes: " + "\n");
            for (EntityDamageEvent.DamageCause damageCause : EntityDamageEvent.DamageCause.values()) {
                writer.write("  - " + damageCause.name() + "\n");
            }

            writer.write("Items: " + "\n");
            for (Material material : Material.values()) {
                writer.write("  - " + material.name() + "\n");
            }

            writer.write("Advancements: " + "\n");
            Iterator<Advancement> advancementList = MineComplete.getPlugin(MineComplete.class).getServer().advancementIterator();
            while (advancementList.hasNext()) {
                Advancement curr = advancementList.next();
                advancements++;
                writer.write("  - " + curr.getKey().getKey() + "\n");
            }

            writer.flush();
        } catch (IOException e) {
            Bukkit.getLogger().severe("Can't write to file " + outputFile.getName() + " properly.");
            e.printStackTrace();
        }

        System.out.print("Damage causes count: " + damageCauses);
        System.out.print("Items count: " + materials);
        System.out.print("Advancements count: " + advancements);

        int countPoints = damageCauses + materials + advancements;

        System.out.println("Count: " + countPoints);
    }
}
