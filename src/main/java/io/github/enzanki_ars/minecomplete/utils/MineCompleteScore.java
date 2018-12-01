package io.github.enzanki_ars.minecomplete.utils;

import io.github.enzanki_ars.minecomplete.MineComplete;
import io.github.enzanki_ars.minecomplete.events.advancement.MineCompleteAdvancementEvent;
import io.github.enzanki_ars.minecomplete.events.item.MineCompleteEnchantmentEvent;
import io.github.enzanki_ars.minecomplete.events.item.MineCompleteItemGetEvent;
import io.github.enzanki_ars.minecomplete.events.player.MineCompletePlayerDeathEvent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.advancement.Advancement;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
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

        playerScore += playerSection.getStringList(MineCompleteItemGetEvent.EVENT_TYPE).size() * MineCompleteItemGetEvent.EVENT_POINTS;
        playerScore += playerSection.getStringList(MineCompleteAdvancementEvent.EVENT_TYPE).size() * MineCompleteAdvancementEvent.EVENT_POINTS;
        playerScore += playerSection.getStringList(MineCompletePlayerDeathEvent.EVENT_TYPE).size() * MineCompletePlayerDeathEvent.EVENT_POINTS;
        playerScore += playerSection.getStringList(MineCompleteEnchantmentEvent.EVENT_TYPE).size() * MineCompleteEnchantmentEvent.EVENT_POINTS;

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

        int damageCausesCount = 0;
        int materialsCount = 0;
        int advancementsCount = 0;
        int enchantmentsCount = 0;

        try {
            FileOutputStream fos = new FileOutputStream(outputFile, false);
            Writer writer = new OutputStreamWriter(fos, StandardCharsets.UTF_8);

            List<EntityDamageEvent.DamageCause> damageCauses = ObjectiveLists.writeListOfDamageCauses(writer);

            List<Material> materials = ObjectiveLists.writeListOfMaterials(writer);

            List<Advancement> advancements = ObjectiveLists.writeListOfAdvancements(writer);

            List<Enchantment> enchantments = ObjectiveLists.writeListOfEnchantments(writer);

            damageCausesCount = damageCauses.size();
            materialsCount = materials.size();
            advancementsCount = advancements.size();
            enchantmentsCount = enchantments.size();

            writer.flush();
        } catch (IOException e) {
            Bukkit.getLogger().severe("Can't write to file " + outputFile.getName() + " properly.");
            e.printStackTrace();
        }

        System.out.print("Damage causes count: " + damageCausesCount);
        System.out.print("Items count: " + materialsCount);
        System.out.print("Advancements count: " + advancementsCount);
        System.out.print("Enchantments count: " + enchantmentsCount);

        int countPoints = damageCausesCount + materialsCount + advancementsCount + enchantmentsCount;

        System.out.println("Count: " + countPoints);
    }

}
