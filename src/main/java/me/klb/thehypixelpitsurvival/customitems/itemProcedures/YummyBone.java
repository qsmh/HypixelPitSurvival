package me.klb.thehypixelpitsurvival.customitems.itemProcedures;

import me.klb.thehypixelpitsurvival.TheHypixelPitSurvival;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Objects;

public class YummyBone implements Listener {
    @EventHandler
    public void onPlayerInteract(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        Entity entity = event.getRightClicked();
        PlayerInventory inventory = player.getInventory();

        if (!(entity instanceof Wolf wolf)) {
            return;
        }

        PersistentDataContainer entityData = entity.getPersistentDataContainer();

        if (entityData.has(new NamespacedKey(TheHypixelPitSurvival.getPlugin(), "CustomEntityPet"), PersistentDataType.STRING)) {
            return;
        }

        boolean hasYummyBone = false;
        ItemMeta itemMeta;

        if (inventory.getItemInMainHand().getType() == Material.BONE) {
            itemMeta = inventory.getItemInMainHand().getItemMeta();

            assert itemMeta != null;
            if (itemMeta.getPersistentDataContainer().has(new NamespacedKey(TheHypixelPitSurvival.getPlugin(), "CustomItem"), PersistentDataType.STRING)
                    && Objects.equals(itemMeta.getPersistentDataContainer().get(new NamespacedKey(TheHypixelPitSurvival.getPlugin(), "CustomItem"), PersistentDataType.STRING), "YummyBone")) {
                hasYummyBone = true;
            }
        } else if (inventory.getItemInOffHand().getType() == Material.BONE) {
            itemMeta = inventory.getItemInOffHand().getItemMeta();

            assert itemMeta != null;
            if (itemMeta.getPersistentDataContainer().has(new NamespacedKey(TheHypixelPitSurvival.getPlugin(), "CustomItem"), PersistentDataType.STRING)
                    && Objects.equals(itemMeta.getPersistentDataContainer().get(new NamespacedKey(TheHypixelPitSurvival.getPlugin(), "CustomItem"), PersistentDataType.STRING), "YummyBone")) {
                hasYummyBone = true;
            }
        }

        if (!hasYummyBone) {
            return;
        }

        if (!(wolf.getOwner() == null)){
            return;
        }

        wolf.setTamed(true);
        wolf.setOwner(player);
        player.playSound(player, Sound.ENTITY_WOLF_AMBIENT, 1, 1.3F);
        event.setCancelled(true);
    }
}
