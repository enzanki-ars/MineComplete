package io.github.enzanki_ars.minecomplete.events.item;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;

import static io.github.enzanki_ars.minecomplete.utils.Checks.checkInventoryForNewItems;

public class MineCompleteInventoryExtractEvent implements Listener {
    public static int EVENT_POINTS = 1;
    public static String EVENT_TYPE = "items";

    @EventHandler
    public void onPlayerInventoryClickEvent(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player) {
            Player player = (Player) event.getWhoClicked();

            checkInventoryForNewItems(player);
        }
    }

    @EventHandler
    public void onPlayerInventoryDragEvent(InventoryDragEvent event) {
        if (event.getWhoClicked() instanceof Player) {
            Player player = (Player) event.getWhoClicked();

            checkInventoryForNewItems(player);
        }
    }
}
