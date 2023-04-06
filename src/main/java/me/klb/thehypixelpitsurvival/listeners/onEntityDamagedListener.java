package me.klb.thehypixelpitsurvival.listeners;

import me.klb.thehypixelpitsurvival.ActionBarHearts;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class onEntityDamagedListener implements Listener {
    @EventHandler
    public void onEntityDamaged(EntityDamageByEntityEvent event){
        Entity damager = event.getDamager();

        if (damager instanceof Projectile && ((Projectile) damager).getShooter() instanceof Player) {
            ActionBarHearts.runActionBarHearts(event, true);
        } else if (damager instanceof Player) {
            ActionBarHearts.runActionBarHearts(event, false);
        }
    }
}
