package me.klb.thehypixelpitsurvival.mystics.darkpants.functions;

import me.klb.thehypixelpitsurvival.TheHypixelPitSurvival;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class DarkPantsVenom implements Listener {

    final private static int VenomTime = TheHypixelPitSurvival.getPlugin().getConfig().getInt("VenomTime");
    public static void venomPlayer(Player player){
        PersistentDataContainer data = player.getPersistentDataContainer();

        if (data.has(new NamespacedKey(TheHypixelPitSurvival.getPlugin(), "Venomed"), PersistentDataType.STRING)){
            return;
        }

        data.set(new NamespacedKey(TheHypixelPitSurvival.getPlugin(), "Venomed"), PersistentDataType.STRING, "true");

        player.addPotionEffect(new PotionEffect(PotionEffectType.POISON, VenomTime * 20, 0, false, false, false));
        player.sendMessage(ChatColor.GREEN.toString() + ChatColor.BOLD + "POISONED! " + ChatColor.RESET + ChatColor.GRAY + "Your mystic items are ineffective!");

        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_SPIDER_AMBIENT, 1F, 2F);

        new BukkitRunnable(){

            @Override
            public void run() {
                data.remove(new NamespacedKey(TheHypixelPitSurvival.getPlugin(), "Venomed"));
            }
        }.runTaskLater(TheHypixelPitSurvival.getPlugin(), VenomTime * 20L);
    }
    @EventHandler
    public void onPoisonDamage(EntityDamageEvent event){
        Entity entity = event.getEntity();

        if(!(entity instanceof Player)){
            return;
        }

        if(event.getCause() == EntityDamageEvent.DamageCause.POISON){
            PersistentDataContainer data = entity.getPersistentDataContainer();

            if (data.has(new NamespacedKey(TheHypixelPitSurvival.getPlugin(), "Venomed"), PersistentDataType.STRING)){
                event.setCancelled(true);
            }
        }
    }
}
