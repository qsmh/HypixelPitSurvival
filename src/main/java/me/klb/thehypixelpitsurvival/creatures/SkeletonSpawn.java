package me.klb.thehypixelpitsurvival.creatures;

import me.klb.thehypixelpitsurvival.TheHypixelPitSurvival;
import me.klb.thehypixelpitsurvival.creatures.customcreatures.PinkEve;
import me.klb.thehypixelpitsurvival.creatures.customcreatures.UwUKieran;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Skeleton;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Objects;
import java.util.Random;

public class SkeletonSpawn {
    public static void runSkeletonSpawn(CreatureSpawnEvent event){
        int chaimmailSkeleton = 70;
        int ironSkeleton = 40;

        Skeleton entity = (Skeleton) event.getEntity();

        Random random = new Random();
        int randomNumber = random.nextInt(141); // Random number 0-140

        if(randomNumber == 140){ // CHECKING FOR PINKEVE
            int randomNumber2 = random.nextInt(10); // Random number 0-9

            switch (randomNumber2){
                case 9:
                    PinkEve.SpawnPinkEve(entity);
                    return;
                case 0:
                    UwUKieran.SpawnUwUKieran(entity);
                    return;
            }
        }

        if(randomNumber < chaimmailSkeleton){
            Objects.requireNonNull(entity.getEquipment()).setHelmet(new ItemStack(Material.CHAINMAIL_HELMET));
            entity.getEquipment().setChestplate(new ItemStack(Material.CHAINMAIL_CHESTPLATE));
            entity.getEquipment().setLeggings(new ItemStack(Material.CHAINMAIL_LEGGINGS));
            entity.getEquipment().setBoots(new ItemStack(Material.CHAINMAIL_BOOTS));

            if(randomNumber > ironSkeleton && randomNumber < chaimmailSkeleton - 5){
                ItemStack Bow = new ItemStack(Material.BOW);
                ItemMeta Meta = Bow.getItemMeta();

                assert Meta != null;
                Meta.addEnchant(Enchantment.ARROW_DAMAGE, 1, false);

                Bow.setItemMeta(Meta);
                entity.getEquipment().setItemInMainHand(Bow);
            }

            if(randomNumber > ironSkeleton && randomNumber < chaimmailSkeleton - 15){
                ItemStack Bow = new ItemStack(Material.BOW);
                ItemMeta Meta = Bow.getItemMeta();

                assert Meta != null;
                Meta.addEnchant(Enchantment.ARROW_DAMAGE, 2, false);
                Meta.addEnchant(Enchantment.ARROW_KNOCKBACK, 1, false);

                Bow.setItemMeta(Meta);
                entity.getEquipment().setItemInMainHand(Bow);
            }

        }

        if(randomNumber < ironSkeleton){
            entity.getEquipment().setHelmet(new ItemStack(Material.IRON_HELMET));
            entity.getEquipment().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
            entity.getEquipment().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
            entity.getEquipment().setBoots(new ItemStack(Material.IRON_BOOTS));

            PersistentDataContainer data1 = entity.getPersistentDataContainer();
            data1.set(new NamespacedKey(TheHypixelPitSurvival.getPlugin(), "GoldReward"), PersistentDataType.DOUBLE, TheHypixelPitSurvival.getPlugin().getConfig().getDouble("ironSkeletonGoldReward"));
            data1.set(new NamespacedKey(TheHypixelPitSurvival.getPlugin(), "XPReward"), PersistentDataType.DOUBLE, TheHypixelPitSurvival.getPlugin().getConfig().getDouble("ironSkeletonXPReward"));

            if(randomNumber < ironSkeleton - 5){
                ItemStack Bow = new ItemStack(Material.BOW);
                ItemMeta Meta = Bow.getItemMeta();

                assert Meta != null;
                Meta.addEnchant(Enchantment.ARROW_DAMAGE, 2, false);
                Meta.addEnchant(Enchantment.ARROW_KNOCKBACK, 2, false);

                Bow.setItemMeta(Meta);
                entity.getEquipment().setItemInMainHand(Bow);
            }

            if(randomNumber < ironSkeleton - 15){
                ItemStack Bow = new ItemStack(Material.BOW);
                ItemMeta Meta = Bow.getItemMeta();

                assert Meta != null;
                Meta.addEnchant(Enchantment.ARROW_DAMAGE, 3, false);
                Meta.addEnchant(Enchantment.ARROW_KNOCKBACK, 2, false);
                Meta.addEnchant(Enchantment.ARROW_FIRE, 1, false);

                Bow.setItemMeta(Meta);
                entity.getEquipment().setItemInMainHand(Bow);
            }

            if(randomNumber < ironSkeleton - 20){
                ItemStack Bow = new ItemStack(Material.BOW);
                ItemMeta Meta = Bow.getItemMeta();

                assert Meta != null;
                Meta.addEnchant(Enchantment.ARROW_DAMAGE, 3, false);
                Meta.addEnchant(Enchantment.ARROW_KNOCKBACK, 2, false);
                Meta.addEnchant(Enchantment.ARROW_FIRE, 2, false);

                Bow.setItemMeta(Meta);
                entity.getEquipment().setItemInMainHand(Bow);
            }

            if(randomNumber < ironSkeleton - 25){
                entity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 10000000 * 20, 0));
            }

            if(randomNumber < ironSkeleton - 30){
                ItemStack Bow = new ItemStack(Material.BOW);
                ItemMeta Meta = Bow.getItemMeta();

                assert Meta != null;
                Meta.addEnchant(Enchantment.ARROW_DAMAGE, 4, false);
                Meta.addEnchant(Enchantment.ARROW_KNOCKBACK, 2, false);
                Meta.addEnchant(Enchantment.ARROW_FIRE, 2, false);

                Bow.setItemMeta(Meta);
                entity.getEquipment().setItemInMainHand(Bow);
                entity.getEquipment().setBoots(new ItemStack(Material.DIAMOND_BOOTS));

                PersistentDataContainer data2 = entity.getPersistentDataContainer();
                data1.set(new NamespacedKey(TheHypixelPitSurvival.getPlugin(), "XPReward"), PersistentDataType.DOUBLE, TheHypixelPitSurvival.getPlugin().getConfig().getDouble("1/4diamondSkeletonXPReward"));
                data2.set(new NamespacedKey(TheHypixelPitSurvival.getPlugin(), "GoldReward"), PersistentDataType.DOUBLE, TheHypixelPitSurvival.getPlugin().getConfig().getDouble("1/4diamondSkeletonGoldReward"));
            }

            if(randomNumber < ironSkeleton - 35){
                entity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 10000000 * 20, 1));
                entity.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, 10000000 * 20, 1));
            }
        }
    }
}
