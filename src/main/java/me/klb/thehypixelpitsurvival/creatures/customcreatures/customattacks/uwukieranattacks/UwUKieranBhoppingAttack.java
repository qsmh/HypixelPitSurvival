package me.klb.thehypixelpitsurvival.creatures.customcreatures.customattacks.uwukieranattacks;

import me.klb.thehypixelpitsurvival.TheHypixelPitSurvival;
import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

public class UwUKieranBhoppingAttack {
    public static void runAttack(Player player, Entity entity, Runnable onFinish) {
        Random ran = new Random();
        int randomMessage = ran.nextInt(TheHypixelPitSurvival.getPlugin().getConfig().getStringList("UwUKieranBhopAttackChatLines").size()); // Not returning message; only number

        player.sendMessage(ChatColor.YELLOW + TheHypixelPitSurvival.getPlugin().getConfig().getString("UwUKieranChatString") + ChatColor.WHITE + TheHypixelPitSurvival.getPlugin().getConfig().getStringList("UwUKieranBhopAttackChatLines").get(randomMessage));

        LivingEntity livingEntity = (LivingEntity) entity;

        livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, TheHypixelPitSurvival.getPlugin().getConfig().getInt("UwUKieranBhopTime") * 20, 7, false, false, false));
        livingEntity.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE).setBaseValue(5.0);

        new BukkitRunnable() {
            @Override
            public void run() {
                livingEntity.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE).setBaseValue(livingEntity.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE).getDefaultValue());

                cancel();
                onFinish.run();
            }
        }.runTaskTimer(TheHypixelPitSurvival.getPlugin(), TheHypixelPitSurvival.getPlugin().getConfig().getInt("UwUKieranBhopTime") * 20L, 0);
    }
}
