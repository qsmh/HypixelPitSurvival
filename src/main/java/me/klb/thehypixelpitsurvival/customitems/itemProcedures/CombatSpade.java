package me.klb.thehypixelpitsurvival.customitems.itemProcedures;

import me.klb.thehypixelpitsurvival.TheHypixelPitSurvival;
import me.klb.thehypixelpitsurvival.other.ArmorBuilder;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class CombatSpade implements Listener {
    public void onEntityDamaged(EntityDamageByEntityEvent event){
        Entity entity = event.getEntity();
        Entity damager = event.getDamager();

        if (!(entity instanceof Player playerDamaged) || !(damager instanceof Player playerDamager)){
            return;
        }

        ItemStack heldItem = playerDamager.getInventory().getItemInMainHand();
        ItemMeta heldItemMeta = heldItem.getItemMeta();

        if (heldItemMeta == null){
            return;
        }

        PersistentDataContainer heldItemData = heldItemMeta.getPersistentDataContainer();

        if (!heldItemData.has(new NamespacedKey(TheHypixelPitSurvival.getPlugin(), "CombatSpade"), PersistentDataType.STRING)){
            return;
        }

        int diamondPieces = ArmorBuilder.hasArmorMaterialDiamond(playerDamaged);

        // Not finished
    }
}
