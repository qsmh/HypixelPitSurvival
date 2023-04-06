package me.klb.thehypixelpitsurvival.creatures.customcreatures.customattacks.pinkeveattacks;

import me.klb.thehypixelpitsurvival.TheHypixelPitSurvival;
import org.bukkit.ChatColor;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

public class PinkEvePotionAttack {
    public static void runAttack(Player player, LivingEntity entity, Runnable onFinish) {

        Random ran = new Random();
        int randomPotion = ran.nextInt(2); // 0-1

        new BukkitRunnable() {
            @Override
            public void run() {
                switch (randomPotion){
                    case 0:
                        entity.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 10 * 20, 5 , true, true, true));
                        player.sendMessage(ChatColor.AQUA + TheHypixelPitSurvival.getPlugin().getConfig().getString("PinkEveChatString") + ChatColor.WHITE + TheHypixelPitSurvival.getPlugin().getConfig().getStringList("PinkEvePotionAttackChatLines").get(0));
                        break;
                    case 1:
                        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 7 * 20, 3 , true, true, true));
                        player.sendMessage(ChatColor.AQUA + TheHypixelPitSurvival.getPlugin().getConfig().getString("PinkEveChatString") + ChatColor.WHITE + TheHypixelPitSurvival.getPlugin().getConfig().getStringList("PinkEvePotionAttackChatLines").get(1));
                        break;
                }

                cancel();
                onFinish.run(); // Run Lambda expression
            }
        }.runTaskTimer(TheHypixelPitSurvival.getPlugin(), 0, 11);
    }
}
