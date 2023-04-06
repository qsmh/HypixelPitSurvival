package me.klb.thehypixelpitsurvival.creatures.customcreatures.customattacks.uwukieranattacks;

import me.klb.thehypixelpitsurvival.TheHypixelPitSurvival;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.data.BlockData;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Objects;
import java.util.Random;

public class UwUKieranPigmanAttack {

    static Random ran = new Random();

    public static void spawnPigmen(Player player, Entity entity){
        int randomName = ran.nextInt(TheHypixelPitSurvival.getPlugin().getConfig().getStringList("UwUKieranPigmanNames").size());

        String pigmanName = TheHypixelPitSurvival.getPlugin().getConfig().getStringList("UwUKieranPigmanNames").get(randomName);

        World world = entity.getWorld();
        LivingEntity pigman = (LivingEntity) world.spawnEntity(entity.getLocation(), EntityType.ZOMBIFIED_PIGLIN);

        ItemStack Chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
        ItemStack Leggings = new ItemStack(Material.LEATHER_LEGGINGS);
        ItemStack Boots = new ItemStack(Material.LEATHER_BOOTS);

        ItemStack Air = new ItemStack(Material.AIR);
        ItemMeta AirMeta = Air.getItemMeta();

        Air.setItemMeta(AirMeta);
        Objects.requireNonNull(pigman.getEquipment()).setItemInMainHand(Air);

        ItemStack[] armor = new ItemStack[]{Chestplate, Leggings, Boots};

        for(ItemStack i : armor){
            ItemMeta itemMeta = i.getItemMeta();
            LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) itemMeta;

            assert itemMeta != null;
            itemMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 8, true);
            itemMeta.addEnchant(Enchantment.THORNS, 5, true);
            leatherArmorMeta.setColor(Color.WHITE);
            itemMeta.setUnbreakable(true);

            i.setItemMeta(itemMeta);
        }

        pigman.getEquipment().setChestplate(Chestplate);
        pigman.getEquipment().setLeggings(Leggings);
        pigman.getEquipment().setBoots(Boots);

        Objects.requireNonNull(pigman.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED)).setBaseValue(0.50);
        Objects.requireNonNull(pigman.getAttribute(Attribute.GENERIC_MAX_HEALTH)).setBaseValue(20);
        Objects.requireNonNull(pigman.getAttribute(Attribute.GENERIC_FOLLOW_RANGE)).setBaseValue(128);
        Objects.requireNonNull(pigman.getAttribute(Attribute.GENERIC_ATTACK_KNOCKBACK)).setBaseValue(15);
        pigman.setHealth(20);

        pigman.setCustomName(ChatColor.WHITE + "Bodyguard " + pigmanName);
        pigman.setCustomNameVisible(true);
        pigman.setPersistent(false);

        PersistentDataContainer data = pigman.getPersistentDataContainer();
        data.set(new NamespacedKey(TheHypixelPitSurvival.getPlugin(), "CustomEntityPet"), PersistentDataType.STRING, "Pigman");

        int count = 200;
        float offsetX = 2f;
        float offsetY = 2f;
        float offsetZ = 2f;
        float speed = 0.1f;
        Particle deathParticle = Particle.BLOCK_DUST;
        BlockData deathData = Material.SMOOTH_QUARTZ.createBlockData();
        world.spawnParticle(deathParticle, pigman.getLocation(), count, offsetX, offsetY, offsetZ, speed, deathData);
        world.playSound(pigman.getLocation(), Sound.BLOCK_STONE_PLACE, 5F, 0.9F);

        ((PigZombie) pigman).setTarget(player);
    }
    public static void runAttack(Player player, Entity entity, Runnable onFinish) {
        int PigmanCount = 0;
        World worldEntity = entity.getWorld();

        for (PigZombie Pigman : worldEntity.getEntitiesByClass(PigZombie.class)) {
            PersistentDataContainer data = Pigman.getPersistentDataContainer();

            if (data.has(new NamespacedKey(TheHypixelPitSurvival.getPlugin(), "CustomEntityPet"), PersistentDataType.STRING)
                    && Objects.equals(data.get(new NamespacedKey(TheHypixelPitSurvival.getPlugin(), "CustomEntityPet"), PersistentDataType.STRING), "Pigman")) {
                PigmanCount++;

                if (PigmanCount >= TheHypixelPitSurvival.getPlugin().getConfig().getInt("UwUKieranMaxBodyGuards")) {
                    onFinish.run();
                    return;
                }
            }
        }

        final int[] counter = {0};

        int randomMessage = ran.nextInt(TheHypixelPitSurvival.getPlugin().getConfig().getStringList("UwUKieranPigmanAttackChatLines").size()); // Not returning message; only number

        player.sendMessage(ChatColor.YELLOW + TheHypixelPitSurvival.getPlugin().getConfig().getString("UwUKieranChatString") + ChatColor.WHITE + TheHypixelPitSurvival.getPlugin().getConfig().getStringList("UwUKieranPigmanAttackChatLines").get(randomMessage));

        new BukkitRunnable() {
            @Override
            public void run() {
                counter[0]++;
                spawnPigmen(player, entity);

                if(counter[0] == 2){

                    cancel();
                    onFinish.run(); // Run Lambda expression
                }
            }
        }.runTaskTimer(TheHypixelPitSurvival.getPlugin(), 0, 0);
    }
}
