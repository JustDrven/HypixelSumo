package dev.justdrven.sumo.item;

import dev.justdrven.sumo.player.GamePlayer;
import org.bukkit.inventory.Inventory;

@FunctionalInterface
public interface ItemContainer {

    void giveItems(GamePlayer gamePlayer, final Inventory inv);

}
