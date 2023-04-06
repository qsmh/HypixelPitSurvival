package me.klb.thehypixelpitsurvival.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedLeaveEvent;

public class onPlayerLeaveBed implements Listener {

    @EventHandler
    public void onBedLeavePlayer(PlayerBedLeaveEvent event){
        Player player = event.getPlayer();
        player.setHealth(20.0); // Heals player to full hp
    }
}
