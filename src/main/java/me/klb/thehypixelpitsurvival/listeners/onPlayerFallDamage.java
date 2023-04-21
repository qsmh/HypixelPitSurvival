package me.klb.thehypixelpitsurvival.listeners;

import me.klb.thehypixelpitsurvival.TheHypixelPitSurvival;
import me.klb.thehypixelpitsurvival.other.TitleBuilder;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class onPlayerFallDamage implements Listener {
    @EventHandler
    public void onPlayerFallDamage(EntityDamageEvent event){
        if(!(event.getEntity() instanceof Player)) {
            return;
        }

        if(event.getCause() == EntityDamageEvent.DamageCause.FALL && TheHypixelPitSurvival.getPlugin().getConfig().getBoolean("fall-damage")){
            event.setCancelled(true);
        }
    }
}
