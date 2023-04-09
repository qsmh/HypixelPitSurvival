package me.klb.thehypixelpitsurvival.fixes;

import me.klb.thehypixelpitsurvival.TheHypixelPitSurvival;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class HologramRemover implements Listener {
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event){
        Block block = event.getBlock();

        Location location = block.getLocation().add(0.5, 1.5, 0.5);
        World world = location.getWorld();

        if (world == null) {
            return;
        }

        for (Entity entity : world.getNearbyEntities(location, 1.0, 0.5, 1.0)) {
            if (entity instanceof ArmorStand armorStand) {
                PersistentDataContainer data = armorStand.getPersistentDataContainer();

                if (data.has(new NamespacedKey(TheHypixelPitSurvival.getPlugin(), "MysticWellHologram"), PersistentDataType.STRING)) {
                    armorStand.remove();
                }
            }
        }
    }
}
