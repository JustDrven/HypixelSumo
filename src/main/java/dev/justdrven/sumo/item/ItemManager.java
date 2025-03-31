package dev.justdrven.sumo.item;

import dev.justdrven.sumo.item.type.EmptyItemContainer;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ItemManager {

    private final EmptyItemContainer emptyItemContainer = new EmptyItemContainer();
    private final Map<String, ItemContainer> itemContainers = new ConcurrentHashMap<>();

    public final void registerItemContainer(String itemContainerId, ItemContainer container) {
        final String containerId = itemContainerId.toLowerCase();

        if (itemContainers.containsKey(containerId)) return;

        itemContainers.put(containerId, container);
    }

    public final ItemContainer get(String itemContainerId) {
        final String containerId = itemContainerId.toLowerCase();

        if (!itemContainers.containsKey(containerId)) return emptyItemContainer;

        return itemContainers.get(containerId);
    }

}
