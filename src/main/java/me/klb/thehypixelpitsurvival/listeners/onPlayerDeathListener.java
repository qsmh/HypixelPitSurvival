package me.klb.thehypixelpitsurvival.listeners;

import me.klb.thehypixelpitsurvival.TheHypixelPitSurvival;
import me.klb.thehypixelpitsurvival.itemchecker.RemoveItemChecker;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import static me.klb.thehypixelpitsurvival.customitems.itemProcedures.FunkyFeather.runFunkyFeather;

public class onPlayerDeathListener implements Listener {
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event){
        playerDied(event);
        Boolean hasFunkyFeather = runFunkyFeather(event);
        RemoveItemChecker.runItemChecker(event.getEntity(), hasFunkyFeather);
        respawnPlayer(event.getEntity());
    }

    public void playerDied(PlayerDeathEvent event){
        Player player = event.getEntity();

        event.setDeathMessage(null);
        player.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD + "DEATH!");
    }

    public void respawnPlayer(Player player){
        if(!player.isDead()){
           return;
        }

        Bukkit.getScheduler().runTaskLater(TheHypixelPitSurvival.getPlugin(), () -> {
            player.spigot().respawn();
        }, 1);
    }
}
