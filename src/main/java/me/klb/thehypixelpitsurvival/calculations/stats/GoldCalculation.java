package me.klb.thehypixelpitsurvival.calculations.stats;

import org.bukkit.entity.Player;

import java.util.Random;

public class GoldCalculation {
    public static Double runGoldCalculation(Player player, Boolean useRandomGoldReward, Double entityGoldReward){
        int goldRewardInt;
        Double goldRewardDouble;

        if(useRandomGoldReward){
            Random random = new Random();
            goldRewardInt = random.nextInt(11) + 10; // Adds 10 to random number to make it 10-20
            goldRewardDouble = goldRewardInt + 0.00;
        }else{
            goldRewardDouble = entityGoldReward;
        }

        // Calculate the rest of the gold here

        return goldRewardDouble;
    }
}
