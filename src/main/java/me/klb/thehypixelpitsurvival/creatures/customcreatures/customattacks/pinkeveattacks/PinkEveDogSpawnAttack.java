package me.klb.thehypixelpitsurvival.creatures.customcreatures.customattacks.pinkeveattacks;

import me.klb.thehypixelpitsurvival.TheHypixelPitSurvival;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.*;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Objects;
import java.util.Random;

public class PinkEveDogSpawnAttack {

    public static Entity createWolf(Entity entity, Player player){
        World worldEntity = entity.getWorld();

        Entity wolf = worldEntity.spawnEntity(entity.getLocation(), EntityType.WOLF);
        ((Wolf) wolf).setTarget(player);

        Random ran = new Random();
        int randomName = ran.nextInt(TheHypixelPitSurvival.getPlugin().getConfig().getStringList("PinkEveDogNames").size()); // Not returning message; only number
        String wolfName = TheHypixelPitSurvival.getPlugin().getConfig().getStringList("PinkEveDogNames").get(randomName);

        wolf.setCustomName(ChatColor.AQUA + entity.getName() + "'s " + ChatColor.RED + wolfName);
        wolf.setCustomNameVisible(true);
        wolf.setPersistent(false); // This will make it despawn

        Objects.requireNonNull(((Wolf) wolf).getAttribute(Attribute.GENERIC_MAX_HEALTH)).setBaseValue(20);
        Objects.requireNonNull(((Wolf) wolf).getAttribute(Attribute.GENERIC_ATTACK_DAMAGE)).setBaseValue(60);
        ((Wolf) wolf).setHealth(20);

        PersistentDataContainer data = wolf.getPersistentDataContainer();
        data.set(new NamespacedKey(TheHypixelPitSurvival.getPlugin(), "CustomEntityPet"), PersistentDataType.STRING, "Wolf");

        return wolf;
    }
    public static void runAttack(Player player, Entity entity, Runnable onFinish) {
        final int[] counter = {0};
        int wolfCount = 0;
        World worldEntity = entity.getWorld();

        for (Wolf wolf : worldEntity.getEntitiesByClass(Wolf.class)) {
            PersistentDataContainer data = wolf.getPersistentDataContainer();

            if (data.has(new NamespacedKey(TheHypixelPitSurvival.getPlugin(), "CustomEntityPet"), PersistentDataType.STRING)
                    && Objects.equals(data.get(new NamespacedKey(TheHypixelPitSurvival.getPlugin(), "CustomEntityPet"), PersistentDataType.STRING), "Wolf")) {
                wolfCount++;

                if (wolfCount >= TheHypixelPitSurvival.getPlugin().getConfig().getInt("PinkEveMaxDogs")) {
                    onFinish.run();
                    return;
                }
            }
        }

        Random ran = new Random();
        int randomMessage = ran.nextInt(TheHypixelPitSurvival.getPlugin().getConfig().getStringList("PinkEveSpawnDogsChatLines").size()); // Not returning message; only number

        player.sendMessage(ChatColor.AQUA + TheHypixelPitSurvival.getPlugin().getConfig().getString("PinkEveChatString") + ChatColor.WHITE + TheHypixelPitSurvival.getPlugin().getConfig().getStringList("PinkEveSpawnDogsChatLines").get(randomMessage));

        new BukkitRunnable() {
            @Override
            public void run() {
                counter[0]++;

                Entity wolf = createWolf(entity, player);

                if(counter[0] == 3){

                    worldEntity.spawnParticle(Particle.EXPLOSION_HUGE, wolf.getLocation(), 5);
                    worldEntity.playSound(wolf.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 1 , 1.5F);

                    cancel();
                    onFinish.run(); // Run Lambda expression
                }
            }
        }.runTaskTimer(TheHypixelPitSurvival.getPlugin(), 0, 0);
    }
}
