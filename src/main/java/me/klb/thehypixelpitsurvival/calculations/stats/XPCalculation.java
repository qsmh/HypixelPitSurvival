package me.klb.thehypixelpitsurvival.calculations.stats;

import org.bukkit.entity.Player;

import java.util.Random;

public class XPCalculation {
    public static Double runXPCalculation(Player player, Boolean useRandomXPReward, Double entityXPReward){
        int xpRewardInt;
        Double xpRewardDouble;

        if(useRandomXPReward){
            Random random = new Random();
            xpRewardInt = random.nextInt(11) + 7; // Adds 7 to random number to make it 7-17
            xpRewardDouble = xpRewardInt + 0.00;
        }else{
            xpRewardDouble = entityXPReward;
        }

        // Calculate the rest of the gold here

        return xpRewardDouble;
    }
}
