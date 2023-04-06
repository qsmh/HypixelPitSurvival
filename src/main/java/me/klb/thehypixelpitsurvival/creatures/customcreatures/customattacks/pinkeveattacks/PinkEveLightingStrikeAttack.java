package me.klb.thehypixelpitsurvival.creatures.customcreatures.customattacks.pinkeveattacks;

import me.klb.thehypixelpitsurvival.TheHypixelPitSurvival;
import me.klb.thehypixelpitsurvival.calculations.damage.TrueDamage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

public class PinkEveLightingStrikeAttack {
    public static void runAttack(Player player, Runnable onFinish) {
        final int[] counter = {0};

        World world = player.getWorld();

        Random ran = new Random();
        int randomMessage = ran.nextInt(TheHypixelPitSurvival.getPlugin().getConfig().getStringList("PinkEveStrikeLightingChatLines").size()); // Not returning message; only number

        player.sendMessage(ChatColor.AQUA + TheHypixelPitSurvival.getPlugin().getConfig().getString("PinkEveChatString") + ChatColor.WHITE + TheHypixelPitSurvival.getPlugin().getConfig().getStringList("PinkEveStrikeLightingChatLines").get(randomMessage));

        new BukkitRunnable() {
            @Override
            public void run() {
                counter[0]++;
                TrueDamage.dealTrueDamage(player, TheHypixelPitSurvival.getPlugin().getConfig().getDouble("PinkEveLightingStrikeDamage"));

                world.strikeLightningEffect(player.getLocation());
                world.playSound(player.getLocation(), Sound.ENTITY_LIGHTNING_BOLT_THUNDER, 1, 1);
                if(counter[0] == 3){

                    cancel();
                    onFinish.run(); // Run Lambda expression
                }
            }
        }.runTaskTimer(TheHypixelPitSurvival.getPlugin(), 0, 11);
    }
}
