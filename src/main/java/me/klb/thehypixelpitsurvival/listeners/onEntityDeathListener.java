package me.klb.thehypixelpitsurvival.listeners;

import me.klb.thehypixelpitsurvival.TheHypixelPitSurvival;
import me.klb.thehypixelpitsurvival.calculations.stats.GoldCalculation;
import me.klb.thehypixelpitsurvival.calculations.stats.XPCalculation;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

import java.text.DecimalFormat;

public class onEntityDeathListener implements Listener {
    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        LivingEntity entity = event.getEntity();
        Player killer = entity.getKiller();

        Double gold;
        double xp;
        int chatXP;
        DecimalFormat formatter = new DecimalFormat("#,###.00");

        if (killer != null) {
            PersistentDataContainer entityData = entity.getPersistentDataContainer();
            PersistentDataContainer killerData = killer.getPersistentDataContainer();

            // GOLD | GOLD | GOLD

            if(entityData.has(new NamespacedKey(TheHypixelPitSurvival.getPlugin(), "GoldReward"), PersistentDataType.DOUBLE)){ // Set gold reward
                Double goldReward = GoldCalculation.runGoldCalculation(killer, false, entityData.get(new NamespacedKey(TheHypixelPitSurvival.getPlugin(), "GoldReward"), PersistentDataType.DOUBLE));
                Double originalGold = killerData.get(new NamespacedKey(TheHypixelPitSurvival.getPlugin(), "GoldBalance"), PersistentDataType.DOUBLE);

                gold = goldReward;

                if(originalGold != null){
                    killerData.set(new NamespacedKey(TheHypixelPitSurvival.getPlugin(), "GoldBalance"), PersistentDataType.DOUBLE, goldReward + originalGold);
                }
            }else{ // No set gold reward
                Double goldReward = GoldCalculation.runGoldCalculation(killer, true, 0.00);
                Double originalGold = killerData.get(new NamespacedKey(TheHypixelPitSurvival.getPlugin(), "GoldBalance"), PersistentDataType.DOUBLE);

                gold = goldReward;

                if(originalGold != null){
                    killerData.set(new NamespacedKey(TheHypixelPitSurvival.getPlugin(), "GoldBalance"), PersistentDataType.DOUBLE, goldReward + originalGold);
                }
            }

            // XP | XP | XP

            if(entityData.has(new NamespacedKey(TheHypixelPitSurvival.getPlugin(), "XPReward"), PersistentDataType.DOUBLE)){ // Set gold reward
                Double xpReward = XPCalculation.runXPCalculation(killer, false, entityData.get(new NamespacedKey(TheHypixelPitSurvival.getPlugin(), "XPReward"), PersistentDataType.DOUBLE));
                Double originalXP = killerData.get(new NamespacedKey(TheHypixelPitSurvival.getPlugin(), "XPAmount"), PersistentDataType.DOUBLE);

                xp = originalXP - xpReward;
                chatXP = (int) Math.abs(xpReward);

                if (xp < 0.00){
                    xp = 0.00;
                }


                if(originalXP != null){
                    killerData.set(new NamespacedKey(TheHypixelPitSurvival.getPlugin(), "XPAmount"), PersistentDataType.DOUBLE, xp);
                }
            }else{ // No set xp reward
                Double xpReward = XPCalculation.runXPCalculation(killer, true, 0.00);
                Double originalXP = killerData.get(new NamespacedKey(TheHypixelPitSurvival.getPlugin(), "XPAmount"), PersistentDataType.DOUBLE);

                xp = originalXP - xpReward;
                chatXP = (int) Math.abs(xpReward);

                if (xp < 0.00){
                    xp = 0.00;
                }

                if(originalXP != null){
                    killerData.set(new NamespacedKey(TheHypixelPitSurvival.getPlugin(), "XPAmount"), PersistentDataType.DOUBLE, xp);
                }
            }

            new BukkitRunnable(){
                int count = 0;

                @Override
                public void run() {
                    count++;
                    switch (count) {
                        case 1:
                            killer.getWorld().playSound(killer.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.9F, 1.7936507F);
                        case 2:
                            killer.getWorld().playSound(killer.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.9F, 1.8253968F);
                        case 3:
                            killer.getWorld().playSound(killer.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.9F, 1.8730159F);
                        case 4:
                            killer.getWorld().playSound(killer.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.9F, 1.9047619F);
                        case 5:
                            killer.getWorld().playSound(killer.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.9F, 1.9523809F);
                    }

                    if(count == 5){
                        cancel();
                    }
                }
            }.runTaskTimer(TheHypixelPitSurvival.getPlugin(), 1, 2);

            killer.sendMessage(ChatColor.GREEN.toString() + ChatColor.BOLD + "KILL! " + ChatColor.RESET + ChatColor.GRAY + "on " + entity.getName() + ChatColor.AQUA + " +" + chatXP + "XP" + ChatColor.GOLD + " +" +  formatter.format(gold) + "g");
        }
    }
}
