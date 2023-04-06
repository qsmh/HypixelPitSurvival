package me.klb.thehypixelpitsurvival.listeners;

import me.klb.thehypixelpitsurvival.TheHypixelPitSurvival;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class onItemApplied implements Listener {

    @EventHandler
    public void itemPreparedInAnvil(PrepareAnvilEvent event){
        ItemStack enchantedItem = event.getResult();
        ItemMeta enchantedItemMeta = enchantedItem.getItemMeta();

        if (enchantedItemMeta != null){
            PersistentDataContainer enchantedItemData = enchantedItemMeta.getPersistentDataContainer();

            if (enchantedItemData.has(new NamespacedKey(TheHypixelPitSurvival.getPlugin(), "CustomItem"), PersistentDataType.STRING)){
                event.setResult(null);
            }
        }
    }
    @EventHandler
    public void itemEnchanted(EnchantItemEvent event){
        ItemStack enchantedItem = event.getItem();
        ItemMeta enchantedItemMeta = enchantedItem.getItemMeta();

        if (enchantedItemMeta != null){
            PersistentDataContainer enchantedItemData = enchantedItemMeta.getPersistentDataContainer();

            if (enchantedItemData.has(new NamespacedKey(TheHypixelPitSurvival.getPlugin(), "CustomItem"), PersistentDataType.STRING)){
                event.setCancelled(true);

                Player player = event.getEnchanter();

                player.playSound(player, Sound.ENTITY_ENDERMAN_HURT, 3F, 0.87F);
                player.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD + "WOAH! " + ChatColor.RESET + ChatColor.GRAY + "You cannot enchant " + enchantedItemMeta.getDisplayName() + "!");
            }
        }
    }
}
