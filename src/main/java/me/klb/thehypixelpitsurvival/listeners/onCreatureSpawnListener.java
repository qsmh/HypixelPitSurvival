package me.klb.thehypixelpitsurvival.listeners;

import me.klb.thehypixelpitsurvival.creatures.SkeletonSpawn;
import me.klb.thehypixelpitsurvival.creatures.ZombieSpawn;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class onCreatureSpawnListener implements Listener {
    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent event){
        switch (event.getEntityType()){ // Do not enhance switch statement
            case ZOMBIE:
                ZombieSpawn.runZombieSpawn(event);
                break;
            case SKELETON:
                SkeletonSpawn.runSkeletonSpawn(event);
                break;
            default:
                break;
        }
    }
}
