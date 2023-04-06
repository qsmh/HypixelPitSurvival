package me.klb.thehypixelpitsurvival.itemchecker;

import me.klb.thehypixelpitsurvival.TheHypixelPitSurvival;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Objects;

public class RemoveItemChecker {
    public static void runItemChecker(Player player, Boolean hasFunkyFeather){
        if (hasFunkyFeather){ return; }

        ItemStack[] contents = player.getInventory().getContents();

        for (ItemStack item : contents) {
            if (item != null && item.hasItemMeta()) {
                ItemMeta itemMeta = item.getItemMeta();

                assert itemMeta != null;
                PersistentDataContainer itemData = item.getItemMeta().getPersistentDataContainer();

                if (itemData.has(new NamespacedKey(TheHypixelPitSurvival.getPlugin(), "CustomItem"), PersistentDataType.STRING)) { // Cannot be simplified further, tried and doesn't work
                    if (Objects.requireNonNull(itemMeta.getLore()).get(0) != null){
                        String loreIndexZero = itemMeta.getLore().get(0);

                        if (loreIndexZero.equals(TheHypixelPitSurvival.getPlugin().getConfig().getString("special-item-lore"))){
                            player.getInventory().remove(item);
                            player.updateInventory();
                        }
                    }
                }
            }
        }

        player.updateInventory();
    }
}
