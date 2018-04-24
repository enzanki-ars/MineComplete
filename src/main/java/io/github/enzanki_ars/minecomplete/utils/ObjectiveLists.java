package io.github.enzanki_ars.minecomplete.utils;

import io.github.enzanki_ars.minecomplete.MineComplete;
import org.bukkit.Material;
import org.bukkit.advancement.Advancement;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.entity.EntityDamageEvent;

import java.io.IOException;
import java.io.Writer;
import java.util.*;

public class ObjectiveLists {
    public static List<Advancement> writeListOfAdvancements(Writer writer) throws IOException {
        writer.write("Advancements: " + System.lineSeparator());
        Iterator<Advancement> advancementList = MineComplete.getPlugin(MineComplete.class).getServer().advancementIterator();

        List<Advancement> list = new ArrayList<>();
        advancementList.forEachRemaining(list::add);

        list.sort(Comparator.comparing(o -> o.getKey().getKey()));


        for (Advancement advancement : list) {
            writer.write("  - " + advancement.getKey().getKey() + System.lineSeparator());
        }
        return list;
    }

    public static List<Material> writeListOfMaterials(Writer writer) throws IOException {
        List<Material> materials = Arrays.asList(Material.values());

        materials.sort(Comparator.comparing(Enum::name));

        writer.write("Items: " + System.lineSeparator());
        for (Material material : materials) {
            writer.write("  - " + material.name() + System.lineSeparator());
        }

        return materials;
    }

    public static List<Enchantment> writeListOfEnchantments(Writer writer) throws IOException {
        List<Enchantment> enchantments = Arrays.asList(Enchantment.values());

        enchantments.sort(Comparator.comparing(Enchantment::getName));

        writer.write("Enchantments: " + System.lineSeparator());
        for (Enchantment enchantment : enchantments) {
            writer.write("  - " + enchantment.getName() + System.lineSeparator());
        }

        return enchantments;
    }

    public static List<EntityDamageEvent.DamageCause> writeListOfDamageCauses(Writer writer) throws IOException {
        List<EntityDamageEvent.DamageCause> damageCauses = Arrays.asList(EntityDamageEvent.DamageCause.values());

        damageCauses.sort(Comparator.comparing(Enum::name));

        writer.write("Damage causes: " + System.lineSeparator());
        for (EntityDamageEvent.DamageCause damageCause : damageCauses) {
            writer.write("  - " + damageCause.name() + System.lineSeparator());
        }

        return damageCauses;
    }
}
