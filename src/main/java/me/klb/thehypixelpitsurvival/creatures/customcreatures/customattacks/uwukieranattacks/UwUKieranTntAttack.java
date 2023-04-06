package me.klb.thehypixelpitsurvival.creatures.customcreatures.customattacks.uwukieranattacks;

import me.klb.thehypixelpitsurvival.TheHypixelPitSurvival;
import me.klb.thehypixelpitsurvival.mystics.darkpants.functions.DarkPantsVenom;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.Random;

public class UwUKieranTntAttack {
    public static void spawnTnt(Player player){
        Location location = player.getLocation();
        World world = player.getWorld();

        TNTPrimed tnt = (TNTPrimed) world.spawnEntity(location.clone().add(0, 0.8, 0), EntityType.PRIMED_TNT);

        tnt.setFuseTicks(2 * 20);
        tnt.setVelocity(new Vector(0, 0.8, 0));
    }

    public static void runAttack(Player player, Runnable onFinish) {
        Random ran = new Random();

        final int[] counter = {0};
        int randomMessage = ran.nextInt(TheHypixelPitSurvival.getPlugin().getConfig().getStringList("UwUKieranTntAttackChatLines").size()); // Not returning message; only number

        player.sendMessage(ChatColor.YELLOW + TheHypixelPitSurvival.getPlugin().getConfig().getString("UwUKieranChatString") + ChatColor.WHITE + TheHypixelPitSurvival.getPlugin().getConfig().getStringList("UwUKieranTntAttackChatLines").get(randomMessage));

        new BukkitRunnable() {
            @Override
            public void run() {
                counter[0]++;

                spawnTnt(player);

                if(counter[0] == 10){

                    cancel();
                    onFinish.run(); // Run Lambda expression
                }
            }
        }.runTaskTimer(TheHypixelPitSurvival.getPlugin(), 0, 10);
    }
}
