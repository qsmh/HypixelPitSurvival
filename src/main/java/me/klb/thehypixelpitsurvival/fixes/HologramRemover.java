package me.klb.thehypixelpitsurvival.fixes;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

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

                if (armorStand.hasMetadata("MysticWellHologram")) {
                    armorStand.remove();
                }

                if (armorStand.hasMetadata("MysticWellHologram2")) {
                    armorStand.remove();
                }
            }
        }
    }
}
