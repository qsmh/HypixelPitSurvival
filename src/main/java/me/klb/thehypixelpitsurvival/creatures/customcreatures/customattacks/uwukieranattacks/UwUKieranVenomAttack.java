package me.klb.thehypixelpitsurvival.creatures.customcreatures.customattacks.uwukieranattacks;

import me.klb.thehypixelpitsurvival.TheHypixelPitSurvival;
import me.klb.thehypixelpitsurvival.mystics.darkpants.functions.DarkPantsVenom;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

public class UwUKieranVenomAttack {
    public static void runAttack(Player player, Runnable onFinish) {
        Random ran = new Random();
        int randomMessage = ran.nextInt(TheHypixelPitSurvival.getPlugin().getConfig().getStringList("UwUKieranVenomChatLines").size()); // Not returning message; only number

        player.sendMessage(ChatColor.YELLOW + TheHypixelPitSurvival.getPlugin().getConfig().getString("UwUKieranChatString") + ChatColor.WHITE + TheHypixelPitSurvival.getPlugin().getConfig().getStringList("UwUKieranVenomChatLines").get(randomMessage));

        new BukkitRunnable() {
            @Override
            public void run() {
                DarkPantsVenom.venomPlayer(player);

                cancel();
                onFinish.run();
            }
        }.runTaskTimer(TheHypixelPitSurvival.getPlugin(), 0, 0);
    }
}
