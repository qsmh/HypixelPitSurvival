package me.klb.thehypixelpitsurvival.itemchecker;

import me.klb.thehypixelpitsurvival.TheHypixelPitSurvival;
import me.klb.thehypixelpitsurvival.customitems.itemMetaData.itemManager;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Objects;

public class ReplaceItemChecker {
    public static void runItemChecker(Player player){
        ItemStack[] contents = player.getInventory().getContents();

        for (int i = 0; i < contents.length; i++) {
            ItemStack item = contents[i];

            if(item != null && item.hasItemMeta()){
                ItemMeta itemMeta = item.getItemMeta();

                assert itemMeta != null;
                PersistentDataContainer itemData = item.getItemMeta().getPersistentDataContainer();

                if (itemData.has(new NamespacedKey(TheHypixelPitSurvival.getPlugin(), "CustomItem"), PersistentDataType.STRING)) {
                    String customItemType = itemData.get(new NamespacedKey(TheHypixelPitSurvival.getPlugin(), "CustomItem"), PersistentDataType.STRING);
                    if (Objects.equals(customItemType, "AidEggCooldown")) {
                        player.getInventory().setItem(i, itemManager.firstAidEgg);
                        player.updateInventory();
                    }
                    // Add more lines down here
                }
            }
        }

        player.updateInventory();
    }
}
