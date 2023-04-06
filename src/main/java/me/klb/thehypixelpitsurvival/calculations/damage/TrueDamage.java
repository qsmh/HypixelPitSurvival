package me.klb.thehypixelpitsurvival.calculations.damage;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

public class TrueDamage {
    public static void dealTrueDamage(Entity entity, Double damageAmount){
        LivingEntity livingEntity = (LivingEntity) entity;

        if (livingEntity.getHealth() - damageAmount < 1){
            livingEntity.setHealth(0);
            return;
        }

        livingEntity.damage(damageAmount);
    }
}
