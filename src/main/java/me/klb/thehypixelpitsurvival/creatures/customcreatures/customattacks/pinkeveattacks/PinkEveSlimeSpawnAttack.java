package me.klb.thehypixelpitsurvival.creatures.customcreatures.customattacks.pinkeveattacks;

import me.klb.thehypixelpitsurvival.TheHypixelPitSurvival;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Slime;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Objects;
import java.util.Random;

public class PinkEveSlimeSpawnAttack {

    public static void createJimpy(Entity entity, Player player){
        World worldEntity = entity.getWorld();

        Entity slime = worldEntity.spawnEntity(entity.getLocation(), EntityType.SLIME);
        ((Slime) slime).setTarget(player);

        slime.setCustomName(ChatColor.GREEN + TheHypixelPitSurvival.getPlugin().getConfig().getString("PinkEveSlimeName"));
        slime.setCustomNameVisible(true);
        slime.setPersistent(false);

        ((Slime) slime).setSize(TheHypixelPitSurvival.getPlugin().getConfig().getInt("PinkEveSlimeSize"));

        ((Slime) slime).getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(100);
        ((Slime) slime).getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(10);
        ((Slime) slime).getAttribute(Attribute.GENERIC_FOLLOW_RANGE).setBaseValue(64);
        ((Slime) slime).addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 999999999, 3, false, false , false));
        ((Slime) slime).addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 999999999, 2, false, false, false));
        ((Slime) slime).setHealth(100);

        PersistentDataContainer data = slime.getPersistentDataContainer();
        data.set(new NamespacedKey(TheHypixelPitSurvival.getPlugin(), "CustomEntityPet"), PersistentDataType.STRING, "Slime");
    }
    public static void runAttack(Player player, Entity entity, Runnable onFinish) {
        int slimeCount = 0;
        World worldEntity = entity.getWorld();

        for (Slime slime : worldEntity.getEntitiesByClass(Slime.class)) {
            PersistentDataContainer data = slime.getPersistentDataContainer();

            if (data.has(new NamespacedKey(TheHypixelPitSurvival.getPlugin(), "CustomEntityPet"), PersistentDataType.STRING)
                    && Objects.equals(data.get(new NamespacedKey(TheHypixelPitSurvival.getPlugin(), "CustomEntityPet"), PersistentDataType.STRING), "Slime")) {
                slimeCount++;

                if (slimeCount >= TheHypixelPitSurvival.getPlugin().getConfig().getInt("PinkEveMaxJimpys")) {
                    onFinish.run();
                    return;
                }
            }
        }

        Random ran = new Random();
        int randomMessage = ran.nextInt(TheHypixelPitSurvival.getPlugin().getConfig().getStringList("PinkEveSpawnSlimeChatLines").size()); // Not returning message; only number

        player.sendMessage(ChatColor.AQUA + TheHypixelPitSurvival.getPlugin().getConfig().getString("PinkEveChatString") + ChatColor.WHITE + TheHypixelPitSurvival.getPlugin().getConfig().getStringList("PinkEveSpawnSlimeChatLines").get(randomMessage));

        new BukkitRunnable() {
            @Override
            public void run() {
                createJimpy(entity, player);

                cancel();
                onFinish.run(); // Run Lambda expression
            }
        }.runTaskLater(TheHypixelPitSurvival.getPlugin(), 0);
    }
}
