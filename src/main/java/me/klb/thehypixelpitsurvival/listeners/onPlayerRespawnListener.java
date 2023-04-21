package me.klb.thehypixelpitsurvival.listeners;

import me.klb.thehypixelpitsurvival.other.TitleBuilder;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class onPlayerRespawnListener implements Listener {
    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event){
        Player player = event.getPlayer();

        TitleBuilder.diedTitle(player);
    }
}
