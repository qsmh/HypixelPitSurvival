package me.klb.thehypixelpitsurvival.customitems.itemProcedures;

import me.klb.thehypixelpitsurvival.TheHypixelPitSurvival;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Objects;

public class FunkyFeather {
    public static boolean runFunkyFeather(PlayerDeathEvent event) {
        Player player = event.getEntity();

        for (ItemStack item : player.getInventory().getContents()) {
            if (item != null && item.hasItemMeta()) {
                ItemMeta itemMeta = item.getItemMeta();

                assert itemMeta != null;
                PersistentDataContainer itemData = itemMeta.getPersistentDataContainer();

                if (itemData.has(new NamespacedKey(TheHypixelPitSurvival.getPlugin(), "CustomItem"), PersistentDataType.STRING)) {
                    String customItemType = itemData.get(new NamespacedKey(TheHypixelPitSurvival.getPlugin(), "CustomItem"), PersistentDataType.STRING);
                    if (Objects.equals(customItemType, "FunkyFeather")) {
                        removeFunkyFeather(event, player, item);

                        return true; // Has fedur
                    }
                }
            }
        }

        return false; // Does not have fedur
    }

    private static void removeFunkyFeather(PlayerDeathEvent event, Player player, ItemStack item) {
        item.setAmount(item.getAmount() - 1);

        event.setDroppedExp(0);
        event.getDrops().clear();
        event.setKeepInventory(true);
        event.setKeepLevel(true);

        // Save player inventory after removing Funky Feather
        player.updateInventory();
        player.sendMessage(ChatColor.DARK_AQUA.toString() + ChatColor.BOLD + "FUNKY FEATHER! " + ChatColor.RESET + ChatColor.GRAY + "inventory protected!");
    }
}

