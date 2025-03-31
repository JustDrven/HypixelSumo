package dev.justdrven.sumo.item.type;

import dev.justdrven.sumo.item.ItemContainer;
import dev.justdrven.sumo.player.GamePlayer;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;
import java.util.List;

public class LobbyItemContainer implements ItemContainer {
    @Override
    public void giveItems(GamePlayer gamePlayer, Inventory inv) {

        ItemStack backToLobby = new ItemStack(Material.BED, 1);
        ItemMeta itemMeta = backToLobby.getItemMeta();
        itemMeta.spigot().setUnbreakable(true);
        itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        itemMeta.setDisplayName("§aBack to Lobby");
        List<String> lore = Collections.singletonList("§7Click to connect to lobby!");
        itemMeta.setLore(lore);
        backToLobby.setItemMeta(itemMeta);

        inv.setItem(8, backToLobby);
    }
}
