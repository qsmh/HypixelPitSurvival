package me.klb.thehypixelpitsurvival.listeners;

import me.klb.thehypixelpitsurvival.TheHypixelPitSurvival;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Wolf;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

public class onEntityTargetListener implements Listener {
    @EventHandler
    public void onEntityTarget(EntityTargetEvent event) {
        if (event.getEntityType() == EntityType.WOLF) {
            LivingEntity target = (LivingEntity) event.getTarget();
            Wolf wolf = (Wolf) event.getEntity();

            PersistentDataContainer wolfData = wolf.getPersistentDataContainer();

            if (wolfData.has(new NamespacedKey(TheHypixelPitSurvival.getPlugin(), "CustomEntityPet"), PersistentDataType.STRING)){
                new BukkitRunnable(){

                    @Override
                    public void run() {
                        if(!wolf.isAngry() || wolf.getTarget() == null){
                            wolf.remove();
                        }
                    }
                }.runTaskLater(TheHypixelPitSurvival.getPlugin(), 3);
            }

            if (target == null){ return; }

            PersistentDataContainer targetData = target.getPersistentDataContainer();

            if (targetData.has(new NamespacedKey(TheHypixelPitSurvival.getPlugin(), "CustomEntity"), PersistentDataType.STRING)) {
                event.setCancelled(true);
            }
        }
    }
}
